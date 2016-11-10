package com.bwf.demo_javascript;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        webView = (WebView) findViewById(R.id.webView);

        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //可以获取到在WebView上点击的链接
                Log.d("MainActivity", "url:"+url);
                if(url.startsWith("http://www.eqingdan.com/mobile/things/")){
                    String[] strings = url.split("/");
                    String thingId = strings[strings.length-1];
                    Toast.makeText(MainActivity.this, "商品id："+thingId, Toast.LENGTH_SHORT).show();
                }
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                //当页面加载完成之后，执行注入JS的操作
                webView.loadUrl("javascript:(function(){\n" +
                        "    var imgTags = document.getElementsByTagName(\"img\");\n" +
                        "    for(var i = 0;i < imgTags.length;i++){\n" +
                        "        imgTags[i].onclick = function(){\n" +
                        "            BWF.clickImg(this.src);\n" +
                        "        }\n" +
                        "    }\n" +
                        "})()");
                Toast.makeText(MainActivity.this, "注入成功", Toast.LENGTH_SHORT).show();
            }
        });
        webView.loadUrl("http://www.eqingdan.com/app/articles/812");
        webView.addJavascriptInterface(new ImgClickJavascriptInterface(),"BWF");
        webView.getSettings().setJavaScriptEnabled(true);
    }

    private class ImgClickJavascriptInterface{
        @android.webkit.JavascriptInterface
        public void clickImg(String imgUrl){
            Toast.makeText(MainActivity.this, imgUrl, Toast.LENGTH_SHORT).show();
        }
    }
}
