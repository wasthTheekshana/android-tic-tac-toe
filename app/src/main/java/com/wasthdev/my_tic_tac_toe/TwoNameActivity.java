package com.wasthdev.my_tic_tac_toe;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class TwoNameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two_name);
        System.out.println("MultiyPlaye : "+ MainActivity.Multipayer);
    }
}