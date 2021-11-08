package com.example.newauth.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.newauth.Card;
import com.example.newauth.HomePage;
import com.example.newauth.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class GameFragment extends Fragment implements View.OnClickListener {

    private ArrayList<Card> cards = new ArrayList<>();
    private ArrayList<Integer> questionsArray = new ArrayList<>();
    private String[] answers = new String[4];
    private int chosenWord = 0, rounds = 0, curRound = 0, locationOfCorrectAnswer = 0, correctAnswers = 0;
    private TextView originalWord, score;
    private Button btn1, btn2, btn3, btn4, btn5;
    private Random rand;
    FirebaseDatabase db;
    DatabaseReference reference;
    String KEY = "key";
    String str, user;
    int count=0;


    public GameFragment() {
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            str = getArguments().getString(KEY);
            cards = getArguments().getParcelableArrayList("key2");
            count = cards.size();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_game, container, false);

        rand = new Random();

        originalWord = view.findViewById(R.id.text_view_original);
        score = view.findViewById(R.id.score);

        btn1 = view.findViewById(R.id.button1);
        btn1.setOnClickListener(this);
        btn2 = view.findViewById(R.id.button2);
        btn2.setOnClickListener(this);
        btn3 = view.findViewById(R.id.button3);
        btn3.setOnClickListener(this);
        btn4 = view.findViewById(R.id.button4);
        btn4.setOnClickListener(this);
        btn5 = view.findViewById(R.id.btn5);
        btn5.setOnClickListener(this);
        user = str.replace(".", "");
        db = FirebaseDatabase.getInstance();
        reference = db.getReference(user);
        rounds = count;
        nextWord();
        return view;
    }

    private void checkNextAction() {
        Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(() -> {
            if (curRound < rounds) {
                nextWord();
            } else {
                /*GameResultFragment fragment = new GameResultFragment();
                Bundle arguments = new Bundle();
                arguments.putInt("game_correct", correctAnswers);
                arguments.putInt("game_all", rounds);
                arguments.putString("key3", str);
                fragment.setArguments(arguments);
                ((HomePage) getActivity()).getSupportFragmentManager().beginTransaction().replace(R.id.container,
                        fragment, "game_results").commit();*/
                btn1.setVisibility(View.INVISIBLE);
                btn2.setVisibility(View.INVISIBLE);
                btn3.setVisibility(View.INVISIBLE);
                btn4.setVisibility(View.INVISIBLE);
                score.setVisibility(View.INVISIBLE);
                btn5.setVisibility(View.VISIBLE);
                originalWord.setText("You got "+correctAnswers+" out of "+rounds+" !");
            }
        }, 1000);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button1:
                if (locationOfCorrectAnswer == 0) {
                    correctAnswers++;
                    btn1.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.green));
                } else {
                    btn1.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.red));
                }
                checkNextAction();
                break;
            case R.id.button2:
                if (locationOfCorrectAnswer == 1) {
                    correctAnswers++;
                    btn2.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.green));
                } else {
                    btn2.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.red));
                }
                checkNextAction();
                break;
            case R.id.button3:
                if (locationOfCorrectAnswer == 2) {
                    correctAnswers++;
                    btn3.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.green));
                } else {
                    btn3.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.red));
                }
                checkNextAction();
                break;
            case R.id.button4:
                if (locationOfCorrectAnswer == 3) {
                    correctAnswers++;
                    btn4.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.green));
                } else {
                    btn4.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.red));
                }
                checkNextAction();
                break;
            case R.id.btn5:
                ((HomePage) getActivity()).getSupportFragmentManager().popBackStack();
        }
    }

    private boolean isAlreadyContained(ArrayList<Integer> answers, int answer) {
        for (int i : answers) {
            if (i == answer) return true;
        }
        return false;
    }

    private void nextWord() {

        btn1.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.black));
        btn2.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.black));
        btn3.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.black));
        btn4.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.black));

        //Setting the right score
        curRound++;
        score.setText(curRound + "/" + rounds);

        //Choosing a word from originals array
        chosenWord = rand.nextInt(count);
        while (isAlreadyContained(questionsArray, chosenWord)) {
            chosenWord = rand.nextInt(count);
        }
        questionsArray.add(chosenWord);
        originalWord.setText(cards.get(chosenWord).getWord());

        locationOfCorrectAnswer = rand.nextInt(4);

        ArrayList<Integer> incorrectArray = new ArrayList<>();
        int incorrectAnswer;
        for (int i = 0; i < 4; i++) {
            if (i == locationOfCorrectAnswer) {
                answers[i] = cards.get(chosenWord).getTranslation();
            } else {
                incorrectAnswer = rand.nextInt(count);
                while ((incorrectAnswer == chosenWord) || isAlreadyContained(incorrectArray, incorrectAnswer)) {
                    incorrectAnswer = rand.nextInt(count);
                }
                incorrectArray.add(incorrectAnswer);
                answers[i] = cards.get(incorrectAnswer).getTranslation();
            }
        }
        btn1.setText(answers[0]);
        btn2.setText(answers[1]);
        btn3.setText(answers[2]);
        btn4.setText(answers[3]);

    }
}