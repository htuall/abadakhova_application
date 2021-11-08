package com.example.newauth;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.RequestManager;

import java.util.ArrayList;

public class VideoPlayerRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<VideoItem> videoItems;
    private RequestManager requestManager;


    public VideoPlayerRecyclerAdapter(ArrayList<VideoItem> videoItems, RequestManager requestManager) {
        this.videoItems = videoItems;
        this.requestManager = requestManager;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new VideoPlayerViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.item_container, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ((VideoPlayerViewHolder)viewHolder).onBind(videoItems.get(i), requestManager);
    }

    @Override
    public int getItemCount() {
        return videoItems.size();
    }

}