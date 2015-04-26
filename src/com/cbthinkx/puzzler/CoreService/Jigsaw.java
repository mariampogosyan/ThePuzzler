package com.cbthinkx.puzzler.CoreService;

import org.apache.pdfbox.exceptions.COSVisitorException;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Jigsaw {
	private int peiceWidth;
	private int peiceHeight;
	private int row;
	private int col;
	private int origWidth;
	private int origHeight;
	private PuzzleData pd;
	private PuzzleTree pt;


	public static void main(String[] sa) {
		BufferedImage orig = null;
		try {
			orig = ImageIO.read(new File("res/colors.jpg"));
		} catch (Exception e) {
            e.printStackTrace();
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
		Jigsaw jiggy = new Jigsaw(pd);
		PDFGenerator pdfGenerator = new PDFGenerator(jiggy.getJigsawPieces());
		try {
			pdfGenerator.getfinalPuzzle().save(new File("Jigsaw.pdf"));
		} catch (IOException e) {
			e.printStackTrace();
		} catch (COSVisitorException e) {
			e.printStackTrace();
		}
	}
	public Jigsaw(PuzzleData pd) {
		this.row = (pd.getImage().getWidth() / pd.getSkill().getVal());
		this.col = (pd.getImage().getHeight() / pd.getSkill().getVal());
		this.origWidth = pd.getImage().getWidth();
		this.origHeight = pd.getImage().getHeight();
		this.peiceWidth = pd.getImage().getWidth() / row;
		this.peiceHeight = pd.getImage().getHeight() / col;
		this.pd = pd;
		this.pd.setImage(offSetImage(pd.getImage()));
		jigSawIt();
	}
	private void jigSawIt() {
		this.pt = new PuzzleTree(splitUpImageUp(pd.getImage()), row, col);
		setUpSides();
		for (Object x : pt) {
            PieceNode pn = (PieceNode) x;
			System.out.println(pn.toString());
            BufferedImage crop = cropImage(pn.getBottom(), pn.getRight(), pn.getTop(), pn.getLeft(), pn.getBi());
            pn.setBi(crop);
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
		ArrayList<PieceNode> pieces = new ArrayList<>();
//        int width = main.getWidth();
//        int height = main.getHeight();
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                int offSetW = (peiceWidth / 3);
                int offSetH = (peiceHeight / 3);
                int pWidth = peiceWidth+2*offSetW;
                int pHeight = peiceHeight+2*offSetH;
                int y = (j * ((origWidth) / row)-offSetH);
                y = y + offSetH;
                int x = (i * ((origHeight) / col)-offSetW);
                x = x + offSetW;
				System.out.println("offSetH: " + offSetH + " offSetW: " + offSetW);
				System.out.println("X: " + x + " Y: " + y + " Width: " + main.getWidth() + " Height: " + main.getHeight());
				image = main.getSubimage(x, y, pWidth, pHeight);
				PieceNode pn = new PieceNode(j, i, x, y, image, pWidth, pHeight);
				pieces.add(pn);
			}
		}
		return pieces;
	}
	private void setUpSides() {
		for (Object x : pt) {
			PieceNode pieceNode = (PieceNode) x;
			if (pt.hasRightPiece(pieceNode)) {
				int ranRight = getRandomSide();
				pieceNode.setRight(ranRight);
				pt.getRightPiece(pieceNode).setLeft(getOppesite(ranRight));
			} else {
				pieceNode.setRight(0);
			}
			if (pt.hasBottomPiece(pieceNode)) {
				int ranBot = getRandomSide();
				pieceNode.setBottom(ranBot);
				pt.getBottomPiece(pieceNode).setTop(getOppesite(ranBot));
			} else {
				pieceNode.setBottom(0);
			}
			if (pieceNode.getY()  == 0) {
				pieceNode.setTop(0);
			}
			if (pieceNode.getY() == pt.getRow()) {
				pieceNode.setBottom(0);
			}
			if (pieceNode.getX() == 0) {
				pieceNode.setLeft(0);
			}
			if (pieceNode.getX() == pt.getColumn()) {
				pieceNode.setRight(0);
			}
		}
	}
	private int getRandomSide() {
		int ran = (int)(Math.random() * 2);
		return (ran == 0) ? -1 : 1;
	}
	private int getOppesite(int num) {
		return (num == -1) ? 1 : -1;
	}
	public ArrayList<PieceNode> getJigsawPieces(){
		return pt.getArrayList();
	}
    private BufferedImage cropImage(int bottom, int right, int top, int left, BufferedImage img) {
        int height = img.getHeight();
        int width = img.getWidth();
        BufferedImage fin = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = fin.createGraphics();
        AffineTransform gat = new AffineTransform();
        gat.translate(
                3*width/15,
                3*height/15
        );
        Path2D p2d = new Path2D.Double();
//      left
        p2d.moveTo(3*width/15, 3*height/15);
        p2d.lineTo(6*width/15, 3*height/15);
        p2d.lineTo(6*width/15, 3*height/15 - left*height/15);
        p2d.lineTo(5.5*width/15, 3*height/15- 2*left*height/15);
        p2d.curveTo(5.5*width/15, 3*height/15 - 2*left*height/15, 7.5*width/15, 3*height/15-left*3*height/15, 9.5*width/15, 3*height/15 - 2*left*height/15);
        p2d.lineTo( 9*width/15, 3*height/15 - left*height/15);
        p2d.lineTo(9*width/15, 3*height/15);
        p2d.lineTo(12*width/15, 3*height/15);
//		bottom
        p2d.lineTo(12*width/15, 6*height/15);
        p2d.lineTo(12*width/15 + bottom*width/15, 6*height/15);
        p2d.lineTo(12*width/15 + bottom*2*width/15, 5.5*height/15);
        p2d.curveTo(12*width/15 + bottom*2*width/15, 5.5*height/15, 12*width/15 + bottom*3*width/15, 7.5*height/15, 12*width/15 + bottom*2*width/15, 9.5*height/15);
        p2d.lineTo(12*width/15 + bottom*width/15, 9*height/15);
        p2d.lineTo(12*width/15, 9*height/15);
        p2d.lineTo(12*width/15, 12*height/15);
//		right
        p2d.lineTo(9*width/15, 12*height/15);
        p2d.lineTo(9*width/15, 12*height/15 + right*height/15);
        p2d.lineTo(9.5*width/15, 12*height/15 + 2*right*height/15);
        p2d.curveTo(9.5*width/15, 12*height/15 + 2*right*height/15, 7.5*width/15, 12*height/15+right*3*height/15, 5.5*width/15, 12*height/15 + 2*right*height/15);
        p2d.lineTo(6*width/15, 12*height/15 + right*height/15);
        p2d.lineTo(6*width/15, 12*height/15);
        p2d.lineTo(3*width/15, 12*height/15);
//		top
        p2d.lineTo(3*width/15, 9*height/15);
        p2d.lineTo(3*width/15 - top*width/15, 9*height/15);
        p2d.lineTo(3*width/15 - top*2*width/15, 9.5*height/15);
        p2d.curveTo(3*width/15 - top*2*width/15, 9.5*height/15, 3*width/15 - top*3*width/15, 7.5*height/15, 3*width/15 - top*2*width/15, 5.5*height/15);
        p2d.lineTo(3*width/15 - top*width/15, 6*height/15);
        p2d.lineTo(3*width/15, 6*height/15);
        p2d.lineTo(3*width/15, 3*height/15);
        p2d.closePath();
        Shape sp = p2d.createTransformedShape(null);
        g2d.setClip(sp);
        g2d.drawImage(img, null, 0, 0);
        g2d.dispose();
        return fin;
    }
}
