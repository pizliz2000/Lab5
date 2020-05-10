package com.example.lab5.adapters;


import android.content.Context;
import android.graphics.Color;
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
    private List<Item>likedList= new ArrayList<Item>(10);


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
    public void onBindViewHolder(final ExampleViewHolder holder, int position) {
        Item currentItem = mExampleList.get(position);
        final String imageUrl = currentItem.getImageUrl();
//        Glide.with(mContext).load(imageUrl).asGif().placeholder(R.drawable.loading_gif).into(holder.mImageView);
        Picasso.with(mContext).load(imageUrl).fit().centerInside().into(holder.mImageView);
        holder.likeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View e) {
                likedList.add(new Item(imageUrl));
                Singleton.getInstance().setItems(likedList);
                holder.likeBtn.setBackgroundColor(Color.GREEN);
                holder.disBtn.setBackgroundColor(Color.WHITE);
            }
        });
        holder.disBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View e) {
                holder.disBtn.setBackgroundColor(Color.RED);
                holder.likeBtn.setBackgroundColor(Color.WHITE);
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
            likeBtn=itemView.findViewById(R.id.Likebutton);
            disBtn=itemView.findViewById(R.id.Disbutton);
        }
    }
}
