package com.acme.feed.generator.randomiser;

import java.util.Random;

/**
 * User: Tarik Sassi
 * Date: 12/26/12
 * Time: 9:51 PM
 */
public class QuantityRandomiser implements ValuesProvider<Integer> {
    private final Random random = new Random();
    private final int min, max;

    public QuantityRandomiser(int min, int max) {
        this.min = min;
        this.max = max;
    }

    @Override
    public Integer provide() {
        return random.nextInt(max - min + 1) + min;
    }
}
