package com.wasthdev.my_tic_tac_toe.Adapter;

public class Mark {
    private int id;
    private int point;

    public Mark(int id, int point) {
        this.id = id;
        this.point = point;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    @Override
    public String toString() {
        return "{" + id +", " + point +"}";
    }
}