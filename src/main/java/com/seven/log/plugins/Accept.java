package com.seven.log.plugins;

public interface Accept {
    boolean isAcceptLog(String line);

    boolean isSaveLog(SLog log);
}
