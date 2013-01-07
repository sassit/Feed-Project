package com.acme.feed.parser;

/**
 * User: Tarik Sassi
 * Date: 12/28/12
 * Time: 6:35 PM
 */
public class ParserException extends RuntimeException {
	public ParserException(String message) {
		super(message);
	}

	public ParserException(String message, Exception e) {
		super(message, e);
	}
}
