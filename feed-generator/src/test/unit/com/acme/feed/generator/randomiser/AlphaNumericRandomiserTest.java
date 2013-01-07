package com.acme.feed.generator.randomiser;

import org.junit.Test;
import org.junit.internal.runners.JUnit4ClassRunner;
import org.junit.runner.RunWith;

import static com.acme.feed.common.Constants.conversionTable;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertThat;

/**
 * User: Tarik Sassi
 * Date: 12/26/12
 * Time: 9:01 PM
 */
@RunWith(JUnit4ClassRunner.class)
public class AlphaNumericRandomiserTest {
	public final int resultsLength = 9;
	private final ValuesProvider<String> provider = new AlphaNumericRandomiser(conversionTable, resultsLength);

	@Test
	public void shouldHaveGeneratedAlphaNumericStringWithGivenLength() {
		assertThat(provider.provide().length(), is(equalTo(9)));
	}

	@Test
	public void shouldHaveNotGeneratedSameStrings() {
		assertThat(provider.provide(), is(not(equalTo(provider.provide()))));
	}
}
