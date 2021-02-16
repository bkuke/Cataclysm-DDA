package com.cleverraven.cataclysmdda;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class Helper extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_helper);
        ImageView imageView1=findViewById(R.id.k1);
        imageView1.setImageResource(R.drawable.keyb);
    }
}
