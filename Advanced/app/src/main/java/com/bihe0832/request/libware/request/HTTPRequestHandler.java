package com.bihe0832.request.libware.request;

/**
 * Created by hardyshi on 16/11/22.
 */
public abstract class HTTPRequestHandler {
    public abstract void onSuccess(int statusCode,String response);
    public abstract void onFailure(int statusCode, String responseBody);
}
