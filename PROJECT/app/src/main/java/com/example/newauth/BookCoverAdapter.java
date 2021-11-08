package com.example.newauth;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class BookCoverAdapter extends RecyclerView.Adapter<BookCoverAdapter.BookCoverViewHolder> {
    private ArrayList <Book> bookCovers;
    private LayoutInflater bookIn;
    private OnBookClickListener bookClickListener;
    private Context context;

    public BookCoverAdapter(Context context, ArrayList<Book> bookCovers, OnBookClickListener bookClickListener) {
        this.bookCovers = bookCovers;
        this.bookIn = LayoutInflater.from(context);
        this.bookClickListener = bookClickListener;
        this.context = context;
    }

    @NonNull
    @Override
    public BookCoverAdapter.BookCoverViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mItemView = bookIn.inflate(R.layout.bookcover_item,
                parent, false);
        return new BookCoverViewHolder(mItemView, this, bookClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull BookCoverViewHolder holder, int position) {
        String mCurrent = bookCovers.get(position).imageUrl;
        Glide.with(context).load(Uri.parse(mCurrent)).into(holder.bookCover);
        holder.bookCover.setImageURI(Uri.parse(mCurrent));
    }

    @Override
    public int getItemCount() {
        return bookCovers.size();
    }

    class BookCoverViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public ImageView bookCover;
        final BookCoverAdapter coverAdapter;
        OnBookClickListener bookClickListener;

        public BookCoverViewHolder(@NonNull View itemView, BookCoverAdapter coverAdapter, OnBookClickListener bookClickListener) {
            super(itemView);
            this.coverAdapter = coverAdapter;
            bookCover = itemView.findViewById(R.id.book_cover);
            this.bookCover = bookCover;
            this.bookClickListener = bookClickListener;
            itemView.setOnClickListener(this::onClick);
        }

        @Override
        public void onClick(View v) {
            bookClickListener.onBookClick(getBindingAdapterPosition());
        }
    }

    public interface OnBookClickListener{
        void onBookClick(int position);
    }
}
