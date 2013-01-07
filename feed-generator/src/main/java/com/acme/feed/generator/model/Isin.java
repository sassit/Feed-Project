package com.acme.feed.generator.model;

/**
 * User: Tarik Sassi
 * Date: 04/01/2013
 * Time: 23:32
 */
public class Isin {
	private final String countryCode;
	private final String alphaNum;
	private final String checkDigit;

	public Isin(String countryCode, String alphaNum, String checkDigit) {
		this.countryCode = countryCode;
		this.alphaNum = alphaNum;
		this.checkDigit = checkDigit;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public String getAlphaNum() {
		return alphaNum;
	}

	public String getCheckDigit() {
		return checkDigit;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		return builder.append(countryCode).append(alphaNum).append(checkDigit).toString();
	}
}
