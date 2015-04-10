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
    	return getPiece(pn.getX(), pn.getY()+1);
    }
    public PieceNode getRightPiece(PieceNode pn) {
        return getPiece(pn.getX()+1, pn.getY());
    }
    private boolean checkBounds(int x, int y) {
    	if (x <= row && y <= column){
    		return true;
    	} 
    	return false;
    }
    private class PuzzleTreeIterator<PieceNode> implements java.util.Iterator<PieceNode> {
    	private PuzzleTree ptlist;
    	private com.cbthinkx.puzzler.CoreService.PieceNode current;
    	public PuzzleTreeIterator(PuzzleTree ptlist) {
    		this.ptlist = ptlist;
    		this.current = ptlist.getPiece(0, 0);    		
    	}
		@Override
		public boolean hasNext() {
			if (!hasNextX() && !hasNextY()) {
				return false;
			}
			return true;
		}
		private boolean hasNextX(){
			if (current.getX() <= row) {
				return true;
			}
			return false;			
		}
		private boolean hasNextY(){
			if (current.getY() <= column){
				return true;
			}
			return false;
		}		
		@Override
		public PieceNode next() {
			if (hasNext())	{
				if (hasNextX()) {
					current = ptlist.getPiece(current.getX()+1, current.getY());
				} else {
					if (hasNextY()) {
						current = ptlist.getPiece(0, current.getY()+1);				
					}
				}
			} else {
				throw new java.util.NoSuchElementException();
			}
			return null;
		}    	
    }
}
