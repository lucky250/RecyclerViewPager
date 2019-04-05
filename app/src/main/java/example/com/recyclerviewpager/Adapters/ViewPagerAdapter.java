package example.com.recyclerviewpager.Adapters;

import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

import example.com.recyclerviewpager.R;

public class ViewPagerAdapter extends PagerAdapter {

    ArrayList<Integer> data;

    public ViewPagerAdapter(ArrayList<Integer> data) {
        this.data = data;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.viewpager_item_layout, null);
        ImageView img = view.findViewById(R.id.viewpager_item_img);
        img.setImageResource(data.get(position));
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public float getPageWidth(int position) {
        return super.getPageWidth(position);
    }
}
