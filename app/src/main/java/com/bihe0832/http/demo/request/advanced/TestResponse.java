package com.bihe0832.http.demo.request.advanced;


import android.util.Log;


import com.bihe0832.http.advanced.HttpResponse;
import com.bihe0832.http.demo.request.Constants;

import org.json.JSONException;
import org.json.JSONObject;


public class TestResponse extends HttpResponse {

    private  static final String LOG_TAG = "bihe0832 REQUEST";
    public String para = "";



    @Override
    public void parseJson(JSONObject json) {
        super.parseBaseJson(json);
        if (HttpResponse.RET_SUCC == ret) {
            parseGuestUserCheckSuccRespones(json);
        } else {
            Log.w(LOG_TAG, json.toString());
        }
    }

    private void parseGuestUserCheckSuccRespones(JSONObject json) {
        try {
            para = json.getString(Constants.PARA_PARA);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
