package com.acme.feed.parser.mapper;

import com.acme.feed.common.calculator.IsinCalculator;
import com.acme.feed.common.model.CertificateUpdate;
import com.acme.feed.parser.Assert;
import com.acme.feed.parser.model.CertificateUpdateReport;
import com.acme.feed.parser.model.CsvItem;

import java.math.BigDecimal;
import java.util.List;

import static com.acme.feed.common.Constants.Invalid.*;
import static com.acme.feed.common.Constants.*;

/**
 * User: Tarik Sassi
 * Date: 12/29/12
 * Time: 2:45 PM
 */
public class ValidatingCsvItemToCertUpdateReportMapper implements ItemMapper<CertificateUpdateReport, CsvItem> {
	private CertificateUpdate update;
	private StringBuilder errors;
	private IsinCalculator calculator;

	public ValidatingCsvItemToCertUpdateReportMapper(IsinCalculator calculator) {
		this.calculator = calculator;
	}

	@Override
	public CertificateUpdateReport mapItem(CsvItem csvItem) {
		Assert.assertNotNull(csvItem, "CsvItem is null.");
		List<String> items = csvItem.getLineItems();
		init().validateTimestamp(items.get(timestampPos), invalidTimestamp)
				.validateIsin(items.get(isinPos), invalidIsin)
				.validateCurrency(items.get(currencyPos), invalidCurrency)
				.validateBidPriceRange(validatePrice(items.get(bidPricePos), invalidBidPrice), invalidBidRange)
				.validateBidSize(items.get(bidSizePos), invalidBidSize)
				.validateAskPriceRange(validatePrice(items.get(askPricePos), invalidAskPrice), invalidAskRange)
				.validateAskSize(items.get(askSizePos), invalidAskSize).validateSpread(invalidSpread);
		String error = errors.toString();
		if (error.endsWith(",")) {
			error = error.substring(0, error.length() - 1);
		}
		return new CertificateUpdateReport(csvItem.getCsvLine(), error, update);
	}

	private ValidatingCsvItemToCertUpdateReportMapper validateSpread(String message) {
		BigDecimal askPrice = update.getAskPrice(), bidPrice = update.getBidPrice();
		if (askPrice != null && bidPrice != null && askPrice.compareTo(bidPrice) < 0) {
			errors.append(message);
		}
		return this;
	}

	private ValidatingCsvItemToCertUpdateReportMapper init() {
		this.update = new CertificateUpdate();
		this.errors = new StringBuilder();
		return this;
	}

	private ValidatingCsvItemToCertUpdateReportMapper validateTimestamp(String timestamp, String message) {
		try {
			update.setTimestamp(Long.valueOf(timestamp));
		} catch (NumberFormatException e) {
			errors.append(message);
		}
		return this;
	}

	private ValidatingCsvItemToCertUpdateReportMapper validateCurrency(String currency, String message) {
		if (currency.length() != 3) {
			errors.append(message);
			return this;
		}
		update.setCurrency(currency);
		return this;
	}

	private ValidatingCsvItemToCertUpdateReportMapper validateIsin(String isin, String message) {
		if (isin.length() != 12) {
			errors.append(message);
			return this;
		}
		String result = calculator.calculate(isin.substring(0, isin.length() - 1));
		if (!isin.endsWith(result)) {
			errors.append(message);
			return this;
		}
		update.setIsin(isin);
		return this;
	}

	private ValidatingCsvItemToCertUpdateReportMapper validateAskSize(String size, String message) {
		update.setAskSize(validateSize(size, message));
		return this;
	}

	private ValidatingCsvItemToCertUpdateReportMapper validateBidSize(String size, String message) {
		update.setBidSize(validateSize(size, message));
		return this;
	}

	private int validateSize(String quantity, String message) {
		int parsed = 0;
		try {
			parsed = Integer.valueOf(quantity);
		} catch (NumberFormatException e) {
			errors.append(message);
		}
		if (parsed <= 0) { errors.append(message); }
		return parsed;
	}

	private ValidatingCsvItemToCertUpdateReportMapper validateAskPriceRange(BigDecimal price, String message) {
		BigDecimal result = validatePriceRange(price, message);
		if (result != null) {
			update.setAskPrice(price);
		} else {
			errors.append(message);
		}
		return this;
	}

	private ValidatingCsvItemToCertUpdateReportMapper validateBidPriceRange(BigDecimal price, String message) {
		BigDecimal result = validatePriceRange(price, message);
		if (result != null) {
			update.setBidPrice(price);
		} else {
			errors.append(message);
		}
		return this;
	}

	private BigDecimal validatePriceRange(BigDecimal price, String message) {
		if (price != null) {
			if (minPrice.compareTo(price) > 0 || maxPrice.compareTo(price) < 0) {
				return null;
			}
		}
		return price;
	}

	private BigDecimal validatePrice(String price, String message) {
		BigDecimal parsed = null;
		try {
			parsed = new BigDecimal(price, mathContext);
		} catch (NumberFormatException e) {
			errors.append(message);
		}
		return parsed;
	}
}
