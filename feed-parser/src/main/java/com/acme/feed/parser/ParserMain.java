package com.acme.feed.parser;

import com.sun.tools.internal.ws.processor.generator.GeneratorException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Properties;

import static com.acme.feed.common.utils.PropertyUtils.loadProperties;

/**
 * User: Tarik Sassi
 * Date: 1/2/13
 * Time: 7:51 PM
 */
public class ParserMain {
	private final String[] args;

	private ParserMain(String[] args) {
		this.args = args;
	}

	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		ParserMain parser = new ParserMain(args);
		parser.start();
		System.out.println("Run time: " + (System.currentTimeMillis() - start) / 1000 + "s");
	}

	private void start() {
		try {
			Config config = configure(args);
			ParserApp app = ParserAppFactory.create(config.props, config.numOfThreads,
			                                        new BufferedReader(new FileReader(config.inputFile)),
			                                        new File(config.reportFile),
			                                        new File(config.errorFile));
			app.parse();
		} catch (Exception e) {
			System.err.println("Aborting parse.");
			e.printStackTrace();
			System.exit(1);
		}
	}

	private Config configure(String[] args) throws GeneratorException {
		String message = "Usage: <numOfThreads> <inputfileName> <priceReportFilename> " +
				                 "<exceptionReportFilename>";
		if (args.length != 4) {
			System.err.println(message);
			System.exit(1);
		}
		int numOfThreads = 0;
		try {
			numOfThreads = Integer.valueOf(args[0]);
		} catch (NumberFormatException e) {
			System.err.println(message);
			System.exit(1);
		}
		String inputFile = String.valueOf(args[1]);
		String reportFile = String.valueOf(args[2]);
		String errorFile = String.valueOf(args[3]);
		if (numOfThreads <= 0 || inputFile == null || reportFile == null || errorFile == null) {
			System.err.println("All numerical parameters have to be bigger than 0.");
			System.exit(1);
		}
		Properties props = loadProperties("parser.properties");
		return new Config(props, numOfThreads, inputFile, reportFile, errorFile);
	}

	private class Config {
		private final Properties props;
		private final int numOfThreads;
		private final String inputFile;
		private final String reportFile;
		private final String errorFile;

		private Config(Properties props, int numOfThreads, String inputFile, String reportFile, String errorFile) {
			this.props = props;
			this.numOfThreads = numOfThreads;
			this.inputFile = inputFile;
			this.reportFile = reportFile;
			this.errorFile = errorFile;
		}
	}
}
