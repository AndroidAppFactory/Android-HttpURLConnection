# Android-HttpURLConnection

## 说明

### **lib-http 后续不再单独在此维护，统一合并到AndroidAppFactory 中继续维护，对应Git地址为：**

**AndroidAppFactory：[https://github.com/bihe0832/AndroidAppFactory](https://github.com/bihe0832/AndroidAppFactory)**

**对应组件库为：LibHttpCommon & LibHttpAdvanced**

**调用事例参考：https://github.com/bihe0832/AndroidAppFactory/tree/master/BaseDebug/src/main/java/com/bihe0832/android/base/debug/request**

## 简介

一款封装HttpURLConnection实现的简单的网络请求的事例，提供了对应的apk和源码以及调用事例。

## 使用方法

### 添加依赖

在根目录添加发布插件的相关依赖

    buildscript {  
        repositories {  
            jcenter()  
        }  
    }   

    allprojects {  
        repositories {  
            jcenter()  
        }  
    }
    
### import

	dependencies {
	    api 'com.bihe0832.android:lib-http-advanced:1.3.0'
	}

或

	dependencies {
	    api 'com.bihe0832.android:lib-wrapper:1.0.3'
	}
		

### 调用

- 请求封装

		public class BasicGetRequest extends HttpRequest {
		
		    private String mPara = "";
		
			private HttpResponseHandler mResponseHandlerHandler;
		
			public BasicGetRequest(String para, HttpResponseHandler handler) {
		        this.mPara = para;
		        this.mResponseHandlerHandler = handler;
		    }
		
			@Override
			public String getUrl() {
		        StringBuilder builder = new StringBuilder();
		        builder.append(Constants.PARA_PARA + HTTP_REQ_ENTITY_MERGE + mPara);
		        return getBaseUrl()+"?"+builder.toString();
			}
		
			private String getBaseUrl(){
		        return Constants.HTTP_DOMAIN + Constants.PATH_GET;
		    }
		
		    @Override
		    protected void onResponse(int statusCode, String result) {
		        this.mResponseHandlerHandler.onResponse(statusCode,result);
		    }
		}
	
- 回调处理

		private inner class TestBasicResponseHandler : HttpResponseHandler {
	
		    override fun onResponse(statusCode: Int, response: String) {
		        showResult("HTTP状态码：\n\t" + statusCode + " \n " +
		                "网络请求内容：\n\t" + response)
		    }
		}	
		
		private fun showResult(tips: String) {
		    runOnUiThread { result.text = tips }
		}
	
- 具体调用


		var result = paraEditText.text?.toString()
		if (result?.length?:0 > 0) {
			val handle = TestBasicResponseHandler()
			val request = BasicGetRequest(result, handle)
			HTTPServer.getInstance().doRequest(request)
		} else {
			showResult("请在输入框输入请求内容！")
		}
		
## 源码运行方法

如何修改配置及运行工程，请参考本人博客：[终端基于gradle的开源项目运行环境配置指引](
http://blog.bihe0832.com/android-as-gradle-config.html)

### 项目区别

http-advanced和http-common的异同主要表现在

- 共同点

	- 两个项目的网络请求都是基于HTTPURLConnection封装的
	
	- 两个项目都支持通过url是以http开头还是https来选择发起HTTP还是HTTPS请求
	
	- 通过网络请求是否包含data内容来区分get或者post
	
	- 支持带cookie的网络请求
	
	- 所有网络请求单线程分发，多线程处理
	
- 差异点

	- Advanced会在库里面完成网络请求结果的解析，最终网络请求结果会被处理为一个json数据，Basic层没有做任何处理，将网络请求的内容以String返回

    
