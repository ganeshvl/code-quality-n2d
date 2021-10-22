package com.news2day.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.news2day.VerticalViewPager;
import com.news2day.Adapter.VerticlePagerAdapter;
import com.news2day.R;


public class BuzzFragment extends Fragment {
   // FragmentHomeBinding fragmentHomeBinding;
    RecyclerView rvHome;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        return view;
    }

}
