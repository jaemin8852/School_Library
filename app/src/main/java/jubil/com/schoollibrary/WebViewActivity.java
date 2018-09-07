package jubil.com.schoollibrary;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.Toast;

public class WebViewActivity extends AppCompatActivity {

    WebView wv;
    String url = "http://reading.ssem.or.kr/r/reading/search/";
    Handler mHandler;
    Button btn;
    int current_code= -1;
    SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.web_view);

        pref = getSharedPreferences("pref", MODE_PRIVATE);
        btn = findViewById(R.id.btn);

        if(pref.getInt("code", -1) != -1) {
            btn.setVisibility(View.GONE);
            Log.d("saved code : ", Integer.toString(pref.getInt("code", -1)));
        }

        wv = findViewById(R.id.web_view);
        wv.getSettings().setJavaScriptEnabled(true);
        if(pref.getInt("code", -1) != -1) wv.loadUrl(url.concat("schoolCodeSetting.jsp?schoolCode="+pref.getInt("code", -1)+"&returnUrl="));
        else wv.loadUrl(url.concat("schoolListForm.jsp"));
        wv.setWebViewClient(new WebViewClientClass());


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
                                if(current_code == -1) Toast.makeText(getApplicationContext(), "Fail", Toast.LENGTH_LONG).show();
                                else {
                                    Toast.makeText(getApplicationContext(), "Success!", Toast.LENGTH_LONG).show();
                                    SharedPreferences.Editor editor = pref.edit();
                                    editor.putInt("code", current_code);
                                    editor.commit();
                                    btn.setVisibility(View.GONE);
                                }
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
        public void onPageStarted(WebView view, String url, Bitmap favicon) {       //School code를 가져옴.
            Log.d("", "getUrl : " + view.getUrl());
            Log.d("", "url : " + url);
            if(url.contains("schoolCode")) current_code = Integer.parseInt(url.substring(url.indexOf('?')+12, url.indexOf('&')));
            Log.d("code = ", Integer.toString(current_code));
        }

    }
}
