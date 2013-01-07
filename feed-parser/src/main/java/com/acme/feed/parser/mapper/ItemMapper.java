package com.acme.feed.parser.mapper;

/**
 * User: Tarik Sassi
 * Date: 12/29/12
 * Time: 2:10 PM
 */
public interface ItemMapper<T, U> {
	T mapItem(U u);
}
