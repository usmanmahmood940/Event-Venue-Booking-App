package com.example.wedding_hall;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Tab3#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Tab3 extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    ListView lvBookings;
    List<Booking> bookingList;

    public Tab3() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static Tab3 newInstance(String param1, String param2) {
        Tab3 fragment = new Tab3();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_tab2, container, false);

        lvBookings = root.findViewById(R.id.listViewBookings);
        bookingList = new ArrayList<>();



        AllData.loadData(new FirebaseCallBack() {
            @Override
            public void callBack(List<TimeSlot> avTimeSlots) {

            }

            @Override
            public void callBack() {
                bookingList = new ArrayList<>();
                showList();

            }

            @Override
            public void callBack(User recieverUser) {

            }
        },"Booking");

        return root;
    }
    public void showList(){
        for (Booking tempBooking:AllData.dSortBookingList()) {
            if(tempBooking.getBookingStatus().equals("Completed"))
                bookingList.add(tempBooking);
        }

        if(getActivity()!=null) {
            BookingRowAdaptor bookingRowAdaptor = new BookingRowAdaptor(bookingList, getActivity());
            lvBookings.setAdapter(bookingRowAdaptor);
        }
    }
}