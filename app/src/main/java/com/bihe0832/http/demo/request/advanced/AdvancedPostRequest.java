package com.bihe0832.http.demo.request.advanced;


import com.bihe0832.http.advanced.HttpRequest;
import com.bihe0832.http.advanced.HttpResponse.AdvancedResponseHandler;
import com.bihe0832.http.common.TextUtils;
import com.bihe0832.http.demo.request.Constants;

import org.json.JSONObject;

public class AdvancedPostRequest extends HttpRequest {

	private AdvancedResponseHandler<TestResponse> mAdvancedResponseHandlerHandler;

	public AdvancedPostRequest(String para, AdvancedResponseHandler<TestResponse> handler) {
        String encodedParam = Constants.PARA_PARA + HTTP_REQ_ENTITY_MERGE + para;
        this.data = TextUtils.getBytesUTF8(encodedParam);
        this.mAdvancedResponseHandlerHandler = handler;
    }

	@Override
	public String getUrl() {
        return getBaseUrl();
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
        return Constants.HTTP_DOMAIN + Constants.PATH_POST;
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
