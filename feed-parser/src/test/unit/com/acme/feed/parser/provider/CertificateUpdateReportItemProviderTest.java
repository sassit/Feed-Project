package com.acme.feed.parser.provider;

import com.acme.feed.parser.ModelFactory;
import com.acme.feed.parser.mapper.ItemMapper;
import com.acme.feed.parser.model.CertificateUpdateReport;
import com.acme.feed.parser.model.CsvItem;
import com.acme.feed.parser.reader.ContentReader;
import org.hamcrest.Matchers;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static com.acme.feed.parser.FeedParserTestConstants.csv1Line;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

/**
 * User: Tarik Sassi
 * Date: 12/28/12
 * Time: 11:57 PM
 */
@RunWith(JMock.class)
public class CertificateUpdateReportItemProviderTest {
	private final Mockery context = new Mockery();
	private final ContentReader reader = context.mock(ContentReader.class);
	private final ItemMapper mapper = context.mock(ItemMapper.class);

	@Ignore
	@Test
	public void shouldProvide1CertificateUpdate() throws Exception {
		final CsvItem csvItem = ModelFactory.createCsvItem(csv1Line);
		final List<CsvItem> csvItems = ModelFactory.createCsvItems(csvItem.getCsvLine());
		final CertificateUpdateReport report = ModelFactory.createCertificateUpdateReport(csvItem);
		CertificateUpdateReportItemProvider provider = new CertificateUpdateReportItemProvider(reader, mapper);
		context.checking(new Expectations() {{
			oneOf(reader).read();
			will(returnValue(csvItems));
			oneOf(mapper).mapItem(csvItem);
			will(returnValue(report));
		}});
		assertThat(provider.provide(), Matchers.<CertificateUpdateReport>everyItem(equalTo(report)));
	}
}
