package com.acme.feed.parser.sorter;

import com.acme.feed.common.model.CertificateUpdate;
import com.acme.feed.parser.Assert;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: Tarik Sassi
 * Date: 12/31/12
 * Time: 4:33 PM
 */
public class CertificateUpdateToBucketSorter implements Sorter<Map<String, List<CertificateUpdate>>, List<CertificateUpdate>> {
	@Override
	public Map<String, List<CertificateUpdate>> sort(List<CertificateUpdate> updates) {
		Assert.assertNotNull(updates, "Cannot sort on null updates.");
		Map<String, List<CertificateUpdate>> sorted = new HashMap<String, List<CertificateUpdate>>();
		for (CertificateUpdate update : updates) {
			String isin = update.getIsin();
			if (sorted.containsKey(isin)) {
				sorted.get(isin).add(update);
			} else {
				List<CertificateUpdate> list = new ArrayList<CertificateUpdate>();
				list.add(update);
				sorted.put(isin, list);
			}
		}
		return sorted;
	}
}
