package com.cbthinkx.puzzler.CoreService;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Jigsaw extends Square{
	private PuzzleTree pt;

	public Jigsaw(PuzzleData pd) {
		super(pd);
		pt = new PuzzleTree(getPieces(), getNph(), getNpw());
		
	}

}
