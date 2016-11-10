package com.bwf.qingdan.utils.http;

/**
 * Created by LG on 2016/10/17.
 * Tips:
 */

public class StringBody extends RequestBody{
    private String body;
    public StringBody(String contentType,String body) {
        super(contentType);
        this.body = body;
    }
    @Override
    public byte[] getBodyBytes() {
        return body.getBytes();
    }
}
