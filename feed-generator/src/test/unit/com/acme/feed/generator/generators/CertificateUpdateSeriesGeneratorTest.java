package com.acme.feed.generator.generators;

import com.acme.feed.common.model.CertificateUpdate;
import com.acme.feed.generator.model.AskBidPrice;
import com.acme.feed.generator.randomiser.ValuesProvider;
import org.hamcrest.Matchers;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.internal.runners.JUnit4ClassRunner;
import org.junit.runner.RunWith;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.emptyCollectionOf;
import static org.junit.Assert.assertThat;

/**
 * User: Tarik Sassi
 * Date: 12/26/12
 * Time: 10:44 PM
 */
@RunWith(JUnit4ClassRunner.class)
public class CertificateUpdateSeriesGeneratorTest {
	private final Mockery context = new Mockery();
	private final String isin = "DE1234567894";
	private final ValuesProvider<String> ccyCodeProvider = context.mock(ValuesProvider.class, "ccyCode");
	private final ValuesProvider<Integer> quantityProvider = context.mock(ValuesProvider.class, "quantity");
	private final ValuesProvider<Long> timestampProvider = context.mock(ValuesProvider.class, "invalidTimestamp");
	private final ValuesProvider<AskBidPrice> pricePairProvider = context.mock(ValuesProvider.class, "pricePair");
	private final CertificateUpdateSeriesGenerator generator = new CertificateUpdateSeriesGenerator(ccyCodeProvider,
	                                                                                                quantityProvider,
	                                                                                                timestampProvider);

	@Ignore
	public void shouldHaveGeneratedTheSpecifiedNumberOfCerts() {
		int ten = 10;
		definedValuesForProviders(ten);
		assertThat(generator.generate(pricePairProvider, isin, ten), Matchers.<CertificateUpdate>hasSize(ten));
	}

	@Test
	public void shouldHaveGeneratedEmptySetOnInvalidNumOfUpdates() {
		int invalid = -1;
		definedValuesForProviders(invalid);
		assertThat(generator.generate(pricePairProvider, isin, invalid),
		           is(emptyCollectionOf(CertificateUpdate.class)));
	}

	private void definedValuesForProviders(final int howManyUpdates) {
		context.checking(new Expectations() {{
			exactly(howManyUpdates).of(pricePairProvider).provide();
			will(returnValue(new double[]{150, 152}));
			exactly(howManyUpdates).of(timestampProvider).provide();
			will(returnValue(System.currentTimeMillis()));
			exactly(howManyUpdates).of(ccyCodeProvider).provide();
			will(returnValue("USD"));
			exactly(howManyUpdates).of(quantityProvider).provide();
			will(returnValue(8));
			exactly(howManyUpdates).of(quantityProvider).provide();
			will(returnValue(10));
		}});
	}
}
