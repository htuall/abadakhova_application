package com.example.newauth.Fragments;

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

import com.example.newauth.MainActivity;
import com.example.newauth.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.SignInMethodQueryResult;


public class SignUpFragment extends Fragment {

    View v;
    private EditText email, password;
    private Button btn, arrow;
    private ProgressBar pb;

    private FirebaseAuth firebaseAuth;

    public void createUser(){
        pb.setVisibility(View.VISIBLE);
        btn.setEnabled(false);
        if (!email.getText().toString().isEmpty() && !password.getText().toString().isEmpty()){
            firebaseAuth.createUserWithEmailAndPassword(
                    email.getText().toString(),
                    password.getText().toString())
                    .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            Toast.makeText(getContext(),
                                    "User created",
                                    Toast.LENGTH_LONG ).show();
                            pb.setVisibility(View.INVISIBLE);
                            btn.setEnabled(true);
                            email.setText("");
                            password.setText("");
                            if (firebaseAuth.getCurrentUser()!=null){
                                firebaseAuth.signOut();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            pb.setVisibility(View.INVISIBLE);
                            btn.setEnabled(true);
                            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG ).show();
                        }
                    });
        } else {
            Toast.makeText(
                    getContext(),
                    "Please fill in the fields",
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void check(){
        try {
            if (firebaseAuth!=null && !email.getText().toString().isEmpty()){
                pb.setVisibility(View.VISIBLE);
                btn.setEnabled(false);
                firebaseAuth.fetchSignInMethodsForEmail(
                        email.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
                            @Override
                            public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
                                boolean checkRes = !task.getResult().getSignInMethods().isEmpty();
                                if(!checkRes){
                                    createUser();
                                } else {
                                    Toast.makeText(
                                            getContext(),
                                            "User with this email already exists",
                                            Toast.LENGTH_SHORT)
                                            .show();
                                    pb.setVisibility(View.INVISIBLE);
                                    btn.setEnabled(true);
                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                pb.setVisibility(View.INVISIBLE);
                                btn.setEnabled(true);
                                Toast.makeText(
                                        getContext(),
                                        e.getMessage(),
                                        Toast.LENGTH_SHORT ).show();
                            }
                        });
            }
        } catch (Exception e){
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT ).show();
        }
    }


    public SignUpFragment() {
    }

    private void attach(){
        email = v.findViewById(R.id.emailET);
        password = v.findViewById(R.id.passwordET);
        btn = v.findViewById(R.id.btn);
        pb = v.findViewById(R.id.pb);
        arrow = v.findViewById(R.id.btn5);

        firebaseAuth = FirebaseAuth.getInstance();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check();
            }
        });
        arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).getSupportFragmentManager().popBackStack();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_sign_up, container, false);
        attach();
        return  v;
    }
}