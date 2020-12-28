package com.presidium.analyzer.analysis;

import lombok.Value;

import java.util.Map;

@Value
public class AnalysisCache {
    String text;
    Map<String, Double> analysisResult;

}
