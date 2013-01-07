package com.acme.feed.parser.calculator;

import com.acme.feed.common.model.CertificateUpdate;
import com.acme.feed.parser.model.CertificateStatistic;

import java.math.BigDecimal;
import java.util.List;

import static com.acme.feed.common.Constants.mathContext;
import static com.acme.feed.parser.Assert.assertNotEmptyOrNull;
import static java.math.BigDecimal.ZERO;

/**
 * User: Tarik Sassi
 * Date: 12/30/12
 * Time: 6:55 PM
 */
public class CertificateUpdateStatisticsCalculator implements
		com.acme.feed.parser.calculator.Calculator<CertificateStatistic, List<CertificateUpdate>> {
	@Override
	public CertificateStatistic calculate(List<CertificateUpdate> series) {
		assertNotEmptyOrNull(series, "No series provided.");
		BigDecimal sumOfBidPrices = ZERO, sumOfAskPrices = ZERO;
		String isin = null;
		for (CertificateUpdate update : series) {
			isin = update.getIsin();
			sumOfBidPrices = sumOfBidPrices.add(update.getBidPrice(), mathContext);
			sumOfAskPrices = sumOfAskPrices.add(update.getAskPrice(), mathContext);
		}
		BigDecimal size = BigDecimal.valueOf(series.size());
		return new CertificateStatistic(isin,
		                                size.intValue(),
		                                sumOfBidPrices.divide(size, mathContext),
		                                sumOfAskPrices.divide(size, mathContext));
	}
}
