package example.com.recyclerviewpager.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

import example.com.recyclerviewpager.R;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.RecyclerViewViewHolder> {

    private ArrayList<Integer> data;

    public RecyclerAdapter(ArrayList<Integer> data) {
        this.data = data;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public void onBindViewHolder(RecyclerViewViewHolder holder, int position) {
        ImageView img = holder.itemView.findViewById(R.id.recyclerview_item_img);
        img.setImageResource(data.get(position));
    }

    @Override
    public RecyclerViewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item_layout,parent,false);
        RecyclerViewViewHolder viewHolder = new RecyclerViewViewHolder(itemView);
        return viewHolder;
    }

    static class RecyclerViewViewHolder extends RecyclerView.ViewHolder {

        public RecyclerViewViewHolder(View itemView) {
            super(itemView);
        }
    }
}
