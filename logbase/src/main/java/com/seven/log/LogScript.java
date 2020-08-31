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
        LogInvoke.invoke(client, "E:\\github\\LogScript\\logbase\\src\\main\\resources\\nohup.out");
    }
}
