package com.ayer.jarAnalyze;

import com.ayer.jarAnalyze.analyze.AnalyzePlugin01;
import com.ayer.jarAnalyze.model.AnalyzeMeta;
import com.google.common.collect.HashMultimap;

import java.util.Map;

public class JarEngine {
    static AnalyzePlugin analyzePlugin = new AnalyzePlugin01();

    public static void invoke(String fp) {
        AnalyzeMeta analyzeMeta = analyzePlugin.analyzeJar(fp);
        HashMultimap<String, String> classMetas = analyzeMeta.getClassMetas();
        for (Map.Entry<String, String> entry : classMetas.entries()) {
            System.out.println(entry.getKey() + "@" + entry.getValue());
        }
    }
}
