package com.bwf.qingdan.utils.http;

import android.util.Log;

import com.bwf.qingdan.utils.http.qingdan.HttpClient;

import org.junit.Test;

/**
 * Created by LG on 2016/11/1.
 * Tips:
 */
public class HttpUtilsTest {
    @Test
    public void execute() throws Exception {
        String json = "{\"client_id\":\"herEv4O9tg4PuviM\",\"client_secret\":\"v20ulmkZP5ykQMn9uUwNbyEiuTkzFfPn\",\"grant_type\":\"client_credentials\"}";
        RequestBody body = new StringBody(RequestBody.jsonContentType,json);
        Request requestToken = new Request.Builder()
                .url("http://api.eqingdan.com/v1/oauth2/access-token")
                .post(body)
                .build();
        HttpUtils.getInstance().execute(requestToken, new HttpUtils.Callback() {
            @Override
            public void onResponse(String response) {
                Log.d("HttpUtilsTest", response);
            }

            @Override
            public void onError() {

            }
        });
        Request.Builder builder = new Request.Builder()
                .url("http://api.eqingdan.com/v1/articles/846")
                .get();
        HttpClient.excute(builder, new HttpUtils.Callback() {
            @Override
            public void onResponse(String response) {
                Log.d("HttpUtilsTest", response);
            }

            @Override
            public void onError() {

            }
        });
    }

}