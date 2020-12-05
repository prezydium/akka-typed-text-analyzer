package com.presidium.analyzer.rule;

import com.presidium.analyzer.rule.function.LetterARule;
import com.presidium.analyzer.rule.function.WordGodRule;
import lombok.Getter;

import java.util.function.Function;

@Getter
public enum Rule {
    LETTER_A_OCCURRENCE(new LetterARule()),
    LONGEST_WORD(null),
    WORD_EYE_OCCURRENCE(null),
    WORD_GOD_OCCURRENCE(new WordGodRule()),
    NUMBER_OF_SPACES(null);

    private final String name;
    private final Function<String, Double> analysisFunction;

    Rule(Function<String, Double> analysisFunction) {
        name = this.name();
        this.analysisFunction = analysisFunction;
    }
}
