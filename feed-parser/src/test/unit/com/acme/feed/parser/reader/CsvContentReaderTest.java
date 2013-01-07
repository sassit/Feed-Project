package com.acme.feed.parser.reader;

import com.acme.feed.parser.FeedParserTestConstants;
import com.acme.feed.parser.ModelFactory;
import com.acme.feed.parser.ParserException;
import com.acme.feed.parser.model.CsvItem;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;

/**
 * User: Tarik Sassi
 * Date: 12/28/12
 * Time: 9:21 PM
 */
@RunWith(JMock.class)
public class CsvContentReaderTest {
    private final Mockery context = new Mockery();
    private final LineReader lineReader = context.mock(LineReader.class);

    @Test
    public void shouldHaveRead1Line() throws Exception {
        final CsvItem csvItem = ModelFactory.createCsvItem(FeedParserTestConstants.csv1Line);
        ContentReader<List<CsvItem>> csvReader = new CsvContentReader(lineReader);
        context.checking(new Expectations() {{
            exactly(1).of(lineReader).nextLine();
            will(returnValue(csvItem));
            exactly(1).of(lineReader).nextLine();
            will(returnValue(null));
        }});
        assertThat(csvReader.read(), hasSize(1));
    }

    @Test
    public void shouldHaveRead2Lines() throws Exception {
        final CsvItem csvItem = ModelFactory.createCsvItem(FeedParserTestConstants.csv1Line);
        ContentReader<List<CsvItem>> csvReader = new CsvContentReader(lineReader);
        context.checking(new Expectations() {{
            exactly(2).of(lineReader).nextLine();
            will(returnValue(csvItem));
            exactly(1).of(lineReader).nextLine();
            will(returnValue(null));
        }});
        assertThat(csvReader.read(), hasSize(2));
    }

    @Test(expected = ParserException.class)
    public void shouldHaveThrownExceptionOnNulledLineReader() throws Exception {
        ContentReader<List<CsvItem>> csvReader = new CsvContentReader(null);
        context.checking(new Expectations() {{
            ignoring(lineReader);
        }});
        csvReader.read();
    }
}
