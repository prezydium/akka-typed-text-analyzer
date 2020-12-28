package com.presidium.analyzer.rule;

import com.presidium.analyzer.rule.function.LetterAToOthersRule;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

@Getter
public enum DependentRule {

    LETTER_A_TO_OTHERS(new LetterAToOthersRule());

    private final String name;
    private final BiFunction<String, Map<String, Double>, Double> analysisFunction;

    DependentRule(BiFunction<String, Map<String, Double>, Double> analysisFunction) {
        name = this.name();
        this.analysisFunction = analysisFunction;
    }

    public static List<String> getListOfRuleNames(){
        return Arrays.stream(DependentRule.values()).map(DependentRule::getName).collect(Collectors.toList());
    }
}
