package com.acme.feed.generator;

import com.acme.feed.common.calculator.IsinCalculator;
import com.acme.feed.common.calculator.IsinCheckDigitCalculator;
import com.acme.feed.common.model.CertificateUpdate;
import com.acme.feed.generator.generators.CertificateUpdateGenerator;
import com.acme.feed.generator.generators.CertificateUpdateSeriesGenerator;
import com.acme.feed.generator.model.Isin;
import com.acme.feed.generator.randomiser.*;
import org.junit.Test;
import org.junit.internal.runners.JUnit4ClassRunner;
import org.junit.runner.RunWith;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.acme.feed.common.Constants.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * User: Tarik Sassi
 * Date: 12/27/12
 * Time: 4:43 PM
 */
@RunWith(JUnit4ClassRunner.class)
public class CertificateUpdateGeneratorIntegrationTest {
	private final int minQty = 5, maxQty = 15, numOfCertificates = 100, numOfPriceUpdates = 100;
	private final ValuesProvider<String> alphaNumericRandomiser = new AlphaNumericRandomiser(conversionTable, 9);
	private final ValuesProvider<String> countryCodeRandomiser = new CountryCodeRandomiser(countryCodes);
	private final IsinCalculator isinCalculator = new IsinCheckDigitCalculator(conversionTable);
	private final ValuesProvider<Isin> isinRandomiser = new IsinRandomiser(isinCalculator, alphaNumericRandomiser,
	                                                                         countryCodeRandomiser);
	private final ValuesProvider<String> ccyCodeProvider = new CurrencyCodeRandomiser(ccyCodes);
	private final ValuesProvider<Integer> quantityProvider = new QuantityRandomiser(minQty, maxQty);
	private final ValuesProvider<Long> timestampProvider = new TimestampRandomiser();
	private final CertificateUpdateSeriesGenerator seriesGenerator = new CertificateUpdateSeriesGenerator
			                                                                 (ccyCodeProvider,
			                                                                  quantityProvider,
			                                                                  timestampProvider);

	@Test
	public void shouldHaveCorrectNumberOfDifferentIsinsAndTotalOfUpdates() {
		CertificateUpdateGenerator generator = new CertificateUpdateGenerator(seriesGenerator, isinRandomiser,
		                                                                      minPrice, maxPrice, maxSpread, maxSpreadAll);
		List<CertificateUpdate> updates = generator.generateInputs(numOfCertificates, numOfPriceUpdates);
		Set<String> isins = new HashSet<String>();
		for (CertificateUpdate update : updates) {
			isins.add(update.getIsin());
		}
		assertThat(isins.size(), is(equalTo(numOfCertificates)));
		assertThat(updates.size(), is(equalTo(numOfCertificates * numOfPriceUpdates)));
	}
}
