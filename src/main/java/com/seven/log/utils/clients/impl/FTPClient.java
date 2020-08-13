package com.seven.log.utils.clients.impl;

import com.seven.log.utils.clients.Client;
import org.apache.commons.lang.StringUtils;

import java.io.InputStream;
import java.io.OutputStream;

public class FTPClient implements Client {

    private FtpUtil conn;

    public FTPClient(ClientCtx ctx) {
        conn = new FtpUtil(ctx.address(), ctx.port(), ctx.username(), ctx.password(), ctx.charset());
        conn.initFtpClient();
        conn.connect();
    }

    @Override
    public InputStream get(String fp) throws Exception {
        String currentPath = conn.getCurrentPath();
        conn.disconnect();
        conn.initFtpClient();
        conn.completePendingCommand();
        InputStream is = conn.getInputStreamNoConvert(fp);
        if (is == null) {
            if (StringUtils.isNotBlank(currentPath) && currentPath.length() > 1) {
                if (StringUtils.startsWith(fp, currentPath)) {
                    fp = fp.substring(currentPath.length());
                    is = conn.getInputStream(fp);
                }
            }
        }
        return is;
    }

    @Override
    public OutputStream open(String fp) throws Exception {
        //ftp有个连接超时的问题，在这个位置断掉重新连一下
        String currentPath = conn.getCurrentPath();
        conn.disconnect();
        conn.initFtpClient();
        if (StringUtils.isNotBlank(currentPath)) {
            conn.changeWorkingDirectory(currentPath);
        }
        conn.completePendingCommand();
        return conn.getOutputStreamNoConvert(fp);
    }

    @Override
    public void close() {
        conn.completePendingCommand();
        conn.disconnect();
    }
}
