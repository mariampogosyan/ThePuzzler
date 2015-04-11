package com.cbthinkx.puzzler.CoreService;

import java.awt.image.BufferedImage;
import java.util.Iterator;

public class PieceNode {
    private int x;
    private int y;
	private BufferedImage bi;

    public PieceNode(int x, int y, BufferedImage bi) {
        this.x = x;
        this.y = y;
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
}
