package com.example.beatwalk;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.beatwalk.R;
import com.example.beatwalk.data.RegistrationStore;
import com.example.beatwalk.data.RegistrationStoreMongo;

import java.util.Arrays;
import java.util.Map;

public class ReviewUserActivity extends AppCompatActivity {
    TextView userName;
    RegistrationStore users;
    String userEmail;
    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);

        userEmail = this.getIntent().getStringExtra("Email");
        userName = findViewById(R.id.reviewUserName);
        userName.setText("Review: " + userEmail);


        users = RegistrationStoreMongo.getInstance();
        String[] dataViews = new String[] {"1", "2", "3", "4", "5"};
        spinner = findViewById(R.id.ratingNumber);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, dataViews);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }


    public void rateUser(View view) {
        String ratingSelection = spinner.getSelectedItem().toString();
        Integer rating = Integer.valueOf(ratingSelection);
        users.rateUser(userEmail, rating);
        finishActivity(0);
        finish();
    }



}
