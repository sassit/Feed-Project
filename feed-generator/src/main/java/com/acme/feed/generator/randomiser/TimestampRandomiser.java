package com.acme.feed.generator.randomiser;

/**
 * User: Tarik Sassi
 * Date: 12/26/12
 * Time: 10:30 PM
 */
public class TimestampRandomiser implements ValuesProvider<Long>  {
    @Override
    public Long provide() {
        return System.currentTimeMillis();
    }
}
