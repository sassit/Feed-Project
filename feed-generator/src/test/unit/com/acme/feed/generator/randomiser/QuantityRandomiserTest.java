package com.acme.feed.generator.randomiser;

import org.junit.Test;
import org.junit.internal.runners.JUnit4ClassRunner;
import org.junit.runner.RunWith;

import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;

/**
 * User: Tarik Sassi
 * Date: 12/26/12
 * Time: 10:06 PM
 */
@RunWith(JUnit4ClassRunner.class)
public class QuantityRandomiserTest {
	public static final int min = 0, max = 100;
	private final ValuesProvider<Integer> provider = new QuantityRandomiser(min, max);

	@Test
	public void shouldHaveGeneratedDifferentQuantities() {
		Set<Integer> generated = new HashSet<Integer>();
		for (int i = 0; i < 10; i++) {
			generated.add(provider.provide());
		}
		assertThat(generated, hasSize(greaterThanOrEqualTo(5)));
	}
}
