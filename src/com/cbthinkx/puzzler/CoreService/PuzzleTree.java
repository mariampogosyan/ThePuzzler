package com.cbthinkx.puzzler.CoreService;

import java.util.ArrayList;

public class PuzzleTree implements Iterable {
    private ArrayList<PieceNode> arrList;
    private int row;
    private int column;

    public PuzzleTree(ArrayList<PieceNode> list, int row, int column) {
    	this.arrList = list;
    	this.row = row;
    	this.column = column;
    }
	@Override
	public java.util.Iterator<PieceNode> iterator() {
		return new PuzzleTreeIterator<PieceNode>(this);
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
    private boolean checkBounds(int x, int y) {
    	if (x <= row && y <= column){
    		return true;
    	} 
    	return false;
    }
	public PieceNode getBottomPiece(PieceNode pn) {
		return getPiece(pn.getX(), pn.getY() + 1);
	}
	public PieceNode getRightPiece(PieceNode pn) {
		return getPiece(pn.getX() + 1, pn.getY());
	}

    private class PuzzleTreeIterator<E> implements java.util.Iterator<PieceNode> {
    	private PuzzleTree ptlist;
		private int x = -1;
		private int y = 0;

    	public PuzzleTreeIterator(PuzzleTree ptlist) {
    		this.ptlist = ptlist;
			this.x = -1;
			this.y = 0;
    	}
		@Override
		public boolean hasNext() {
			if (!hasNextX() && !hasNextY()) {
				return false;
			}
			return true;
		}
		private boolean hasNextX(){
			if (x + 1 < row) {
				return true;
			}
			return false;			
		}
		private boolean hasNextY(){
			if (y + 1 < column){
				return true;
			}
			return false;
		}
		@Override
		public PieceNode next() {
			if (hasNext())	{
				if (hasNextX()) {
					x++;
				} else {
					if (hasNextY()) {
						x = 0;
						y++;
					}
				}
			} else {
				throw new java.util.NoSuchElementException();
			}
			return ptlist.getPiece(x, y);
		}    	
    }
}
