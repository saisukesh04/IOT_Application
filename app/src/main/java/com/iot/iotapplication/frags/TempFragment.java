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

public class TempFragment extends Fragment {

    public static RecyclerView rv1;

    public TempFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_temp, container, false);

        rv1 = root.findViewById(R.id.rv_data);
        rv1.setAdapter(new FeedAdapter(feedList, 0));

        return root;
    }
}