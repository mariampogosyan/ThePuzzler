package com.cbthinkx.puzzler.CoreService;

import java.awt.image.BufferedImage;

public class PieceNode {
	private PieceNode top = null;
	private PieceNode bottom = null;
	private PieceNode left = null;
	private PieceNode right = null;
	private BufferedImage bi;

	
	public PieceNode(PieceNode top, PieceNode bottom, PieceNode left,
			PieceNode right, BufferedImage bi) {
		this.top = top;
		this.bottom = bottom;
		this.left = left;
		this.right = right;
		this.bi = bi;
	}
	public PieceNode getTop() {
		return top;
	}
	public void setTop(PieceNode top) {
		this.top = top;
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
	public PieceNode getRight() {
		return right;
	}
	public void setRight(PieceNode right) {
		this.right = right;
	}
	public BufferedImage getBi() {
		return bi;
	}
	public void setBi(BufferedImage bi) {
		this.bi = bi;
	}

}
