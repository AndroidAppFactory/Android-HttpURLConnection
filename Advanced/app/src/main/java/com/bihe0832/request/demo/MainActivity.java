package com.bihe0832.request.demo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.bihe0832.request.demo.advanced.R;
import com.bihe0832.request.demo.request.GetRequest;
import com.bihe0832.request.demo.request.PostRequest;
import com.bihe0832.request.demo.request.TestResponse;
import com.bihe0832.request.libware.request.HTTPServer;
import com.bihe0832.request.libware.request.HttpResponseHandler;

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
            TestResponesHandler handle = new TestResponesHandler();
            GetRequest request = new GetRequest(para,handle);
            HTTPServer.getInstance().doRequest(request);
        }else{
            showResult("请在输入框输入请求内容！");
        }
    }

    private void sendPostRequest(){
        String para = mRequestParaEditText.getText().toString();
        if(null != para && para.length() > 0){
            TestResponesHandler handle = new TestResponesHandler();
            PostRequest request = new PostRequest(para,handle);
            HTTPServer.getInstance().doRequest(request);
        }else{
            showResult("请在输入框输入请求内容！");
        }
    }

    private class TestResponesHandler implements HttpResponseHandler<TestResponse> {

        @Override
        public void onResponse(TestResponse response) {
            showResult("ret：\n\t" + response.ret + " \n " +
                    "msg：\n\t" + response.msg+ " \n " +
                    "para：\n\t" + response.para+ " \n ");
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
