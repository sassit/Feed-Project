package com.acme.feed.parser.mapper;

import com.acme.feed.common.calculator.IsinCalculator;
import com.acme.feed.common.calculator.IsinCheckDigitCalculator;
import com.acme.feed.parser.ModelFactory;
import com.acme.feed.parser.ParserException;
import com.acme.feed.parser.model.CertificateUpdateReport;
import com.acme.feed.parser.model.CsvItem;
import org.junit.Test;
import org.junit.internal.runners.JUnit4ClassRunner;
import org.junit.runner.RunWith;

import static com.acme.feed.common.Constants.Invalid.*;
import static com.acme.feed.common.Constants.lineDelimiter;
import static com.acme.feed.parser.FeedParserTestConstants.mappingTable;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * User: Tarik Sassi
 * Date: 12/29/12
 * Time: 2:46 PM
 */
@RunWith(JUnit4ClassRunner.class)
public class ValidatingCsvItemToCertUpdateReportMapperTest {
	@Test
	public void shouldHaveInvalidTimestamp() throws Exception {
		CsvItem item = ModelFactory.createCsvItem("invalidTimestamp,DE1234567896,EUR,101.23,1000,103.45," +
				                                          "1000" + lineDelimiter);
		CertificateUpdateReport report = createCertificateUpdateReport(item);
		assertThat(report.isValid(), is(false));
		assertThat(report.getErrorReport() + ",", containsString(invalidTimestamp));
	}

	@Test
	public void shouldHaveInvalidIsin() throws Exception {
		CsvItem item = ModelFactory.createCsvItem("1352122280502,invalidIsin,EUR,101.23,1000,103.45," +
				                                          "1000" + lineDelimiter);
		CertificateUpdateReport report = createCertificateUpdateReport(item);
		assertThat(report.isValid(), is(false));
		assertThat(report.getErrorReport() + ",", containsString(invalidIsin));
	}

	@Test
	public void shouldHaveInvalidCurrency() throws Exception {
		CsvItem item = ModelFactory.createCsvItem("1352122280502,DE1234567896,invalidCcy,101.23,1000,103.45," +
				                                          "1000" + lineDelimiter);
		CertificateUpdateReport report = createCertificateUpdateReport(item);
		assertThat(report.isValid(), is(false));
		assertThat(report.getErrorReport() + ",", containsString(invalidCurrency));
	}

	@Test
	public void shouldHaveInvalidBidPrice() throws Exception {
		CsvItem item = ModelFactory.createCsvItem("1352122280502,DE1234567896,EUR,invalidBidPrice,1000,103.45," +
				                                          "1000" + lineDelimiter);
		CertificateUpdateReport report = createCertificateUpdateReport(item);
		assertThat(report.isValid(), is(false));
		assertThat(report.getErrorReport() + ",", containsString(invalidBidPrice));
	}

	@Test
	public void shouldHaveInvalidBidQuantity() throws Exception {
		CsvItem item = ModelFactory.createCsvItem("1352122280502,DE1234567896,EUR,101.23,invalidBidQty,103.45," +
				                                          "1000" + lineDelimiter);
		CertificateUpdateReport report = createCertificateUpdateReport(item);
		assertThat(report.isValid(), is(false));
		assertThat(report.getErrorReport() + ",", containsString(invalidBidSize));
	}

	@Test
	public void shouldHaveInvalidAskPrice() throws Exception {
		CsvItem item = ModelFactory.createCsvItem("1352122280502,DE1234567896,EUR,101.23,1000,invalidAskPrice," +
				                                          "1000" + lineDelimiter);
		CertificateUpdateReport report = createCertificateUpdateReport(item);
		assertThat(report.isValid(), is(false));
		assertThat(report.getErrorReport() + ",", containsString(invalidAskPrice));
	}

	@Test
	public void shouldHaveInvalidAskQuantity() throws Exception {
		CsvItem item = ModelFactory.createCsvItem("1352122280502,DE1234567896,EUR,101.23,1000,103.45," +
				                                          "invalidAskQty" + lineDelimiter);
		CertificateUpdateReport report = createCertificateUpdateReport(item);
		assertThat(report.isValid(), is(false));
		assertThat(report.getErrorReport() + ",", containsString(invalidAskSize));
	}

	@Test(expected = ParserException.class)
	public void shouldHaveThrownExceptionOnNulledCsvItem() throws Exception {
		createCertificateUpdateReport(null);
	}

	private CertificateUpdateReport createCertificateUpdateReport(CsvItem item) throws Exception {
		IsinCalculator calculator = new IsinCheckDigitCalculator(mappingTable);
		ItemMapper<CertificateUpdateReport, CsvItem> validator
				= new ValidatingCsvItemToCertUpdateReportMapper(calculator);
		return validator.mapItem(item);
	}
}
