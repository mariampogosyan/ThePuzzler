package com.cbthinkx.puzzler.CoreService;

import java.awt.image.BufferedImage;
import java.util.Iterator;

public class PieceNode {
    private int x;
    private int y;
    private int row;
    private int col;
    private int peiceWidth;
    private int peiceHeight;
	private BufferedImage bi;
    private int top;
    private int bot;
    private int left;
    private int right;
	

    public PieceNode(int x, int y, int r, int c,   BufferedImage bi, int pw, int ph) {
        this.x = x;
        this.y = y;
        this.col = c;
        this.row = r;
        this.bi = bi;
        this.peiceWidth = pw;
        this.peiceHeight = ph;
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
    public int getTop() {
		return top;
	}
	public void setTop(int top) {
		this.top = top;
	}
	public int getRight() {
		return right;
	}
	public void setRight(int right) {
		this.right = right;
	}
	public int getBottom() {
		return bot;
	}
	public void setBottom(int bottom) {
		this.bot = bottom;
	}
	public int getLeft() {
		return left;
	}
	public void setLeft(int left) {
		this.left = left;
	}
    public int getPeiceWidth() {
        return peiceWidth;
    }
    public void setPeiceWidth(int peiceWidth) {
        this.peiceWidth = peiceWidth;
    }
    public int getPeiceHeight() {
        return peiceHeight;
    }
    public void setPeiceHeight(int peiceHeight) {
        this.peiceHeight = peiceHeight;
    }

    @Override
    public String toString() {
        return "PieceNode{" +
                "x=" + x +
                ", y=" + y +
                ", row=" + row +
                ", col=" + col +
                ", top=" + top +
                ", bot=" + bot +
                ", left=" + left +
                ", right=" + right +
                '}';
    }
}
