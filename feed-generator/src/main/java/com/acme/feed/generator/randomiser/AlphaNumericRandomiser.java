package com.acme.feed.generator.randomiser;

import java.util.Random;

/**
 * User: Tarik Sassi
 * Date: 12/25/12
 * Time: 11:50 PM
 */
public class AlphaNumericRandomiser implements ValuesProvider<String> {
    private final Random random = new Random();
    private final String mappingTable;
    private final int resultsLength;

    public AlphaNumericRandomiser(String mappingTable, int resultsLength) {
        this.mappingTable = mappingTable;
        this.resultsLength = resultsLength;
    }

    @Override
    public String provide() {
        char[] result = new char[resultsLength];
        for (int i = 0; i < resultsLength; i++) {
            result[i] = mappingTable.charAt(random.nextInt(mappingTable.length()));
        }
        return String.valueOf(result);
    }
}
