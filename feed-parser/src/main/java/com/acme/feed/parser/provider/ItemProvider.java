package com.acme.feed.parser.provider;

/**
 * User: Tarik Sassi
 * Date: 12/29/12
 * Time: 10:03 PM
 */
public interface ItemProvider<T> {
	T provide();
}
