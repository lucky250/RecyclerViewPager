package example.com.recyclerviewpager;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;

import example.com.recyclerviewpager.Adapters.ViewPagerAdapter;

public class ViewPagerBasedActivity extends AppCompatActivity{


    ViewPager viewPager;
    PagerAdapter pagerAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewpager_layout);
        viewPager = findViewById(R.id.viewpager);
        ArrayList<Integer> data = new ArrayList<>();
        for (int i =0; i < 100; i++) {
            data.add(R.drawable.dog);
        }
        pagerAdapter = new ViewPagerAdapter(data);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setOffscreenPageLimit(10);
    }
}
