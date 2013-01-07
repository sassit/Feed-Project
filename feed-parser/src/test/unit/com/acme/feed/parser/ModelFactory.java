package com.acme.feed.parser;

import com.acme.feed.common.calculator.IsinCheckDigitCalculator;
import com.acme.feed.common.model.CertificateUpdate;
import com.acme.feed.parser.mapper.ItemMapper;
import com.acme.feed.parser.mapper.ValidatingCsvItemToCertUpdateReportMapper;
import com.acme.feed.parser.model.CertificateUpdateReport;
import com.acme.feed.parser.model.CsvItem;
import com.acme.feed.parser.reader.CsvLineReader;
import com.acme.feed.parser.reader.LineReader;

import java.util.*;

import static com.acme.feed.common.Constants.csvDelimiter;
import static com.acme.feed.parser.FeedParserTestConstants.mappingTable;

/**
 * User: Tarik Sassi
 * Date: 12/29/12
 * Time: 11:01 PM
 */
public class ModelFactory {

	public static CsvItem createCsvItem(String csvLine) {
		LineReader<CsvItem> reader = new CsvLineReader(new Scanner(csvLine), csvDelimiter);
		return reader.nextLine();
	}

	public static List<CsvItem> createCsvItems(String csvLine) {
		return Collections.singletonList(createCsvItem(csvLine));
	}

	public static List<CsvItem> createCsvItems(String[] csvLines) {
		List<CsvItem> csvItems = new ArrayList<CsvItem>();
		for (int i = 0; i < csvLines.length; i++) {
			csvItems.add(createCsvItem(csvLines[i]));
		}
		return csvItems;
	}

	public static List<CertificateUpdate> createCertificateUpdates(String csvLine) {
		return createCertificateUpdates(new String[]{csvLine});
	}

	public static List<CertificateUpdate> createCertificateUpdates(String[] csvLines) {
		List<CsvItem> csvItems = createCsvItems(csvLines);
		List<CertificateUpdate> updates = new ArrayList<CertificateUpdate>();
		for (CsvItem csvItem : csvItems) {
			CertificateUpdateReport report = createCertificateUpdateReport(csvItem);
			if (report.isValid()) {
				updates.add(report.getCertificateUpdate());
			}
		}
		return updates;
	}

	public static CertificateUpdateReport createCertificateUpdateReport(CsvItem csvItem) {
		IsinCheckDigitCalculator calculator = new IsinCheckDigitCalculator(mappingTable);
		ItemMapper<CertificateUpdateReport, CsvItem> mapper
				= new ValidatingCsvItemToCertUpdateReportMapper(calculator);
		return mapper.mapItem(csvItem);
	}

	public static List<CertificateUpdateReport> createCertificateUpdateReports(String csvLine) {
		return Collections.singletonList(createCertificateUpdateReport(csvLine));
	}

	public static Map<String, List<CertificateUpdate>> createCertificateUpdateReportBuckets
			(List<CertificateUpdate> updates) {
		Map<String, List<CertificateUpdate>> buckets = new HashMap<String, List<CertificateUpdate>>();
		buckets.put(updates.get(0).getIsin(), updates);
		return buckets;
	}

	public static List<CertificateUpdateReport> createCertificateUpdateReports(CsvItem csvItem) {
		return Collections.singletonList(createCertificateUpdateReport(csvItem));
	}

	public static List<CertificateUpdateReport> createCertificateUpdateReports(String[] csvLines) {
		List<CertificateUpdateReport> reports = new ArrayList<CertificateUpdateReport>();
		for (int i = 0; i < csvLines.length; i++) {
			reports.add(createCertificateUpdateReport(csvLines[i]));
		}
		return reports;
	}

	private static CertificateUpdateReport createCertificateUpdateReport(String csvLine) {
		return createCertificateUpdateReport(createCsvItem(csvLine));
	}
}
