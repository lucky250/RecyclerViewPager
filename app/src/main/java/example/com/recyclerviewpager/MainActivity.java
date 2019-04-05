package example.com.recyclerviewpager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button recyclerViewButton;
    Button viewPagerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerViewButton = findViewById(R.id.recyclerview_button);
        viewPagerButton = findViewById(R.id.viewpager_button);

        recyclerViewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RecyclerViewBasedActivity.class);
                startActivity(intent);
            }
        });

        viewPagerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ViewPagerBasedActivity.class);
                startActivity(intent);
            }
        });
    }
}
