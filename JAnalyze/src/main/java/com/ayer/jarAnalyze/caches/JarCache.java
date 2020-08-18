package com.ayer.jarAnalyze.caches;

import lombok.SneakyThrows;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class JarCache{
    private String basepath;

    private List<String> caches = new ArrayList<>(100);

    public JarCache() {
        String pathname = "./jarcache/" + UUID.randomUUID().toString();
        File cacheDir = new File(pathname);
        if (!cacheDir.exists()) {
            cacheDir.mkdirs();
        }
        this.basepath = pathname;
    }

    public static JarCache getInstance() {
        return new JarCache();
    }

    @SneakyThrows(Exception.class)
    public void cache(JarEntry entry, JarFile jarFile) {
        String name = entry.getName();
        String s = StringUtils.substringAfterLast(name, "/");
        try (
                InputStream is = jarFile.getInputStream(entry);
                FileOutputStream fos = new FileOutputStream(basepath + "/" + s)
        ) {
            IOUtils.copyLarge(is, fos);
            caches.add(basepath + "/" + s);
        }
    }

    public Iterator<String>  iterator() {
        return caches.iterator();
    }

    public void clear() {

    }
}
