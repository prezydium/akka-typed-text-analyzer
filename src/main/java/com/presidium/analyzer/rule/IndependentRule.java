package com.presidium.analyzer.rule;

import com.presidium.analyzer.rule.function.LetterARule;
import com.presidium.analyzer.rule.function.WordGodRule;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
public enum IndependentRule {
    LETTER_A_OCCURRENCE(new LetterARule()),
   // LONGEST_WORD(null),
    WORD_GOD_OCCURRENCE(new WordGodRule());

    private final String name;
    private final Function<String, Double> analysisFunction;

    IndependentRule(Function<String, Double> analysisFunction) {
        name = this.name();
        this.analysisFunction = analysisFunction;
    }

    public static List<String> getListOfRuleNames(){
        return Arrays.stream(IndependentRule.values()).map(IndependentRule::getName).collect(Collectors.toList());
    }
}
