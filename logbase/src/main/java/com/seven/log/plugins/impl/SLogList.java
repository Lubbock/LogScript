package com.seven.log.plugins.impl;

import com.seven.log.plugins.LogPlugin;

import java.util.ArrayList;
import java.util.List;

class SLogList {
    static ThreadLocal<List<SLog>> threadLocalLogs = new ThreadLocal<>();

    static ThreadLocal<SLog> threadLocalLog = new ThreadLocal<>();

    static {
        threadLocalLogs.set(new ArrayList<>(20));
        threadLocalLog.set(new SLog());
    }

    //todo  数据量太大，simHash不支持，还要来个布隆过滤器
    public static void collect(boolean b, String line, LogPlugin lp) {
        int sort = 1;
        if (b && threadLocalLog.get().hasElement()) {
            List<SLog> sLogs = threadLocalLogs.get();
            SLog temp = threadLocalLog.get();
            boolean isAccept = temp.calcSimHash(lp);
            if (isAccept) {
                sLogs.add(temp);
            }
            SLog item = new SLog();
            item.setSort(sort++);
            threadLocalLog.set(item);
        }
        threadLocalLog.get().addLine(line);
    }

    public static List<SLog> endCollect(LogPlugin lp) {
        SLog e = threadLocalLog.get();
        boolean isAccept = e.calcSimHash(lp);
        if (isAccept) {
            threadLocalLogs.get().add(e);
        }
        return threadLocalLogs.get();
    }
}