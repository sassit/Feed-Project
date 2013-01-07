package com.acme.feed.generator.randomiser;

import com.acme.feed.generator.model.AskBidPrice;

import java.math.BigDecimal;
import java.util.Random;

import static com.acme.feed.common.Constants.mathContext;

/**
 * Created with IntelliJ IDEA.
 * User: Tarik Sassi
 * Date: 12/26/12
 * Time: 2:09 PM
 */
public class AskBidPriceProvider implements ValuesProvider<AskBidPrice> {
	private final Random random;
	private final double minPrice;
	private final double maxPrice;
	private final double maxSpread;

	public AskBidPriceProvider(Random random, BigDecimal minPrice, BigDecimal maxPrice, BigDecimal maxSpread) {
		this.random = random;
		this.minPrice = minPrice.doubleValue();
		this.maxPrice = maxPrice.doubleValue();
		this.maxSpread = maxSpread.doubleValue();
	}

	public AskBidPrice provide() {
		double askPrice = nextRandom(random, minPrice, maxPrice);
		double bidPrice = nextRandom(random, askPrice - maxSpread, askPrice);
		return new AskBidPrice(new BigDecimal(askPrice, mathContext),
		                       new BigDecimal(bidPrice, mathContext));
	}

	private double nextRandom(Random random, double min, double max) {
		return random.nextDouble() * (max - min) + min;
	}
}
