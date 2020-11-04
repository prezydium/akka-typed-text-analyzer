package com.presidium.actors.analysis;

import lombok.Value;

import java.util.Map;

@Value
public class AnalysisCache {
    String fileName;
    String text;
    Map<String, Double> analysisResult;

}
