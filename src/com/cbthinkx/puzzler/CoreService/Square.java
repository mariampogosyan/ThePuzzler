package com.cbthinkx.puzzler.CoreService;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
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
		npw = (width/pd.getSkill().getVal());
		nph = (height/pd.getSkill().getVal());
		
		for (int i = 0; i < nph; i++) {
			for (int j = 0; j < npw; j++) {
				image = nImage.getSubimage(j * width / npw, i * height / nph, width / npw, height / nph);
				PieceNode pn = new PieceNode(j, i, image);
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
