package com.seven.log;


import com.seven.log.plugins.LogInvoke;
import com.seven.log.utils.clients.*;

import java.io.InputStream;

public class LogScript {
    public static void main(String[] args) throws Exception {
        ClientCtx clientCtx = new ClientCtx();
        clientCtx.protoEnum(ProtoEnum.FILE);
        Client client = ClientFactory.createClient(clientCtx);
        LogInvoke.invoke(client, "D:\\code\\LogScript\\src\\main\\resources\\res\\holmes.2020-01-14.0.log");
    }
}
