package com.cleverraven.cataclysmdda;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.CheckBox;

public class Checklistener implements View.OnClickListener {

    Context cont;
    CheckBox box1;
    CheckBox box2;
    CheckBox box3;

    Checklistener(Context context, CheckBox b1 ,CheckBox b2,CheckBox b3){
        this.cont=context;
        this.box1=b1;
        this.box2=b2;
        this.box3=b3;
    }


    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        SharedPreferences sharedPreferences=PreferenceManager.getDefaultSharedPreferences(this.cont);
        SharedPreferences.Editor ed1 = sharedPreferences.edit();
        boolean b1= box1.isChecked();
        boolean b2=!(box2.isChecked());
        boolean b3=box3.isChecked();
        ed1.putBoolean("Force fullscreen",b1);
        ed1.putBoolean("Software rendering",b2);
        ed1.putBoolean("safek",b3);
        ed1.commit();
        this.cont.startActivity(new Intent(this.cont,CataclysmDDA.class));
        Activity a=(Activity)this.cont;
        a.finish();
    }
}
