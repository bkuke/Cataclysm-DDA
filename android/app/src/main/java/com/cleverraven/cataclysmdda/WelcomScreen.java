package com.cleverraven.cataclysmdda;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import java.io.File;

public class WelcomScreen extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "CDDA";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcom_screen);
        CheckBox checkBox1=findViewById(R.id.fscreen);
        CheckBox checkBox2=findViewById(R.id.GPU);
        CheckBox checkBox3=findViewById(R.id.keyb);
        Button button=findViewById(R.id.startgame);
        Button button2=findViewById(R.id.endg);
        TextView textView1=findViewById(R.id.w1);
        TextView textView2=findViewById(R.id.w2);
        TextView textView3=findViewById(R.id.w3);
        TextView textView4=findViewById(R.id.w4);
        TextView textView5=findViewById(R.id.w5);
        TextView textView6=findViewById(R.id.w6);
        TextView textView7=findViewById(R.id.w7);
        TextView textView8=findViewById(R.id.w8);
        button.setOnClickListener(new Checklistener(this,checkBox1,checkBox2,checkBox3));
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
        textView7.setOnClickListener(this);
        textView8.setOnClickListener(this);
        SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(this);
        checkBox1.setChecked(sharedPreferences.getBoolean("Force fullscreen", true));
        checkBox2.setChecked(!sharedPreferences.getBoolean("Software rendering",false));
        checkBox3.setChecked(sharedPreferences.getBoolean("safek",false));
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
            case R.id.w7: {
                File[] externalStorageVolumes = ContextCompat.getExternalFilesDirs(getApplicationContext(), null);
                File primaryExternalStorage = externalStorageVolumes[0];
                final String apkpath = primaryExternalStorage.getAbsolutePath() + "/data/hackerskeyboard.apk";
                final File file=new File(apkpath);
                DialogInterface.OnClickListener a=new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        file.exists();
                    }
                };
                if (!file.exists())  {
                    AlertDialog alertDialog=new  AlertDialog.Builder(WelcomScreen.this).create();
                    alertDialog.setTitle("APK文件不存在");
                    alertDialog.setMessage("APK不存在，也许你删除了？");
                    alertDialog.setButton(DialogInterface.BUTTON1,"明白",a);
                    alertDialog.show();
                }else {
                    Intent intent1=new Intent(Intent.ACTION_INSTALL_PACKAGE);
                    intent1.setData(FileProvider.getUriForFile(this,"com.cleverraven.cataclysmdda.fileprovider",file));
                    intent1.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    startActivity(intent1);
                }
                break;
            }
            case R.id.w8: startActivity(new Intent(this,Helper.class));break;
            default: u1="";
        }
        if (!u1.isEmpty()) {
            Uri uri = Uri.parse(u1);
            startActivity(new Intent(Intent.ACTION_VIEW, uri));
        }
    }
}
