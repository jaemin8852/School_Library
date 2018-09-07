package jubil.com.schoollibrary;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

public class WebViewActivity extends AppCompatActivity {

    WebView wv;
    String url = "http://reading.ssem.or.kr/r/reading/search/schoolCodeSetting.jsp?schoolCode=31051&returnUrl=";
    Handler mHandler;
    Button btn;
    int current_code= -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.web_view);

        wv = findViewById(R.id.web_view);
        wv.getSettings().setJavaScriptEnabled(true);
        wv.loadUrl(url);
        wv.setWebViewClient(new WebViewClientClass());

        btn = findViewById(R.id.btn);

        mHandler = new Handler();
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                //여기서 x
                mHandler.post(new Runnable () {

                    @Override
                    public void run() {
                        btn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Log.d("Url", wv.getUrl());
                                wv.reload();
                                Log.d("Url", wv.getUrl());
                                wv.setWebViewClient(new WebViewClientClass());
                            }
                        });
                    }
                });
            }
        });
        t.start();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && wv.canGoBack()) {
            wv.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private class WebViewClientClass extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            Log.d("", "getUrl : " + view.getUrl());
            Log.d("", "url : " + url);
            if(url.contains("schoolCode")) current_code = Integer.parseInt(url.substring(url.indexOf('?')+12, url.indexOf('&')));
            Log.d("code = ", Integer.toString(current_code));
        }

    }
}
