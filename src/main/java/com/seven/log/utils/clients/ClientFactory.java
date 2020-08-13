package com.seven.log.utils.clients;

public class ClientFactory {
    public static Client createClient(ClientCtx ctx) throws Exception {
        ProtoEnum protoEnum = ctx.protoEnum();
        Client client;
        switch (protoEnum) {
            case SFTP:
                client = new SFTPClient(ctx);
                break;
            case FTP:
                client = new FTPClient(ctx);
                break;
            case FILE:
                client = new FileClient();
                break;
            default:
                client = new SFTPClient(ctx);
                break;
        }
        return client;
    }
}
