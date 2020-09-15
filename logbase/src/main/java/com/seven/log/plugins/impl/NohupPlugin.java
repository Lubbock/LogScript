package com.seven.log.plugins.impl;

import com.seven.log.plugins.LogPlugin;
import org.apache.commons.lang.StringUtils;

public class NohupPlugin implements LogPlugin {

    @Override
    public boolean isAcceptLog(String line) {
        return true;
    }

    @Override
    public boolean isSaveLog(SLog log) {
        return true;
    }

    @Override
    public String transform(String line) {
        return StringUtils.substringAfter(line, "]");
    }
}
