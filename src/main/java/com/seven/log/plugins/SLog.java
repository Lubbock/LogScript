package com.seven.log.plugins;

import com.seven.log.calc.SimHashService;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
class SLog {
    private List<String> lines = new ArrayList<>(20);

    private SimHashService simHash = null;

    private int sort = 0;

    public void addLine(String line) {
        lines.add(line);
    }

    public boolean hasElement() {
        return lines.size() > 0;
    }

    public boolean calcSimHash(LogPlugin lp) {
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
        }
        if (!isAccept) {
            return false;
        }
        lines.set(0, lp.transform(lines.get(0)));
        simHash = new SimHashService(sb.toString(), 64);
        return true;
    }
}