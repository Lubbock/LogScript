package com.seven.log.plugins;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.seven.log.calc.SimHashService;

import java.io.*;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LogInvoke {
    public static String invoke(String fp) throws Exception {
        try (FileInputStream inputStream = new FileInputStream(fp);
             BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        ) {
            String str = null;
            while ((str = bufferedReader.readLine()) != null) {
                LogCollect.collect(str);
            }
        }
        List<SLog> sLogs = LogCollect.endCollect();
        analSlog(sLogs);
        return "";
    }

    public static void saveAnalyResult(String fp, Collection<SLog> sLogs) throws Exception {
        try (
                FileOutputStream fos = new FileOutputStream(fp);
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(fos))
        ) {
            for (SLog item : sLogs) {
                List<String> lines = item.getLines();
                for (String line : lines) {
                    bufferedWriter.write(line);
                    bufferedWriter.newLine();
                }
            }
        }
    }

    /**
     * <p>长得像数据去除</p>
     * **/
    public static void analSlog(List<SLog> sLogs) {
        Multimap<String, SLog> mslogs = HashMultimap.create();
        System.out.println("============start analyies " + sLogs.size());
        for (SLog item : sLogs) {
            SimHashService simHash = item.getSimHash();
            try {
                if (hasAccept(item)) {
                    mslogs.put(simHash.strSimHash, item);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            saveAnalyResult("D:\\code\\LogScript\\src\\main\\resources\\res\\holmes.2020-01-14.0.analy.log", mslogs.values());
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("============after analyies " + mslogs.asMap().keySet().size());
    }

    public static boolean hasAccept(SLog sLog) {
        String exclude = "o.s.w.s.m.m.a.RequestMappingHandlerMapping - Mapped \"{[/]}\" onto public org.springframework.web.servlet.ModelAndView com.idss.Application.index()";
        List<String> lines = sLog.getLines();
        SimHashService x =new SimHashService(exclude, 64);

        for (int i = 0; i < lines.size(); i++) {
            String s = lines.get(i);
            SimHashService temp = new SimHashService(s, 64);
            int distance = SimHashService.getDistance(temp.strSimHash, x.strSimHash);
            if (distance < 13) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) throws Exception {
        invoke("D:\\code\\LogScript\\src\\main\\resources\\res\\holmes.2020-01-14.0.log");
    }
}
