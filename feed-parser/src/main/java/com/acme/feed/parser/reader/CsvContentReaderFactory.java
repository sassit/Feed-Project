package com.acme.feed.parser.reader;

import java.io.Reader;
import java.util.Scanner;

import static com.acme.feed.common.Constants.csvDelimiter;
import static com.acme.feed.common.Constants.lineDelimiter;
import static com.acme.feed.parser.Assert.assertNotNull;

/**
 * User: Tarik Sassi
 * Date: 12/28/12
 * Time: 8:57 PM
 */
public class CsvContentReaderFactory {
	public static CsvLineReader create(Reader reader) {
		assertNotNull(reader, "Reader is null.");
		Scanner lineScanner = new Scanner(reader);
		lineScanner.useDelimiter(lineDelimiter);
		return new CsvLineReader(lineScanner, csvDelimiter);
	}
}
