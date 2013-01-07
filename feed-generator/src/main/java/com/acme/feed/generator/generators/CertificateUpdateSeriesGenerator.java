package com.acme.feed.generator.generators;

import com.acme.feed.common.model.CertificateUpdate;
import com.acme.feed.generator.model.AskBidPrice;
import com.acme.feed.generator.randomiser.ValuesProvider;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * User: Tarik Sassi
 * Date: 12/24/12
 * Time: 7:08 PM
 */
public class CertificateUpdateSeriesGenerator {
	private final ValuesProvider<String> currencyCodeProvider;
	private final ValuesProvider<Integer> quantityProvider;
	private final ValuesProvider<Long> timestampProvider;

	public CertificateUpdateSeriesGenerator(ValuesProvider<String> currencyCodeProvider,
	                                        ValuesProvider<Integer> quantityProvider,
	                                        ValuesProvider<Long> timestampProvider) {
		this.currencyCodeProvider = currencyCodeProvider;
		this.quantityProvider = quantityProvider;
		this.timestampProvider = timestampProvider;
	}

	public Set<CertificateUpdate> generate(ValuesProvider<AskBidPrice> bidAskPriceProvider, String isin,
	                                       int updatesPerCert) {
		Set<CertificateUpdate> updates = new LinkedHashSet<CertificateUpdate>();
		for (int i = 0; i < updatesPerCert; i++) {
			AskBidPrice askBidPrice = bidAskPriceProvider.provide();
			CertificateUpdate update = new CertificateUpdate(timestampProvider.provide(), isin,
			                                                 currencyCodeProvider.provide(), askBidPrice.getBidPrice(),
			                                                 quantityProvider.provide(), askBidPrice.getAskPrice(),
			                                                 quantityProvider.provide());
			updates.add(update);
		}
		return updates;
	}
}
