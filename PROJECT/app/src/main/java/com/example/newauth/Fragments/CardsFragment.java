package com.example.newauth.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.newauth.Card;
import com.example.newauth.CardsAdapter;
import com.example.newauth.HomePage;
import com.example.newauth.R;
import com.example.newauth.VideoItem;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class CardsFragment extends Fragment {

    private RecyclerView cardsRV;
    private CardsAdapter cardsAdapter;
    private Button learnBtn;
    private FragmentManager fragmentManager;
    private TextView check;
    FirebaseDatabase db;
    DatabaseReference reference;
    String KEY = "key";
    String str, user;
    ArrayList<Card> cards= new ArrayList<>();
    int count;
    boolean flag = false;


    public CardsFragment() {
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

        View v = inflater.inflate(R.layout.fragment_cards, container, false);
        cardsRV=v.findViewById(R.id.cards_RV);
        learnBtn=v.findViewById(R.id.learnBtn);
        cardsAdapter = new CardsAdapter(this.getContext(), cards);
        check = v.findViewById(R.id.check);
        count = 0;
        PagerSnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(cardsRV);
        cardsRV.setAdapter(cardsAdapter);
        cardsRV.setLayoutManager(new LinearLayoutManager(this.getContext()));
        user = str.replace(".", "");
        db = FirebaseDatabase.getInstance();
        reference = db.getReference(user);
        fragmentManager = ((HomePage) getActivity()).getSupportFragmentManager();

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Card card = dataSnapshot.getValue(Card.class);
                    if (!isAlreadyContained(cards, card)){
                        cards.add(card);
                    }
                }
                cardsAdapter.notifyDataSetChanged();
                check.setText(String.valueOf(cards.size()));
                count = cards.size();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        learnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (count >= 4) {
                    GameFragment fragment = new GameFragment();
                    flag = true;
                    Bundle args = new Bundle();
                    args.putString("key", str);
                    args.putParcelableArrayList("key2", cards);
                    fragment.setArguments(args);
                    fragmentManager.beginTransaction().replace(R.id.container,
                            fragment, "game").addToBackStack(null).commit();
                } else {
                    Toast.makeText(getActivity(), "There must be at least 4 words in a set to play!",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
        return v;
    }

    private boolean isAlreadyContained(ArrayList<Card> cards, Card card) {
        for (Card i : cards) {
            if (i.getWord() == card.getWord() && i.getTranslation() == card.getTranslation()) return true;
        }
        return false;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getArguments() != null) {
            str = getArguments().getString(KEY);
        }
    }
}