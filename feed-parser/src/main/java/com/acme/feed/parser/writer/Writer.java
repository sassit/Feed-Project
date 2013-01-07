package com.acme.feed.parser.writer;

/**
 * User: Tarik Sassi
 * Date: 12/29/12
 * Time: 10:16 PM
 */
public interface Writer<T> {
	void write(T t);
}
