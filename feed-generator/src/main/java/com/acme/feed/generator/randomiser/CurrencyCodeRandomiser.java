package com.acme.feed.generator.randomiser;

import java.util.Random;
import java.util.Set;

/**
 * User: Tarik Sassi
 * Date: 12/26/12
 * Time: 9:39 PM
 */
public class CurrencyCodeRandomiser implements ValuesProvider<String>  {
    private final Random random = new Random();
    private final String[] ccyCodes;

    public CurrencyCodeRandomiser(Set<String> ccyCodes) {
        this.ccyCodes = ccyCodes.toArray(new String[ccyCodes.size()]);
    }

    @Override
    public String provide() {
        return ccyCodes[random.nextInt(ccyCodes.length)];
    }
}
