package com.bihe0832.http.demo

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.bihe0832.android.http.common.common.HTTPServer
import com.bihe0832.android.http.common.common.HttpBasicRequest
import com.bihe0832.android.http.common.common.HttpResponseHandler
import com.bihe0832.http.advanced.HttpAdvancedRequest
import com.bihe0832.http.demo.request.Constants
import com.bihe0832.http.demo.request.advanced.AdvancedGetRequest
import com.bihe0832.http.demo.request.advanced.AdvancedPostRequest
import com.bihe0832.http.demo.request.advanced.TestResponse
import com.bihe0832.http.demo.request.basic.BasicGetRequest
import com.bihe0832.http.demo.request.basic.BasicPostRequest
import com.bihe0832.request.demo.basic.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getBasic.setOnClickListener { sendGetBasicRequest() }

        postBasic.setOnClickListener { sendPostBasicRequest() }

        getAdvanced.setOnClickListener { sendGetAdvancedRequest() }

        postAdvanced.setOnClickListener { sendPostAdvancedRequest() }

        clearResult.setOnClickListener { result.text = "" }
    }

    private fun showResult(tips: String) {
        runOnUiThread { result.text = tips }
    }

    private fun sendGetBasicRequest() {
        var result = paraEditText.text?.toString()
        if (result?.length ?: 0 > 0) {
            val handle = TestBasicResponseHandler()
            val request = BasicGetRequest(result, handle)
            HTTPServer.getInstance().doRequest(request)
        } else {
            showResult("请在输入框输入请求内容！")
        }
    }

    private fun sendGetAdvancedRequest() {
        var result = paraEditText.text?.toString()
        if (result?.length ?: 0 > 0) {

            HTTPServer.getInstance().doRequest(object : HttpAdvancedRequest<TestResponse>() {
                var res = object : AdvancedResponseHandler<TestResponse> {
                    override fun onRequestSuccess(response: TestResponse) {
                        showResult(response.toString())
                    }

                    override fun onRequestFailure(statusCode: Int, response: String?) {
                        showResult("HTTP状态码：\n\t$statusCode \n 网络请求内容：\n\t$response")
                    }
                }

                override fun getUrl(): String {
                    val builder = StringBuilder()
                    builder.append(Constants.PARA_PARA + HttpBasicRequest.HTTP_REQ_ENTITY_MERGE + result)
                    return Constants.HTTP_DOMAIN + Constants.PATH_GET + "?" + builder.toString()
                }

                override fun getAdvancedResponseHandler(): AdvancedResponseHandler<*> {
                    return res
                }
            })

            AdvancedGetRequest(result, TestAdvancedResponseHandler()).let {
                HTTPServer.getInstance().doRequest(it)
            }
        } else {
            showResult("请在输入框输入请求内容！")
        }
    }

    private fun sendPostBasicRequest() {
        var result = paraEditText.text?.toString()
        if (result?.length ?: 0 > 0) {
            val handle = TestBasicResponseHandler()
            val request = BasicPostRequest(result, handle)
            HTTPServer.getInstance().doRequest(request)
        } else {
            showResult("请在输入框输入请求内容！")

        }
    }

    private fun sendPostAdvancedRequest() {
        var result = paraEditText.text?.toString()

        if (result?.length?:0 > 0) {

            HTTPServer.getInstance().doRequest(object : HttpAdvancedRequest<TestResponse>() {
                init {
                    try {
                        this.data = (Constants.PARA_PARA + HttpBasicRequest.HTTP_REQ_ENTITY_MERGE + result).toByteArray(charset("UTF-8"))
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
                var res = object : AdvancedResponseHandler<TestResponse> {
                    override fun onRequestSuccess(response: TestResponse) {
                        showResult(response.toString())
                    }

                    override fun onRequestFailure(statusCode: Int, response: String?) {
                        showResult("HTTP状态码：\n\t$statusCode \n 网络请求内容：\n\t$response")
                    }
                }

                override fun getUrl(): String {
                    return Constants.HTTP_DOMAIN + Constants.PATH_POST
                }

                override fun getAdvancedResponseHandler(): AdvancedResponseHandler<*> {
                    return res
                }
            })

            AdvancedPostRequest(result, TestAdvancedResponseHandler()).let {
                HTTPServer.getInstance().doRequest(it)
            }
        } else {
            showResult("请在输入框输入请求内容！")
        }
    }

    private inner class TestBasicResponseHandler : HttpResponseHandler {

        override fun onResponse(statusCode: Int, response: String) {
            showResult("HTTP状态码：\n\t" + statusCode + " \n " +
                    "网络请求内容：\n\t" + response)
        }
    }

    private inner class TestAdvancedResponseHandler : HttpAdvancedRequest.AdvancedResponseHandler<TestResponse> {
        override fun onRequestSuccess(response: TestResponse) {
            showResult(response.toString())
        }

        override fun onRequestFailure(statusCode: Int, response: String?) {
            showResult("HTTP状态码：\n\t$statusCode \n 网络请求内容：\n\t$response")
        }
    }
}
