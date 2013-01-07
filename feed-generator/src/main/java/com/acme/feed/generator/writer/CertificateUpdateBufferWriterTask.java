package com.acme.feed.generator.writer;

import com.acme.feed.common.model.CertificateUpdate;

import java.nio.ByteBuffer;
import java.util.List;
import java.util.concurrent.Callable;

import static com.acme.feed.common.Constants.*;

public class CertificateUpdateBufferWriterTask implements Callable<Void> {
	private final ByteBuffer buffer;
	private final List<CertificateUpdate> updates;

	public CertificateUpdateBufferWriterTask(ByteBuffer buffer,
	                                         List<CertificateUpdate> updates) {
		this.buffer = buffer;
		this.updates = updates;
	}

	@Override
	public Void call() throws Exception {
		for (CertificateUpdate update : updates) {
			String csvRow = mapRow(update);
			byte[] bytes = csvRow.getBytes(codingScheme);
			synchronized (buffer) { buffer.put(bytes);}
		}
		return null;
	}

	private String mapRow(CertificateUpdate update) {
		StringBuilder builder = new StringBuilder();
		builder.append(update.getTimestamp());
		builder.append(csvDelimiter);
		builder.append(update.getIsin());
		builder.append(csvDelimiter);
		builder.append(update.getCurrency());
		builder.append(csvDelimiter);
		builder.append(update.getBidPrice());
		builder.append(csvDelimiter);
		builder.append(update.getBidSize());
		builder.append(csvDelimiter);
		builder.append(update.getAskPrice());
		builder.append(csvDelimiter);
		builder.append(update.getAskSize());
		builder.append(lineDelimiter);
		return builder.toString();
	}
}
