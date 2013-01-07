package com.acme.feed.parser.writer;

import com.acme.feed.parser.ParserException;

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
abstract class RandomAccessFileWriterTemplate implements Writer<ByteBuffer>{
	private final File file;

	public RandomAccessFileWriterTemplate(File file) {
		this.file = file;
	}

	public void write() {
		RandomAccessFile randomAccessFile;
		FileChannel channel = null;
		try {
			randomAccessFile = new RandomAccessFile(file, "rw");
			channel = randomAccessFile.getChannel();
			ByteBuffer buffer = channel.map(READ_WRITE, 0, 512 * MB);
			write(buffer);
			channel.force(true);
			channel.truncate(buffer.position());
		} catch (Exception e) {
			throw new ParserException("Could not write the file:", e);
		} finally {
			if (channel != null) {
				try {
					channel.close();
				} catch (IOException e) {
					throw new ParserException("Could not close file.", e);
				}
			}
		}
	}
}
