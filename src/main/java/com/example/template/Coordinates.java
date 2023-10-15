package com.example.template;

public class Coordinates {
    private int row;
    private int column;
//    private boolean isABorderCord = false;

    public Coordinates(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }
}
