package com.acme.feed.generator;

import com.acme.feed.common.calculator.IsinCalculator;
import com.acme.feed.common.calculator.IsinCheckDigitCalculator;
import com.acme.feed.common.utils.PropertyUtils;
import com.acme.feed.generator.generators.CertificateUpdateGenerator;
import com.acme.feed.generator.generators.CertificateUpdateSeriesGenerator;
import com.acme.feed.generator.randomiser.*;
import com.acme.feed.generator.writer.CertificateUpdatesBufferWriter;

import java.io.File;
import java.math.BigDecimal;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * User: Tarik Sassi
 * Date: 12/27/12
 * Time: 1:34 PM
 */
public class GeneratorAppFactory {

	public static GeneratorApp create(Properties properties, int numOfCerts, int numOfCertUpdates,
	                                  int numOfThreads, String csvFile
	                                 ) {
		ExecutorService service = Executors.newFixedThreadPool(numOfThreads);
		BigDecimal minPrice = PropertyUtils.propAsBigDecimal(properties, "min-price");
		BigDecimal maxPrice = PropertyUtils.propAsBigDecimal(properties, "max-price");
		BigDecimal maxSpread = PropertyUtils.propAsBigDecimal(properties, "max-spread");
		BigDecimal maxSpreadAll = PropertyUtils.propAsBigDecimal(properties, "max-spread-all");
		String mappingTable = properties.getProperty("mapping-table");
		int alphaNumLength = PropertyUtils.propAsInt(properties, "alpha-num-length");
		AlphaNumericRandomiser alphaNumRandomiser = new AlphaNumericRandomiser(mappingTable, alphaNumLength);
		Set<String> countryCodes = PropertyUtils.propAsSet(properties, "country-codes");
		CountryCodeRandomiser countryCodeRandomiser = new CountryCodeRandomiser(countryCodes);
		IsinCalculator isinCalculator = new IsinCheckDigitCalculator(mappingTable);
		IsinRandomiser isinRandomiser = new IsinRandomiser(isinCalculator, alphaNumRandomiser, countryCodeRandomiser);
		Set<String> currencyCodes = PropertyUtils.propAsSet(properties, "currency-codes");
		int minQty = PropertyUtils.propAsInt(properties, "min-qty");
		int maxQty = PropertyUtils.propAsInt(properties, "max-qty");
		CurrencyCodeRandomiser currencyCodeRandomiser = new CurrencyCodeRandomiser(currencyCodes);
		QuantityRandomiser quantityRandomiser = new QuantityRandomiser(minQty, maxQty);
		TimestampRandomiser timestampRandomiser = new TimestampRandomiser();
		CertificateUpdateSeriesGenerator seriesGenerator = new CertificateUpdateSeriesGenerator(currencyCodeRandomiser,
		                                                                                        quantityRandomiser,
		                                                                                        timestampRandomiser);
		CertificateUpdateGenerator generator = new CertificateUpdateGenerator(seriesGenerator,
		                                                                      isinRandomiser,
		                                                                      minPrice,
		                                                                      maxPrice,
		                                                                      maxSpread,
		                                                                      maxSpreadAll);
		CertificateUpdatesBufferWriter writer = new CertificateUpdatesBufferWriter(service,
		                                                                           new File(csvFile));
		return new GeneratorApp(generator, writer, numOfCerts, numOfCertUpdates);
	}
}
