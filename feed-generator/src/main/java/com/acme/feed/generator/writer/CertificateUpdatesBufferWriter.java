package com.acme.feed.generator.writer;

import com.acme.feed.common.model.CertificateUpdate;

import java.io.File;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * User: Tarik Sassi
 * Date: 12/27/12
 * Time: 3:41 PM
 */
public class CertificateUpdatesBufferWriter implements Writer<List<CertificateUpdate>> {
	private final ExecutorService executor;
	private final File file;

	public CertificateUpdatesBufferWriter(ExecutorService executor, File file) {
		this.executor = executor;
		this.file = file;
	}

	@Override
	public void write(final List<CertificateUpdate> updates) {
		RandomAccessFileWriterTemplate template = new RandomAccessFileWriterTemplate(file) {
			@Override
			public void write(ByteBuffer buffer) throws Exception {
				List<CertificateUpdateBufferWriterTask> writers = createWriters(buffer, updates);
				List<Future<Void>> futures = executor.invokeAll(writers, 20, SECONDS);
				for (Future<Void> future : futures) {
					future.get(10, SECONDS);
				}
			}
		};
		template.write();
	}

	private List<CertificateUpdateBufferWriterTask> createWriters(ByteBuffer buffer, List<CertificateUpdate> updates) {
		List<CertificateUpdateBufferWriterTask> writers = new ArrayList<CertificateUpdateBufferWriterTask>();
		String isin = null;
		List<CertificateUpdate> partition = null;
		for (CertificateUpdate update : updates) {
			if (!update.getIsin().equals(isin)) {
				partition = new ArrayList<CertificateUpdate>();
				writers.add(new CertificateUpdateBufferWriterTask(buffer, partition));
			}
			partition.add(update);
			isin = update.getIsin();
		}
		return writers;
	}
}
