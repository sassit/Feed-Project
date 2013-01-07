package com.acme.feed.generator.randomiser;

import java.util.Random;
import java.util.Set;

/**
 * User: Tarik Sassi
 * Date: 12/26/12
 * Time: 12:08 PM
 */
public class CountryCodeRandomiser implements ValuesProvider<String> {
    private final Random random = new Random();
    private final String[] countryCodes;

    public CountryCodeRandomiser(Set<String> countryCodes) {
        this.countryCodes = countryCodes.toArray(new String[countryCodes.size()]);
    }

    @Override
    public String provide() {
        return countryCodes[random.nextInt(countryCodes.length)];
    }
}
