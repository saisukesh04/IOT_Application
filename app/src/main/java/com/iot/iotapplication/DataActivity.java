package com.iot.iotapplication;

import static com.iot.iotapplication.frags.GasFragment.rv2;
import static com.iot.iotapplication.frags.TempFragment.rv1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.airbnb.lottie.LottieAnimationView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.tabs.TabLayout;
import com.iot.iotapplication.database.ReadingDao;
import com.iot.iotapplication.database.ReadingsDatabase;
import com.iot.iotapplication.frags.GasFragment;
import com.iot.iotapplication.frags.TempFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DataActivity extends AppCompatActivity {

    private final String TAG = "DataActivity";
    public static List<Reading> feedList = new ArrayList<>();

    @BindView(R.id.loading_animation) LottieAnimationView loading_animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);

        ButterKnife.bind(this);
        feedList.clear();

        ViewPager viewPager = findViewById(R.id.viewpager);
        TabLayout tabs = findViewById(R.id.result_tabs);

        // Set Tabs inside Toolbar
        setupViewPager(viewPager);
        tabs.setupWithViewPager(viewPager);

        getVolleyData();
    }

    private void setupViewPager(ViewPager viewPager) {
        Adapter adapter = new Adapter(getSupportFragmentManager());
        adapter.addFragment(new TempFragment(), "Temperature");
        adapter.addFragment(new GasFragment(), "Gas");
        viewPager.setAdapter(adapter);
    }

    private class Adapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public Adapter(FragmentManager manager) {
            super(manager);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    private void getVolleyData() {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://api.thingspeak.com/channels/1840672/feeds.json?api_key=4UFDN5JOIUTEUBSF&results=5";
        ReadingDao readingDao = ReadingsDatabase.getInstance(this).readingDao();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                url,
                null,
                response -> {
                    Log.e(TAG, "Response:" + response.toString());
                    try {

                        JSONArray feeds = new JSONObject(String.valueOf(response)).getJSONArray("feeds");
                        for (int i = 0; i < feeds.length(); i++) {
                            JSONObject feedJson = feeds.getJSONObject(i);
                            readingDao.insertReading(new Reading(
                                    feedJson.getString("created_at"),
                                    feedJson.getString("field1"),
                                    feedJson.getString("field2"),
                                    feedJson.getInt("entry_id")
                            ));
                        }
                        feedList.addAll(readingDao.loadAllReadings());
                        loading_animation.setVisibility(View.GONE);
                        rv1.getAdapter().notifyDataSetChanged();
                        rv2.getAdapter().notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> Log.e(TAG, "Error:" + error.getMessage()));

        //add to queue and call the route
        queue.add(jsonObjectRequest);
    }
}