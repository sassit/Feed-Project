package com.acme.feed.parser.reader;

import com.acme.feed.parser.model.CsvItem;

import java.util.ArrayList;
import java.util.List;

import static com.acme.feed.parser.Assert.assertNotNull;

/**
 * User: Tarik Sassi
 * Date: 12/28/12
 * Time: 9:34 PM
 */
public class CsvContentReader implements ContentReader<List<CsvItem>> {
	private final LineReader<CsvItem> reader;

	public CsvContentReader(LineReader<CsvItem> reader) {
		this.reader = reader;
	}

	public List<CsvItem> read() {
		assertNotNull(reader, "LineReader is null.");
		List<CsvItem> csvItems = new ArrayList<CsvItem>();
		CsvItem csvItem;
		while ((csvItem = reader.nextLine()) != null) {
			csvItems.add(csvItem);
		}
		return csvItems;
	}
}
