package com.shunyi.autoparts.ui.common;

import okhttp3.*;

import java.io.IOException;

public class HttpRequest {

    private static final MediaType JSON  = MediaType.get("application/json; charset=utf-8");

    // avoid creating several instances, should be singleon
    private static OkHttpClient client = new OkHttpClient();

    /**
     * Get
     *
     * @param url
     * @param token
     * @return
     * @throws IOException
     */
    public static String GET(String url, String token) throws IOException {
        HttpUrl.Builder urlBuilder = HttpUrl.parse(url).newBuilder();
//        urlBuilder.addQueryParameter("v", "1.0");
//        urlBuilder.addQueryParameter("user", "vogella");
        url = urlBuilder.build().toString();
        Request request = new Request.Builder()
                .header("Authorization", token)
                .url(url)
                .build();
        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }

    /**
     * Post
     *
     * @param url
     * @param json
     * @param token
     * @return
     * @throws IOException
     */
    public static String POST(String url, String json, String token) throws IOException {
        RequestBody body = RequestBody.create(json, JSON);
        Request request = new Request.Builder()
                .header("Authorization", token)
                .url(url)
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }
}
