package com.example.newauth.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.newauth.HomePage;
import com.example.newauth.MainActivity;
import com.example.newauth.R;


public class GreetingFragment extends Fragment implements View.OnClickListener {

    Button hi_btn, have_btn;

    public GreetingFragment() {
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
        View v = inflater.inflate(R.layout.fragment_greeting, container, false);
        hi_btn = v.findViewById(R.id.hi_button);
        have_btn = v.findViewById(R.id.have_btn);
        hi_btn.setOnClickListener(this);
        have_btn.setOnClickListener(this);
        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.hi_button:
                replaceFragment(new SignUpFragment());
                break;
            case R.id.have_btn:
                replaceFragment(new SignInFragment());
                break;
        }
    }
    private void replaceFragment(Fragment fr) {
        FragmentTransaction fragmentTransaction = ((MainActivity) getActivity()).getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.container, fr).addToBackStack(null).commit();
    }
}