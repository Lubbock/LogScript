package com.seven.log.plugins.impl;

import com.seven.log.calc.SimHashService;
import com.seven.log.plugins.LogPlugin;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class DDMPlugin implements LogPlugin {

    private final String IP_FILTER = "172.16.0.135";

    //重复文本去重
    private List<String> simhashs = new ArrayList<>();


    @Override
    public boolean isAcceptLog(String line) {
        return StringUtils.indexOf(line, IP_FILTER) > -1;
    }

    @Override
    public boolean isSaveLog(SLog log) {
        String s = log.getLines().get(0);
        if (StringUtils.contains(s, "notifySql")) {
            return true;
        }
        return false;
    }

    @Override
    public String transform(String line) {
        return StringUtils.substringAfter(line, "DbproxyAgent]");
    }
}
