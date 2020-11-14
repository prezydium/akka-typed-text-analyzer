package com.presidium.analyzer.rule.function;

import java.util.function.Function;

public class LetterARule implements Function<String, Double> {

    @Override
    public Double apply(String text) {
        long count = text.chars().filter(ch -> ch == 'a').count();
        return (double) count;
    }
}
