package com.seven.log.plugins.impl;

import com.seven.log.calc.SimHashService;
import com.seven.log.plugins.LogPlugin;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class NohupPlugin implements LogPlugin {

    //重复文本去重
    private List<String> simhashs = new ArrayList<>();


    @Override
    public boolean isAcceptLog(String line) {
        return true;
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
//        if (StringUtils.contains(s, "notifySql")) {
            return true;
//        }
//        return false;
    }

    @Override
    public String transform(String line) {
        return StringUtils.substringAfter(line, "]");
    }
}
