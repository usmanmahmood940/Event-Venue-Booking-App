package com.example.wedding_hall;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

//import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import androidx.cardview.widget.CardView;

import com.smarteist.autoimageslider.SliderView;
import com.squareup.picasso.Picasso;

import java.util.List;

public class HallRowAdaptor extends BaseAdapter {

    List<Hall> hallList;

    LayoutInflater inflater;
    Context c;


    public HallRowAdaptor(List<Hall> hallList, Context c) {
        this.c=c;

        this.hallList=hallList;


        inflater=(LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return hallList.size();
    }

    @Override
    public Object getItem(int position) {
        return hallList.get(position);
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
        View v=inflater.inflate(R.layout.single_row,null);

        ImageView image = v.findViewById(R.id.imagehall);

        CardView card = (CardView) v.findViewById(R.id.hallcard);
        TextView Items=(TextView) v.findViewById(R.id.tvName);
        TextView Addr=(TextView) v.findViewById(R.id.tvAddress);
        androidx.appcompat.widget.AppCompatRatingBar ratingBar = v.findViewById(R.id.rbReviews);


        Hall temp=hallList.get(i);
        Items.setText(temp.getHallName());
        Addr.setText(temp.getAddress());
        float rating = Float.parseFloat(temp.getRating());



        ratingBar.setRating(Float.parseFloat(temp.getRating()));
        if(!temp.getImagesUrlList().get(0).trim().equals("")) {
            Picasso.get().load(temp.getImagesUrlList().get(0)).into(image);

        }


        card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent;
                if(AllData.objUser.getCategory().equals("user")) {
                    intent = new Intent(c.getApplicationContext(), Hall_Details.class);
                }
                else{
                    intent = new Intent(c.getApplicationContext(), Vendor_Hall_Details.class );
                }
                intent.putExtra("hall",temp);
                c.startActivity(intent);

            }
        });



        return v;
    }


}
