# Android-HttpURLConnection

## 简介

一款封装HttpURLConnection实现的简单的网络请求的事例，提供了对应的apk和源码以及调用事例。暂时放上源码，后续提供代码介绍。

## 文件结构
 
	├── com.bihe0832.request.demo.advanced.apk ：网络请求结果解析为json后返回的demo
	│
	├── Advanced : com.bihe0832.request.demo.advanced.apk对应的源码
	│
	├── com.bihe0832.request.demo.basic.apk ：网络请求结果以String返回的demo
	│
	└── Basic：com.bihe0832.request.demo.basic.apk对应的源码
	
## 项目区别

Advanced和Basic的差异主要表现在

### 共同点

- 两个项目的网络请求都是基于HTTPURLConnection封装的

- 两个项目都支持以下请求：
	- 请求协议：通过url是以http开头还是https
		- HTTP
		- HTTPS

	- 请求类型：通过网络请求是否包含data内容来区分get或者post
		- GET
		- POST	
	- 其他
		- 支持带cookie的网络请求


### 差异点

- Advanced所有的网络请求时单线程分发，但是多线程处理的；Basic的网络请求的处理和分发都是在同一个线程
- Advanced会在库里面完成网络请求结果的解析，最终网络请求结果会被处理为一个json数据，Basic层没有做任何处理，将网络请求的内容以String返回。

## 代码介绍

代码介绍的篇幅主要介绍封装的库以及使用时候的调用方法，主要以Advanced为基础来介绍，例外这里不对源码做详细的介绍，只介绍几个主要类的使用方法，详细代码请自行参照源码。

### 封装源码介绍

这部分内容是对于HttpURLConnection的具体封装的代码。

	
	├── TextUtils.java ：基本的工具类 处理字符串判空、编码格式转换等
	│
	├── request
	│   │
	│   ├── BaseConnection.java ：基本的工具类 处理字符串判空、编码格式转换等
	│   │
	│   ├── HTTPConnection.java ：HTTP 请求类
	│   │
	│   ├── HTTPRequestHandler.java ：网络请求基类中将请求结果处理后的返回内容转化为业务逻辑相关错误
	│   │
	│   ├── HTTPSConnection.java ：HTTPS 请求类
	│   │
	│   ├── HTTPServer.java ：网络请求分发、执行类
	│   │
	│   ├── HttpRequest.java ：网络请求实例类的基类，所有具体的网络请求都是他的实现
	│   │
	│   ├── HttpResponse.java ：网络请求返回结果的基类，所有具体的网络请求返回都是他的实现
	│   │
	│   ├── HttpResponseHandler.java ：具体业务网络请求结果实例的基类
	│   │
	│   └── MyX509TrustManager.java
	│   
	└── thread
	    │
	    └── ThreadManager.java ：基于HandlerThread的多线程处理分发类
	    
	    
### 具体请求实例

这部分内容是具体开发中一个新的HTTP请求以及返回结果解析处理的类

	├── GetRequest.java : 一个GET请求的事例，完成了GET参数拼装
	│
	├── PostRequest.java : 一个POST请求的事例，完成了POST参数拼装
	│
	└── TestResponse.java ：请求返回处理事例，完成请求结果的解析。由于上面两个请求的返回结构一致，因此使用相同的结果处理类

### 调用代码

这部分内容是具体开发中当发送一个对应的网络请求的时候的代码实现

	//具体GET请求构造实例
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

	//具体POST请求构造实例
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

	//具体请求返回结果的处理逻辑
    private class TestResponesHandler implements HttpResponseHandler<TestResponse> {

        @Override
        public void onResponse(TestResponse response) {
            showResult("ret：\n\t" + response.ret + " \n " +
                    "msg：\n\t" + response.msg+ " \n " +
                    "para：\n\t" + response.para+ " \n ");
        }
    }



    