package com.presidium.analyzer.rule.function;

import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;

public class LetterAToOthersRule implements BiFunction<String, Map<String, Double>, Double> {


    @Override
    public Double apply(String text, Map<String, Double> previousResults) {
        return 0.0;
    }
}
