package com.acme.feed.parser.calculator;

import com.acme.feed.common.model.CertificateUpdate;
import com.acme.feed.parser.ModelFactory;
import com.acme.feed.parser.ParserException;
import com.acme.feed.parser.model.CertificateStatistic;
import com.acme.feed.parser.model.CertificateUpdateReport;
import com.acme.feed.parser.sorter.Sorter;
import com.acme.feed.parser.transformer.Transformer;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static com.acme.feed.parser.FeedParserTestConstants.csv1Line;
import static com.acme.feed.parser.ModelFactory.createCertificateUpdateReports;
import static com.acme.feed.parser.ModelFactory.createCertificateUpdates;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.junit.Assert.assertThat;

/**
 * User: Tarik Sassi
 * Date: 1/2/13
 * Time: 1:03 PM
 */
@RunWith(JMock.class)
public class CertificateUpdatesStatisticAggregatorTest {
	private final Mockery context = new Mockery();
	private final Sorter sorter = context.mock(Sorter.class);
	private final Transformer transformer = context.mock(Transformer.class);
	private final Calculator calculator = context.mock(Calculator.class);
	private final CertificateStatistic expected = new CertificateStatistic("DE1234567896", 1, new BigDecimal(101.23),
	                                                                       new BigDecimal(101.45));

	@Test
	public void shouldHaveCalculatedExpectedStatistic() throws Exception {
		Calculator<List<CertificateStatistic>, List<CertificateUpdateReport>> aggregator
				= new CertificateUpdatesStatisticAggregator(sorter, transformer, calculator);
		final List<CertificateUpdate> updates = createCertificateUpdates(csv1Line);
		final List<CertificateUpdateReport> reports = createCertificateUpdateReports(csv1Line);
		final Map<String, List<CertificateUpdate>> buckets = ModelFactory.createCertificateUpdateReportBuckets(updates);
		context.checking(new Expectations() {{
			oneOf(transformer).transform(reports);
			will(returnValue(updates));
			oneOf(sorter).sort(updates);
			will(returnValue(buckets));
			oneOf(calculator).calculate(updates);
			will(returnValue(expected));
		}});
		List<CertificateStatistic> calculate = aggregator.calculate(reports);
		assertThat(calculate, hasItem(expected));
	}

	@Test(expected = ParserException.class)
	public void shouldHaveThrownExceptionOnNulledReports() throws Exception {
		Calculator<List<CertificateStatistic>, List<CertificateUpdateReport>> aggregator
				= new CertificateUpdatesStatisticAggregator(sorter, transformer, calculator);
		context.checking(new Expectations() {{
			ignoring(sorter);
			ignoring(transformer);
			ignoring(calculator);
		}});
		aggregator.calculate(null);
	}
}
