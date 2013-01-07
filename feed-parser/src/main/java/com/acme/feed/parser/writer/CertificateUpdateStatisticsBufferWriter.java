package com.acme.feed.parser.writer;

import com.acme.feed.parser.model.CertificateStatistic;

import java.io.File;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;

/**
 * User: Tarik Sassi
 * Date: 12/29/12
 * Time: 10:39 PM
 */
public class CertificateUpdateStatisticsBufferWriter extends BufferWriter<List<CertificateStatistic>> {

	public CertificateUpdateStatisticsBufferWriter(ExecutorService executor, File file, int numOfThreads) {
		super(executor, file, numOfThreads);
	}

	public List<Callable<Void>> createWriters(ByteBuffer buffer, List<CertificateStatistic> statistics) {
		List<Callable<Void>> writers = new ArrayList<Callable<Void>>();
		List<CertificateStatistic> partition = new ArrayList<CertificateStatistic>();
		int partitionSize = statistics.size() / numOfThreads * 2, i = 0;
		for (CertificateStatistic statistic : statistics) {
			partition.add(statistic);
			i++;
			if (i % partitionSize == 0 || i == statistics.size()) {
				writers.add(new CertificateUpdateStatisticsBufferWriterTask(buffer, partition));
				partition = new ArrayList<CertificateStatistic>();
			}
		}
		return writers;
	}

	public void writeRootElement(ByteBuffer buffer, List<CertificateStatistic> statistics) throws Exception {
		StringBuilder builder = new StringBuilder();
		builder.append("<PriceReport numOfCertificates=\"");
		builder.append(statistics.size());
		builder.append("\">");
		lockAndWriteToBuffer(buffer, builder.toString());
	}

	public void writeEndOfDocument(ByteBuffer buffer) throws Exception {
		String end = "\n</PriceReport>";
		lockAndWriteToBuffer(buffer, end);
	}
}
