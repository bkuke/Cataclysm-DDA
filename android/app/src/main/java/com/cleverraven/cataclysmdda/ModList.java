package com.cleverraven.cataclysmdda;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.io.File;

public class ModList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mod_list);
        File[] externalStorageVolumes = ContextCompat.getExternalFilesDirs(getApplicationContext(), null);
        File primaryExternalStorage = externalStorageVolumes[0];
        int keycode=this.getIntent().getIntExtra("key",0);
        String key;
        if (keycode == 1) {
            key = "Mod.json";
        } else {
            key = "SoundPack.json";
        }
        String fp = primaryExternalStorage.getAbsolutePath() + "/data/"+key;
        RecyclerView recyclerView=findViewById(R.id.mdl);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        RecyclerViewAdapter adapter=new RecyclerViewAdapter(this,fp,keycode);
        recyclerView.setAdapter(adapter);

    }
}
