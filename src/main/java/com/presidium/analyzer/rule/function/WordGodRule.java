package com.presidium.analyzer.rule.function;

import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WordGodRule implements Function<String, Double> {

   private static final Pattern pattern = Pattern.compile("[Gg][Oo][Dd][\\s.'?!]");

    @Override
    public Double apply(String text) {
        Matcher matcher = pattern.matcher(text);
        long wordGodOccurrencesInText = matcher.results().count();
        return (double) wordGodOccurrencesInText;
    }
}
