package com.github.lzyzsd.circleprogressexample;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;


/**
 * A placeholder fragment containing a simple view.
 */
public class ArcInFragmentFragment extends Fragment {

    FrameLayout fragmentContainer;
    Button button1, button2;
    ArcTabFragment tab1;
    ArcTabFragment tab2;
    public ArcInFragmentFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_arc_in, container, false);
        fragmentContainer = (FrameLayout) view.findViewById(R.id.tab_container);
        if (savedInstanceState  == null) {
            tab1 = new ArcTabFragment();
            tab2 = new ArcTabFragment();
            getFragmentManager()
                .beginTransaction()
                .add(R.id.tab_container, tab1, "tab1")
                .add(R.id.tab_container, tab2, "tab2")
                .hide(tab2)
                .commit();
        } else {
            tab1 = (ArcTabFragment) getFragmentManager().findFragmentByTag("tab1");
            tab2 = (ArcTabFragment) getFragmentManager().findFragmentByTag("tab2");
            getFragmentManager().beginTransaction().hide(tab2).commit();
        }
        button1 = (Button) view.findViewById(R.id.button1);
        button2 = (Button) view.findViewById(R.id.button2);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager()
                    .beginTransaction()
                    .show(tab1)
                    .hide(tab2)
                    .commit();
                tab1.onSelect();
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager()
                    .beginTransaction()
                    .show(tab2)
                    .hide(tab1)
                    .commit();
                tab2.onSelect();
            }
        });
    }
}
