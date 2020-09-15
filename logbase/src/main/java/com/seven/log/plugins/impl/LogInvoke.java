package com.seven.log.plugins.impl;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.seven.log.calc.SimHashService;
import com.seven.log.plugins.LogPlugin;
import com.seven.log.utils.clients.Client;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class LogInvoke {
    public static String invoke(Client client, String fp) throws Exception {
        NohupPlugin ddmPlugin = new NohupPlugin();
        try (
                InputStream inputStream = client.get(fp);
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        ) {
            String str = null;
            while ((str = bufferedReader.readLine()) != null) {
                LogCollect.collect(str, ddmPlugin);
            }
        } finally {
            client.close();
        }
        List<SLog> sLogs = LogCollect.endCollect(ddmPlugin);
        analSlog(sLogs, ddmPlugin);
        return "";
    }

    public static void saveAnalyResult(String fp, Collection<SLog> sLogs) throws Exception {
        try (
                FileOutputStream fos = new FileOutputStream(fp);
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(fos))
        ) {
            List<SLog> items = sLogs.stream().sorted(Comparator.comparingInt(SLog::getSort)).collect(Collectors.toList());
            for (SLog item : items) {
                List<String> lines = item.getLines();
                for (String line : lines) {
                    bufferedWriter.write(line);
                    bufferedWriter.newLine();
                    bufferedWriter.newLine();
                }
            }
        }
    }

    /**
     * <p>长得像数据去除</p>
     **/
    public static void analSlog(List<SLog> sLogs, LogPlugin lp) {
        List<SLog> slogs = new ArrayList<>();
        for (SLog item : sLogs) {
            try {
                if (lp.isSaveLog(item)) {
                    slogs.add(item);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            saveAnalyResult("E:\\github\\LogScript\\logbase\\src\\main\\resources\\nohup.analy.out", slogs);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("============after analyies " + sLogs.size());
    }

    public static boolean hasAccept(SLog sLog) {
        String exclude = "o.s.w.s.m.m.a.RequestMappingHandlerMapping - Mapped \"{[/]}\" onto public org.springframework.web.servlet.ModelAndView com.idss.Application.index()";
        List<String> lines = sLog.getLines();
        SimHashService x = new SimHashService(exclude, 64);

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
}
