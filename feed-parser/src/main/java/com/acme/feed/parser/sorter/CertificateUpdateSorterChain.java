package com.acme.feed.parser.sorter;

import com.acme.feed.common.model.CertificateUpdate;

import java.util.List;
import java.util.Map;

import static com.acme.feed.parser.Assert.assertNotNull;

/**
 * User: Tarik Sassi
 * Date: 12/31/12
 * Time: 5:28 PM
 */
public class CertificateUpdateSorterChain implements Sorter<Map<String, List<CertificateUpdate>>, List<CertificateUpdate>> {
	private final Sorter<List<CertificateUpdate>, List<CertificateUpdate>> isin;
	private final Sorter<Map<String, List<CertificateUpdate>>, List<CertificateUpdate>> bucket;

	public CertificateUpdateSorterChain(Sorter isin, Sorter bucket) {
		this.isin = isin;
		this.bucket = bucket;
	}

	@Override
	public Map<String, List<CertificateUpdate>> sort(List<CertificateUpdate> updates) {
		assertNotNull(updates, "Cannot sort on null updates.");
		return bucket.sort(isin.sort(updates));
	}
}
