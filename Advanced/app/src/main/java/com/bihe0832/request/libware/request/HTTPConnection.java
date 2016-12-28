package com.bihe0832.request.libware.request;

import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by hardyshi on 16/11/22.
 */
public class HTTPConnection extends BaseConnection {

    private HttpURLConnection mConn = null;
    public HTTPConnection(String url) {
        super();
        try {
            mConn = (HttpURLConnection)new URL(url).openConnection();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    protected HttpURLConnection getURLConnection() {
        return mConn;
    }
}
