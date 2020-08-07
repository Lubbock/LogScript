package com.seven.log.plugins;

import org.apache.commons.lang.StringUtils;

import java.util.HashSet;
import java.util.Set;

public class DDMPlugin implements LogPlugin {

    private final String IP_FILTER = "172.16.0.135";

    //重复文本去重
    private Set<String> unique = new HashSet<>();


    @Override
    public boolean isAcceptLog(String line) {
        return StringUtils.indexOf(line, IP_FILTER) > 0;
    }

    @Override
    public boolean isSaveLog(SLog log) {
        String strSimHash = log.getSimHash().strSimHash;
        if (unique.contains(strSimHash)) {
            return false;
        }
        unique.add(strSimHash);
        String s = log.getLines().get(0);
        if (StringUtils.startsWith(s, "notifySql")) {
            return true;
        }
        return false;
    }

    @Override
    public String transform(String line) {
        return StringUtils.substringAfter(line, "DbproxyAgent]");
    }
}
