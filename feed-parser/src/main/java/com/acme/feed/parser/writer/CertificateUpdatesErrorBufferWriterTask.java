package com.acme.feed.parser.writer;

import com.acme.feed.parser.model.CertificateUpdateReport;

import java.nio.ByteBuffer;
import java.util.List;
import java.util.concurrent.Callable;

import static com.acme.feed.common.Constants.codingScheme;

/**
 * User: Tarik Sassi
 * Date: 12/30/12
 * Time: 1:28 PM
 */
class CertificateUpdatesErrorBufferWriterTask implements Callable<Void> {
	private final ByteBuffer buffer;
	private final List<CertificateUpdateReport> reports;

	public CertificateUpdatesErrorBufferWriterTask(ByteBuffer buffer, List<CertificateUpdateReport> reports) {
		this.buffer = buffer;
		this.reports = reports;
	}

	@Override
	public Void call() throws Exception {
		StringBuilder builder = new StringBuilder();
		for (CertificateUpdateReport report : reports) {
			builder.append("\n\t<Details\n\t\tfeedLine=\"");
			builder.append(report.getCsv());
			builder.append("\"\n\t\texception=\"");
			builder.append(report.getErrorReport());
			builder.append("\"\n\t/>");
			String line = builder.toString();
			byte[] bytes = line.getBytes(codingScheme);
			synchronized (buffer) {
				buffer.put(bytes);
			}
			builder.delete(0, builder.length());
		}
		return null;
	}
}
