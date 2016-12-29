package com.bihe0832.request.libware.request;

/**
 * 具体业务网络请求结果实例的基类
 */
public interface HttpResponseHandler<T> {
	public void onResponse(T response);
}
