package com.example.newauth;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CardsAdapter extends RecyclerView.Adapter<CardsAdapter.CardViewHolder> {
    private List<Card> cards;
    private LayoutInflater cardIn;

    public CardsAdapter(Context context, List<Card> cards) {
        this.cards = cards;
        this.cardIn = LayoutInflater.from(context);
    }


    @NonNull
    @Override
    public CardsAdapter.CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mItemView = cardIn.inflate(R.layout.card_container,
                parent, false);
        return new CardViewHolder(mItemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull CardsAdapter.CardViewHolder holder, int position) {
        Card mCurrent = cards.get(position);
        holder.word.setText(mCurrent.word);
        holder.translation.setText(mCurrent.translation);

    }

    @Override
    public int getItemCount() {
        return cards.size();
    }
    class CardViewHolder extends RecyclerView.ViewHolder{
        public TextView word, translation;
        final CardsAdapter cardAdapter;

        public CardViewHolder(@NonNull View itemView, CardsAdapter cardAdapter) {
            super(itemView);
            word = itemView.findViewById(R.id.word);
            translation = itemView.findViewById(R.id.translation);
            this.cardAdapter = cardAdapter;
        }
    }
}
