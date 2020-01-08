package com.bihe0832.http.demo.request.basic;


import com.bihe0832.android.http.common.common.HttpRequest;
import com.bihe0832.android.http.common.common.HttpResponseHandler;
import com.bihe0832.http.demo.request.Constants;

public class BasicPostRequest extends HttpRequest {

	private HttpResponseHandler mResponseHandlerHandler;

	public BasicPostRequest(String para, HttpResponseHandler handler) {
        this.mResponseHandlerHandler = handler;
        String encodedParam = Constants.PARA_PARA + HTTP_REQ_ENTITY_MERGE + para;
        try {
            this.data = encodedParam.getBytes("UTF-8");
        }catch (Exception e){
            e.printStackTrace();
        }

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
