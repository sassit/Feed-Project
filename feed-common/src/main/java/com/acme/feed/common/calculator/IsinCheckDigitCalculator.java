package com.acme.feed.common.calculator;

/**
 * User: Tarik Sassi
 * Date: 1/2/13
 * Time: 4:34 PM
 */
public class IsinCheckDigitCalculator implements IsinCalculator {
	private final String conversionTable;

	public IsinCheckDigitCalculator(String conversionTable) {
		this.conversionTable = conversionTable;
	}

	public String calculate(String alphaNum) {
		StringBuilder converted = new StringBuilder();
		for (char character : alphaNum.toCharArray()) {
			converted.append(conversionTable.indexOf(character));
		}
		StringBuilder calculated = new StringBuilder();
		int length = converted.length(), digit;
		for (int i = 0; i < length; i++) {
			digit = Character.digit(converted.charAt(i), 10);
			calculated.append((i % 2 == 0) ? digit * 2 : digit);
		}
		int preResult = 0, length2 = calculated.length();
		for (int i = 0; i < length2; i++) {
			preResult += Character.digit(calculated.charAt(i), 10);
		}
		return String.valueOf((preResult % 10 == 0) ? 0 : (((preResult / 10) + 1) * 10) - preResult);
	}
}
