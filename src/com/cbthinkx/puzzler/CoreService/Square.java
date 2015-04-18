package com.cbthinkx.puzzler.CoreService;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


public class Square {
	PuzzleData pd;
	BufferedImage image;
	BufferedImage nImage;
    private ArrayList<PieceNode> pieces = new ArrayList<>();
	int width;
	int height;
	int npw;
	int nph;

    public ArrayList<PieceNode> getPieces() {
        return pieces;
    }
    public void setPieces(ArrayList<PieceNode> pieces) {
        this.pieces = pieces;
    }
    public static void main(String[] sa) {
        BufferedImage orig = null;
        try {
            orig = ImageIO.read(new File("res/puzzle.jpg"));
        } catch (Exception e) {

        }
        PuzzleData pd = new PuzzleData(
                PieceShape.SQUARE,
                PuzzleShape.SQUARE,
                PuzzleSkill.ADULT,
                PuzzleType.ONESIDED,
                orig,
                "jpg",
                10.0
        );
        Square sp = new Square(pd);
		System.out.println(sp.getPieces().size());
		System.out.println("NPH: " + sp.getNph() + " NPW: " + sp.getNpw());
		PuzzleTree pTree = new PuzzleTree(sp.getPieces(), sp.getNph(), sp.getNpw());
		int c = 0;
		for (Object x : pTree) {
			PieceNode y = (PieceNode) x;
			System.out.println("X: " + y.getX() + " Y: " + y.getY());
			c++;
		}
		System.out.println("Count: " + c);
        PDFGenerator pdfg = new PDFGenerator(sp.getPieces());
        System.out.println(pdfg.getfinalPuzzle().getNumberOfPages());
        try {
            pdfg.getfinalPuzzle().save(new File("squarePDF.pdf"));
            pdfg.getfinalPuzzle().close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	public Square (PuzzleData pd) {
		this.pd = pd;
		nImage = new ImageUtility().newImage(this.pd.getSize(), this.pd.getShape(), this.pd.getImage());
		height = nImage.getHeight();
		width = nImage.getWidth();	
		squareIt();
	}
	public void squareIt() {
		BufferedImage new1 = new BufferedImage(
				nImage.getWidth() + (nImage.getWidth()/3)*2,
				nImage.getHeight() + (nImage.getHeight()/3)*2,
				BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = new1.createGraphics();
		g2d.drawImage(nImage, null, nImage.getWidth() / 3, nImage.getHeight() / 3);
		g2d.dispose();
		try {
			ImageIO.write(new1,"png", new File("newImage.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		nImage = new1;

		npw = (width/pd.getSkill().getVal());
		nph = (height/pd.getSkill().getVal());
		
		for (int i = 0; i < nph; i++) {
			for (int j = 0; j < npw; j++) {
				int row = j * width / npw - (width/3);
				int col = i * height / nph - (height/3);
				image = nImage.getSubimage(row, col, width / npw, height / nph);
				PieceNode pn = new PieceNode(j, i, row, col, image);
				pieces.add(pn);
			}
       }
	}
	public int getPWidth() {
		return  width/npw;
	}
	public int getPHeight() {
		return  height/nph;
	}
	public int getNpw() {
		return npw;		
	}
	public int getNph() {
		return nph;
	}
}
