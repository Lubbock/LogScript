package com.seven.log.utils.clients;

import java.io.InputStream;
import java.io.OutputStream;

public interface Client {
    InputStream get(String fp) throws Exception;

    OutputStream open(String fp) throws Exception;

    void close();
}
