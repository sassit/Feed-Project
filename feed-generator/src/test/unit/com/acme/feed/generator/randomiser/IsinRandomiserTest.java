package com.acme.feed.generator.randomiser;

import com.acme.feed.common.calculator.IsinCalculator;
import com.acme.feed.common.calculator.IsinCheckDigitCalculator;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.junit.Test;
import org.junit.runner.RunWith;

import static com.acme.feed.common.Constants.conversionTable;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

/**
 * Created with IntelliJ IDEA.
 * User: Tarik Sassi
 * Date: 12/25/12
 * Time: 2:02 PM
 */
@RunWith(JMock.class)
public class IsinRandomiserTest {
	private Mockery context = new Mockery();
	private ValuesProvider<String> alphaNumeric = context.mock(ValuesProvider.class, "alpha");
	private ValuesProvider<String> countryCodes = context.mock(ValuesProvider.class, "country");

	@Test
	public void shouldHaveGeneratedResultOfSample() {
		IsinCalculator isinCalculator = new IsinCheckDigitCalculator(conversionTable);
		IsinRandomiser randomiser = new IsinRandomiser(isinCalculator, alphaNumeric, countryCodes);
		context.checking(new Expectations() {{
			oneOf(alphaNumeric).provide();
			will(returnValue("123456789"));
			oneOf(countryCodes).provide();
			will(returnValue("DE"));
		}});
		assertThat(randomiser.provide().toString(), equalTo("DE1234567896"));
	}
}
