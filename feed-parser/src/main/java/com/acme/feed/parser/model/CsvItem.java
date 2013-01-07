package com.acme.feed.parser.model;

import java.util.List;

/**
 * User: Tarik Sassi
 * Date: 12/29/12
 * Time: 6:12 PM
 */
public class CsvItem {
	private final String csvLine;
	private final List<String> lineItems;

	public CsvItem(String csvLine, List<String> lineItems) {
		this.csvLine = csvLine;
		this.lineItems = lineItems;
	}

	public String getCsvLine() {
		return csvLine;
	}

	public List<String> getLineItems() {
		return lineItems;
	}
}
