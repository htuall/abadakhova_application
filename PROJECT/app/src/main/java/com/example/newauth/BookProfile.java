package com.example.newauth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.newauth.Fragments.BooksFragment;

public class BookProfile extends AppCompatActivity {
    TextView author, title, description;
    ImageView bookCover;
    Button button, arrow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_profile);
        Book book = getIntent().getParcelableExtra("selected");
        arrow = findViewById(R.id.btn5);
        author = findViewById(R.id.author);
        title = findViewById(R.id.title);
        bookCover = findViewById(R.id.book_cover);
        description = findViewById(R.id.description);
        button = findViewById(R.id.button);
        author.setText(book.author);
        title.setText(book.title);
        description.setText(book.description);
        Glide.with(this).load(Uri.parse(book.imageUrl)).into(bookCover);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BookProfile.this, BookReader.class);
                intent.putExtra("selected", book);
                startActivity(intent);
                finish();
            }
        });
        arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BookProfile.this, HomePage.class);
                startActivity(intent);
                finish();
            }
        });
    }
}