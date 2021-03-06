package jubil.com.schoollibrary;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button search_btn;
    Button reset_btn;
    SharedPreferences pref;
    TextView code_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pref = getSharedPreferences("pref", MODE_PRIVATE);

        search_btn = findViewById(R.id.search_btn);
        reset_btn = findViewById(R.id.reset_btn);
        code_text = findViewById(R.id.code_text);

        if(pref.getInt("code", -1) == -1) code_text.setVisibility(View.INVISIBLE);
        else {
            code_text.setText("My code : " + Integer.toString(pref.getInt("code", -1)));
            code_text.setVisibility(View.VISIBLE);
        }

        search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Search
                Intent intent=new Intent(MainActivity.this,WebViewActivity.class);
                startActivity(intent);
            }
        });

        reset_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Reset
                if(pref.getInt("code", -1) != -1){
                    Toast.makeText(getApplicationContext(), "Success!", Toast.LENGTH_LONG).show();
                    SharedPreferences.Editor editor = pref.edit();

                    editor.remove("code");
                    editor.commit();

                    code_text.setVisibility(View.INVISIBLE);
                }
                else {
                    Toast.makeText(getApplicationContext(), "이미 코드가 존재하지 않습니다.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
