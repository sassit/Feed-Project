package com.acme.feed.generator.randomiser;

import org.junit.Test;
import org.junit.internal.runners.JUnit4ClassRunner;
import org.junit.runner.RunWith;

import java.util.HashSet;
import java.util.Set;

import static com.acme.feed.common.Constants.countryCodes;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;

/**
 * User: Tarik Sassi
 * Date: 12/26/12
 * Time: 8:52 PM
 */
@RunWith(JUnit4ClassRunner.class)
public class CountryCodeRandomiserTest {
	private final ValuesProvider<String> provider = new CountryCodeRandomiser(countryCodes);

	@Test
	public void shouldHaveGeneratedFor3TimesItsSizeAtLeastAVariationEqualToHalfItsSize() {
		Set<String> generated = new HashSet<String>();
		for (int i = 0; i < countryCodes.size() * 10; i++) {
			generated.add(provider.provide());
		}
		assertThat(generated, hasSize(greaterThanOrEqualTo(countryCodes.size() / 2)));
	}
}
