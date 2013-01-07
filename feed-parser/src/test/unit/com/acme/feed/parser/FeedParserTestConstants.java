package com.acme.feed.parser;

import java.util.Arrays;
import java.util.List;

/**
 * User: Tarik Sassi
 * Date: 12/28/12
 * Time: 9:27 PM
 */
public interface FeedParserTestConstants {
    String csv1Line = "1352122280502,DE1234567896,EUR,101.23,1000,103.45,1000" + com.acme.feed.common.Constants.lineDelimiter;
    List<String> csv1LineAsList = Arrays.asList("1352122280502", "DE1234567896", "EUR",
            "101.23", "1000", "103.45", "1000");
	String mappingTable = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
}
