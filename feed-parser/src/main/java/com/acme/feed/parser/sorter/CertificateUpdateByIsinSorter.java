package com.acme.feed.parser.sorter;

import com.acme.feed.common.model.CertificateUpdate;

import java.util.Collections;
import java.util.List;

/**
 * User: Tarik Sassi
 * Date: 12/31/12
 * Time: 4:49 PM
 */
public class CertificateUpdateByIsinSorter implements Sorter<List<CertificateUpdate>, List<CertificateUpdate>> {
	@Override
	public List<CertificateUpdate> sort(List<CertificateUpdate> updates) {
		Collections.sort(updates, new IsinComparator());
		return updates;
	}
}
