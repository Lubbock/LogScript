package com.seven.log.utils.clients;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class FileClient implements Client{
    @Override
    public InputStream get(String fp) throws Exception {
        return new FileInputStream(fp);
    }

    @Override
    public OutputStream open(String fp) throws Exception {
        return new FileOutputStream(fp);
    }

    @Override
    public void close() {

    }
}
