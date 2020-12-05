package com.presidium.analyzer.rule.function;

import com.presidium.analyzer.rule.Rule;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WordGodRuleTest {

    private final Rule wordGodRule = Rule.WORD_GOD_OCCURRENCE;


    @Test
    public void testWordGodRule(){
        String text = "God almighty is good and woe to godless people. prise the God!";
        Double result = wordGodRule.getAnalysisFunction().apply(text);
        assertEquals(2.0, result, 0);
    }

}