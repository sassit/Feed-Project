package com.acme.feed.parser.sorter;

import com.acme.feed.common.model.CertificateUpdate;

import java.util.Comparator;

/**
 * User: Tarik Sassi
 * Date: 12/31/12
 * Time: 4:10 PM
 */
public class IsinComparator implements Comparator<CertificateUpdate> {
	@Override
	public int compare(CertificateUpdate certificateUpdate, CertificateUpdate certificateUpdate2) {
		return certificateUpdate.getIsin().compareTo(certificateUpdate2.getIsin());
	}
}
