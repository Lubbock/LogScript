package com.ayer.jarAnalyze.model;

import com.google.common.collect.HashMultimap;
import lombok.Getter;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.List;

public class AnalyzeMeta {
    private String name;


    public AnalyzeMeta(@NonNull String name) {
        this.name = name;
    }

    /*
     * jar 中className 和 方法名信息
     * **/
    private @Getter HashMultimap<String, String> classMetas = HashMultimap.create();

    /*
     * jar依赖
     * **/
    private List<AnalyzeMeta> childrens = new ArrayList<>(200);

    public void addClassMeta(String className, String methodName) {
        classMetas.put(className, methodName);
    }

    public void addChildren(AnalyzeMeta meta) {
        childrens.add(meta);
    }
}
