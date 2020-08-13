package com.seven.log.plugins;

import com.seven.log.plugins.impl.SLog;

public interface Accept {
    boolean isAcceptLog(String line);

    boolean isSaveLog(SLog log);
}
