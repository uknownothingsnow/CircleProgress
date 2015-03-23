package com.github.lzyzsd.circleprogressexample;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.lzyzsd.circleprogress.ArcProgress;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ArcTabFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ArcTabFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    ArcProgress arcProgress1;
    ArcProgress arcProgress2;

    public static ArcTabFragment newInstance(String param1, String param2) {
        ArcTabFragment fragment = new ArcTabFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public ArcTabFragment() {
        // Required empty public constructor
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
        View view = inflater.inflate(R.layout.fragment_arc_tab, container, false);
        arcProgress1 = (ArcProgress) view.findViewById(R.id.arc_progress1);
        arcProgress2 = (ArcProgress) view.findViewById(R.id.arc_progress2);
        return view;
    }

    public void onSelect() {
        arcProgress1.setProgress((int)(Math.random() * 100));
        arcProgress2.setProgress((int)(Math.random() * 100));
    }
}
