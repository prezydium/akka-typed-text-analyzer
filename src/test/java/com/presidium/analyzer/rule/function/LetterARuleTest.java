package com.presidium.analyzer.rule.function;

import com.presidium.analyzer.rule.Rule;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LetterARuleTest {

    Rule ruleA = Rule.LETTER_A_OCCURRENCE;

    @Test
    public void shouldReturn0(){
        String testInput = "blehblehbleh\n bleh";
        Double result = ruleA.getAnalysisFunction().apply(testInput);
        assertEquals(0, result, 0);

    }


}