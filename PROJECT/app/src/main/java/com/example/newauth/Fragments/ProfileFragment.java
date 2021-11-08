package com.example.newauth.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.newauth.HomePage;
import com.example.newauth.MainActivity;
import com.example.newauth.R;
import com.google.firebase.auth.FirebaseAuth;


public class ProfileFragment extends Fragment {

    FirebaseAuth firebaseAuth;
    TextView username;
    Button signOut;


    public ProfileFragment() {
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
        View v = inflater.inflate(R.layout.fragment_profile, container, false);
        firebaseAuth = FirebaseAuth.getInstance();
        username = v.findViewById(R.id.username);
        signOut = v.findViewById(R.id.btn_so);
        if(firebaseAuth!=null){
            String name = firebaseAuth.getCurrentUser().getEmail();
            username.setText(name+", welcome back!");
        }
        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (firebaseAuth!=null){
                    firebaseAuth.signOut();
                    startActivity(new Intent(v.getContext(), MainActivity.class));
                }
            }
        });
        return v;
    }
}