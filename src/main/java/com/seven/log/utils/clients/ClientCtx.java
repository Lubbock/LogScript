package com.seven.log.utils.clients;


import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(fluent = true)
public class ClientCtx {
    private String username;

    private String password;

    private String address;

    private Integer port;

    private String charset;
}
