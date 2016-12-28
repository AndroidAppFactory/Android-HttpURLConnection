package com.bihe0832.request.libware;

/**
 * Created by hardyshi on 16/11/22.
 */
public abstract class HTTPRequestHandler {
    public abstract void onResponse(int statusCode,String response);
}
