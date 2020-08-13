package com.seven.log;


import com.seven.log.plugins.impl.LogInvoke;
import com.seven.log.utils.clients.*;
import com.seven.log.utils.clients.impl.ClientCtx;
import com.seven.log.utils.clients.impl.ProtoEnum;

public class LogScript {
    public static void main(String[] args) throws Exception {
        ClientCtx clientCtx = new ClientCtx();
        clientCtx.protoEnum(ProtoEnum.FILE);
        Client client = ClientFactory.createClient(clientCtx);
        LogInvoke.invoke(client, "D:\\code\\LogScript\\logbase\\src\\main\\resources\\holmes.2020-01-14.0.log");
    }
}
