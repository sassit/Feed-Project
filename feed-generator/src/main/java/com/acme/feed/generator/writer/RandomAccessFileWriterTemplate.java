package com.acme.feed.generator.writer;

import com.acme.feed.generator.GeneratorException;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

import static com.acme.feed.common.Constants.MB;
import static java.nio.channels.FileChannel.MapMode.READ_WRITE;

/**
 * User: Tarik Sassi
 * Date: 12/28/12
 * Time: 2:38 PM
 */
public abstract class RandomAccessFileWriterTemplate {
	private final File file;

	public RandomAccessFileWriterTemplate(File file) {
		this.file = file;
	}

	public void write() {
		RandomAccessFile randomAccessFile = null;
		FileChannel channel = null;
		try {
			randomAccessFile = new RandomAccessFile(file, "rw");
			channel = randomAccessFile.getChannel();
			ByteBuffer buffer = channel.map(READ_WRITE, 0, 1536 * MB);
			write(buffer);
			channel.force(true);
			channel.truncate(buffer.position());
		} catch (Exception e) {
			throw new GeneratorException("Could not write the file:", e);
		} finally {
			if (channel != null) {
				try {
					channel.close();
					randomAccessFile.close();
				} catch (IOException e) {
					throw new GeneratorException("Could not close file.", e);
				}
			}
		}
	}

	public abstract void write(ByteBuffer buffer) throws Exception;
}
