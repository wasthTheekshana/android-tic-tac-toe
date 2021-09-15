package com.wasthdev.my_tic_tac_toe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wasthdev.my_tic_tac_toe.Adapter.ChessboardAdapter;

import java.util.ArrayList;

public class GameBoard extends AppCompatActivity {
    public static String TAG = GameBoard.class.getName();
    CharSequence player1 = "Player 1";
    CharSequence player2 = "Player 2";
    boolean selectedsingleplayer;

    private RecyclerView rv_table;
    public static boolean turnO = true;
    public static TextView txt_turn, win_x, win_o, txt_win,playerName;
    public static ImageView img_stroke, img_win;
    public static RelativeLayout rl_win;
    private ChessboardAdapter chessboardAdapter;
    private Button btn_reset, btn_again, btn_home;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_board);
        win_x = findViewById(R.id.p1score);
        win_o = findViewById(R.id.p2score);

        rv_table = findViewById(R.id.rv_chessboard);
        txt_win = findViewById(R.id.txt_win);
        img_stroke = findViewById(R.id.img_stroke);
        rl_win = findViewById(R.id.rl_win);
        img_win =findViewById(R.id.img_win);
        playerName =findViewById(R.id.PlayerName);


        btn_reset = findViewById(R.id.btn_reset);
        btn_again =findViewById(R.id.btn_again);
        btn_home = findViewById(R.id.btn_home);
        CharSequence[] players = getIntent().getCharSequenceArrayExtra("playersName");
        player1 = players[0];
        player2 = players[1];
        selectedsingleplayer = getIntent().getBooleanExtra("selectedsingleplayer", true);

        playerName.setText(player1);

        ArrayList<Bitmap> arrBms = new ArrayList<>();
        for (int i= 0; i < 9; i++){
            arrBms.add(null);
        }
        chessboardAdapter = new ChessboardAdapter(getApplicationContext(), arrBms);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 3);
        rv_table.setLayoutManager(layoutManager);
        rv_table.setAdapter(chessboardAdapter);
        btn_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reset();
            }
        });
    }

    private void reset() {
    ArrayList<Bitmap> arrBms = new ArrayList<>();
    for (int i = 0; i < 9;i++){
        arrBms.add(null);
    }
        img_stroke.setImageBitmap(null);
        chessboardAdapter.setArrBms(arrBms);
        chessboardAdapter.notifyDataSetChanged();
        turnO = true;
        Toast.makeText(this,"Turn of O",Toast.LENGTH_SHORT).show();
    }
}