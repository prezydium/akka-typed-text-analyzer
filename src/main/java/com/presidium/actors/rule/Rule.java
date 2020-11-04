package com.presidium.actors.rule;

import lombok.Getter;

import java.util.function.Function;

@Getter
public enum Rule {
    WORD_A_OCCURRENCE(null),
    LONGEST_WORD(null),
    WORD_EYE_OCCURRENCE(null),
    WORD_GOD_OCCURRENCE(null),
    NUMBER_OF_SPACES(null);

    private final String name;
    private final Function<String, Double> analysisFunction;

    Rule(Function<String, Double> analysisFunction) {
        name = this.name();
        this.analysisFunction = analysisFunction;
    }
}
