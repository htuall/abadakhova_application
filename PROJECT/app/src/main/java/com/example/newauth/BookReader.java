package com.example.newauth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

public class BookReader extends AppCompatActivity implements OnWordListener {

    private TextView tv;
    private Button arrow;
    private ClickableSpan clickableSpan;
    FirebaseAuth firebaseAuth;
    String name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_reader);
        Book book = getIntent().getParcelableExtra("selected");
        tv = findViewById(R.id.text);
        arrow = findViewById(R.id.btn5);
        ////////
        firebaseAuth = FirebaseAuth.getInstance();
        name = firebaseAuth.getCurrentUser().getEmail();
        ///////
        ArrayList<String> wordsToHighlight = new ArrayList<>();
        String fullText =String.valueOf(Html.fromHtml(book.bookText));
        String[] words = fullText.split(" ");
        wordsToHighlight.addAll(Arrays.asList(words));

        SpannableString spannableString = new SpannableString(fullText);

        ArrayList<String> brokenDownFullText = new ArrayList<>(Arrays.asList(fullText.split(" ")));
        brokenDownFullText.retainAll(wordsToHighlight);
        int i;
        for(i=0; i<wordsToHighlight.size(); i++) {
            int indexOfWord = fullText.indexOf(wordsToHighlight.get(i));
            int finalI = i;
            clickableSpan = new ClickableSpan() {
                @Override
                public void onClick(@NonNull View widget) {
                    //Snackbar.make(findViewById(R.id.root),wordsToHighlight.get(finalI),Snackbar.LENGTH_LONG).show();
                    translate(wordsToHighlight.get(finalI));
                }
                @Override
                public void updateDrawState(TextPaint ds) {
                    super.updateDrawState(ds);
                    ds.setUnderlineText(false);
                }
            };
            spannableString.setSpan(clickableSpan, indexOfWord, indexOfWord + wordsToHighlight.get(i).length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        tv.setText(spannableString);
        tv.setTextColor(Color.BLACK);
        tv.setMovementMethod(LinkMovementMethod.getInstance());
        tv.setHighlightColor(Color.YELLOW);

        arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BookReader.this, BookProfile.class);
                intent.putExtra("selected", book);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void onWord(String word, String translation) {
        String user = name.replace(".", "");
        Card card = new Card(word, translation);
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference(user);
        ref.push().setValue(card);
    }

    public void translate(String original){
        Thread thread = new Thread(() -> {
            String textTo = null;
            try {
                textTo = MyTranslate.Translate("en", "ru", original);
                if (textTo.contains("&#39;")){
                    String fileExtension = textTo;
                    String newExtension= fileExtension;
                    newExtension = newExtension.replace("&#39;", "'");
                    textTo = textTo.replace(fileExtension, newExtension);
                }
            } catch (IOException e) {}
            Snackbar.make(findViewById(R.id.root),original+"\n"+textTo,Snackbar.LENGTH_LONG).show();
            onWord(original, textTo);
        });
        thread.start();
    }
}