package com.shunyi.autoparts.ui.http;

import com.shunyi.autoparts.ui.common.ENV;
import com.shunyi.autoparts.ui.model.RemoteConnection;
import okhttp3.*;

import java.io.IOException;

/** HTTP客户端 */
public class HttpClient {

    private static final MediaType JSON  = MediaType.get("application/json; charset=utf-8");

    // avoid creating several instances, should be singleon
    private static OkHttpClient client = new OkHttpClient();

    /**
     * Http get
     *
     * @param path
     * @return
     * @throws IOException
     */
    public static String GET(String path) throws IOException {
        return GET(path, ENV.getInstance().lookup("Authorization").toString());
    }

    /**
     * Http get
     *
     * @param path
     * @param token
     * @return
     * @throws IOException
     */
    public static String GET(String path, String token) throws IOException {
        RemoteConnection rc = (RemoteConnection) ENV.getInstance().lookup("RemoteConnection");
        String url = rc.getProtocol()+"://"+rc.getHostName()+":"+rc.getPort()+""+path;
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
     *
     * @param path
     * @param json
     * @return
     * @throws IOException
     */
    public static String POST(String path, String json) throws IOException {
        return POST(path, json, ENV.getInstance().lookup("Authorization").toString());
    }

    /**
     * Http post
     *
     * @param path
     * @param json
     * @param token
     * @return
     * @throws IOException
     */
    public static String POST(String path, String json, String token) throws IOException {
        RemoteConnection rc = (RemoteConnection) ENV.getInstance().lookup("RemoteConnection");
        String url = rc.getProtocol()+"://"+rc.getHostName()+":"+rc.getPort()+""+path;
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

    /**
     *
     * @param path
     * @param json
     * @return
     * @throws IOException
     */
    public static String PUT(String path, String json) throws IOException {
        String token = ENV.getInstance().lookup("Authorization").toString();
        RemoteConnection rc = (RemoteConnection) ENV.getInstance().lookup("RemoteConnection");
        String url = rc.getProtocol()+"://"+rc.getHostName()+":"+rc.getPort()+""+path;
        RequestBody body = RequestBody.create(json, JSON);
        Request request = new Request.Builder()
                .header("Authorization", token)
                .url(url)
                .put(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }

    /**
     *
     * @param path
     * @return
     * @throws IOException
     */
    public static String DELETE(String path) throws IOException {
        String token = ENV.getInstance().lookup("Authorization").toString();
        RemoteConnection rc = (RemoteConnection) ENV.getInstance().lookup("RemoteConnection");
        String url = rc.getProtocol()+"://"+rc.getHostName()+":"+rc.getPort()+""+path;
        HttpUrl.Builder urlBuilder = HttpUrl.parse(url).newBuilder();
        url = urlBuilder.build().toString();
        Request request = new Request.Builder()
                .header("Authorization", token)
                .url(url)
                .delete()
                .build();
        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }
}