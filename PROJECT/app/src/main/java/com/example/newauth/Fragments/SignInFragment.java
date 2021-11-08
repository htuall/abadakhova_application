package com.example.newauth.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.newauth.HomePage;
import com.example.newauth.MainActivity;
import com.example.newauth.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignInFragment extends Fragment {

    private View v;
    private FirebaseAuth firebaseAuth;
    private EditText email, password;
    private Button signInBtn, arrow;
    private ProgressBar pb;



    public SignInFragment() {
    }

    private void attach(){
        firebaseAuth = FirebaseAuth.getInstance();
        email = v.findViewById(R.id.emailET);
        password = v.findViewById(R.id.passwordET);
        signInBtn = v.findViewById(R.id.sign_in_btn);
        arrow = v.findViewById(R.id.btn5);
        pb = v.findViewById(R.id.pb);
        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });
        arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).getSupportFragmentManager().popBackStack();
            }
        });
    }

    private void signIn(){
        try {
            if (!email.getText().toString().isEmpty() &&
                    !password.getText().toString().isEmpty()){
                if (firebaseAuth!=null){
                    pb.setVisibility(View.VISIBLE);
                    signInBtn.setEnabled(false);

                    firebaseAuth.signInWithEmailAndPassword(
                            email.getText().toString(),
                            password.getText().toString())
                            .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                @Override
                                public void onSuccess(AuthResult authResult) {
                                    startActivity(new Intent(
                                            getActivity().getApplicationContext(),
                                            HomePage.class));
                                    pb.setVisibility(View.INVISIBLE);
                                    signInBtn.setEnabled(true);
                                    getActivity().finish();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            pb.setVisibility(View.INVISIBLE);
                            signInBtn.setEnabled(true);
                            Toast.makeText(getContext(),
                                    e.getMessage(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            } else {
                Toast.makeText(
                        getContext(),
                        "Please fill in the fields",
                        Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e){
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_sign_in, container, false);
        attach();
        return v;
    }
}