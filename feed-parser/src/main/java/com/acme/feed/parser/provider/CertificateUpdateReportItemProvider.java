package com.acme.feed.parser.provider;

import com.acme.feed.parser.mapper.ItemMapper;
import com.acme.feed.parser.model.CertificateUpdateReport;
import com.acme.feed.parser.model.CsvItem;
import com.acme.feed.parser.reader.ContentReader;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Tarik Sassi
 * Date: 12/29/12
 * Time: 12:09 PM
 */
public class CertificateUpdateReportItemProvider implements ItemProvider<List<CertificateUpdateReport>> {
	private final ContentReader<List<CsvItem>> reader;
	private final ItemMapper<CertificateUpdateReport, CsvItem> mapper;

	public CertificateUpdateReportItemProvider(ContentReader<List<CsvItem>> reader,
	                                           ItemMapper<CertificateUpdateReport, CsvItem> mapper) {
		this.reader = reader;
		this.mapper = mapper;
	}

	public List<CertificateUpdateReport> provide() {
		List<CertificateUpdateReport> reports = new ArrayList<CertificateUpdateReport>();
		for (CsvItem item : reader.read()) {
			CertificateUpdateReport report = mapper.mapItem(item);
			reports.add(report);
		}
		return reports;
	}
}
