package com.acme.feed.parser.writer;

import com.acme.feed.parser.model.CertificateStatistic;
import com.acme.feed.parser.model.CertificateUpdateReport;

import java.util.List;
import java.util.concurrent.ExecutorService;

/**
 * User: Tarik Sassi
 * Date: 1/1/13
 * Time: 9:33 PM
 */
public class SharedExecutorWriter {
	private final Writer<List<CertificateStatistic>> reportWriter;
	private final Writer<List<CertificateUpdateReport>> errorWriter;
	private final ExecutorService executor;

	public SharedExecutorWriter(Writer<List<CertificateStatistic>> reportWriter, Writer<List<CertificateUpdateReport>> errorWriter, ExecutorService executor) {
		this.reportWriter = reportWriter;
		this.errorWriter = errorWriter;
		this.executor = executor;
	}

	public void write(List<CertificateStatistic> statistics, List<CertificateUpdateReport> reports) {
		writeStatistics(statistics);
		writeErrors(reports);
		executor.shutdown();
	}

	private void writeStatistics(List<CertificateStatistic> statistics)  {
		reportWriter.write(statistics);
	}

	private void writeErrors(List<CertificateUpdateReport> reports) {
		errorWriter.write(reports);
	}
}
