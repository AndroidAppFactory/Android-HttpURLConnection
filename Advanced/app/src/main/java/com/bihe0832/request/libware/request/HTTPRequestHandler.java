package com.bihe0832.request.libware.request;

/**
 * 网络请求基类中将请求结果处理后的返回内容转化为业务逻辑相关错误
 */
public abstract class HTTPRequestHandler {
    public abstract void onSuccess(int statusCode,String response);
    public abstract void onFailure(int statusCode, String responseBody);
}
