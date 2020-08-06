package com.seven.log.plugins;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LogCollect {

    static Pattern pattern = Pattern.compile("^(\\d{4}-\\d{2}-\\d{2})");

    public static String collect(String str) {
        Matcher matcher = pattern.matcher(str);
        boolean b = matcher.find();
        SLogList.collect(b, str);
        return str;
    }

    public static List<SLog> endCollect() {
        return SLogList.endCollect();
    }
}
