package com.acme.feed.common.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import static com.acme.feed.common.Constants.mathContext;

/**
 * User: Tarik Sassi
 * Date: 12/28/12
 * Time: 3:08 PM
 */
public class PropertyUtils {

	public static Properties loadProperties(String path) {
		Properties properties = new Properties();
		InputStream stream = null;
		try {
			stream = PropertyUtils.class.getClassLoader().getResourceAsStream(path);
			properties.load(stream);
		} catch (Exception e) {
			File appBase = new File(".");
			String current = appBase.getAbsolutePath();
			throw new RuntimeException("Could not load properties from: " + current, e);
		} finally {
			if (stream != null) {
				try {
					stream.close();
				} catch (IOException e) {
					throw new RuntimeException("Could not close property file.", e);
				}
			}
		}
		return properties;
	}

	public static BigDecimal propAsBigDecimal(Properties props, String prop) {
		return new BigDecimal(props.getProperty(prop), mathContext);
	}

	public static int propAsInt(Properties props, String key) {
		return Integer.valueOf(props.getProperty(key));
	}

	public static Set<String> propAsSet(Properties props, String key) {
		return new HashSet<String>(Arrays.asList(props.getProperty(key).split(",")));
	}
}
