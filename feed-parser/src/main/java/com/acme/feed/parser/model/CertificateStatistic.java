package com.acme.feed.parser.model;

import java.math.BigDecimal;

/**
 * User: Tarik Sassi
 * Date: 12/30/12
 * Time: 7:01 PM
 */
public class CertificateStatistic {
	private final String isin;
	private final int size;
	private final BigDecimal avgBidPrice;
	private final BigDecimal avgAskPrice;

	public CertificateStatistic(String isin, int size, BigDecimal avgBidPrice, BigDecimal avgAskPrice) {
		this.isin = isin;
		this.size = size;
		this.avgBidPrice = avgBidPrice;
		this.avgAskPrice = avgAskPrice;
	}

	public String getIsin() {
		return isin;
	}

	public int getSize() {
		return size;
	}

	public BigDecimal getAvgBidPrice() {
		return avgBidPrice;
	}

	public BigDecimal getAvgAskPrice() {
		return avgAskPrice;
	}
}
