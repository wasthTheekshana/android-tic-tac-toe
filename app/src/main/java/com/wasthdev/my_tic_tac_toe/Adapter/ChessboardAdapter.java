package com.wasthdev.my_tic_tac_toe.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.wasthdev.my_tic_tac_toe.GameBoard;
import com.wasthdev.my_tic_tac_toe.MainActivity;
import com.wasthdev.my_tic_tac_toe.R;

import java.util.ArrayList;

public class ChessboardAdapter extends RecyclerView.Adapter<ChessboardAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Bitmap> arrBms,arrBmTest;
    private Bitmap bm_O,bm_X,draw;
    private ArrayList<Bitmap> arrStroke;
    private String winChara = "O";
    private Animation anim_x_o,animStroke,anim_win;
    private boolean checkMax = true;
    private int depth = 0;

    public ChessboardAdapter(Context context, ArrayList<Bitmap> arrBms) {
        this.context = context;
        this.arrBms = arrBms;
        bm_O = BitmapFactory.decodeResource(context.getResources(),R.drawable.o);
        bm_X = BitmapFactory.decodeResource(context.getResources(),R.drawable.x);
        draw = BitmapFactory.decodeResource(context.getResources(),R.drawable.draw);
        arrStroke = new ArrayList<>();
        arrStroke.add(BitmapFactory.decodeResource(context.getResources(),R.drawable.stroke1));
        arrStroke.add(BitmapFactory.decodeResource(context.getResources(),R.drawable.stroke2));
        arrStroke.add(BitmapFactory.decodeResource(context.getResources(),R.drawable.stroke3));
        arrStroke.add(BitmapFactory.decodeResource(context.getResources(),R.drawable.stroke4));
        arrStroke.add(BitmapFactory.decodeResource(context.getResources(),R.drawable.stroke5));
        arrStroke.add(BitmapFactory.decodeResource(context.getResources(),R.drawable.stroke6));
        arrStroke.add(BitmapFactory.decodeResource(context.getResources(),R.drawable.stroke7));
        arrStroke.add(BitmapFactory.decodeResource(context.getResources(),R.drawable.stroke8));
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_table,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        holder.img_item_table.setImageBitmap(arrBms.get(position));
        holder.img_item_table.setAnimation(anim_x_o);

        anim_x_o = AnimationUtils.loadAnimation(context,R.anim.anim_x_o);
        animStroke = AnimationUtils.loadAnimation(context,R.anim.anim_stroke);

        GameBoard.img_stroke.setAnimation(animStroke);
        anim_win = AnimationUtils.loadAnimation(context,R.anim.anim_win);

        if (MainActivity.Multipayer){
            MultiPlayerGame(holder,position);
        }else {
            PlayWithRobot(holder,position);
        }
    }

    private void PlayWithRobot(ViewHolder holder, int position) {
    }

    private void MultiPlayerGame(ViewHolder holder, int position) {
        holder.img_item_table.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (arrBms.get(position) == null ){}
            }
        });
    }

    // check the win or lose
    public boolean CheckWin(){

        //
        if (arrBms.get(0)  == arrBms.get(3) && arrBms.get(3) == arrBms.get(6) && arrBms.get(0) != null){
            GameBoard.img_stroke.setImageBitmap(arrStroke.get(2));
            CheckWinPlayer(0);
            return true;
        }else if(arrBms.get(1)  == arrBms.get(4) && arrBms.get(4) == arrBms.get(7) && arrBms.get(1) != null){
           //left side down match
            GameBoard.img_stroke.setImageBitmap(arrStroke.get(3));
            CheckWinPlayer(1);
            return true;
        }else if(arrBms.get(2)  == arrBms.get(5) && arrBms.get(5) == arrBms.get(8) && arrBms.get(2) != null){
            //middle side down match
            GameBoard.img_stroke.setImageBitmap(arrStroke.get(4));
            CheckWinPlayer(2);
            return true;
        }else if(arrBms.get(0)  == arrBms.get(1) && arrBms.get(1) == arrBms.get(2) && arrBms.get(0) != null){
            //first row match
            GameBoard.img_stroke.setImageBitmap(arrStroke.get(5));
            CheckWinPlayer(0);
            return true;
        }else if(arrBms.get(3)  == arrBms.get(4) && arrBms.get(4) == arrBms.get(5) && arrBms.get(3) != null){
            //middle row match
            GameBoard.img_stroke.setImageBitmap(arrStroke.get(6));
            CheckWinPlayer(3);
            return true;
        }else if(arrBms.get(6)  == arrBms.get(7) && arrBms.get(7) == arrBms.get(8) && arrBms.get(6) != null){
            //last row match
            GameBoard.img_stroke.setImageBitmap(arrStroke.get(7));
            CheckWinPlayer(6);
            return true;
        }else if(arrBms.get(0)  == arrBms.get(4) && arrBms.get(4) == arrBms.get(8) && arrBms.get(0) != null){
            // \ cross
            GameBoard.img_stroke.setImageBitmap(arrStroke.get(1));
            CheckWinPlayer(0);
            return true;
        }else if(arrBms.get(2)  == arrBms.get(4) && arrBms.get(4) == arrBms.get(6) && arrBms.get(2) != null){
            //middle row match
            GameBoard.img_stroke.setImageBitmap(arrStroke.get(0));
            CheckWinPlayer(2);
            return true;
        }


        return false;
    }

    //check the player who is win
    public void CheckWinPlayer(int i){
        if (arrBms.get(i) == bm_O){
            winChara = "O";
        }else {
            winChara = "x";
        }
    }
    @Override
    public int getItemCount() {
        return arrBms.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView img_item_table;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img_item_table = itemView.findViewById(R.id.img_item_table);
        }
    }
}
