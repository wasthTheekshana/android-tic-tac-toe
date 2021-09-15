package com.wasthdev.my_tic_tac_toe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import java.util.Timer;
import java.util.TimerTask;

public class NameActivity extends AppCompatActivity {

    EditText player1;
    Button button2;
    public CharSequence player_1 = "1";
    public CharSequence player_2 = "2";
    private int length;
    public boolean selectedSinglePLayer = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_name);

        MainActivity.Multipayer = false;
        player1 =findViewById(R.id.playeronewa1);
        button2 = findViewById(R.id.button2);
        player1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                player_1 = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                length = player1.getText().length();
            }
        },0,2);

        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (length > 1){
                    button2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(NameActivity.this,GameBoard.class);
                            CharSequence[] players = {player_1,player_2};
                            intent.putExtra("playersName",players);
                            intent.putExtra("selectedSinglePLayer",selectedSinglePLayer);
                            startActivity(intent);

                        }
                    });
                }
            }
        },0,20);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(NameActivity.this,MainActivity.class);
        startActivity(intent);
    }
}
