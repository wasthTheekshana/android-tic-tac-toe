package com.wasthdev.my_tic_tac_toe.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
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

    public ArrayList<Bitmap> getArrBms() {
        return arrBms;
    }

    public void setArrBms(ArrayList<Bitmap> arrBms) {
        this.arrBms = arrBms;
    }

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
        if (!CheckWin()) checkDraw();
    }

    private void PlayWithRobot(ViewHolder holder, int position) {
        holder.img_item_table.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (arrBms.get(position)==null&&!CheckWin()&&GameBoard.turnO){
                    if(GameBoard.turnO){
                        arrBms.set(position, bm_O);
                        GameBoard.turnO = false;
                       // GameBoard.txt_turn.setText("turn of X");
                    }
                    holder.img_item_table.startAnimation(anim_x_o);
                    if (CheckWin()){
                        Win();
                    }
                    notifyItemChanged(position);
                    Handler handler = new Handler();
                    Runnable r = new Runnable() {
                        @Override
                        public void run() {
                            arrBmTest = arrBms;
                            ArrayList<Mark> arrMark = solver(bm_X);
                            if (arrMark.size()>0){
                                int max = arrMark.get(0).getPoint();
                                int id = 0;
                                for (int i = 0; i < arrMark.size(); i++) {
                                    if (max < arrMark.get(i).getPoint()) {
                                        max = arrMark.get(i).getPoint();
                                        id = i;
                                    }
                                }
                                int p = id;
                                arrBms.set(arrMark.get(p).getId(), bm_X);
                                if (CheckWin()){
                                    Win();
                                }else{
                                    GameBoard.turnO = true;
                                   // GameBoard.txt_turn.setText("turn of O");
                                }
                                notifyItemChanged(arrMark.get(p).getId());
                            }
                        }
                    };
                    if (!CheckWin()){
                        handler.postDelayed(r, 1000);
                    }
                }
            }
        });
    }
    private ArrayList<Mark> solver(Bitmap bm) {
        ArrayList<Mark> arrPoints = new ArrayList<>();
        for (int i = 0; i < 9; i++){
            if(arrBmTest.get(i)==null){
                if (bm==bm_X) {
                    arrBmTest.set(i, bm_X);
                }else{
                    arrBmTest.set(i, bm_O);
                }
                if (checkWinTmp(bm)==-100){
                    if (bm==bm_X){
                        depth++;
                        ArrayList<Mark> arr = solver(bm_O);
                        depth--;
                        int minimum = 50;
                        int id = 50;
                        for (int j = 0; j < arr.size(); j++){
                            if (minimum>arr.get(j).getPoint()){
                                minimum = arr.get(j).getPoint();
                                id = i;
                            }
                        }
                        if (minimum!=50&&id!=50){
                            arrPoints.add(new Mark(i, minimum));
                        }
                    }else{
                        depth++;
                        ArrayList<Mark> arr = solver(bm_X);
                        depth--;
                        int maximum = -50;
                        int id = -50;
                        for (int j = 0; j < arr.size(); j++){
                            if (maximum < arr.get(j).getPoint()){
                                maximum = arr.get(j).getPoint();
                                id = i;
                            }
                        }
                        if (maximum!=-50&&id!=-50) {
                            arrPoints.add(new Mark(i, maximum));
                        }

                    }
                }else{
                    if (bm == bm_X){
                        arrPoints.add(new Mark(i, checkWinTmp(bm) - depth));
                    }else{
                        arrPoints.add(new Mark(i, -(checkWinTmp(bm) - depth)));
                    }
                }
                arrBmTest.set(i,null);
            }
        }
        return arrPoints;
    }

    private int checkWinTmp(Bitmap bm) {
        int countRow = 0;
        for (int i = 0; i < 9; i++){
            if(i%3==0){
                countRow = 0;
            }
            if(arrBmTest.get(i)==bm){
                countRow++;
            }
            if (countRow==3){
                return 10;
            }
        }
        if (arrBmTest.get(0)==arrBmTest.get(3)&&arrBmTest.get(3)==arrBmTest.get(6)&&arrBmTest.get(0)==bm
                ||arrBmTest.get(1)==arrBmTest.get(4)&&arrBmTest.get(4)==arrBmTest.get(7)&&arrBmTest.get(1)==bm
                ||arrBmTest.get(2)==arrBmTest.get(5)&&arrBmTest.get(5)==arrBmTest.get(8)&&arrBmTest.get(2)==bm){
            return 10;
        }
        if (arrBmTest.get(0)==arrBmTest.get(4)&&arrBmTest.get(4)==arrBmTest.get(8)&&arrBmTest.get(0)==bm) return 10;
        if (arrBmTest.get(2)==arrBmTest.get(4)&&arrBmTest.get(4)==arrBmTest.get(6)&&arrBmTest.get(2)==bm) return 10;
        int count = 0;
        for (int i = 0; i < 9; i++){
            if (arrBmTest.get(i)!=null){
                count++;
            }
        }
        if (count==9){
            return 0;
        }
        return -100;
    }
    private void MultiPlayerGame(ViewHolder holder, int position) {
        holder.img_item_table.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (arrBms.get(position) == null ){}
            }
        });
    }

    //check Draw
    private void checkDraw() {
        int count = 0;
        for (int i = 0; i < arrBms.size(); i++){
            if (arrBms.get(i)!=null){
                count++;
            }
        }
        if (count == 9){
            GameBoard.img_stroke.startAnimation(animStroke);
            GameBoard.rl_win.setVisibility(View.VISIBLE);
            GameBoard.rl_win.setAnimation(anim_win);
            GameBoard.rl_win.startAnimation(anim_win);
            GameBoard.img_win.setImageBitmap(draw);
            GameBoard.txt_win.setText("draw");
        }
    }


    //win method
    public void Win(){
        System.out.println("Come to the Win");
        GameBoard.img_stroke.startAnimation(anim_win);
        GameBoard.rl_win.setVisibility(View.VISIBLE);
        GameBoard.rl_win.startAnimation(anim_win);
        if (winChara.equals("O")){
            GameBoard.img_win.setImageBitmap(bm_O);
            MainActivity.scoreO++;
            GameBoard.win_o.setText(""+MainActivity.scoreO);
        }else {
            GameBoard.img_win.setImageBitmap(bm_X);
            MainActivity.scoreX++;
            GameBoard.win_x.setText(""+MainActivity.scoreX);
        }
       // GameBoard.txt_win.setText("Win");
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
            // / cross
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
