package com.acme.feed.generator.randomiser;

import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static com.acme.feed.common.Constants.ccyCodes;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;

/**
 * User: Tarik Sassi
 * Date: 12/26/12
 * Time: 10:04 PM
 */
public class CurrencyCodeRandomiserTest {
	private final ValuesProvider<String> provider = new CurrencyCodeRandomiser(ccyCodes);

	@Test
	public void shouldHaveGeneratedFor3TimesItsSizeAtLeastAVariationEqualToHalfItsSize() {
		Set<String> ccyCodesGenerated = new HashSet<String>();
		for (int i = 0; i < ccyCodes.size() * 10; i++) {
			ccyCodesGenerated.add(provider.provide());
		}
		assertThat(ccyCodesGenerated, hasSize(greaterThanOrEqualTo(ccyCodes.size() / 2)));
	}
}
