package com.bihe0832.request.libware.request;
public interface HttpResponseHandler<T> {
	public void onResponse(T response);
}
