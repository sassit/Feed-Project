package com.acme.feed.parser.writer;

import com.acme.feed.parser.ParserException;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

import static com.acme.feed.common.Constants.codingScheme;

/**
 * User: Tarik Sassi
 * Date: 12/29/12
 * Time: 10:38 PM
 */
abstract class BufferWriter<T extends List> implements Writer<T> {
	private final ExecutorService executor;
	private final File file;
	final int numOfThreads;

	BufferWriter(ExecutorService executor, File file, int numOfThreads) {
		this.executor = executor;
		this.file = file;
		this.numOfThreads = numOfThreads;
	}

	@Override
	public void write(final T list) {
		RandomAccessFileWriterTemplate template = new RandomAccessFileWriterTemplate(file) {
			@Override
			public void write(ByteBuffer buffer) {
				try {
					writeRootElement(buffer, list);
					if (list.size() != 0)
						writeBody(buffer, list);
					writeEndOfDocument(buffer);
				} catch (Exception e) {
					throw new ParserException("Could not write files: ", e);
				}
			}
		};
		template.write();
	}

	private void writeBody(ByteBuffer buffer, T list) throws Exception {
		List<Callable<Void>> writers = createWriters(buffer, list);
		for (Callable<Void> writer : writers) {
			executor.submit(writer).get(5, TimeUnit.SECONDS);
		}
	}

	void lockAndWriteToBuffer(ByteBuffer buffer, String end) throws UnsupportedEncodingException {
		byte[] bytes = end.getBytes(codingScheme);
		synchronized (buffer) {
			buffer.put(bytes);
		}
	}

	public abstract List<Callable<Void>> createWriters(ByteBuffer buffer, T list);

	public abstract void writeRootElement(ByteBuffer buffer, T list) throws Exception;

	public abstract void writeEndOfDocument(ByteBuffer buffer) throws Exception;
}


