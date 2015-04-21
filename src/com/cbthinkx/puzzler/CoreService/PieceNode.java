package com.cbthinkx.puzzler.CoreService;

import java.awt.image.BufferedImage;
import java.util.Iterator;

public class PieceNode {
    private int x;
    private int y;
    private int row;
    private int col;
	private BufferedImage bi;
	private PieceNode top = null;
	private PieceNode right = null;
	private PieceNode bottom = null;
	private PieceNode left = null;

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
    public PieceNode getTop() {
		return top;
	}
	public void setTop(PieceNode top) {
		this.top = top;
	}
	public PieceNode getRight() {
		return right;
	}
	public void setRight(PieceNode right) {
		this.right = right;
	}
	public PieceNode getBottom() {
		return bottom;
	}
	public void setBottom(PieceNode bottom) {
		this.bottom = bottom;
	}
	public PieceNode getLeft() {
		return left;
	}
	public void setLeft(PieceNode left) {
		this.left = left;
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
