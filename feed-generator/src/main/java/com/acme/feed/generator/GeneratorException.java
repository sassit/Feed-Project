package com.acme.feed.generator;

/**
 * User: Tarik Sassi
 * Date: 12/27/12
 * Time: 2:37 PM
 */
public class GeneratorException extends RuntimeException {
    public GeneratorException(String message, Exception e) {
        super(message, e);
    }

    public GeneratorException(String message) {
        super(message);
    }
}
