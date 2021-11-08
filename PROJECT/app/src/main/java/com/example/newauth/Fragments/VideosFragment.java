package com.example.newauth.Fragments;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.example.newauth.Card;
import com.example.newauth.OnWordListener;
import com.example.newauth.R;
import com.example.newauth.VerticalSpacingItemDecorator;
import com.example.newauth.VideoItem;
import com.example.newauth.VideoPlayerRecyclerAdapter;
import com.example.newauth.VideoPlayerRecyclerView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;


public class VideosFragment extends Fragment implements OnWordListener {

    View view;
    FirebaseDatabase db;
    VideoPlayerRecyclerView recyclerView;
    DatabaseReference reference;
    VideoPlayerRecyclerAdapter mAdapter;
    ArrayList<VideoItem> videoItems;
    String KEY = "key";
    String user;
    String str;

    public VideosFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            str = getArguments().getString(KEY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_videos, container, false);
        db = FirebaseDatabase.getInstance();
        reference = db.getReference("videos");
        recyclerView = view.findViewById(R.id.recycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(layoutManager);
        VerticalSpacingItemDecorator itemDecorator = new VerticalSpacingItemDecorator(10);
        recyclerView.addItemDecoration(itemDecorator);
        videoItems = new ArrayList<>();
        mAdapter = new VideoPlayerRecyclerAdapter(videoItems, initGlide());
        user = str.replace(".", "");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    VideoItem videoItem = dataSnapshot.getValue(VideoItem.class);
                    videoItems.add(videoItem);
                }
                mAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        recyclerView.setMediaObjects(videoItems);
        recyclerView.setOnWordListener(this::onWord);
        PagerSnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView);
        recyclerView.setAdapter(mAdapter);
        return view;
    }

    private RequestManager initGlide(){
        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.white_background)
                .error(R.drawable.white_background);

        return Glide.with(this)
                .setDefaultRequestOptions(options);
    }

    @Override
    public void onDestroy() {
        if(recyclerView!=null)
            recyclerView.releasePlayer();
        super.onDestroy();
    }

    @Override
    public void onWord(String word, String translation) {
        Card card = new Card(word, translation);
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference(user);
        ref.push().setValue(card);
    }
}