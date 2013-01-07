package com.acme.feed.generator;

import java.util.Properties;

import static com.acme.feed.common.utils.PropertyUtils.loadProperties;

/**
 * User: Tarik Sassi
 * Date: 12/27/12
 * Time: 1:33 PM
 */
public class GeneratorMain {
	private final String[] args;

	private GeneratorMain(String[] args) {
		this.args = args;
	}

	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		GeneratorMain generator = new GeneratorMain(args);
		generator.start();
		System.out.println("Run time: " + (System.currentTimeMillis() - start) / 1000 + "s");
	}

	private void start() {
		try {
			Config config = configure(args);
			GeneratorApp app = GeneratorAppFactory.create(config.props, config.numOfCerts, config.numOfCertUpdates,
			                                              config.numOfThreads, config.outputFile);
			app.generate();
		} catch (Exception e) {
			System.err.println("Aborting feed generation.");
			e.printStackTrace();
			System.exit(1);
		}
	}

	private Config configure(String[] args) throws GeneratorException {
		String message = "Usage: <numOfCerts> <numOfUpdatesPerCert> <numOfThreads> <outputFile>";
		if (args.length != 4) {
			System.err.println(message);
			System.exit(1);
		}
		int numOfCerts = 0;
		int numOfUpdates = 0;
		int numOfThreads = 0;
		try {
			numOfCerts = Integer.valueOf(args[0]);
			numOfUpdates = Integer.valueOf(args[1]);
			numOfThreads = Integer.valueOf(args[2]);
		} catch (NumberFormatException e) {
			System.err.println(message);
			System.exit(1);
		}
		if (numOfCerts <= 0 || numOfUpdates <= 0 || numOfThreads <= 0) {
			System.err.println("All numerical parameters have to be bigger than 0.");
			System.exit(1);
		}
		Properties props = loadProperties("generator.properties");
		return new Config(props, numOfCerts, numOfUpdates,
		                  numOfThreads, args[3]);
	}

	private class Config {
		private final Properties props;
		private final int numOfCerts;
		private final int numOfCertUpdates;
		private final int numOfThreads;
		private final String outputFile;

		private Config(Properties props, int numOfCerts, int numOfCertUpdates, int numOfThreads, String outputFile) {
			this.props = props;
			this.numOfCerts = numOfCerts;
			this.numOfCertUpdates = numOfCertUpdates;
			this.numOfThreads = numOfThreads;
			this.outputFile = outputFile;
		}
	}
}
