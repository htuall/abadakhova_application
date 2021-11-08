package com.example.newauth.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.newauth.Book;
import com.example.newauth.BookCoverAdapter;
import com.example.newauth.BookProfile;
import com.example.newauth.Card;
import com.example.newauth.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class BooksFragment extends Fragment implements BookCoverAdapter.OnBookClickListener {

    private RecyclerView bookRV;
    private BookCoverAdapter booksAdapter;
    FirebaseDatabase db;
    DatabaseReference reference;
    ArrayList<Book> books = new ArrayList<>();


    public BooksFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_books, container, false);
        bookRV = view.findViewById(R.id.book_rv);
        booksAdapter = new BookCoverAdapter(this.getContext(), books, this::onBookClick);
        bookRV.setAdapter(booksAdapter);
        bookRV.setLayoutManager(new GridLayoutManager(this.getContext(), 2));
        db = FirebaseDatabase.getInstance();
        reference = db.getReference("books");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Book book = dataSnapshot.getValue(Book.class);
                    books.add(book);
                }
                booksAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return view;
    }

    @Override
    public void onBookClick(int position) {
        Book selected = books.get(position);
        Intent intent = new Intent(this.getContext(), BookProfile.class);
        intent.putExtra("selected", selected);
        startActivity(intent);
    }

    private boolean isAlreadyContained(ArrayList<Book> books, Book book) {
        for (Book i : books) {
            if (i.getTitle() == book.getTitle() ) return true;
        }
        return false;
    }
}