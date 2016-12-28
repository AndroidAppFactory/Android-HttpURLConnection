package com.bihe0832.request.demo.request;


import com.bihe0832.request.libware.request.HttpRequest;
import com.bihe0832.request.libware.request.HttpResponseHandler;

import org.json.JSONObject;

public class GetRequest extends HttpRequest {

    private  static final String LOG_TAG = "bihe0832 REQUEST";


    public static final String PATH = "/AndroidHTTP/get.php";
    public static final String PARA_PARA = "para";

    private String mPara = "";

	private HttpResponseHandler<TestResponse> mResponseHandlerHandler;

	public GetRequest(String para, HttpResponseHandler<TestResponse> handler) {
        super(PATH);
        this.mPara = para;
        this.mResponseHandlerHandler = handler;
    }

	@Override
	public String getUrl() {
        StringBuilder builder = new StringBuilder();
        builder.append(PARA_PARA + HTTP_REQ_ENTITY_MERGE + mPara);
        return getBaseUrl()+"?"+builder.toString();
	}

    @Override
    protected void onRequestSuccess(int statusCode, JSONObject response) {
        TestResponse responses = new TestResponse();
        responses.parseSuccessResponse(statusCode, response);
        if (mResponseHandlerHandler != null) {
            mResponseHandlerHandler.onResponse(responses);
        }
    }

    @Override
    protected void onRequestFailure(int statusCode, String errorResponse) {
        TestResponse responses = new TestResponse();
        responses.parseFailureResponse(statusCode, errorResponse);
        if (mResponseHandlerHandler != null) {
            mResponseHandlerHandler.onResponse(responses);
        }
    }
}
