package com.bihe0832.request.demo.request;


import com.bihe0832.request.libware.TextUtils;
import com.bihe0832.request.libware.request.HttpRequest;
import com.bihe0832.request.libware.request.HttpResponseHandler;

import org.json.JSONObject;

public class PostRequest extends HttpRequest {

    private  static final String LOG_TAG = "bihe0832 REQUEST";


    public static final String PATH = "/AndroidHTTP/post.php";
    public static final String PARA_PARA = "para";

	private HttpResponseHandler<TestResponse> mResponseHandlerHandler;

	public PostRequest(String para, HttpResponseHandler<TestResponse> handler) {
        super(PATH);
        String encodedParam = PARA_PARA + HTTP_REQ_ENTITY_MERGE + para;
        this.data = TextUtils.getBytesUTF8(encodedParam);
        this.mResponseHandlerHandler = handler;
    }

	@Override
	public String getUrl() {
        return getBaseUrl();
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
