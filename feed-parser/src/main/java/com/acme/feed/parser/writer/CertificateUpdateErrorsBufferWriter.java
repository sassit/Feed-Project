package com.acme.feed.parser.writer;

import com.acme.feed.parser.model.CertificateUpdateReport;

import java.io.File;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;

/**
 * User: Tarik Sassi
 * Date: 12/30/12
 * Time: 9:34 PM
 */
public class CertificateUpdateErrorsBufferWriter extends BufferWriter<List<CertificateUpdateReport>> {
	public CertificateUpdateErrorsBufferWriter(ExecutorService executor, File file, int numOfThreads) {
		super(executor, file, numOfThreads);
	}

	@Override
	public List<Callable<Void>> createWriters(ByteBuffer buffer, List<CertificateUpdateReport> reports) {
		List<Callable<Void>> writers = new ArrayList<Callable<Void>>();
		List<CertificateUpdateReport> partition = new ArrayList<CertificateUpdateReport>();
		int partitionSize = reports.size() / numOfThreads * 2, i = 0;
		for (CertificateUpdateReport report : reports) {
			partition.add(report);
			i++;
			if (i % partitionSize == 0 || i == reports.size()) {
				writers.add(new CertificateUpdatesErrorBufferWriterTask(buffer, partition));
				partition = new ArrayList<CertificateUpdateReport>();
			}
		}
		return writers;
	}

	public void writeRootElement(ByteBuffer buffer, List<CertificateUpdateReport> reports) throws Exception {
		StringBuilder builder = new StringBuilder();
		builder.append("<ExceptionReport numOfExceptions=\"");
		builder.append(reports.size());
		builder.append("\">");
		lockAndWriteToBuffer(buffer, builder.toString());
	}

	public void writeEndOfDocument(ByteBuffer buffer) throws Exception {
		String end = "\n</ExceptionReport>";
		lockAndWriteToBuffer(buffer, end);
	}
}
