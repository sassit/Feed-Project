package com.acme.feed.parser.reader;

import com.acme.feed.parser.ParserException;
import org.junit.Test;
import org.junit.internal.runners.JUnit4ClassRunner;
import org.junit.runner.RunWith;

import java.io.StringReader;
import java.util.Scanner;

/**
 * User: Tarik Sassi
 * Date: 12/30/12
 * Time: 11:10 PM
 */
@RunWith(JUnit4ClassRunner.class)
public class CsvLineReaderTest {

    @Test(expected = ParserException.class)
    public void shouldHaveThrownExceptionOnNulledScanner() throws Exception {
        CsvLineReader csvLineReader = new CsvLineReader(null, ",");
        csvLineReader.nextLine();
    }

    @Test(expected = ParserException.class)
    public void shouldHaveThrownExceptionOnNulledDelimiter() throws Exception {
        CsvLineReader csvLineReader = new CsvLineReader(new Scanner(new StringReader("")), null);
        csvLineReader.nextLine();
    }
}
