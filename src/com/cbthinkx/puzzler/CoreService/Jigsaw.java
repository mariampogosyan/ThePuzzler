package com.cbthinkx.puzzler.CoreService;

import org.apache.pdfbox.exceptions.COSVisitorException;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
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
		this.row = (pd.getImage().getWidth()/ PuzzleSkill.ADULT.getVal());
		this.col = (pd.getImage().getHeight()/ PuzzleSkill.ADULT.getVal());
		this.origWidth = pd.getImage().getWidth();
		this.origHeight = pd.getImage().getHeight();
		this.peiceWidth = pd.getImage().getWidth() / col;
		this.peiceHeight = pd.getImage().getHeight() / row;
		this.pd = pd;
		this.pd.setImage(offSetImage(pd.getImage()));
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
		ArrayList<PieceNode> pieces = new ArrayList<>();
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < col; j++) {
				int offSetW = (peiceWidth / 3);
				int offSetH = (peiceHeight / 3);
				int pWidth = peiceWidth+2*offSetW;
				int pHeight = peiceHeight+2*offSetH;
				int y = (j * (((origWidth) / row)-offSetH));
				int x = (i * (((origHeight) / col)-offSetW));
				System.out.println("offSetH: " + offSetH + " offSetW: " + offSetW);
				System.out.println("X: " + x + "Y: " + y + "Width: " + main.getWidth() + " Height: " + main.getHeight());
				image = main.getSubimage(x, y, pWidth, pHeight);
				PieceNode pn = new PieceNode(j, i, x, y, image);
				pieces.add(pn);
			}
		}
		return pieces;
	}
	public ArrayList<PieceNode> getJigsawPieces(){
		return pt.getArrayList();
	}

	private BufferedImage combineVertical(BufferedImage cur, BufferedImage bot) {
		int height = cur.getHeight() + bot.getHeight();
		int width = Math.max(cur.getWidth(), bot.getWidth());
		BufferedImage fin = new BufferedImage(width, height, cur.getType());
		Graphics2D g2 = fin.createGraphics();
		g2.drawImage(cur, null, 0, 0);
		g2.drawImage(bot, null, 0, cur.getHeight());
		g2.dispose();
		return fin;
	}
	private BufferedImage combineHorizontal(BufferedImage cur, BufferedImage right) {
		int height = Math.max(cur.getHeight(), right.getHeight());
		int width = cur.getWidth() + right.getWidth();
		BufferedImage fin = new BufferedImage(width, height, cur.getType());
		Graphics2D g2 = fin.createGraphics();
		g2.drawImage(cur, null, 0, 0);
		g2.drawImage(right, null, cur.getWidth(), 0);
		g2.dispose();
		return fin;
	}
	private BufferedImage jigSawVertical(BufferedImage img, boolean isCur) {
		BufferedImage fin = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_ARGB);
		if (isCur) {
			AffineTransform tran = new AffineTransform();
			tran.translate(
					img.getWidth() /256,
					img.getHeight() / 2
			);
			tran.rotate(Math.PI / 2);
			Path2D p2d = new Path2D.Double();
			p2d.moveTo(-img.getHeight() / 2, 0);
			p2d.lineTo(0, 0);
			p2d = addCurvePath(img.getHeight() / 2, img.getWidth(), p2d);
			p2d.lineTo(-img.getWidth(), -img.getHeight() / 2);
			p2d.closePath();
			Shape sp = p2d.createTransformedShape(tran);
			Graphics2D g2 = fin.createGraphics();
			g2.setColor(new Color(0, true));
			g2.fillRect(0, 0, img.getWidth(), img.getHeight());
			g2.setStroke(new BasicStroke(6.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
			g2.setClip(sp);
			g2.drawImage(img, null, 0, 0);
			g2.dispose();
			BufferedImage crop = new BufferedImage(fin.getWidth(), (int)(fin.getHeight() * (.68)), BufferedImage.TYPE_INT_ARGB);
			Graphics2D g2dd = crop.createGraphics();
			g2dd.drawImage(fin, null, 0, 0);
			g2dd.dispose();
			return crop;
		} else {
			AffineTransform tran = new AffineTransform();
			tran.translate(
					img.getWidth() /256,
					img.getHeight() / 2
			);
			tran.rotate(Math.PI / 2);
			Path2D p2d = new Path2D.Double();
			p2d.moveTo(img.getHeight() / 2, 0);
			p2d.lineTo(0, 0);
			p2d = addCurvePath(img.getHeight() / 2, img.getWidth(), p2d);
			p2d.lineTo(img.getWidth(), -img.getHeight() / 2);
			p2d.closePath();
			Shape sp = p2d.createTransformedShape(tran);
			Graphics2D g2 = fin.createGraphics();
			g2.setColor(new Color(0, true));
			g2.fillRect(0, 0, img.getWidth(), img.getHeight());
			g2.setStroke(new BasicStroke(6.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
			g2.setClip(sp);
			g2.drawImage(img, null, 0, 0);
			g2.dispose();
			BufferedImage crop = new BufferedImage(fin.getWidth(), fin.getHeight()/2, BufferedImage.TYPE_INT_ARGB);
			Graphics2D g2dd = crop.createGraphics();
			g2dd.drawImage(fin, null, 0, -fin.getHeight() / 2);
			g2dd.dispose();
			return crop;
		}
	}
	private BufferedImage jigSawHorizontal(BufferedImage img, boolean isCur) {
		BufferedImage fin = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_ARGB);
		if (isCur) {
			AffineTransform tran = new AffineTransform();
			tran.translate(
					img.getWidth() /2,
					img.getHeight() / 256
			);
			tran.rotate(Math.PI);
			Path2D p2d = new Path2D.Double();
			p2d.moveTo(img.getHeight(), 0);
			p2d.lineTo(0, 0);
			p2d = addCurvePath(img.getHeight(), img.getWidth() / 2, p2d);
			p2d.lineTo(img.getWidth() / 2, -img.getHeight());
			p2d.closePath();
			Shape sp = p2d.createTransformedShape(tran);
			Graphics2D g2 = fin.createGraphics();
			g2.setColor(new Color(0, true));
			g2.fillRect(0, 0, img.getWidth(), img.getHeight());
			g2.setStroke(new BasicStroke(6.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
			g2.setClip(sp);
			g2.drawImage(img, null, 0, 0);
			g2.dispose();
			BufferedImage crop = new BufferedImage(fin.getWidth() / 2,fin.getHeight(), BufferedImage.TYPE_INT_ARGB);
			Graphics2D g2dd = crop.createGraphics();
			g2dd.drawImage(fin, null, 0, 0);
			g2dd.dispose();
			return crop;
		} else {
			AffineTransform tran = new AffineTransform();
			tran.translate(
					img.getWidth() /2,
					img.getHeight() / 256
			);
			tran.rotate(Math.PI);
			Path2D p2d = new Path2D.Double();
			p2d.moveTo(-img.getHeight(), 0);
			p2d.lineTo(0, 0);
			p2d = addCurvePath(img.getHeight(), img.getWidth() / 2, p2d);
			p2d.lineTo(-img.getWidth() / 2, -img.getHeight());
			p2d.closePath();
			Shape sp = p2d.createTransformedShape(tran);
			Graphics2D g2 = fin.createGraphics();
			g2.setColor(new Color(0, true));
			g2.fillRect(0, 0, img.getWidth(), img.getHeight());
			g2.setStroke(new BasicStroke(6.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
			g2.setClip(sp);
			g2.drawImage(img, null, 0, 0);
			g2.dispose();
			BufferedImage crop = new BufferedImage((int)(fin.getWidth() * (0.68)),fin.getHeight(), BufferedImage.TYPE_INT_ARGB);
			Graphics2D g2dd = crop.createGraphics();
			g2dd.drawImage(fin, null, (int)(-fin.getWidth() / 2 ), 0);
			g2dd.dispose();
			return crop;
		}
	}
	private Path2D addCurvePath(int height, int width, Path2D p2dd) {
		p2dd.lineTo(0.0, -height/6);
		p2dd.lineTo(1*width/6, -height/6);
		p2dd.lineTo(width / 5, -3 * height / 10);
		p2dd.curveTo(width / 6, -2 * height / 3, width / 3, -height / 3, width / 5, -7 * height / 10);
		p2dd.lineTo(width/10, -3*height/5);
		p2dd.lineTo(0.0, -5*height/6);
		p2dd.lineTo(0.0, -height);
		return p2dd;
	}
}
