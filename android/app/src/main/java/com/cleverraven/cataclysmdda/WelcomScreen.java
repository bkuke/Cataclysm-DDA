package com.cleverraven.cataclysmdda;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

public class WelcomScreen extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcom_screen);
        CheckBox checkBox1=findViewById(R.id.fscreen);
        CheckBox checkBox2=findViewById(R.id.GPU);
        Button button=findViewById(R.id.startgame);
        Button button2=findViewById(R.id.endg);
        TextView textView1=findViewById(R.id.w1);
        TextView textView2=findViewById(R.id.w2);
        TextView textView3=findViewById(R.id.w3);
        TextView textView4=findViewById(R.id.w4);
        TextView textView5=findViewById(R.id.w5);
        TextView textView6=findViewById(R.id.w6);
        button.setOnClickListener(new Checklistener(this,checkBox1,checkBox2));
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        textView1.setOnClickListener(this);
        textView2.setOnClickListener(this);
        textView3.setOnClickListener(this);
        textView4.setOnClickListener(this);
        textView5.setOnClickListener(this);
        textView6.setOnClickListener(this);
        SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(this);
        checkBox1.setChecked(sharedPreferences.getBoolean("Force fullscreen", true));
        checkBox2.setChecked(!sharedPreferences.getBoolean("Software rendering",false));
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        String u1 = "";
        Intent intent=new Intent(this,ModList.class);
        switch (v.getId()) {
            case R.id.w1: u1="https://cdda-trunk.aloxaf.cn";break;
            case R.id.w2: u1="https://cddabase.site";break;
            case R.id.w3: u1="https://cataclysmdda.org";break;
            case R.id.w4: startActivity(intent.putExtra("key",1));break;
            case R.id.w5: startActivity(intent.putExtra("key",2));break;
            case R.id.w6: u1="https://cddawiki.chezzo.com/cdda_wiki";break;
            default: u1="https://github.com/CleverRaven/Cataclysm-DDA";
        }
        if (!u1.isEmpty()) {
            Uri uri = Uri.parse(u1);
            startActivity(new Intent(Intent.ACTION_VIEW, uri));
        }
    }
}
