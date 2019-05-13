package com.bihe0832.http.demo.request.advanced;


import com.bihe0832.http.advanced.HttpRequest;
import com.bihe0832.http.advanced.HttpResponse;
import com.bihe0832.http.demo.request.Constants;

import org.json.JSONObject;


public class AdvancedGetRequest extends HttpRequest {

    private String mPara = "";

	private HttpResponse.AdvancedResponseHandler<TestResponse> mAdvancedResponseHandlerHandler;

	public AdvancedGetRequest(String para, HttpResponse.AdvancedResponseHandler<TestResponse> handler) {
        this.mPara = para;
        this.mAdvancedResponseHandlerHandler = handler;
    }

	@Override
	public String getUrl() {
        StringBuilder builder = new StringBuilder();
        builder.append(Constants.PARA_PARA + HTTP_REQ_ENTITY_MERGE + mPara);
        return getBaseUrl()+"?"+builder.toString();
	}

    @Override
    protected void onRequestSuccess(int statusCode, JSONObject response) {
        TestResponse responses = new TestResponse();
        responses.parseSuccessResponse(statusCode, response);
        if (mAdvancedResponseHandlerHandler != null) {
            mAdvancedResponseHandlerHandler.onResponse(responses);
        }
    }

    private String getBaseUrl(){
        return Constants.HTTP_DOMAIN + Constants.PATH_GET;
    }

    @Override
    protected void onRequestFailure(int statusCode, String errorResponse) {
        TestResponse responses = new TestResponse();
        responses.parseFailureResponse(statusCode, errorResponse);
        if (mAdvancedResponseHandlerHandler != null) {
            mAdvancedResponseHandlerHandler.onResponse(responses);
        }
    }
}
