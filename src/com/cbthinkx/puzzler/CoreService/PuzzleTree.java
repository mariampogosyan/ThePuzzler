package com.cbthinkx.puzzler.CoreService;

import java.util.ArrayList;

public class PuzzleTree {
    private ArrayList<PieceNode> arrList;
    private int row;
    private int column;

    public PuzzleTree(ArrayList<PieceNode> list, int row, int column) {
    	this.arrList = list;
    	this.row = row;
    	this.column = column;
    }
    public PieceNode getPiece(int x, int y) throws java.util.NoSuchElementException {
    	if (checkBounds(x, y)) {
        	for (PieceNode pn : arrList) {
        		if (pn.getX() == x && pn.getY() == y) {
        			return pn;
        		}
        	}
    	} else {
    		throw new java.util.NoSuchElementException();
    	}
		return null;
    }

    public PieceNode getBottomPiece(PieceNode pn) {
        return null;
    }
    public PieceNode getRightPiece(PieceNode pn) {
        return null;
    }
    private boolean checkBounds(int x, int y) {
    	if (x <= row && y <= column){
    		return true;
    	} 
    	return false;
    }

}
