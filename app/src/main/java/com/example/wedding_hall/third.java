package com.example.wedding_hall;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;

public class third extends Fragment {

    ListView lvAcc;
    private Toolbar mToolbar;
    private FirebaseAuth mAuth;
    public third(){
        // require a empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);
        View root = inflater.inflate(R.layout.fragment_third, container, false);
        mAuth = FirebaseAuth.getInstance();

        lvAcc = root.findViewById(R.id.listViewAccount);
        AccountRowAdapter accRowAdaptor = new  AccountRowAdapter(getActivity());
        lvAcc.setAdapter(accRowAdaptor);

        lvAcc.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0) {

                    Intent in1=new Intent(getActivity(), MyProfileActivity.class);
                    startActivity(in1);
                }

                else if(position == 1) {
                    Intent in2=new Intent(getActivity(), My_Feedbacck.class);
                    startActivity(in2);

                }
                else if(position == 2) {
                    Intent in3=new Intent(getActivity(), About_us.class);
                    startActivity(in3);

                }
                else if(position == 3) {
                    SharedPreferences myPref = getActivity().getSharedPreferences("MyStorage",MODE_PRIVATE);
                    SharedPreferences.Editor editor = myPref.edit();
                    editor.putInt("loginFlag",0);
                    editor.commit();
                    mAuth.signOut();
                    Intent in4=new Intent(getActivity(), LoginActivity.class);
                    in4.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(in4);

                }



            }
        });


        return root;
    }

}