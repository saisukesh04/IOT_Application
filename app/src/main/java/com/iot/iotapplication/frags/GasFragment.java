package com.iot.iotapplication.frags;

import static com.iot.iotapplication.DataActivity.feedList;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.iot.iotapplication.FeedAdapter;
import com.iot.iotapplication.R;

public class GasFragment extends Fragment {

    public static RecyclerView rv2;

    public GasFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_gas, container, false);

        rv2 = root.findViewById(R.id.rv_data);
        rv2.setAdapter(new FeedAdapter(feedList, 1));

        return root;
    }
}