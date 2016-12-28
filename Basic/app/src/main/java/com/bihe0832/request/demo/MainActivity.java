package com.bihe0832.request.demo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.bihe0832.request.demo.basic.R;
import com.bihe0832.request.demo.request.GetRequest;
import com.bihe0832.request.demo.request.PostRequest;
import com.bihe0832.request.libware.HTTPRequestHandler;
import com.bihe0832.request.libware.HTTPServer;

public class MainActivity extends AppCompatActivity {


    private EditText mRequestParaEditText;
    private TextView mResultView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRequestParaEditText = (EditText)findViewById(R.id.request_paraEditText);
        mResultView = (TextView)findViewById(R.id.request_resultTextView);

        Button getRequestButton = (Button) findViewById(R.id.request_getBtn);
        getRequestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendGetRequest();
            }
        });

        Button postRequestButton = (Button) findViewById(R.id.request_postBtn);
        postRequestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendPostRequest();
            }
        });

        Button clearResultButton = (Button) findViewById(R.id.request_clear);
        clearResultButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mResultView.setText("");
            }
        });
    }

    private void sendGetRequest(){
        String para = mRequestParaEditText.getText().toString();
        if(null != para && para.length() > 0){
            TestRequestHandler handle = new TestRequestHandler();
            GetRequest request = new GetRequest(para,handle);
            HTTPServer.getInstance().doRequest(request);
        }else{
            showResult("请在输入框输入请求内容！");
        }
    }

    private void sendPostRequest(){
        String para = mRequestParaEditText.getText().toString();
        if(null != para && para.length() > 0){
            TestRequestHandler handle = new TestRequestHandler();
            PostRequest request = new PostRequest(para,handle);
            HTTPServer.getInstance().doRequest(request);
        }else{
            showResult("请在输入框输入请求内容！");
        }
    }

    private class TestRequestHandler extends HTTPRequestHandler{

        @Override
        public void onResponse(int statusCode, String response) {
            showResult("HTTP状态码：\n\t" + statusCode + " \n " +
                      "网络请求内容：\n\t" + response);
        }
    }

    private void showResult(final String tips){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mResultView.setText(tips);
            }
        });
    }




}
