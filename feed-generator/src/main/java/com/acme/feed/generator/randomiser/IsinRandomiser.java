package com.acme.feed.generator.randomiser;


import com.acme.feed.common.calculator.IsinCalculator;
import com.acme.feed.generator.model.Isin;

/**
 * Created with IntelliJ IDEA.
 * User: Tarik Sassi
 * Date: 12/25/12
 * Time: 1:57 PM
 */
public class IsinRandomiser implements ValuesProvider<Isin> {
	private final IsinCalculator isinCalculator;
	private final ValuesProvider<String> alphaNumRandomiser;
	private final ValuesProvider<String> countryCodeRandomiser;

	public IsinRandomiser(IsinCalculator isinCalculator, ValuesProvider alphaNumRandomiser,
	                      ValuesProvider countryCodeRandomiser) {
		this.isinCalculator = isinCalculator;
		this.alphaNumRandomiser = alphaNumRandomiser;
		this.countryCodeRandomiser = countryCodeRandomiser;
	}

	public Isin provide() {
		StringBuilder builder = new StringBuilder();
		String countryCode = countryCodeRandomiser.provide();
		String alphaNum = alphaNumRandomiser.provide();
		String checkDigit = isinCalculator.calculate(countryCode + alphaNum);
		return new Isin(countryCode, alphaNum, checkDigit);
	}
}
