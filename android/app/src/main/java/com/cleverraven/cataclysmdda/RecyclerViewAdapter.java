package com.cleverraven.cataclysmdda;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private static final String TAG = "CDDA";

    private final Context context;
    private final Tool[] tooler;
    private final int code;

    RecyclerViewAdapter(Context ct,String fp,int code1){
        this.context=ct;
        this.tooler=Converter(fp);
        this.code=code1;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final LinearLayout mlinear;

        public ViewHolder(LinearLayout v){
            super(v);
            mlinear=v;
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LinearLayout linearLayout = (LinearLayout) LayoutInflater.from(this.context).inflate(R.layout.sample, viewGroup, false);
        if (i == 0) {
            TextView hp = linearLayout.findViewById(R.id.name1);
            TextView dl = linearLayout.findViewById(R.id.link1);
            TextView des = linearLayout.findViewById(R.id.des1);
            hp.setTextColor(Color.parseColor("#CCCCCC"));
            hp.setTextSize(15);
            dl.setVisibility(View.GONE);
            if (code == 1) {
                des.setTextColor(Color.parseColor("#ff0000"));
            }
        }
        return new ViewHolder(linearLayout);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        TextView hp = viewHolder.mlinear.findViewById(R.id.name1);
        TextView dl = viewHolder.mlinear.findViewById(R.id.link1);
        TextView des = viewHolder.mlinear.findViewById(R.id.des1);
        des.setTextIsSelectable(true);
        if (position != 0) {
            final Tool t1 = this.tooler[position-1];
            if (t1.type.equals("direct_download")) {
                dl.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Uri uri = Uri.parse(t1.url);
                        context.startActivity(new Intent(Intent.ACTION_VIEW, uri));
                    }
                });
            } else {
                dl.setVisibility(View.GONE);
            }
            if (t1.description == null) {
                des.setVisibility(View.GONE);
            } else {
                des.setText(t1.description);
            }
            hp.setText(t1.name);
            hp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(t1.homepage)));
                }
            });

        }else {
            hp.setText("并不建议从这里直接下载，首先链接可能失效，其次就算不失效下载速度也可能很慢（就像其他境外网站一样慢）。也许百度是个更好的选择。");
            if (code==1) {
                des.setText("如果你没有备份存档文件，安装这些MOD后，在加载游戏时可能会彻底摧毁你的存档！");
            }else {
                des.setText("和MOD列表不一样，音效包没有介绍，这是因为音乐不好形容，但可以保证的是它们基本都是阴间音效。");
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
            return tooler.length+1;
    }

    private static class Tool{
        public String type;
        public String name;
        public String url;
        public String homepage;
        public String description;
    }

    private Tool[] Converter(String filepath)  {
        Gson gson=new Gson();
        File file=new File(filepath);
        byte[] bytes=new byte[(int) file.length()];
        try {
            FileInputStream fileInputStream=new FileInputStream(file);
            fileInputStream.read(bytes);
            fileInputStream.close();
            String cc=new String(bytes,"UTF-8");
            return gson.fromJson(cc,Tool[].class);
        } catch (IOException e) {
            Log.i(TAG, "Converter: ");
        }
        return null;
    }


}
