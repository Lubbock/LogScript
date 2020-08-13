package com.seven.log.plugins;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.seven.log.calc.SimHashService;
import com.seven.log.utils.clients.Client;
import com.seven.log.utils.clients.ClientCtx;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class LogInvoke {
    public static String invoke(Client client, String fp) throws Exception {
        DDMPlugin ddmPlugin = new DDMPlugin();
        try (
                InputStream inputStream = client.get(fp);
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        ){
            String str = null;
            while ((str = bufferedReader.readLine()) != null) {
                LogCollect.collect(str, ddmPlugin);
            }
        }finally {
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
        Multimap<String, SLog> mslogs = HashMultimap.create();
        System.out.println("============start analyies " + sLogs.size());
        for (SLog item : sLogs) {
            SimHashService simHash = item.getSimHash();
            try {
                if (lp.isSaveLog(item)) {
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
