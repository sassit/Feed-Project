package com.acme.feed.parser.reader;


import com.acme.feed.parser.model.CsvItem;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static com.acme.feed.parser.Assert.assertNotNull;

/**
 * User: Tarik Sassi
 * Date: 12/28/12
 * Time: 5:38 PM
 */
public class CsvLineReader implements LineReader<CsvItem> {
	private final Scanner lineScanner;
	private final String csvDelimiter;

	public CsvLineReader(Scanner lineScanner, String csvDelimiter) {
		this.lineScanner = lineScanner;
		this.csvDelimiter = csvDelimiter;
	}

	@Override
	public CsvItem nextLine() {
		assertNotNull(lineScanner, "Scanner is null.");
		assertNotNull(csvDelimiter, "Csv delimiter is null.");
		if (lineScanner.hasNextLine()) {
			String csvLine = lineScanner.nextLine();
			List<String> items = Arrays.asList(read(csvLine));
			return new CsvItem(csvLine, items);
		}
		lineScanner.close();
		return null;
	}

	private String[] read(String line) {
		return line.split(csvDelimiter);
	}
}
