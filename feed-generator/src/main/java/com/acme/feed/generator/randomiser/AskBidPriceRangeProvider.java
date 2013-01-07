package com.acme.feed.generator.randomiser;

import com.acme.feed.generator.model.Range;

import java.math.BigDecimal;
import java.util.Random;

import static com.acme.feed.common.Constants.mathContext;

/**
 * User: Tarik Sassi
 * Date: 1/3/13
 * Time: 7:00 PM
 */
public class AskBidPriceRangeProvider implements ValuesProvider<Range> {
	private final Random random;
	private final double maxSpread;
	private final double minPrice;
	private final double maxPrice;
	private final double maxSpreadAll;

	public AskBidPriceRangeProvider(Random random, BigDecimal minPrice, BigDecimal maxPrice,
	                                BigDecimal maxSpread, BigDecimal maxSpreadAll) {
		this.random = random;
		this.maxSpread = maxSpread.doubleValue();
		this.minPrice = minPrice.doubleValue();
		this.maxPrice = maxPrice.doubleValue();
		this.maxSpreadAll = maxSpreadAll.doubleValue();
	}

	@Override
	public Range provide() {
		double base = random.nextDouble() * (maxPrice - minPrice) + minPrice;
		return new Range(new BigDecimal(Math.min(base + maxSpread, maxPrice), mathContext),
		                 new BigDecimal(Math.min(base + maxSpreadAll, maxPrice), mathContext));
	}
}
