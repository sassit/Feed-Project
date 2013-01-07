package com.acme.feed.generator.model;

/**
 * User: Tarik Sassi
 * Date: 12/25/12
 * Time: 12:52 PM
 */
public class Certificate {
    private final String isin;

    public Certificate(String isin) {
        this.isin = isin;
    }

    public String getIsin() {
        return this.isin;
    }
}
