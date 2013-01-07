package com.acme.feed.parser;

import java.io.*;

import static com.acme.feed.common.Constants.codingScheme;

/**
 * User: Tarik Sassi
 * Date: 12/31/12
 * Time: 8:03 PM
 */
public class TestUtils {
	public static BufferedReader loadFromClasspath(String path) throws UnsupportedEncodingException {
		InputStream inStream = TestUtils.class.getClassLoader().getResourceAsStream(path);
		return new BufferedReader(new InputStreamReader(inStream, codingScheme));
	}

	public static BufferedReader loadFromPath(String path) throws FileNotFoundException {
		return new BufferedReader(new FileReader(path));
	}

	public static boolean contentEquals(BufferedReader reader1, BufferedReader reader2) throws IOException {
		String line1 = reader1.readLine();
		while (line1 != null) {
			String line2 = reader2.readLine();
			if (!line1.equals(line2)) {
				return false;
			}
			line1 = reader1.readLine();
		}
		return true;
	}
}
