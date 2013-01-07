package com.acme.feed.parser;

import com.acme.feed.common.calculator.IsinCheckDigitCalculator;
import com.acme.feed.parser.calculator.CertificateUpdateStatisticsCalculator;
import com.acme.feed.parser.calculator.CertificateUpdatesStatisticAggregator;
import com.acme.feed.parser.filter.CertificateUpdateReportFilter;
import com.acme.feed.parser.mapper.ValidatingCsvItemToCertUpdateReportMapper;
import com.acme.feed.parser.provider.CertificateUpdateReportItemProvider;
import com.acme.feed.parser.reader.CsvContentReader;
import com.acme.feed.parser.reader.CsvContentReaderFactory;
import com.acme.feed.parser.reader.CsvLineReader;
import com.acme.feed.parser.sorter.CertificateUpdateByIsinSorter;
import com.acme.feed.parser.sorter.CertificateUpdateSorterChain;
import com.acme.feed.parser.sorter.CertificateUpdateToBucketSorter;
import com.acme.feed.parser.transformer.CertificateReportToUpdateTransformer;
import com.acme.feed.parser.writer.CertificateUpdateErrorsBufferWriter;
import com.acme.feed.parser.writer.CertificateUpdateStatisticsBufferWriter;
import com.acme.feed.parser.writer.SharedExecutorWriter;

import java.io.File;
import java.io.Reader;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * User: Tarik Sassi
 * Date: 12/29/12
 * Time: 10:26 PM
 */
class ParserAppFactory {

	public static ParserApp create(Properties properties, int numOfThreads, Reader reader, File report, File error
	                              ) throws Exception {
		// Shared executor
		ExecutorService executor = Executors.newFixedThreadPool(numOfThreads);
		// Share between writers
		CertificateUpdateStatisticsBufferWriter reportWriter = new CertificateUpdateStatisticsBufferWriter(executor,
		                                                                                               report,
		                                                                                               numOfThreads);
		CertificateUpdateErrorsBufferWriter errorWriter = new CertificateUpdateErrorsBufferWriter(executor,
		                                                                                      error,
		                                                                                      numOfThreads);
		SharedExecutorWriter writer = new SharedExecutorWriter(reportWriter, errorWriter, executor);
		// CsvReaders and validating mapper
		CsvLineReader lineReader = CsvContentReaderFactory.create(reader);
		CsvContentReader csvContentReader = new CsvContentReader(lineReader);
		IsinCheckDigitCalculator isinCalculator = new IsinCheckDigitCalculator(properties
				                                                                               .getProperty("mapping-table"));
		ValidatingCsvItemToCertUpdateReportMapper updateMapper = new ValidatingCsvItemToCertUpdateReportMapper
				                                                         (isinCalculator);
		// Actual provider of parsed content
		CertificateUpdateReportItemProvider reportProvider = new CertificateUpdateReportItemProvider(csvContentReader,
		                                                                                             updateMapper);
		// Transformation pipeline, filter invalid/valid -> sort by calculator -> sort to buckets by calculator
		CertificateUpdateReportFilter updateReportFilter = new CertificateUpdateReportFilter();
		CertificateUpdateByIsinSorter isinSorter = new CertificateUpdateByIsinSorter();
		CertificateUpdateToBucketSorter bucketSorter = new CertificateUpdateToBucketSorter();
		CertificateUpdateSorterChain sorterChain = new CertificateUpdateSorterChain(isinSorter, bucketSorter);
		// IsinCalculator for statistics per certificate calculator and an aggregator for results
		CertificateUpdateStatisticsCalculator calculator = new CertificateUpdateStatisticsCalculator();
		CertificateReportToUpdateTransformer transformer = new CertificateReportToUpdateTransformer();
		CertificateUpdatesStatisticAggregator aggregator = new CertificateUpdatesStatisticAggregator
				                                                   (sorterChain, transformer, calculator);
		return new ParserApp(updateReportFilter, reportProvider, writer, aggregator);
	}
}
