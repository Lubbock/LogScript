package com.ayer.jarAnalyze.analyze;


import com.ayer.jarAnalyze.AnalyzePlugin;
import com.ayer.jarAnalyze.caches.JarCache;
import com.ayer.jarAnalyze.constants.SysConstant;
import com.ayer.jarAnalyze.model.AnalyzeMeta;
import lombok.NonNull;
import lombok.SneakyThrows;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class AnalyzePlugin01 implements AnalyzePlugin {
    @Override
    @SneakyThrows(Exception.class)
    public AnalyzeMeta analyzeJar(@NonNull String fp) {
        File jf = new File(fp);
        URL url = jf.toURI().toURL();
        URLClassLoader myClassLoader = new URLClassLoader(new URL[]{url},
                Thread.currentThread().getContextClassLoader());
        JarFile jar = new JarFile(fp);
        //返回zip文件条目的枚举
        Enumeration<JarEntry> enumFiles = jar.entries();
        JarEntry entry;
        List<JarEntry> jarCaches = new ArrayList<>();
        //测试此枚举是否包含更多的元素
        AnalyzeMeta analyzeMeta = new AnalyzeMeta(fp);
        JarCache cache = JarCache.getInstance();
        while (enumFiles.hasMoreElements()) {
            entry = enumFiles.nextElement();
            if (!entry.getName().contains(SysConstant.META_INF)) {
                String classFullName = entry.getName();
                if (!classFullName.endsWith(".class")) {
                    //跳过
                    if (classFullName.endsWith(".jar")) {
                        cache.cache(entry, jar);
                    }
                } else {
                    //去掉后缀.class
                    String className = classFullName.substring(0, classFullName.length() - 6).replace("/", ".");
                    Class<?> myclass = myClassLoader.loadClass(className);
                    //打印类名
                    //得到类中包含的属性
                    Method[] methods = myclass.getMethods();
                    for (Method method : methods) {
                        String methodName = method.getName();
                        analyzeMeta.addClassMeta(className, methodName);
                        Class<?>[] parameterTypes = method.getParameterTypes();
                        for (Class<?> clas : parameterTypes) {
                            // String parameterName = clas.getName();
                            String parameterName = clas.getSimpleName();
                        }
                        ;
                    }
                }
            }
        }

        Iterator<String> iterator = cache.iterator();
        if (iterator.hasNext()) {
            String next = iterator.next();
            AnalyzeMeta item = analyzeJar(next);
            analyzeMeta.addChildren(item);
        }
        cache.clear();
        return analyzeMeta;
    }
}
