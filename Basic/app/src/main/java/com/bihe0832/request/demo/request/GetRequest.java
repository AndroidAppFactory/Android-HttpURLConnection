package com.bihe0832.request.demo.request;


import com.bihe0832.request.libware.HTTPRequestHandler;
import com.bihe0832.request.libware.HttpRequest;

public class GetRequest extends HttpRequest {

    public static final String PATH = "/AndroidHTTP/get.php";
    public static final String PARA_PARA = "para";

    private String mPara = "";

	private HTTPRequestHandler mResponseHandlerHandler;

	public GetRequest(String para, HTTPRequestHandler handler) {
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
    protected void onRequest(int statusCode, String result) {
        this.mResponseHandlerHandler.onResponse(statusCode,result);
    }
}
