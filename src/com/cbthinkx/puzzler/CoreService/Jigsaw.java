package com.cbthinkx.puzzler.CoreService;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

public class Jigsaw extends Square{
	private PuzzleTree pt;

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
		Jigsaw jiggy = new Jigsaw(pd);
	}
	public Jigsaw(PuzzleData pd) {
		super(pd);
		pt = new PuzzleTree(getPieces(), getNph(), getNpw());
		PieceNode pn = pt.getPiece(2, 3);
		try {
			ImageIO.write(combineVertical(pn.getBi(), pt.getBottomPiece(pn).getBi()), pd.getImgTail(),new File("vertical."+pd.getImgTail()));
			ImageIO.write(comvineHorizontal(pn.getBi(), pt.getRightPiece(pn).getBi()), pd.getImgTail(),new File("horizontal."+pd.getImgTail()));
			ImageIO.write(jigSawVertical(combineVertical(pn.getBi(), pt.getBottomPiece(pn).getBi()), true), pd.getImgTail(), new File("clipped." + pd.getImgTail()));
		} catch (IOException e) {
			e.printStackTrace();
		}
//		jigSawIt();
	}
	private void jigSawIt() {
		for (Object x : pt) {
			PieceNode pn = (PieceNode) x;

		}
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
	private BufferedImage comvineHorizontal(BufferedImage cur, BufferedImage right) {
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
//			tran.rotate(Math.PI/2);
			tran.translate(
					img.getWidth() /256,
					img.getHeight() / 2
			);
			tran.rotate(Math.PI/2);

//			tran.translate(img.getWidth(), img.getHeight()/256);
			Path2D p2d = new Path2D.Double();
			p2d.moveTo(-img.getHeight() / 2, 0);
			p2d.lineTo(0,0);
			p2d = addCurvePath(img.getHeight() / 2, img.getWidth(), p2d);
			p2d.lineTo(-img.getWidth(), -img.getHeight() / 2);
			p2d.closePath();
			//p2d.transform(tran);
			Shape sp = p2d.createTransformedShape(tran);
			Graphics2D g2 = fin.createGraphics();
			g2.setColor(new Color(0, true));
			g2.fillRect(0, 0, img.getWidth(), img.getHeight());
			g2.setStroke(new BasicStroke(6.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
//			g2.setColor(Color.CYAN);
//			g2.fillRect(0, 0, img.getWidth(), img.getHeight());
			g2.setClip(sp);
//			g2.draw(sp);
			g2.drawImage(img, null, 0, 0);
			g2.dispose();
		}
		return fin;
	}
	private BufferedImage jigSawHorizontal(BufferedImage img, boolean isCur) {
		return null;
	}
	private Path2D addCurvePath(int height, int width, Path2D p2dd) {
		p2dd.lineTo(0.0, -4*height/9);
		p2dd.lineTo(width/9, -4*height/9);
		p2dd.lineTo(2 * width / 9, -3 * height / 9);
		p2dd.curveTo(2 * width / 9, -3 * height / 9, 5 * width / 9, -5 * height / 9, 2 * width / 9, -7 * height / 9);
		p2dd.lineTo(width/9, -6*height/9);
		p2dd.lineTo(0.0, -6*height/9);
		p2dd.lineTo(0.0, -height);
		return p2dd;
	}
}
