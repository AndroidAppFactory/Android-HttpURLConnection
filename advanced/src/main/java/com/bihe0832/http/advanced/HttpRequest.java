package com.bihe0832.http.advanced;


import android.util.Log;


import com.bihe0832.http.common.HttpBasicRequest;
import com.bihe0832.http.common.HttpResponseHandler;
import com.bihe0832.http.common.TextUtils;

import org.json.JSONException;
import org.json.JSONObject;


/**
 *
 * 网络请求实例类的基类，所有具体的网络请求都是他的实现，他与{@link HttpResponse}一般成对使用
 *
 */

public abstract class HttpRequest extends HttpBasicRequest {

	protected abstract void onRequestSuccess(int statusCode, JSONObject responseJson);

	protected abstract void onRequestFailure(int statusCode, String errorResponse);

	@Override
	public HttpResponseHandler getResponseHandler() {
		return mHttpResponseHandler;
	}

	public HttpResponseHandler mHttpResponseHandler = new HttpResponseHandler() {

		@Override
		public void onResponse(int statusCode, String response) {
			int code = HttpResponse.NetWorkException;
			if (statusCode == 0) {
				code = HttpResponse.NetWorkException;
				onRequestFailure(code, response);
			} else if (statusCode == -1) {
				code = HttpResponse.NetWorkException;
				onRequestFailure(code, response);
			}else if (statusCode > 300) {
				code = HttpResponse.HttpSatutsError;
				onRequestFailure(code, response);
			} else {
				try {
					if (TextUtils.ckIsEmpty(response)) {
						Log.e(LOG_TAG, "responseBody is null");
					} else {
						try {
							JSONObject json = new JSONObject(response);
							int ret = json.getInt(HttpResponse.HTTP_RESP_PARAM_RET);
							onRequestSuccess(ret, json);
						} catch (JSONException e) {
							onRequestFailure(HttpResponse.HttpRespParseError, response);
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
					onRequestFailure(HttpResponse.Error, response);
				}

			}
		}
	};
}
