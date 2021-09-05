package com.wasthdev.my_tic_tac_toe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wasthdev.my_tic_tac_toe.Adapter.ChessboardAdapter;

import java.util.ArrayList;

public class GameBoard extends AppCompatActivity {
    public static String TAG = GameBoard.class.getName();
    CharSequence player1 = "Player 1";
    CharSequence player2 = "Player 2";
    boolean selectedsingleplayer;

    private RecyclerView rv_table;
    public static boolean turnO = true;
    public static TextView txt_turn, win_x, win_o, txt_win;
    public static ImageView img_stroke, img_win;
    public static RelativeLayout rl_win;
    private ChessboardAdapter chessboardAdapter;
    private Button btn_reset, btn_again, btn_home;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_board);
        win_x = findViewById(R.id.PlayerName);
        win_o = findViewById(R.id.PlayerName2);

        rv_table = findViewById(R.id.rv_chessboard);
        txt_win = findViewById(R.id.txt_win);
        img_stroke = findViewById(R.id.img_stroke);
        rl_win = findViewById(R.id.rl_win);
        img_win =findViewById(R.id.img_win);


        btn_reset = findViewById(R.id.btn_reset);
        btn_again =findViewById(R.id.btn_again);
        btn_home = findViewById(R.id.btn_home);
        CharSequence[] players = getIntent().getCharSequenceArrayExtra("playersName");
        player1 = players[0];
        player2 = players[1];
        selectedsingleplayer = getIntent().getBooleanExtra("selectedsingleplayer", true);

        win_x.setText(player1);

        ArrayList<Bitmap> arrBms = new ArrayList<>();
        for (int i= 0; i < 9; i++){
            arrBms.add(null);
        }
    }
}