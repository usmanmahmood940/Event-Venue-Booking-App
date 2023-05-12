package com.example.wedding_hall;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class AccountRowAdapter extends BaseAdapter {

    LayoutInflater inflater;
    ArrayList<Account> list;
    int[] images = {R.drawable.ic_baseline_account_box_24,
            R.drawable.ic_baseline_dynamic_feed_24,R.drawable.about_us, R.drawable.ic_icons8_log_out_32,  };


    Context c;


    public AccountRowAdapter ( Context c) {

        this.c=c;
        list=new ArrayList<Account>();
        Resources res=c.getResources();
        String[] Acc_Items= res.getStringArray(R.array.Account_Items);
        for(int i=0;i<4;i++)
        {
            list.add(new Account(Acc_Items[i], images[i]));
        }


        inflater=(LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public static int getScreenHeight() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }

    public static int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        View v=inflater.inflate(R.layout.account_row,null);

        TextView Items=(TextView) v.findViewById(R.id.name);
        ImageView imgView =(ImageView) v.findViewById(R.id.icon);
        Account temp=list.get(i);

        Items.setText(temp.accItems);
        imgView.setImageResource(temp.image);
        

        return v;
    }

}

