package com.acme.feed.parser.filter;

/**
 * User: Tarik Sassi
 * Date: 12/29/12
 * Time: 10:08 PM
 */
public interface Filter<T, V> {
	void filter(T t);
	V getValid();
	V getInvalid();
}
