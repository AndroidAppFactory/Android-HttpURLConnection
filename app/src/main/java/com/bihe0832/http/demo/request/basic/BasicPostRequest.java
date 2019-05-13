package com.bihe0832.http.demo.request.basic;


import com.bihe0832.http.common.HttpResponseHandler;
import com.bihe0832.http.common.HttpRequest;
import com.bihe0832.http.common.TextUtils;
import com.bihe0832.http.demo.request.Constants;

public class BasicPostRequest extends HttpRequest {

	private HttpResponseHandler mResponseHandlerHandler;

	public BasicPostRequest(String para, HttpResponseHandler handler) {
        this.mResponseHandlerHandler = handler;
        String encodedParam = Constants.PARA_PARA + HTTP_REQ_ENTITY_MERGE + para;
        this.data = TextUtils.getBytesUTF8(encodedParam);
    }

	@Override
	public String getUrl() {
        return Constants.HTTP_DOMAIN + Constants.PATH_POST;
	}

    @Override
    protected void onResponse(int statusCode, String result) {
        this.mResponseHandlerHandler.onResponse(statusCode,result);
    }
}
