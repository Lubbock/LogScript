package com.seven.log.plugins;

import java.util.ArrayList;
import java.util.List;

class SLogList {
    static ThreadLocal<List<SLog>> threadLocalLogs = new ThreadLocal<>();

    static ThreadLocal<SLog> threadLocalLog = new ThreadLocal<>();

    static {
        threadLocalLogs.set(new ArrayList<>(20));
        threadLocalLog.set(new SLog());
    }

    public static void collect(boolean b, String line) {
        if (b && threadLocalLog.get().hasElement()) {
            List<SLog> sLogs = threadLocalLogs.get();
            SLog temp = threadLocalLog.get();
            temp.calcSimHash();
            sLogs.add(temp);
            SLog item = new SLog();
            threadLocalLog.set(item);
        }
        threadLocalLog.get().addLine(line);
    }

    public static List<SLog> endCollect() {
        SLog e = threadLocalLog.get();
        e.calcSimHash();
        threadLocalLogs.get().add(e);
        return threadLocalLogs.get();
    }
}