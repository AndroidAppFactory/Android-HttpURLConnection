package com.bihe0832.request.demo.request;


import com.bihe0832.request.libware.TextUtils;
import com.bihe0832.request.libware.HTTPRequestHandler;
import com.bihe0832.request.libware.HttpRequest;

public class PostRequest extends HttpRequest {

    public static final String PATH = "/AndroidHTTP/post.php";
    public static final String PARA_PARA = "para";

	private HTTPRequestHandler mResponseHandlerHandler;

	public PostRequest(String para, HTTPRequestHandler handler) {
        super(PATH);
        this.mResponseHandlerHandler = handler;
        String encodedParam = PARA_PARA + HTTP_REQ_ENTITY_MERGE + para;
        this.data = TextUtils.getBytesUTF8(encodedParam);
    }

	@Override
	public String getUrl() {
        return getBaseUrl();
	}

    @Override
    protected void onRequest(int statusCode, String result) {
        this.mResponseHandlerHandler.onResponse(statusCode,result);
    }
}
