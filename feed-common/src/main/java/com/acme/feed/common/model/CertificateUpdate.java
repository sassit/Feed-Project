package com.acme.feed.common.model;

import java.math.BigDecimal;

/**
 * User: Tarik Sassi
 * Date: 12/24/12
 * Time: 6:39 PM
 */
public class CertificateUpdate {
	private long timestamp;
	private String isin;
	private String currency;
	private BigDecimal bidPrice;
	private int bidSize;
	private BigDecimal askPrice;
	private int askSize;

	public CertificateUpdate() {
	}

	public CertificateUpdate(long timestamp, String isin, String currency, BigDecimal bidPrice, int bidSize,
	                         BigDecimal askPrice, int askSize) {
		this.timestamp = timestamp;
		this.isin = isin;
		this.currency = currency;
		this.bidPrice = bidPrice;
		this.bidSize = bidSize;
		this.askPrice = askPrice;
		this.askSize = askSize;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) { return true; }
		if (o == null || getClass() != o.getClass()) { return false; }

		CertificateUpdate that = (CertificateUpdate) o;

		if (askSize != that.askSize) { return false; }
		if (bidSize != that.bidSize) { return false; }
		if (timestamp != that.timestamp) { return false; }
		if (!askPrice.equals(that.askPrice)) { return false; }
		if (!bidPrice.equals(that.bidPrice)) { return false; }
		if (!currency.equals(that.currency)) { return false; }
		if (!isin.equals(that.isin)) { return false; }

		return true;
	}

	@Override
	public int hashCode() {
		int result = (int) (timestamp ^ (timestamp >>> 32));
		result = 31 * result + isin.hashCode();
		result = 31 * result + currency.hashCode();
		result = 31 * result + bidPrice.hashCode();
		result = 31 * result + bidSize;
		result = 31 * result + askPrice.hashCode();
		result = 31 * result + askSize;
		return result;
	}

	@Override
	public String toString() {
		return "CertificateUpdate{" +
				       "invalidTimestamp=" + timestamp +
				       ", invalidIsin='" + isin + '\'' +
				       ", invalidCurrency='" + currency + '\'' +
				       ", invalidBidPrice=" + bidPrice +
				       ", invalidBidSize=" + bidSize +
				       ", invalidAskPrice=" + askPrice +
				       ", invalidAskSize=" + askSize +
				       '}';
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public String getIsin() {
		return isin;
	}

	public void setIsin(String isin) {
		this.isin = isin;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public BigDecimal getBidPrice() {
		return bidPrice;
	}

	public void setBidPrice(BigDecimal bidPrice) {
		this.bidPrice = bidPrice;
	}

	public int getBidSize() {
		return bidSize;
	}

	public void setBidSize(int bidSize) {
		this.bidSize = bidSize;
	}

	public BigDecimal getAskPrice() {
		return askPrice;
	}

	public void setAskPrice(BigDecimal askPrice) {
		this.askPrice = askPrice;
	}

	public int getAskSize() {
		return askSize;
	}

	public void setAskSize(int askSize) {
		this.askSize = askSize;
	}
}
