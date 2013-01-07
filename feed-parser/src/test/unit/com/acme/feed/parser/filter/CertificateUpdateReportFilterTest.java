package com.acme.feed.parser.filter;

import com.acme.feed.parser.ModelFactory;
import com.acme.feed.parser.ParserException;
import com.acme.feed.parser.model.CertificateUpdateReport;
import com.acme.feed.parser.model.CsvItem;
import org.junit.Test;
import org.junit.internal.runners.JUnit4ClassRunner;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import static com.acme.feed.common.Constants.lineDelimiter;
import static com.acme.feed.parser.ModelFactory.createCertificateUpdateReports;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;

/**
 * User: Tarik Sassi
 * Date: 12/29/12
 * Time: 11:00 PM
 */
@RunWith(JUnit4ClassRunner.class)
public class CertificateUpdateReportFilterTest {
    @Test
    public void shouldHaveFiltered1InvalidItemAndNoValidOnes() throws Exception {
        CsvItem item = ModelFactory.createCsvItem("1352122280502,DE1234567896,EUR,invalidAskPrice,1000,103.45," +
                "1000" + lineDelimiter);
        List<CertificateUpdateReport> reports = createCertificateUpdateReports(item);
        Filter<List<CertificateUpdateReport>, List<CertificateUpdateReport>> filter = new CertificateUpdateReportFilter();
        filter.filter(reports);
        assertThat(filter.getInvalid(), hasSize(1));
        assertThat(filter.getValid(), hasSize(0));
    }

    @Test
    public void shouldHaveFiltered1ValidtemAndNoValidOnes() throws Exception {
        CsvItem item = ModelFactory.createCsvItem("1352122280502,DE1234567896,EUR,100.45,1000,103.45," +
                "1000" + lineDelimiter);
        List<CertificateUpdateReport> reports = createCertificateUpdateReports(item);
        Filter<List<CertificateUpdateReport>, List<CertificateUpdateReport>> filter = new CertificateUpdateReportFilter();
        filter.filter(reports);
        assertThat(filter.getInvalid(), hasSize(0));
        assertThat(filter.getValid(), hasSize(1));
    }

    @Test(expected = ParserException.class)
    public void shouldHaveThrownExceptionOnNulledReports() throws Exception {
        Filter<List<CertificateUpdateReport>, List<CertificateUpdateReport>> filter = new CertificateUpdateReportFilter();
        filter.filter(null);
    }

    @Test(expected = ParserException.class)
    public void shouldHaveThrownExceptionOnEmptyReports() throws Exception {
        Filter<List<CertificateUpdateReport>, List<CertificateUpdateReport>> filter = new CertificateUpdateReportFilter();
        filter.filter(new ArrayList<CertificateUpdateReport>());
    }
}
