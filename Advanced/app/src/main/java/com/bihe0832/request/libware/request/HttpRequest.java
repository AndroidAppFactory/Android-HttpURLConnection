package com.bihe0832.request.libware.request;


import android.util.Log;

import com.bihe0832.request.libware.TextUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 *
 * 网络请求实例类的基类，所有具体的网络请求都是他的实现，他与{@link HttpResponse}一般成对使用
 *
 */

public abstract class HttpRequest {

	private  static final String LOG_TAG = "bihe0832 REQUEST";


	public static final String HTTP_REQ_ENTITY_MERGE = "=";
	public static final String HTTP_REQ_ENTITY_JOIN = "&";

	protected static String HTTP_DOMAIN = "http://microdemo.bihe0832.com";
	protected String path = "";
	protected long requestTime = 0;
	public byte[] data = null;
	public HashMap<String,String> cookieInfo = new HashMap<>();

	protected abstract String getUrl();

	protected abstract void onRequestSuccess(int statusCode, JSONObject responseJson);

	protected abstract void onRequestFailure(int statusCode, String errorResponse);

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
		public void onSuccess(int statusCode,String response) {
			try {
				if(TextUtils.ckIsEmpty(response)){
					Log.e(LOG_TAG,"responseBody is null");
				}else{
					try {
						JSONObject json = new JSONObject(response);
						int ret = json.getInt(HttpResponse.HTTP_RESP_PARAM_RET);
						onRequestSuccess(ret, json);
					} catch (JSONException e) {
						onRequestFailure(HTTPServer.HttpRespParseError, response);
					}
				}
			}catch (Exception e){
				e.printStackTrace();
			}

		}

		@Override
		public void onFailure(int statusCode, String responseBody) {
			int code = HTTPServer.NetWorkException;
			if (statusCode == 0) {
				code = HTTPServer.NetWorkException;
			} else if (statusCode > 300) {
				code = HTTPServer.HttpSatutsError;
			}
			onRequestFailure(code, responseBody);
		}
	};
}
