package com.acme.feed.generator.generators;

import com.acme.feed.common.model.CertificateUpdate;
import com.acme.feed.generator.model.AskBidPrice;
import com.acme.feed.generator.model.Certificate;
import com.acme.feed.generator.model.Isin;
import com.acme.feed.generator.model.Range;
import com.acme.feed.generator.randomiser.AskBidPriceProvider;
import com.acme.feed.generator.randomiser.AskBidPriceRangeProvider;
import com.acme.feed.generator.randomiser.ValuesProvider;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * User: Tarik Sassi
 * Date: 12/26/12
 * Time: 10:19 PM
 */
public class CertificateUpdateGenerator {
	private final CertificateUpdateSeriesGenerator seriesGenerator;
	private final ValuesProvider<Isin> isinProvider;
	private final BigDecimal minPrice;
	private final BigDecimal maxPrice;
	private final BigDecimal maxSpread;
	private final BigDecimal maxSpreadAll;

	public CertificateUpdateGenerator(CertificateUpdateSeriesGenerator seriesGenerator,
	                                  ValuesProvider<Isin> isinProvider, BigDecimal minPrice, BigDecimal maxPrice,
	                                  BigDecimal maxSpread, BigDecimal maxSpreadAll) {
		this.seriesGenerator = seriesGenerator;
		this.isinProvider = isinProvider;
		this.minPrice = minPrice;
		this.maxPrice = maxPrice;
		this.maxSpread = maxSpread;
		this.maxSpreadAll = maxSpreadAll;
	}

	public List<CertificateUpdate> generateInputs(int numberOfCertificates, int numberOfPriceUpdates) {
		List<CertificateUpdate> updates = new ArrayList<CertificateUpdate>();
		for (int i = 0; i < numberOfCertificates; i++) {
			Certificate certificate = new Certificate(isinProvider.provide().toString());
			Random random = new Random();
			ValuesProvider<Range> rangeProvider = new AskBidPriceRangeProvider(random, minPrice, maxPrice, maxSpread,
			                                                                   maxSpreadAll);
			Range range = rangeProvider.provide();
			ValuesProvider<AskBidPrice> bidAskPriceProvider = new AskBidPriceProvider(random, range.getMinPrice(),
			                                                                          range.getMaxPrice(), maxSpread);
			updates.addAll(seriesGenerator.generate(bidAskPriceProvider, certificate.getIsin(), numberOfPriceUpdates));
		}
		return updates;
	}
}
