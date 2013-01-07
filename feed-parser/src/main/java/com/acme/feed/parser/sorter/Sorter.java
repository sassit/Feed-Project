package com.acme.feed.parser.sorter;

/**
 * User: Tarik Sassi
 * Date: 12/31/12
 * Time: 4:49 PM
 */
public interface Sorter<T, U> {
	T sort(U u);
}
