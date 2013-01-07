package com.acme.feed.parser.filter;

import com.acme.feed.parser.model.CertificateUpdateReport;

import java.util.ArrayList;
import java.util.List;

import static com.acme.feed.parser.Assert.assertNotEmptyOrNull;

/**
 * User: Tarik Sassi
 * Date: 12/29/12
 * Time: 9:46 PM
 */
public class CertificateUpdateReportFilter implements Filter<List<CertificateUpdateReport>,
		                                                            List<CertificateUpdateReport>> {
	private final List<CertificateUpdateReport> valid = new ArrayList<CertificateUpdateReport>();
	private final List<CertificateUpdateReport> invalid = new ArrayList<CertificateUpdateReport>();

	public void filter(List<CertificateUpdateReport> reports) {
		assertNotEmptyOrNull(reports, "No reports provided.");
		for (CertificateUpdateReport report : reports) {
			if (report.isValid()) {
				valid.add(report);
			} else {
				invalid.add(report);
			}
		}
	}

	public List<CertificateUpdateReport> getValid() {
		return valid;
	}

	public List<CertificateUpdateReport> getInvalid() {
		return invalid;
	}
}
