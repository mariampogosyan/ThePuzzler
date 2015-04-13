package com.cbthinkx.puzzler.CoreService;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

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
			Path2D p2d = new Path2D.Double();
			p2d.moveTo(0, img.getHeight() / 2);
			p2d.lineTo(img.getWidth() * 0.35, img.getHeight() / 2);  //left arrow
			p2d.lineTo(img.getWidth() * 0.45, img.getHeight() * 0.60);  //point
			p2d.lineTo(img.getWidth() * 0.55, img.getHeight() / 2);  //right arrow
			p2d.lineTo(img.getWidth(), img.getHeight() / 2);
			p2d.lineTo(img.getWidth(), 0);
			p2d.lineTo(0, 0);
			p2d.closePath();
			Shape sp = p2d.createTransformedShape(null);
			Graphics2D g2 = fin.createGraphics();
			g2.setColor(new Color(0, true));
			g2.fillRect(0, 0, img.getWidth(), img.getHeight());
			g2.setStroke(new BasicStroke(6.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
			g2.setColor(new Color(0, true));
			g2.fillRect(0, 0, img.getWidth(), img.getHeight());
			g2.setClip(sp);
			g2.drawImage(img, null, 0, 0);
			g2.dispose();
		}
		return fin;
	}
	private BufferedImage jigSawHorizontal(BufferedImage img, boolean isCur) {
		return null;
	}
	private Path2D getCurvePath(int height, int width) {
		return null;
	}
}
