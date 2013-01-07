package com.acme.feed.parser.transformer;

/**
 * User: Tarik Sassi
 * Date: 12/31/12
 * Time: 4:04 PM
 */
public interface Transformer<T, U> {
	T transform(U u);
}
