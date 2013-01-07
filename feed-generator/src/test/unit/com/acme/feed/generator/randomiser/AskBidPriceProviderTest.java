package com.acme.feed.generator.randomiser;

import com.acme.feed.generator.model.AskBidPrice;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Random;

import static com.acme.feed.common.Constants.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.junit.matchers.JUnitMatchers.both;

/**
 * User: Tarik Sassi
 * Date: 12/26/12
 * Time: 1:58 PM
 */
public class AskBidPriceProviderTest {
	private final ValuesProvider<AskBidPrice> provider = new AskBidPriceProvider(new Random(), minPrice, maxPrice,
	                                                                             maxSpread);
	private AskBidPrice askBidPrice = provider.provide();

	@Test
	public void shouldHaveGeneratedBiggerAskThanBidPrice() {
		assertThat(askBidPrice.getAskPrice(), greaterThanOrEqualTo(askBidPrice.getBidPrice()));
	}

	@Test
	public void shouldHaveGeneratedAskPriceWithinRange() {
		assertThat(askBidPrice.getAskPrice(),
		           is(both(greaterThanOrEqualTo(minPrice)).and(lessThanOrEqualTo(maxPrice))));
	}

	@Test
	public void shouldHaveGeneratedBidPriceWithinRange() {
		assertThat(askBidPrice.getBidPrice(),
		           is(both(greaterThanOrEqualTo(minPrice)).and(lessThanOrEqualTo(maxPrice))));
	}

	@Test
	public void shouldHaveGeneratedAskAndBidPriceWithinMaxSpread() {
		BigDecimal bidPrice = askBidPrice.getBidPrice();
		assertThat(askBidPrice.getAskPrice(),
		           is(both(greaterThanOrEqualTo(bidPrice)).and(lessThanOrEqualTo(bidPrice.add(maxSpread)))));
	}
}
