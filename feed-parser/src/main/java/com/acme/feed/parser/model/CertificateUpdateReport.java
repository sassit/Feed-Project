package com.acme.feed.parser.model;

import com.acme.feed.common.model.CertificateUpdate;

/**
 * User: Tarik Sassi
 * Date: 12/29/12
 * Time: 5:51 PM
 */
public class CertificateUpdateReport {
	private final String csv;
	private final String errorReport;
	private final CertificateUpdate certificateUpdate;

	public CertificateUpdateReport(String csv, String errorReport, CertificateUpdate certificateUpdate) {
		this.csv = csv;
		this.errorReport = errorReport;
		this.certificateUpdate = certificateUpdate;
	}

	public String getCsv() {
		return csv;
	}

	public String getErrorReport() {
		return errorReport;
	}

	public CertificateUpdate getCertificateUpdate() {
		if (!isValid()) {
			return null;
		}
		return certificateUpdate;
	}

	public boolean isValid() {
		return errorReport == null || errorReport.length() == 0;
	}
}
