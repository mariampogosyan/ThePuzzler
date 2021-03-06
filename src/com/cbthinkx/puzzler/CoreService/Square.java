package com.cbthinkx.puzzler.CoreService;

import com.cbthinkx.puzzler.CoreService.Enum.PieceShape;
import com.cbthinkx.puzzler.CoreService.Enum.PuzzleShape;
import com.cbthinkx.puzzler.CoreService.Enum.PuzzleSkill;
import com.cbthinkx.puzzler.CoreService.Enum.PuzzleType;

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
	int col;
	int row;
	int pWidth; 
	int pHeight;
    public ArrayList<PieceNode> getPieces() {
        return pieces;
    }
    public void setPieces(ArrayList<PieceNode> pieces) {
        this.pieces = pieces;
    }
    public static void main(String[] sa) {
        BufferedImage orig = null;
        try {
            orig = ImageIO.read(new File("res/Cute_Duck.jpg"));
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
		PuzzleTree pTree = new PuzzleTree(sp.getPieces(), sp.getCol(), sp.getRow());
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
		col = (nImage.getWidth()/ PuzzleSkill.ADULT.getVal());
		pWidth = nImage.getWidth()/col;
		row = (int) (nImage.getHeight()/pWidth);
		pHeight = pWidth;
		row = nImage.getHeight()/pHeight;
		pHeight = nImage.getHeight()/row;
		
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < col; j++) {
				image = nImage.getSubimage(j*pWidth, i*pHeight, pWidth, pHeight);
				PieceNode pn = new PieceNode(i, j, col, row, image, pWidth, pHeight);
				pieces.add(pn);
			}
       }
	}
	public int getCol() {
		return col;		
	}
	public int getRow() {
		return row;
	}
}
