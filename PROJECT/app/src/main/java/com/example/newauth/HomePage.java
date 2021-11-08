package com.example.newauth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.newauth.Fragments.BooksFragment;
import com.example.newauth.Fragments.CardsFragment;
import com.example.newauth.Fragments.ProfileFragment;
import com.example.newauth.Fragments.VideosFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class HomePage extends AppCompatActivity {

    FirebaseAuth firebaseAuth;

    BottomNavigationView bnv;
    BooksFragment booksFragment;
    CardsFragment cardsFragment;
    VideosFragment videosFragment;
    ProfileFragment profileFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        firebaseAuth = FirebaseAuth.getInstance();

        bnv = findViewById(R.id.bnv_hp);
        booksFragment=new BooksFragment();
        cardsFragment=new CardsFragment();
        videosFragment=new VideosFragment();
        profileFragment=new ProfileFragment();

        String name = firebaseAuth.getCurrentUser().getEmail();


        bnv.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId()==R.id.home){
                    Bundle args = new Bundle();
                    args.putString("key", name);
                    videosFragment.setArguments(args);
                    replaceFragment(videosFragment);
                    return true;
                } else if (item.getItemId()==R.id.book){
                    replaceFragment(booksFragment);
                    return true;
                } else if (item.getItemId()==R.id.card){
                    Bundle args = new Bundle();
                    args.putString("key", name);
                    cardsFragment.setArguments(args);
                    replaceFragment(cardsFragment);
                    return true;
                } else if (item.getItemId()==R.id.profile){
                    replaceFragment(profileFragment);
                    return true;
                } else {
                    return false;
                }
            }
        });
        bnv.setSelectedItemId(R.id.profile);
    }

    private void replaceFragment(Fragment fr) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.container, fr).commit();
    }
}