package com.acme.feed.generator;

import com.acme.feed.common.model.CertificateUpdate;
import com.acme.feed.generator.generators.CertificateUpdateSeriesGenerator;
import com.acme.feed.generator.model.AskBidPrice;
import com.acme.feed.generator.model.Range;
import com.acme.feed.generator.randomiser.*;
import org.junit.Test;
import org.junit.internal.runners.JUnit4ClassRunner;
import org.junit.runner.RunWith;

import java.math.BigDecimal;
import java.util.Random;
import java.util.Set;

import static com.acme.feed.common.Constants.*;
import static java.math.BigDecimal.ZERO;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.junit.matchers.JUnitMatchers.both;

/**
 * User: Tarik Sassi
 * Date: 12/27/12
 * Time: 12:11 PM
 */
@RunWith(JUnit4ClassRunner.class)
public class CertificateUpdateSeriesIntegrationTest {
	private final int minQty = 5, maxQty = 25, numOfUpdates = 500000;
	private final String isin = "DE1234567894";
	private final Random random = new Random();
	private final ValuesProvider<String> ccyCodeProvider = new CurrencyCodeRandomiser(ccyCodes);
	private final ValuesProvider<Integer> quantityProvider = new QuantityRandomiser(minQty, maxQty);
	private final ValuesProvider<Long> timestampProvider = new TimestampRandomiser();
	private final CertificateUpdateSeriesGenerator generator = new CertificateUpdateSeriesGenerator(ccyCodeProvider,
	                                                                                                quantityProvider,
	                                                                                                timestampProvider);
	private final ValuesProvider<Range> rangeProvider = new AskBidPriceRangeProvider(random,
	                                                                                 minPrice,
	                                                                                 maxPrice,
	                                                                                 maxSpread,
	                                                                                 maxSpreadAll);
	private final Range range = rangeProvider.provide();
	private final ValuesProvider<AskBidPrice> priceProvider = new AskBidPriceProvider(random,
	                                                                                  range.getMinPrice(),
	                                                                                  range.getMaxPrice(),
	                                                                                  maxSpread);
	private final long startTime = System.currentTimeMillis() - 100;
	private final Set<CertificateUpdate> updates = generator.generate(priceProvider, isin, numOfUpdates);

	@Test
	public void shouldGenerateUpdatesWithBiggerAskThanBidPrice() {
		for (CertificateUpdate update : updates) {
			assertThat(update.getAskPrice(), is(greaterThanOrEqualTo(update.getBidPrice())));
		}
	}

	@Test
	public void shouldHaveAskPricesInRangeOfMinAndMax() {
		for (CertificateUpdate update : updates) {
			assertThat(update.getAskPrice(), is(both(greaterThanOrEqualTo(minPrice)).and(lessThanOrEqualTo(maxPrice))));
		}
	}

	@Test
	public void shouldHaveBidPricesInRangeOfMinAndMax() {
		for (CertificateUpdate update : updates) {
			assertThat(update.getBidPrice(), is(both(greaterThanOrEqualTo(minPrice)).and(lessThanOrEqualTo(maxPrice))));
		}
	}

	@Test
	public void shouldHaveAllPricesInRange() {
		BigDecimal lowestPrice = ZERO, highestPrice = ZERO;
		for (CertificateUpdate update : updates) {
			lowestPrice = (lowestPrice.equals(ZERO)) ? update.getBidPrice() : update.getBidPrice()
			                                                                        .min(lowestPrice);
			highestPrice = update.getAskPrice().max(highestPrice);
		}
		assertThat(highestPrice.subtract(lowestPrice), is(not(greaterThan(maxSpreadAll))));
	}

	@Test
	public void shouldHaveAllAskQantitiesInRange() {
		for (CertificateUpdate update : updates) {
			assertThat(update.getAskSize(), is(both(greaterThanOrEqualTo(minQty)).and(lessThanOrEqualTo(maxQty))));
		}
	}

	@Test
	public void shouldHaveAllBidQantitiesInRange() {
		for (CertificateUpdate update : updates) {
			assertThat(update.getBidSize(), is(both(greaterThanOrEqualTo(minQty)).and(lessThanOrEqualTo(maxQty))));
		}
	}

	@Test
	public void shouldHaveValidCCyCode() {
		for (CertificateUpdate update : updates) {
			assertThat(update.getCurrency(), isIn(ccyCodes));
		}
	}

	@Test
	public void shouldHaveNewerTimestamp() {
		for (CertificateUpdate update : updates) {
			assertThat(update.getTimestamp(), is(greaterThan(startTime)));
		}
	}
}
