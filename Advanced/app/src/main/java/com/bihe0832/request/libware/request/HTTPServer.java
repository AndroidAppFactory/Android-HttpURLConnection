package com.bihe0832.request.libware.request;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import com.bihe0832.request.libware.TextUtils;
import com.bihe0832.request.libware.thread.ThreadManager;

import java.net.HttpURLConnection;

public class HTTPServer {

    private  static final String LOG_TAG = "bihe0832 REQUEST";

    public static final int RET_SUCC      =   0;
    public static final int RET_FAIL      =   1;

    public final static int Succ = 0;
    public final static int Error = -1;
    public final static int NetWorkException = 100000;  //调用网络请求异常
    public final static int NetWorkTimeout = 100101;  //调用网络请求超时
    public final static int HttpSatutsError = 100102;   //HTTP请求状态异常
    public final static int HttpRespNull = 100103;      //HTTP响应为空
    public final static int HttpRespParseError = 100104;//HTTP响应解析错误


    //是否为测试版本
    private static final boolean DEBUG = true;
    private Handler mCallHandler;
    private static final int MSG_REQUEST = 0;

    private HandlerThread mRequestHandlerThread = null;

    private static volatile HTTPServer instance;
    public static HTTPServer getInstance () {
        if (instance == null) {
            synchronized (HTTPServer.class) {
                if (instance == null) {
                    instance = new HTTPServer();
                    instance.init();
                }
            }
        }
        return instance;
    }

    private HTTPServer() {}

    public void init () {

        mRequestHandlerThread = new HandlerThread("HTTPServer");
        mRequestHandlerThread.start();
        mCallHandler = new Handler(mRequestHandlerThread.getLooper()) {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case MSG_REQUEST:
                        if(msg.obj != null && msg.obj instanceof HttpRequest){
                            executeRequest((HttpRequest)msg.obj);
                        }else{
                            Log.d(LOG_TAG,msg.toString());
                        }
                        break;
                    default:
                        break;
                }
            }
        };
    }

    
    public void doRequest(HttpRequest request) {
        Message msg = mCallHandler.obtainMessage();
        msg.what = MSG_REQUEST;
        msg.obj = request;
        mCallHandler.sendMessage(msg);
    }

    private void executeRequest(final HttpRequest request){
        ThreadManager.getInstance().start(new Runnable() {
            @Override
            public void run() {
                executeRequestInExecutor(request);
            }
        });
    }
    private void executeRequestInExecutor(HttpRequest request){
        request.requestTime = System.currentTimeMillis() / 1000;

        String url = request.getUrl();
        if(DEBUG){
            Log.w(LOG_TAG,"=======================================");
            Log.w(LOG_TAG,request.getClass().toString());
            Log.w(LOG_TAG,url);
            Log.w(LOG_TAG,"=======================================");
        }
        BaseConnection connection = null;
        if(url.startsWith("https:")){
            connection = new HTTPSConnection(url);
        }else{
            connection = new HTTPConnection(url);
        }

        String result = connection.doRequest(request);
        if(DEBUG){
            Log.w(LOG_TAG,"=======================================");
            Log.w(LOG_TAG,request.getClass().toString());
            Log.w(LOG_TAG,result);
            Log.w(LOG_TAG, String.valueOf(connection.getResponseCode()));
            Log.w(LOG_TAG,connection.getResponseMessage());
            Log.w(LOG_TAG,"=======================================");
        }

        if(connection.getResponseCode() == HttpURLConnection.HTTP_OK){
            request.mHttpResponseHandler.onSuccess(connection.getResponseCode(), result);
        }else{
            if (TextUtils.ckIsEmpty(result)) {
                if(DEBUG) {
                    Log.e(LOG_TAG, request.getClass().getName());
                }
                Log.e(LOG_TAG,"responseBody is null");
                if(TextUtils.ckIsEmpty(connection.getResponseMessage())){
                    request.mHttpResponseHandler.onFailure(connection.getResponseCode(), "");
                }else{
                    request.mHttpResponseHandler.onFailure(connection.getResponseCode(),connection.getResponseMessage());
                }
            } else {
                request.mHttpResponseHandler.onSuccess(connection.getResponseCode(), result);
            }
        }
    }
}
