package com.seven.log.utils.clients;

import com.jcraft.jsch.*;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

public class SFTPClient implements Client {

    private ChannelSftp chan;

    public SFTPClient(ClientCtx ctx) throws Exception {
        chan = connect(ctx);
    }

    public ChannelSftp connect(ClientCtx ctx) throws Exception {
        JSch jsch = new JSch();
        Session ssh = jsch.getSession(ctx.username(), ctx.address(), ctx.port());
        ssh.setPassword(ctx.password());
        Properties sshConfig = new Properties();
        sshConfig.put("StrictHostKeyChecking", "no");
        ssh.setConfig(sshConfig);
        ssh.connect();
        Channel chan = ssh.openChannel("sftp");
        chan.connect();
        return (ChannelSftp) chan;
    }

    @Override
    public InputStream get(String fp) throws Exception {
        return chan.get(fp);
    }

    @Override
    public OutputStream open(String fp) throws Exception {
        OutputStream os = null;
        sftpMkDir(chan, fp);
        if (chan != null) {
            os = chan.put(fp);
        } else {
//            log.error("sftp创建目录失败");
        }
        return os;
    }

    public static void sftpMkDir(ChannelSftp sftp, String fileName) throws SftpException {
        if (sftp != null) {
            if (fileName.startsWith("/")) {
                fileName = fileName.substring(1);
            }
            String tempName = "";
            if (fileName.contains("/")) {
                String tempPath = fileName.substring(0, fileName.lastIndexOf("/"));
                try {
                    sftp.stat(tempPath);
                } catch (Exception e) {
                    e.printStackTrace();
                    for (String temp : tempPath.split("/")) {
                        if (tempName.isEmpty()) {
                            tempName = temp;
                        } else {
                            tempName += "/" + temp;
                        }
                        try {
                            sftp.stat(tempName);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                            sftp.mkdir(tempName);
                        }
                    }
                }
            }
        }
    }

    @Override
    public void close() {
        chan.disconnect();
    }
}
