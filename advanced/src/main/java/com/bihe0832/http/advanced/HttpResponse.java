package com.bihe0832.http.advanced;


import android.util.Log;

import com.bihe0832.http.common.HTTPServer;
import com.bihe0832.http.common.TextUtils;

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

    public static final int RET_SUCC      =   0;
    public static final int RET_FAIL      =   1;

    public final static int Succ = 0;
    public final static int Error = -1;
    public final static int NetWorkException = 100000;  //调用网络请求异常
    public final static int NetWorkTimeout = 100101;  //调用网络请求超时
    public final static int HttpSatutsError = 100102;   //HTTP请求状态异常
    public final static int HttpRespNull = 100103;      //HTTP响应为空
    public final static int HttpRespParseError = 100104;//HTTP响应解析错误

    public interface AdvancedResponseHandler<T> {
        void onResponse(T response);
    }

    public int ret = RET_FAIL;
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
			this.ret = RET_FAIL;
			this.flag = HttpRespNull;
			this.msg = "msg body is null, statusCode:" + ret;
            Log.e(LOG_TAG,this.msg);
		} else {
            this.parseJson(responseJson);
		}
    }
    
    public void  parseFailureResponse(int flag,String errorResponse) {
		this.ret = RET_FAIL;
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
            	this.ret = RET_SUCC;
                if(json.has(HTTP_RESP_PARAM_CODE)){
                    this.flag = json.getInt(HTTP_RESP_PARAM_CODE);
                }else{
                    this.flag = Succ;
                }
            } else {
                this.ret = RET_FAIL;
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
            ret = RET_FAIL;
            flag = HttpRespParseError;
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
