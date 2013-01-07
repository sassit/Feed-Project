package com.acme.feed.parser;

import java.util.Collection;

/**
 * User: Tarik Sassi
 * Date: 12/30/12
 * Time: 8:59 PM
 */
public class Assert {
	public static void assertNotEmptyOrNull(Collection collection, String message) {
		if (collection == null || collection.size() == 0) {
			throw new ParserException(message + "\nValue: " + collection);
		}
	}

	public static void assertNotNull(Object object, String message) {
		if (object == null) {
			throw new ParserException(message + "\nValue: " + object);
		}
	}
}
