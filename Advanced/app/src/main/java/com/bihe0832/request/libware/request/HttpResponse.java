package com.bihe0832.request.libware.request;


import android.util.Log;

import com.bihe0832.request.libware.TextUtils;

import org.json.JSONException;
import org.json.JSONObject;


/**
 *
 * 网络请求返回结果的基类，所有具体的网络请求返回都是他的实现，他与{@link HttpRequest}一般成对使用
 *
 */

public abstract class HttpResponse {
    private  static final String LOG_TAG = "bihe0832 REQUEST";

	public static final String HTTP_RESP_PARAM_RET = "ret";
    public static final String HTTP_RESP_PARAM_CODE = "flag";
	public static final String HTTP_RESP_PARAM_MSG = "msg";

    public int ret = HTTPServer.RET_FAIL;
    public int flag;
    public String msg;
    
    public HttpResponse() {}
    
    public HttpResponse(int ret, int flag, String msg) {
        this.ret = ret;
        this.flag = flag;
        this.msg = msg;
    }
    
    public void parseSuccessResponse(int ret, JSONObject responseJson) {
		if(responseJson == null) {
			this.ret = HTTPServer.RET_FAIL;
			this.flag = HTTPServer.HttpRespNull;
			this.msg = "msg body is null, statusCode:" + ret;
            Log.e(LOG_TAG,this.msg);
		} else {
            this.parseJson(responseJson);
		}
    }
    
    public void  parseFailureResponse(int flag,String errorResponse) {
		this.ret = HTTPServer.RET_FAIL;
		this.flag = flag;
        if(!TextUtils.ckIsEmpty(errorResponse)){
            this.msg = errorResponse;
        }
    }
    
    public abstract void parseJson(JSONObject json);
    
    public void parseBaseJson(JSONObject json) {
        try {
            int ret = json.getInt(HTTP_RESP_PARAM_RET);
            msg = json.getString(HTTP_RESP_PARAM_MSG);
            if (ret == 0) {
            	this.ret = HTTPServer.RET_SUCC;
                if(json.has(HTTP_RESP_PARAM_CODE)){
                    this.flag = json.getInt(HTTP_RESP_PARAM_CODE);
                }else{
                    this.flag = HTTPServer.Succ;
                }
            } else {
                this.ret = HTTPServer.RET_FAIL;
                if(json.has(HTTP_RESP_PARAM_CODE)){
                    this.flag = json.getInt(HTTP_RESP_PARAM_CODE);
                }else{
                    this.flag = ret;
                }
                Log.e(LOG_TAG,"=======================================");
                Log.e(LOG_TAG,this.getClass().getName());
                Log.e(LOG_TAG,"Server Error,ret:"+ret+";flag:"+ flag +";msg:"+msg);
                Log.e(LOG_TAG,"=======================================");
            }
        } catch (JSONException e) {
            Log.d(LOG_TAG,"Response JSONException : " + json.toString());
            ret = HTTPServer.RET_FAIL;
            flag = HTTPServer.HttpRespParseError;
            msg = "Response JsonException:" + e.getMessage();
        }
    }
    
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("ret=" + ret);
        builder.append("&flag=" + flag);
        builder.append("&msg=" + msg);
        return builder.toString();
    }
}
