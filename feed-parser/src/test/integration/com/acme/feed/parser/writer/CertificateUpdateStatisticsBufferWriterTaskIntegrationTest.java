package com.acme.feed.parser.writer;

import com.acme.feed.common.model.CertificateUpdate;
import com.acme.feed.parser.ModelFactory;
import com.acme.feed.parser.calculator.Calculator;
import com.acme.feed.parser.calculator.CertificateUpdateStatisticsCalculator;
import com.acme.feed.parser.model.CertificateStatistic;
import org.junit.Test;
import org.junit.internal.runners.JUnit4ClassRunner;
import org.junit.runner.RunWith;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;

import static com.acme.feed.common.Constants.codingScheme;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

/**
 * User: Tarik Sassi
 * Date: 12/30/12
 * Time: 9:17 PM
 */
@RunWith(JUnit4ClassRunner.class)
public class CertificateUpdateStatisticsBufferWriterTaskIntegrationTest {
	@Test
	public void shouldHaveCalculatedExpectedResult() throws Exception {
		String csv1Line = "1352122280502,DE1234567896,EUR,101.23,1000,103.45,1000" + com.acme.feed.common.Constants.lineDelimiter;
		String expected = "<Certificate isin=\"DE1234567896\" numOfUpdates=\"1\" avgBidPrice=\"101.23\" avgAskPrice=\"103.45\"/>";
		ByteBuffer buffer = ByteBuffer.allocate(expected.length() * 2);
		List<CertificateUpdate> updates = ModelFactory.createCertificateUpdates(new String[]{csv1Line});
		Calculator<CertificateStatistic, List<CertificateUpdate>> calculator
				= new CertificateUpdateStatisticsCalculator();
		CertificateStatistic statistic = calculator.calculate(updates);
		Callable<Void> writer = new CertificateUpdateStatisticsBufferWriterTask(buffer,
		                                                                    Collections.singletonList(statistic));
		writer.call();
		assertThat(retrieveBufferContent(buffer), equalTo(expected));
	}

	private String retrieveBufferContent(ByteBuffer buffer) throws UnsupportedEncodingException {
		return new String(buffer.array(), codingScheme).trim();
	}
}
