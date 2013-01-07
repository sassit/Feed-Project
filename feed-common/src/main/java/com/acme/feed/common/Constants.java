package com.acme.feed.common;

import java.math.BigDecimal;
import java.math.MathContext;
import java.nio.charset.Charset;
import java.util.HashSet;
import java.util.Set;

import static java.math.RoundingMode.HALF_UP;
import static java.util.Arrays.asList;

/**
 * User: Tarik Sassi
 * Date: 1/2/13
 * Time: 1:13 PM
 */
public interface Constants {
	int MB = 1024 * 1024;
	String lineDelimiter = "\r\n";
	String csvDelimiter = ",";
	String conversionTable = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	Charset codingScheme = Charset.forName("US-ASCII");
	MathContext mathContext = new MathContext(5, HALF_UP);
	BigDecimal minPrice = new BigDecimal(100.0, mathContext),
			maxPrice = new BigDecimal(200.0, mathContext),
			maxSpread = new BigDecimal(5.0, mathContext),
			maxSpreadAll = new BigDecimal(10.0, mathContext);
	Set<String> ccyCodes = new HashSet<String>(asList(new String[]{"EUR", "CHF", "GBP", "USD", "SGD", "CNY"}));
	Set<String> countryCodes = new HashSet<String>(asList(new String[]{"DE", "CH", "GB", "US", "SG", "CN"}));
	int timestampPos = 0, isinPos = 1, currencyPos = 2, bidPricePos = 3, bidSizePos = 4, askPricePos = 5, askSizePos
			                                                                                                      = 6;

	public interface Invalid {
		String invalidTimestamp = "invalid timestamp,";
		String invalidIsin = "invalid isin,";
		String invalidCurrency = "invalid currency,";
		String invalidBidPrice = "invalid bid price,";
		String invalidBidSize = "invalid bid size,";
		String invalidBidRange = "invalid bid range,";
		String invalidAskPrice = "invalid ask price,";
		String invalidAskSize = "invalid ask size,";
		String invalidAskRange = "invalid ask range,";
		String invalidSpread = "invalid spread,";
	}
}
