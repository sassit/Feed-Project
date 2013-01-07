package com.acme.feed.parser.reader;

import com.acme.feed.parser.TestUtils;
import com.acme.feed.parser.model.CsvItem;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.internal.runners.JUnit4ClassRunner;
import org.junit.runner.RunWith;

import java.io.BufferedReader;
import java.io.Reader;
import java.io.StringReader;
import java.util.List;

import static com.acme.feed.parser.FeedParserTestConstants.csv1Line;
import static com.acme.feed.parser.FeedParserTestConstants.csv1LineAsList;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * User: Tarik Sassi
 * Date: 12/28/12
 * Time: 10:36 PM
 */
@RunWith(JUnit4ClassRunner.class)
public class CsvContentReaderIntegrationTest {
    @Test
    public void shouldHave1LineItem() throws Exception {
        List<CsvItem> csvItems = provideReadContents(csv1Line);
        assertThat(csvItems, Matchers.<CsvItem>everyItem(Matchers.<CsvItem>hasProperty("lineItems",
                is(equalTo(csv1LineAsList)))));
        assertThat(csvItems, hasSize(1));
    }

    @Test
    public void shouldHave2LineItems() throws Exception {
        List<CsvItem> csvItems = provideReadContents(csv1Line + csv1Line);
        assertThat(csvItems, Matchers.<CsvItem>everyItem(Matchers.<CsvItem>hasProperty("lineItems",
                is(equalTo(csv1LineAsList)))));
        assertThat(csvItems, hasSize(2));
    }

    @Test
    public void shouldReturn6LineItems() throws Exception {
        BufferedReader reader = TestUtils.loadFromClasspath("6CertificateUpdates.csv");
        LineReader lineReader = CsvContentReaderFactory.create(reader);
        ContentReader<List<CsvItem>> contentReader = new CsvContentReader(lineReader);
        assertThat(contentReader.read(), hasSize(6));
    }

    private List<CsvItem> provideReadContents(String input) throws Exception {
        Reader stringReader = new StringReader(input);
        LineReader lineReader = CsvContentReaderFactory.create(stringReader);
        ContentReader<List<CsvItem>> contentReader = new CsvContentReader(lineReader);
        return contentReader.read();
    }
}