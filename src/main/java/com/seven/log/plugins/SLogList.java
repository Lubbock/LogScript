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

    public static void collect(boolean b, String line,LogPlugin lp) {
        if (b && threadLocalLog.get().hasElement()) {
            List<SLog> sLogs = threadLocalLogs.get();
            SLog temp = threadLocalLog.get();
            boolean isAccept = temp.calcSimHash(lp);
            if (isAccept) {
                sLogs.add(temp);
            }
            SLog item = new SLog();
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