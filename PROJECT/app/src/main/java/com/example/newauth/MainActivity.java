
package com.example.newauth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.newauth.Fragments.GreetingFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import com.example.newauth.Fragments.SignInFragment;
import com.example.newauth.Fragments.SignUpFragment;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    SignInFragment sifr;
    SignUpFragment sufr;
    GreetingFragment greetingFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sifr = new SignInFragment();
        sufr = new SignUpFragment();
        greetingFragment = new GreetingFragment();
        replaceFragment(greetingFragment);
        /*bnv.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId()==R.id.isi){
                    replaceFragment(sifr);
                    return true;
                } else if (item.getItemId()==R.id.isu){
                    replaceFragment(sufr);
                    return true;
                } else {
                    return false;
                }
            }
        });*/

        if (FirebaseAuth.getInstance().getCurrentUser()!=null){
            startActivity(new Intent(
                    MainActivity.this,
                    HomePage.class));
            finish();
        }
    }

    private void replaceFragment(Fragment fr) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.container, fr).addToBackStack(null).commit();
    }

}