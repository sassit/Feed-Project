package com.acme.feed.parser.transformer;

import com.acme.feed.common.model.CertificateUpdate;
import com.acme.feed.parser.model.CertificateUpdateReport;

import java.util.ArrayList;
import java.util.List;

import static com.acme.feed.parser.Assert.assertNotNull;

/**
 * User: Tarik Sassi
 * Date: 12/31/12
 * Time: 4:06 PM
 */
public class CertificateReportToUpdateTransformer implements Transformer<List<CertificateUpdate>,
		                                                                        List<CertificateUpdateReport>> {
	@Override
	public List<CertificateUpdate> transform(List<CertificateUpdateReport> reports) {
		assertNotNull(reports, "Cannot transform null reports.");
		List<CertificateUpdate> updates = new ArrayList<CertificateUpdate>();
		for (CertificateUpdateReport report : reports) {
			updates.add(report.getCertificateUpdate());
		}
		return updates;
	}
}
