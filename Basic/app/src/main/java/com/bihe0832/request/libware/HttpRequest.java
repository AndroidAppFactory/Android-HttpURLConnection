package com.bihe0832.request.libware;


import android.util.Log;

import java.util.HashMap;


public abstract class HttpRequest {

	private static final String LOG_TAG = "bihe0832 REQUEST";
	private static String HTTP_DOMAIN = "http://microdemo.bihe0832.com";

	public static final String HTTP_REQ_ENTITY_MERGE = "=";
	public static final String HTTP_REQ_ENTITY_JOIN = "&";

	protected String path = "";
	protected long requestTime = 0;
	public byte[] data = null;
	public HashMap<String,String> cookieInfo = new HashMap<>();

	protected abstract String getUrl();

	protected abstract void onRequest(int statusCode, String errorResponse);

	protected HttpRequest(String cgi){
		if(TextUtils.ckIsEmpty(cgi)){
			Log.d(LOG_TAG,"path is null");
			path = "";
		}else{
			path = cgi;
		}
	}

	private String getDomain() {
		return HTTP_DOMAIN;
    }

	protected String getBaseUrl() {
		return getDomain() + path;
	}

	public HTTPRequestHandler mHttpResponseHandler = new HTTPRequestHandler() {

		@Override
		public void onResponse(int statusCode, String responseBody) {
			onRequest(statusCode, responseBody);
		}
	};
}
