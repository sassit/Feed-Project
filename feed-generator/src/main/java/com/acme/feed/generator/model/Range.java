package com.acme.feed.generator.model;

import java.math.BigDecimal;

/**
 * User: Tarik Sassi
 * Date: 1/3/13
 * Time: 7:08 PM
 */
public class Range {
	private final BigDecimal minPrice;
	private final BigDecimal maxPrice;

	public Range(BigDecimal minPrice, BigDecimal maxPrice) {
		this.minPrice = minPrice;
		this.maxPrice = maxPrice;
	}

	public BigDecimal getMinPrice() {
		return minPrice;
	}

	public BigDecimal getMaxPrice() {
		return maxPrice;
	}
}
