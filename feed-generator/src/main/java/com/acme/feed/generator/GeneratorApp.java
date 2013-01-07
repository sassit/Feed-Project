package com.acme.feed.generator;

import com.acme.feed.common.model.CertificateUpdate;
import com.acme.feed.generator.generators.CertificateUpdateGenerator;
import com.acme.feed.generator.writer.CertificateUpdatesBufferWriter;

import java.util.List;

/**
 * User: Tarik Sassi
 * Date: 12/28/12
 * Time: 3:18 PM
 */
public class GeneratorApp {

	private final CertificateUpdateGenerator generator;
	private final CertificateUpdatesBufferWriter writer;
	private final int numOfCerts;
	private final int numOfCertUpdates;

	public GeneratorApp(CertificateUpdateGenerator generator, CertificateUpdatesBufferWriter writer, int numOfCerts,
	                    int numOfCertUpdates) {
		this.generator = generator;
		this.writer = writer;
		this.numOfCerts = numOfCerts;
		this.numOfCertUpdates = numOfCertUpdates;
	}

	public void generate() throws GeneratorException {
		List<CertificateUpdate> updates = generateCerts();
		generateFile(updates);
	}

	private List<CertificateUpdate> generateCerts() {
		return generator.generateInputs(numOfCerts, numOfCertUpdates);
	}

	private void generateFile(List<CertificateUpdate> updates) throws GeneratorException {
		writer.write(updates);
	}
}
