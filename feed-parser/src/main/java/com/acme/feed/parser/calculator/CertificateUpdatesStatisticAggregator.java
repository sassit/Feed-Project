package com.acme.feed.parser.calculator;

import com.acme.feed.common.model.CertificateUpdate;
import com.acme.feed.parser.Assert;
import com.acme.feed.parser.model.CertificateStatistic;
import com.acme.feed.parser.model.CertificateUpdateReport;
import com.acme.feed.parser.sorter.Sorter;
import com.acme.feed.parser.transformer.Transformer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * User: Tarik Sassi
 * Date: 12/31/12
 * Time: 6:02 PM
 */
public class CertificateUpdatesStatisticAggregator implements Calculator<List<CertificateStatistic>, List<CertificateUpdateReport>> {
	private final Sorter<Map<String, List<CertificateUpdate>>, List<CertificateUpdate>> sorter;
	private final Transformer<List<CertificateUpdate>, List<CertificateUpdateReport>> transformer;
	private final Calculator<CertificateStatistic, List<CertificateUpdate>> calculator;

	public CertificateUpdatesStatisticAggregator(Sorter sorter, Transformer transformer, Calculator calculator) {
		this.sorter = sorter;
		this.transformer = transformer;
		this.calculator = calculator;
	}

	@Override
	public List<CertificateStatistic> calculate(List<CertificateUpdateReport> reports) {
		Assert.assertNotNull(reports, "Cannot calculate on null reports.");
		Map<String, List<CertificateUpdate>> buckets = sorter.sort(transformer.transform(reports));
		List<CertificateStatistic> statistics = new ArrayList<CertificateStatistic>();
		for (String isin : buckets.keySet()) {
			List<CertificateUpdate> updates = buckets.get(isin);
			statistics.add(calculator.calculate(updates));
		}
		return statistics;
	}
}
