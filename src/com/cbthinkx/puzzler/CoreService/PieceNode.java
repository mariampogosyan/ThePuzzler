package com.cbthinkx.puzzler.CoreService;

import java.awt.image.BufferedImage;
import java.util.Iterator;

public class PieceNode {
    private int x;
    private int y;
    private int row;
    private int col;
	private BufferedImage bi;

    public PieceNode(int x, int y, int r, int c, BufferedImage bi) {
        this.x = x;
        this.y = y;
        this.col = c;
        this.row = r;
        this.bi = bi;
    }
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public BufferedImage getBi() {
        return bi;
    }

    public void setBi(BufferedImage bi) {
        this.bi = bi;
    }

    @Override
    public String toString() {
        return "PieceNode{" +
                "x=" + x +
                ", y=" + y +
                ", Row: " + row +
                ", Col: " + col +
                ", Width: " + bi.getWidth() +
                ", Height: " + bi.getHeight() +
                '}';
    }
}
