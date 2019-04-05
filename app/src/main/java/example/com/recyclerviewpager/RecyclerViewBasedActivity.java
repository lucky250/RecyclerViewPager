package example.com.recyclerviewpager;

import android.graphics.Color;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;

import example.com.recyclerviewpager.Adapters.RecyclerAdapter;

public class RecyclerViewBasedActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    RecyclerAdapter recyclerAdapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recyclerview_layout);
        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setBackgroundColor(Color.parseColor("#FF0000"));
        recyclerView.setOverScrollMode(View.OVER_SCROLL_NEVER);
        // 设置layoutmanager
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);
        // 设置pagesnaphelper
        PagerSnapHelper pagerSnapHelper = new PagerSnapHelper();
        pagerSnapHelper.attachToRecyclerView(recyclerView);
        // 设置adapter
        ArrayList<Integer> data = new ArrayList<>();
        for (int i =0; i < 100; i++) {
            data.add(R.drawable.dog);
        }
        recyclerAdapter = new RecyclerAdapter(data);
        recyclerView.setAdapter(recyclerAdapter);
    }

}
