package com.acme.feed.generator.model;

import java.math.BigDecimal;

/**
 * User: Tarik Sassi
 * Date: 05/01/2013
 * Time: 04:56
 */
public class AskBidPrice {
	private final BigDecimal askPrice;
	private final BigDecimal bidPrice;

	public AskBidPrice(BigDecimal askPrice, BigDecimal bidPrice) {
		this.askPrice = askPrice;
		this.bidPrice = bidPrice;
	}

	public BigDecimal getAskPrice() {
		return askPrice;
	}

	public BigDecimal getBidPrice() {
		return bidPrice;
	}
}
