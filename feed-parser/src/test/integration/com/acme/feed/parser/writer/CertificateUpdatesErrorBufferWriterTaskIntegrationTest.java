package com.acme.feed.parser.writer;

import com.acme.feed.parser.ModelFactory;
import com.acme.feed.parser.model.CertificateUpdateReport;
import org.junit.Test;
import org.junit.internal.runners.JUnit4ClassRunner;
import org.junit.runner.RunWith;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.List;
import java.util.concurrent.Callable;

import static com.acme.feed.common.Constants.codingScheme;
import static com.acme.feed.common.Constants.lineDelimiter;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

/**
 * User: Tarik Sassi
 * Date: 12/30/12
 * Time: 2:17 PM
 */
@RunWith(JUnit4ClassRunner.class)
public class CertificateUpdatesErrorBufferWriterTaskIntegrationTest {
	@Test
	public void shouldHaveWritten1XMLErrorLine() throws Exception {
		String csv1Line = "1352122280502,invalidIsin,EUR,101.23,1000,103.45,1000" + lineDelimiter;
		String expected = "<Details\n\t\tfeedLine=\"1352122280502,invalidIsin,EUR,101.23,1000,103.45," +
				                  "1000\"\n\t\texception=\"invalid isin\"\n\t/>";
		ByteBuffer buffer = ByteBuffer.allocate(expected.length() * 2);
		List<CertificateUpdateReport> reports = ModelFactory.createCertificateUpdateReports(csv1Line);
		Callable<Void> writer = new CertificateUpdatesErrorBufferWriterTask(buffer, reports);
		writer.call();
		assertThat(retrieveBufferContent(buffer), equalTo(expected));
	}

	private String retrieveBufferContent(ByteBuffer buffer) throws UnsupportedEncodingException {
		return new String(buffer.array(), codingScheme).trim();
	}
}
