package com.bihe0832.request.demo.request;


import android.util.Log;

import com.bihe0832.request.libware.request.HTTPServer;
import com.bihe0832.request.libware.request.HttpResponse;

import org.json.JSONException;
import org.json.JSONObject;


public class TestResponse extends HttpResponse {

    private  static final String LOG_TAG = "bihe0832 REQUEST";
    public String para = "";

    public static final String PARA_PARA = "para";

    @Override
    public void parseJson(JSONObject json) {
        super.parseBaseJson(json);
        if (HTTPServer.RET_SUCC == ret) {
            parseGuestUserCheckSuccRespones(json);
        } else {
            Log.w(LOG_TAG, json.toString());
        }
    }

    private void parseGuestUserCheckSuccRespones(JSONObject json) {
        try {
            para = json.getString(PARA_PARA);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
