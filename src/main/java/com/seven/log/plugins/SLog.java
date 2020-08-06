package com.seven.log.plugins;

import com.seven.log.calc.SimHashService;
import lombok.Data;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Data
class SLog {
    private List<String> lines = new ArrayList<>(20);

    private SimHashService simHash = null;

    public void addLine(String line) {
        lines.add(line);
    }

    public boolean hasElement() {
        return lines.size() > 0;
    }

    public void calcSimHash() {
        StringBuilder sb = new StringBuilder();
        int i = 0;
        for (String line : lines) {
            if (i == 0) {
                line = StringUtils.substringAfter(line,"INFO");
                sb.append("\n");
            }
            i++;
            sb.append(line);
        }
        simHash = new SimHashService(sb.toString(), 64);
    }
}