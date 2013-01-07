package com.acme.feed.parser;

import com.acme.feed.common.utils.PropertyUtils;
import org.junit.Test;
import org.junit.internal.runners.JUnit4ClassRunner;
import org.junit.runner.RunWith;

import java.io.BufferedReader;
import java.io.File;

import static junit.framework.Assert.assertTrue;

/**
 * User: Tarik Sassi
 * Date: 12/31/12
 * Time: 12:22 PM
 */
@RunWith(JUnit4ClassRunner.class)
public class ParserAppFactoryIntegrationTest {
	public static final int numOfThreads = 1;

	@Test
	public void shouldHaveWrittenExpectedFilesAndItsContents() throws Exception {
		BufferedReader reader = TestUtils.loadFromClasspath("6CertificateUpdates1ErrorLine.csv");
		ParserAppFactory factory = new ParserAppFactory();
		ParserApp app = factory.create(PropertyUtils.loadProperties("parser.properties"), numOfThreads, reader,
		                               new File("report.xml"),
		                               new File("error.xml"));
		app.parse();
		BufferedReader errorXMLReader = TestUtils.loadFromClasspath("6CertificateUpdates1ErrorLineError.xml");
		BufferedReader reportXMLReader = TestUtils.loadFromClasspath("6CertificateUpdates1ErrorLineReport.xml");
		assertTrue(TestUtils.contentEquals(errorXMLReader, TestUtils.loadFromPath("error.xml")));
		assertTrue(TestUtils.contentEquals(reportXMLReader, TestUtils.loadFromPath("report.xml")));
		reader.close();
		errorXMLReader.close();
		reportXMLReader.close();
	}
}
