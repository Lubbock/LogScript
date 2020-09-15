package com.seven.log.plugins.impl;

import com.seven.log.plugins.LogPlugin;

import java.util.ArrayList;
import java.util.List;

class SLogList {
    static ThreadLocal<List<SLog>> slogs = new ThreadLocal<>();

    static ThreadLocal<SLog> slog = new ThreadLocal<>();

    static {
        slogs.set(new ArrayList<>(20));
        slog.set(new SLog());
    }

    //todo  数据量太大，simHash不支持，还要来个布隆过滤器
    public static void collect(boolean b, String line, LogPlugin lp) {
        int sort = 1;
        if (b && slog.get().hasElement()) {
            List<SLog> sLogs = slogs.get();
            SLog temp = slog.get();
            boolean isAccept = temp.acceptLog(lp);
            if (isAccept) {
                sLogs.add(temp);
            }
            SLog item = new SLog();
            item.setSort(sort);
            sort += 1;
            slog.set(item);
        }
        slog.get().addLine(line);
    }

    public static List<SLog> endCollect(LogPlugin lp) {
        SLog e = slog.get();
        boolean isAccept = e.acceptLog(lp);
        if (isAccept) {
            slogs.get().add(e);
        }
        return slogs.get();
    }
}