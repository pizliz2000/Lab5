package com.example.lab5.adapters;


import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.lab5.Item;

import com.example.lab5.R;
import com.example.lab5.Singleton;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ExampleViewHolder> {
    private Context mContext;
    private ArrayList<Item> mExampleList;
    private List<Item> likedList = new ArrayList<Item>(10);


    public MainAdapter(Context context, ArrayList<Item> exampleList) {
        mContext = context;
        mExampleList = exampleList;
    }

    @Override
    public ExampleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.example_item, parent, false);
        return new ExampleViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ExampleViewHolder holder, final int position) {
        final Item currentItem = mExampleList.get(position);
        final String imageUrl = currentItem.getImageUrl();
//        Glide.with(mContext).load(imageUrl).asGif().placeholder(R.drawable.loading_gif).into(holder.mImageView);
        holder.likeBtn.setBackgroundColor(currentItem.isNiceSelected() ? Color.GREEN : Color.WHITE);
        holder.disBtn.setBackgroundColor(currentItem.isBadSelected() ? Color.RED : Color.WHITE);
        Picasso.with(mContext).load(imageUrl).fit().centerInside().into(holder.mImageView);
        holder.likeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (likedList.size() >= 9) {
                    likedList.remove(0);
                } else {
                    likedList.add(new Item(imageUrl));
                }

                Singleton.getInstance().setItems(likedList);
                currentItem.setNiceSelected(!currentItem.isNiceSelected());
                holder.likeBtn.setBackgroundColor(currentItem.isNiceSelected() ? Color.GREEN : Color.WHITE);
                notifyItemChanged(position);
            }
        });

        holder.disBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View e) {
                for (int i = 0; i < likedList.size(); i++) {
                    if (likedList.get(i).getImageUrl().equals(currentItem.getImageUrl())) {
                        Log.d("Current item", currentItem.getImageUrl());
                        Log.d("DELETED", likedList.get(i).getImageUrl());
                        likedList.remove(i);
                    }
                }
                Singleton.getInstance().setItems(likedList);
                currentItem.setBadSelected(!currentItem.isBadSelected());
                holder.disBtn.setBackgroundColor(currentItem.isBadSelected() ? Color.RED : Color.WHITE);
                notifyItemChanged(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mExampleList.size();
    }

    public class ExampleViewHolder extends RecyclerView.ViewHolder {
        public ImageView mImageView;
        public Button likeBtn, disBtn;

        public ExampleViewHolder(View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.image_view);
            likeBtn = itemView.findViewById(R.id.Likebutton);
            disBtn = itemView.findViewById(R.id.Disbutton);
        }
    }
}
