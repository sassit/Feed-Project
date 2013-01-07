package com.acme.feed.parser;

import com.acme.feed.parser.calculator.Calculator;
import com.acme.feed.parser.filter.Filter;
import com.acme.feed.parser.model.CertificateStatistic;
import com.acme.feed.parser.model.CertificateUpdateReport;
import com.acme.feed.parser.provider.ItemProvider;
import com.acme.feed.parser.writer.SharedExecutorWriter;

import java.util.List;

/**
 * User: Tarik Sassi
 * Date: 12/29/12
 * Time: 9:57 PM
 */
public class ParserApp {
	private final Filter<List<CertificateUpdateReport>, List<CertificateUpdateReport>> filter;
	private final ItemProvider<List<CertificateUpdateReport>> provider;
	private final SharedExecutorWriter shared;
	private final Calculator<List<CertificateStatistic>, List<CertificateUpdateReport>> calculator;

	public ParserApp(Filter filter, ItemProvider provider,
	                 SharedExecutorWriter shared, Calculator calculator
	) {
		this.filter = filter;
		this.provider = provider;
		this.shared = shared;
		this.calculator = calculator;
	}

	public void parse() {
		filter.filter(provider.provide());
		List<CertificateUpdateReport> validUpdates = filter.getValid();
		List<CertificateUpdateReport> invalidUpdates = filter.getInvalid();
		shared.write(calculator.calculate(validUpdates), invalidUpdates);
	}
}
