package com.seven.log.plugins;

import com.seven.log.calc.SimHashService;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DDMPlugin implements LogPlugin {

    private final String IP_FILTER = "172.16.0.135";

    //重复文本去重
    private List<String> simhashs = new ArrayList<>();


    @Override
    public boolean isAcceptLog(String line) {
        return StringUtils.indexOf(line, IP_FILTER) > 0;
    }

    @Override
    public boolean isSaveLog(SLog log) {
        String strSimHash = log.getSimHash().strSimHash;
        boolean isTextCopy = false;
        for (String simhash : simhashs) {
            int distance = SimHashService.getDistance(strSimHash, simhash);
            if (distance < 8) {
                // 文本距离小于10 认为是相文本
                isTextCopy = true;
                break;
            }
        }
        if (isTextCopy) {
            return false;
        }
        simhashs.add(strSimHash);
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
