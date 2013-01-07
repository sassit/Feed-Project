package com.acme.feed.parser.calculator;

import com.acme.feed.common.model.CertificateUpdate;
import com.acme.feed.parser.ModelFactory;
import com.acme.feed.parser.ParserException;
import com.acme.feed.parser.model.CertificateStatistic;
import org.junit.Test;
import org.junit.internal.runners.JUnit4ClassRunner;
import org.junit.runner.RunWith;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static com.acme.feed.parser.FeedParserTestConstants.csv1Line;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

/**
 * User: Tarik Sassi
 * Date: 12/30/12
 * Time: 6:51 PM
 */
@RunWith(JUnit4ClassRunner.class)
public class CertificateUpdateStatisticsCalculatorTest {
	@Test
	public void shouldHaveCalculated1Report() throws Exception {
		List<CertificateUpdate> reports = ModelFactory.createCertificateUpdates(new String[]{csv1Line});
		Calculator<CertificateStatistic,
				          List<CertificateUpdate>> calculator = new CertificateUpdateStatisticsCalculator();
		CertificateStatistic statistic = calculator.calculate(reports);
		assertThat(statistic.getIsin(), equalTo("DE1234567896"));
		assertThat(statistic.getSize(), equalTo(1));
		assertThat(statistic.getAvgBidPrice(), equalTo(BigDecimal.valueOf(101.23)));
		assertThat(statistic.getAvgAskPrice(), equalTo(BigDecimal.valueOf(103.45)));
	}

	@Test
	public void shouldHaveCalculated2Reports() throws Exception {
		List<CertificateUpdate> updates = ModelFactory.createCertificateUpdates(new
				                                                                        String[]{csv1Line, csv1Line});
		Calculator<CertificateStatistic,
				          List<CertificateUpdate>> calculator = new CertificateUpdateStatisticsCalculator();
		CertificateStatistic statistic = calculator.calculate(updates);
		assertThat(statistic.getIsin(), equalTo("DE1234567896"));
		assertThat(statistic.getSize(), equalTo(2));
		assertThat(statistic.getAvgBidPrice(), equalTo(BigDecimal.valueOf(101.23)));
		assertThat(statistic.getAvgAskPrice(), equalTo(BigDecimal.valueOf(103.45)));
	}

	@Test(expected = ParserException.class)
	public void shouldHaveThrownExceptionOnEmptyReportsList() throws Exception {
		List<CertificateUpdate> updates = new ArrayList<CertificateUpdate>();
		Calculator<CertificateStatistic,
				          List<CertificateUpdate>> calculator = new CertificateUpdateStatisticsCalculator();
		calculator.calculate(updates);
	}

	@Test(expected = ParserException.class)
	public void shouldHaveThrownExceptionOnNulledReportsList() throws Exception {
		Calculator<CertificateStatistic,
				          List<CertificateUpdate>> calculator = new CertificateUpdateStatisticsCalculator();
		calculator.calculate(null);
	}
}
