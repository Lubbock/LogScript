package com.seven.log.plugins.impl;

import com.seven.log.calc.BloomFilterService;
import com.seven.log.calc.SimHashService;
import com.seven.log.plugins.LogPlugin;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class SLog {
    private List<String> lines = new ArrayList<>(20);

    private int sort = 0;

    public void addLine(String line) {
        lines.add(line);
    }

    public boolean hasElement() {
        return lines.size() > 0;
    }

    public boolean acceptLog(LogPlugin lp) {
        StringBuilder sb = new StringBuilder();
        int i = 0;
        boolean isAccept = false;
        for (String line : lines) {
            if (i == 0) {
                isAccept = lp.isAcceptLog(line);
                if (!isAccept) {
                    break;
                }
                line = lp.transform(line);
                sb.append("\n");
            }
            i++;
            sb.append(line);
            if (sb.length() > 2048) {
                isAccept = false;
            }
        }
        if (isAccept) {
            boolean isPresent = BloomFilterService.getInstance().putIfNotPresent(sb.toString());
            lines.set(0, lp.transform(lines.get(0)));
            return !isPresent;
        }
        return isAccept;
    }
}