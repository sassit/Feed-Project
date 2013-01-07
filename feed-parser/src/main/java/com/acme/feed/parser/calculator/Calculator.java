package com.acme.feed.parser.calculator;

/**
 * User: Tarik Sassi
 * Date: 12/30/12
 * Time: 7:01 PM
 */
public interface Calculator<T, V> {
	T calculate(V v);
}
