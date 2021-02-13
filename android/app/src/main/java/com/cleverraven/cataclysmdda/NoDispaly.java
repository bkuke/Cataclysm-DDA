package com.cleverraven.cataclysmdda;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class NoDispaly extends AppCompatActivity {
    private static final String TAG = "CDDA";

    static class Options {
        private String info;
        @SerializedName("default")
        private String mdefault;
        private String name;
        private String value;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(this);
        String currentVersion = sharedPreferences.getString("installed", "");
        if (currentVersion.isEmpty()||!(currentVersion.equals(getVersionName()))) {
            startActivity(new Intent(this, SplashScreen.class));
        }else if (!(getJsonv())) {
            startActivity(new Intent(this,CataclysmDDA.class));
        }else {
            startActivity(new Intent(this,WelcomScreen.class));
        }
        finish();
    }

    private String getVersionName() {
        try {
            Context context = getApplicationContext();
            PackageInfo pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return pInfo.versionName;
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }

    private boolean getJsonv() {

        File[] externalStorageVolumes = ContextCompat.getExternalFilesDirs(getApplicationContext(), null);
        File primaryExternalStorage = externalStorageVolumes[0];
        String fp = primaryExternalStorage.getAbsolutePath() + "/config/options.json";
        File file=new File(fp);
        if (!file.exists()) return false;
        Gson gson=new Gson();
        long fl=file.length();
        byte[] buf=new byte[(int) fl];
        try {
            FileInputStream fin=new FileInputStream(file);
            if(fin.read(buf)==0) return false;
            fin.close();
            String cc=new String(buf,"UTF-8");
            Options[] options = gson.fromJson(cc,Options[].class);
            for (Options op1 :
                options) {
                if (op1.name.equals("ANDROID_OPENHELPER")&& op1.value.equals("true")) return true;
            }
        } catch (IOException e) {
            Log.i(TAG, "getJsonv: ");
            return false;
        }
        return false;
    }
}
