package com.acme.feed.parser.reader;

import com.acme.feed.parser.model.CsvItem;
import org.junit.Test;
import org.junit.internal.runners.JUnit4ClassRunner;
import org.junit.runner.RunWith;

import java.io.StringReader;

import static com.acme.feed.parser.FeedParserTestConstants.csv1Line;
import static com.acme.feed.parser.FeedParserTestConstants.csv1LineAsList;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

/**
 * User: Tarik Sassi
 * Date: 12/28/12
 * Time: 5:33 PM
 */
@RunWith(JUnit4ClassRunner.class)
public class CsvContentReaderFactoryIntegrationTest {
    @Test
    public void shouldHaveRead1Line() throws Exception {
        LineReader<CsvItem> csvLineReader = provideReader(csv1Line);
        assertThat(csvLineReader.nextLine().getLineItems(), is(equalTo(csv1LineAsList)));
    }

    @Test
    public void shouldHaveReturnedNullOnSecondAttempt() throws Exception {
        LineReader<CsvItem> csvLineReader = provideReader(csv1Line);
        csvLineReader.nextLine();
        assertThat(csvLineReader.nextLine(), nullValue());
    }

    @Test
    public void shouldHaveRead2Lines() throws Exception {
        LineReader<CsvItem> csvLineReader = provideReader(csv1Line + csv1Line);
        assertThat(csvLineReader.nextLine().getLineItems(), is(equalTo(csv1LineAsList)));
        assertThat(csvLineReader.nextLine().getLineItems(), is(equalTo(csv1LineAsList)));
    }

    private CsvLineReader provideReader(String input) {
        return CsvContentReaderFactory.create(new StringReader(input));
    }
}
