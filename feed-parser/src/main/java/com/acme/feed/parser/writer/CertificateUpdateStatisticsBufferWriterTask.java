package com.acme.feed.parser.writer;

import com.acme.feed.parser.model.CertificateStatistic;

import java.nio.ByteBuffer;
import java.util.List;
import java.util.concurrent.Callable;

import static com.acme.feed.common.Constants.codingScheme;

/**
 * User: Tarik Sassi
 * Date: 12/30/12
 * Time: 5:09 PM
 */
class CertificateUpdateStatisticsBufferWriterTask implements Callable<Void> {
	private final ByteBuffer buffer;
	private final List<CertificateStatistic> statistics;

	public CertificateUpdateStatisticsBufferWriterTask(ByteBuffer buffer,
	                                                   List<CertificateStatistic> partition) {
		this.buffer = buffer;
		statistics = partition;
	}

	@Override
	public Void call() throws Exception {
		StringBuilder builder = new StringBuilder();
		for (CertificateStatistic statistic : statistics) {
			builder.append("\n\t<Certificate isin=\"");
			builder.append(statistic.getIsin());
			builder.append("\" numOfUpdates=\"");
			builder.append(statistic.getSize());
			builder.append("\" avgBidPrice=\"");
			builder.append(statistic.getAvgBidPrice().toPlainString());
			builder.append("\" avgAskPrice=\"");
			builder.append(statistic.getAvgAskPrice().toPlainString());
			builder.append("\"/>");
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
