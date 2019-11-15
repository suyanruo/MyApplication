package com.example.myapplication.webview;

import android.annotation.TargetApi;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.JsPromptResult;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.myapplication.BaseActivity;
import com.example.myapplication.R;

import java.util.HashMap;
import java.util.Set;

public class JavaScriptActivity extends BaseActivity {

  private LinearLayout mLayout;
  private LWebView mWebView;
  private ProgressBar mProgressBar;
  private TextView mTv_title;
  private LinearLayout mBar_layout;
  private WebChromeClient mWebChromeClient = new WebChromeClient() {
    @Override
    public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
      // 根据协议的参数，判断是否是所需要的url(原理同方式2)
      // 一般根据scheme（协议格式） & authority（协议名）判断（前两个参数）
      //假定传入进来的 url = "js://webview?arg1=111&arg2=222"（同时也是约定好的需要拦截的）

      Uri uri = Uri.parse(message);
      // 如果url的协议 = 预先约定的 js 协议
      // 就解析往下解析参数
      if ( uri.getScheme().equals("js")) {

        // 如果 authority  = 预先约定协议里的 webview，即代表都符合约定的协议
        // 所以拦截url,下面JS开始调用Android需要的方法
        if (uri.getAuthority().equals("webview")) {
          // 执行JS所需要调用的逻辑
          System.out.println("js调用了Android的方法");
          // 可以在协议上带有参数并传递到Android上
          HashMap<String, String> params = new HashMap<>();
          Set<String> collection = uri.getQueryParameterNames();

          //参数result:代表消息框的返回值(输入值)
          result.confirm("js调用了Android的方法成功啦");
        }
        return true;
      }
      return super.onJsPrompt(view, url, message, defaultValue, result);
    }
  };

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    initView();
    initData();
    initEvent();
  }

  private void initView() {
    mLayout = (LinearLayout) View.inflate(this, R.layout.activity_java_script, null);
    setContentView(mLayout);
    mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
    mTv_title = (TextView) findViewById(R.id.tv_title);
    mBar_layout = (LinearLayout) findViewById(R.id.bar_layout);

    mWebView = new LWebView(this);
    mWebView.clearCache(true);
    mWebView.setBarLayout(mBar_layout);
    mWebView.setProgressBarView(mProgressBar);
    mWebView.setTitleTextView(mTv_title);

    mWebView.setLayoutParams(new RelativeLayout.LayoutParams(-1, -1));
    mLayout.addView(mWebView);
  }

  private void initData() {
    String path = "file:///android_asset/testJs.html";
    Log.d("JavaScriptActivity", path);
    mWebView.loadUrl(path);
  }

  private void initEvent() {
    mWebView.addJavascriptInterface(new JSCallJavaInterface(),"JSCallJava");
  }

  private Handler mHandler  = new Handler();

  /**
   * js调用java本地方法是子线程，需要注意
   */
  private final class JSCallJavaInterface {
    @JavascriptInterface
    public String callJava(final String msg){
      mHandler.post(new Runnable() {
        @TargetApi(Build.VERSION_CODES.KITKAT)
        @Override
        public void run() {
          Log.d("MainActivity", "JS Call Java msg=" + msg);
          String jsmethod = "javascript:javaCallJS(\'JavaCallJSMethod"+msg+"\')";
//          android 4.4 开始使用evaluateJavascript调用js函数 ValueCallback获得调用js函数的返回值
//                    mWebView.loadUrl(jsmethod);
          mWebView.evaluateJavascript(jsmethod, new ValueCallback<String>() {

            @Override
            public void onReceiveValue(String value) {
              Log.d("MainActivity", "onReceiveValue value=" + value);
            }
          });
        }
      });
      return "Java result "+msg;
    }

    // android 4.4之前使用额外增加一个回调方法让js调用该函数返回函数结果，4.4开始可以使用ValueCallback
    @JavascriptInterface
    public void onJavaCallJSResult(String msg){
      Log.d("MainActivity","java call js result "+msg);
    }
  }

  @Override
  public boolean onKeyDown(int keyCode, KeyEvent event) {
    //canGoBack()  有历史项目 为true  回退
    if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebView.canGoBack()) {
      mWebView.goBack();
      return true;
    }
    return super.onKeyDown(keyCode, event);
  }
}
