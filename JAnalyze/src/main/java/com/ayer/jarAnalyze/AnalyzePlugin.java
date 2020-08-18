package com.ayer.jarAnalyze;

import com.ayer.jarAnalyze.model.AnalyzeMeta;

import java.util.List;

public interface AnalyzePlugin {
    AnalyzeMeta analyzeJar(String fp);
}
