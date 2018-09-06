package jubil.com.schoollibrary;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button search_btn;
    Button reset_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        search_btn = findViewById(R.id.search_btn);
        reset_btn = findViewById(R.id.reset_btn);

        search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Search
                //http://reading.ssem.or.kr/r/reading/search/schoolCodeSetting.jsp?schoolCode=31051&returnUrl=

                Intent intent=new Intent(MainActivity.this,WebViewActivity.class);
                startActivity(intent);
            }
        });
    }
}
