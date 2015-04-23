package com.cbthinkx.puzzler.CoreService;

import org.apache.pdfbox.exceptions.COSVisitorException;

import javax.imageio.ImageIO;



import java.awt.Graphics2D;
import java.awt.geom.Path2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Jigsaw1 {
	private int peiceWidth;
	private int peiceHeight;
	private int row;
	private int col;
	private PuzzleData pd;
	private PuzzleTree pt;
	private ArrayList<PieceNode> pieces;
	private int k;
	private int pWidth;
	private int pHeight;
	public static void main(String[] sa) {
 BufferedImage orig = null;
		try {
			orig = ImageIO.read(new File("res/colors.jpg"));
		} catch (Exception e) {

		}
		PuzzleData pd = new PuzzleData(
				PieceShape.SQUARE,
				PuzzleShape.SQUARE,
				PuzzleSkill.BABY,
				PuzzleType.ONESIDED,
				orig,
				"jpg",
				10.0
		);
		Jigsaw1 puzzle = new Jigsaw1(pd);
		PDFGenerator pdfGenerator = new PDFGenerator(puzzle.getJigsawPieces());
		try {
			pdfGenerator.getfinalPuzzle().save(new File("Jigsaw.pdf"));
		} catch (IOException e) {
			e.printStackTrace();
		} catch (COSVisitorException e) {
			e.printStackTrace();
		}
	}
	public Jigsaw1(PuzzleData pd) {
		this.row = (pd.getImage().getWidth()/pd.getSkill().getVal());
		System.out.println("Row: " + row);
		this.col = (pd.getImage().getHeight()/pd.getSkill().getVal());
		System.out.println("Col: " + col);
		this.peiceWidth = pd.getImage().getWidth() / this.row;
		System.out.println("pWidth: " + peiceWidth);
		this.peiceHeight = pd.getImage().getHeight() / this.col;
		System.out.println("pHeight: " + peiceHeight);
		this.pd = pd;
		this.pd.setImage(offSetImage(pd.getImage()));
		System.out.println("Image Width: " + pd.getImage().getWidth() + " Height: " + pd.getImage().getHeight());
		jigSawIt();
	}
	private void jigSawIt() {
		this.pt = new PuzzleTree(splitUpImageUp(pd.getImage()), row, col);
		for (Object x : pt) {
			System.out.println(x.toString());
		}
	}
	private BufferedImage offSetImage(BufferedImage img) {
		BufferedImage offSet = new BufferedImage(
				img.getWidth() + (peiceWidth/3)*2,
				img.getHeight() + (peiceHeight/3)*2,
				BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = offSet.createGraphics();
		g2d.drawImage(img, null, peiceWidth/3, peiceHeight/3);
		g2d.dispose();
		return offSet;
	}
	private ArrayList<PieceNode> splitUpImageUp(BufferedImage main) {
		BufferedImage image;
		int width = main.getWidth();
		int height = main.getHeight();
		pieces = new ArrayList<>();
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < col; j++) {
				int offSetW = peiceWidth/3;
				int offSetH = peiceHeight/3;
				int x = j * (width-offSetW) / row;
				int y = i * (height-offSetH) / col;
				pWidth = (width+offSetW) / row;
				pHeight = (height+offSetH) / col;
				image = main.getSubimage(x, y, pWidth, pHeight);
				PieceNode pn = new PieceNode(j, i, x, y, image);
				pieces.add(pn);
			}
//			sides(pHeight, pWidth);
//			position(row, col);
		}
		return pieces;
	}
	public ArrayList<PieceNode> getJigsawPieces(){
		return pt.getArrayList();
	}
//	private void sides(int height, int width) {
//		for (int i = 0; i < pieces.size(); i++) {
//			pieces.get(i).setTop(pieces.get(i - height));
//			pieces.get(i).setRight(pieces.get(i + 1));
//			pieces.get(i).setBottom(pieces.get(i + height));
//			pieces.get(i).setLeft(pieces.get(i - 1));
//		}
//	}
	private Path2D addCurvePath(int height, int width, Path2D p2dd, int k) {
		p2dd.lineTo(0.0, -3*height/8);
		p2dd.lineTo(k*width/12, -3*height/8);
		p2dd.lineTo(k*width/4, -height/4);
		p2dd.curveTo(k*width/4, -height/4, k*width/3, -height/2, k*width/4, -3*height/4);
		p2dd.lineTo(k*width/12, -5*height/8);
		p2dd.lineTo(0.0, -5*height/8);
		p2dd.lineTo(0.0, -height);
		return p2dd;
	}
//	private int factor() {
//		int [] n = {-1, 1};
//		Random random = new Random();
//		k = n[random.nextInt(n.length)];
//		return k;
//	}
//	private BufferedImage drawCurve(BufferedImage img) {
//		BufferedImage fin = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_ARGB);
//		for (int i = 0; i < pieces.size(); i++) {
//			pieces.get(i).getBottom(); //add c
//			pieces.get(i).getRight();
//		}	
//		return null;
//	}
//	private void position(int row, int col) {
//		for (int i = 0; i < pieces.size(); i++) {
//			pieces.get(i).setColPos(i/row);
//			pieces.get(i).setRowPos(i/col);			
//		}				
//	}
}
