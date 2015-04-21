package com.cbthinkx.puzzler.CoreService;
import java.awt.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.util.Random;

import javax.swing.*;
public class DrawPiece extends JFrame {
	private static final long serialVersionUID = 1L;
		public DrawPiece() {
			super("Piece");
			setDefaultCloseOperation(EXIT_ON_CLOSE);
			add(new MyJPanel());
			pack();
			setLocationRelativeTo(null);
			setVisible(true);
		}
		public static void main(String[] sa) {
			EventQueue.invokeLater(
					() -> new DrawPiece()
			);	
		}
		private class MyJPanel extends JPanel {
			private int height = 120 ;
			private int width = 120 ;
			private static final long serialVersionUID = 1L;
			public MyJPanel() {
				super();
				setLayout(null);
				setBackground(Color.WHITE);
			}
			@Override
			public Dimension getPreferredSize() {
				return new Dimension(1024, 768);
			}
			@Override
//			protected void paintComponent(Graphics g) {
//
//				super.paintComponent(g);
//				Graphics2D g2d = (Graphics2D)g.create();
//				AffineTransform gat = new AffineTransform();
//				gat.translate(
//					getWidth() / 2.0,
//					getHeight() / 2.0
//				);
//				gat.scale(1.0, -1.0);
//				g2d.transform(gat);
//				Path2D.Double p2dd = new Path2D.Double();
//
//				p2dd.moveTo (0.0, 0.0);
//				p2dd.lineTo(0.0, -4*height/9);
//				p2dd.lineTo(width/9, -4*height/9);
//				p2dd.lineTo(2*width/9, -3*height/9);
//				p2dd.curveTo(2*width/9, -3*height/9, 5*width/9, -5*height/9, 2*width/9, -7*height/9);
//				p2dd.lineTo(width/9, -6*height/9);
//				p2dd.lineTo(0.0, -6*height/9);
//				p2dd.lineTo(0.0, -height);
//
//
//				p2dd.lineTo(-width,-height);
//				p2dd.lineTo(-width, 0);
//				p2dd.closePath();
//				g2d.setStroke(new BasicStroke(4.5f));
//				g2d.draw(p2dd);
//	            g2d.dispose();
//			}
			protected void paintComponent(Graphics g) {

				super.paintComponent(g);
				Graphics2D g2d = (Graphics2D)g.create();
				AffineTransform gat = new AffineTransform();
				gat.translate(
						getWidth() / 2.0,
						getHeight() / 2.0
				);
				gat.scale(1.0, -1.0);
				g2d.transform(gat);
				Path2D p2d = new Path2D.Double();
				AffineTransform tran = new AffineTransform();
				tran.rotate( Math.PI);
				p2d.moveTo(height, 0);
				p2d.lineTo(0, 0);
				p2d = addCurvePath(height, width, p2d);
				p2d.lineTo(width, -height);
				
				p2d.closePath();
				p2d.transform(tran);
				g2d.setStroke(new BasicStroke(4.5f));
				g2d.draw(p2d);
				g2d.dispose();

			}
			private Path2D addCurvePath(int height, int width, Path2D p2dd) {
				int [] n = {-1, 1};
				int k;
				Random random = new Random();
				k = n[random.nextInt(n.length)];
				p2dd.lineTo(0.0, -3*height/8);
				p2dd.lineTo(k*width/12, -3*height/8);
				p2dd.lineTo(k*width/4, -height/4);
				p2dd.curveTo(k*width/4, -height/4, k*width/3, -height/2, k*width/4, -3*height/4);
				p2dd.lineTo(k*width/12, -5*height/8);
				p2dd.lineTo(0.0, -5*height/8);
				p2dd.lineTo(0.0, -height);
 				System.out.println("w="+getWidth());
				return p2dd;
			}
		}

//		private BufferedImage combineVertical(BufferedImage cur, BufferedImage bot) {
//			int height = cur.getHeight() + bot.getHeight();
//			int width = Math.max(cur.getWidth(), bot.getWidth());
//			BufferedImage fin = new BufferedImage(width, height, cur.getType());
//			Graphics2D g2 = fin.createGraphics();
//			g2.drawImage(cur, null, 0, 0);
//			g2.drawImage(bot, null, 0, cur.getHeight());
//			g2.dispose();
//			return fin;
//		}
//		private BufferedImage combineHorizontal(BufferedImage cur, BufferedImage right) {
//			int height = Math.max(cur.getHeight(), right.getHeight());
//			int width = cur.getWidth() + right.getWidth();
//			BufferedImage fin = new BufferedImage(width, height, cur.getType());
//			Graphics2D g2 = fin.createGraphics();
//			g2.drawImage(cur, null, 0, 0);
//			g2.drawImage(right, null, cur.getWidth(), 0);
//			g2.dispose();
//			return fin;
//		}
//		private BufferedImage jigSawVertical(BufferedImage img, boolean isCur) {
//			BufferedImage fin = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_ARGB);
//			if (isCur) {
//				AffineTransform tran = new AffineTransform();
//				tran.translate(
//						img.getWidth() /256,
//						img.getHeight() / 2
//				);
//				tran.rotate(Math.PI / 2);
//				Path2D p2d = new Path2D.Double();
//				p2d.moveTo(-img.getHeight() / 2, 0);
//				p2d.lineTo(0, 0);
//				p2d = addCurvePath(img.getHeight() / 2, img.getWidth(), p2d);
//				p2d.lineTo(-img.getWidth(), -img.getHeight() / 2);
//				p2d.closePath();
//				Shape sp = p2d.createTransformedShape(tran);
//				Graphics2D g2 = fin.createGraphics();
//				g2.setColor(new Color(0, true));
//				g2.fillRect(0, 0, img.getWidth(), img.getHeight());
//				g2.setStroke(new BasicStroke(6.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
//				g2.setClip(sp);
//				g2.drawImage(img, null, 0, 0);
//				g2.dispose();
//				BufferedImage crop = new BufferedImage(fin.getWidth(), (int)(fin.getHeight() * (.68)), BufferedImage.TYPE_INT_ARGB);
//				Graphics2D g2dd = crop.createGraphics();
//				g2dd.drawImage(fin, null, 0, 0);
//				g2dd.dispose();
//				return crop;
//			} else {
//				AffineTransform tran = new AffineTransform();
//				tran.translate(
//						img.getWidth() /256,
//						img.getHeight() / 2
//				);
//				tran.rotate(Math.PI / 2);
//				Path2D p2d = new Path2D.Double();
//				p2d.moveTo(img.getHeight() / 2, 0);
//				p2d.lineTo(0, 0);
//				p2d = addCurvePath(img.getHeight() / 2, img.getWidth(), p2d);
//				p2d.lineTo(img.getWidth(), -img.getHeight() / 2);
//				p2d.closePath();
//				Shape sp = p2d.createTransformedShape(tran);
//				Graphics2D g2 = fin.createGraphics();
//				g2.setColor(new Color(0, true));
//				g2.fillRect(0, 0, img.getWidth(), img.getHeight());
//				g2.setStroke(new BasicStroke(6.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
//				g2.setClip(sp);
//				g2.drawImage(img, null, 0, 0);
//				g2.dispose();
//				BufferedImage crop = new BufferedImage(fin.getWidth(), fin.getHeight()/2, BufferedImage.TYPE_INT_ARGB);
//				Graphics2D g2dd = crop.createGraphics();
//				g2dd.drawImage(fin, null, 0, -fin.getHeight() / 2);
//				g2dd.dispose();
//				return crop;
//			}
//		}
//		private BufferedImage jigSawHorizontal(BufferedImage img, boolean isCur) {
//			BufferedImage fin = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_ARGB);
//			if (isCur) {
//				AffineTransform tran = new AffineTransform();
//				tran.translate(
//						img.getWidth() /2,
//						img.getHeight() / 256
//				);
//				tran.rotate(Math.PI);
//				Path2D p2d = new Path2D.Double();
//				p2d.moveTo(img.getHeight(), 0);
//				p2d.lineTo(0, 0);
//				p2d = addCurvePath(img.getHeight(), img.getWidth() / 2, p2d);
//				p2d.lineTo(img.getWidth() / 2, -img.getHeight());
//				p2d.closePath();
//				Shape sp = p2d.createTransformedShape(tran);
//				Graphics2D g2 = fin.createGraphics();
//				g2.setColor(new Color(0, true));
//				g2.fillRect(0, 0, img.getWidth(), img.getHeight());
//				g2.setStroke(new BasicStroke(6.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
//				g2.setClip(sp);
//				g2.drawImage(img, null, 0, 0);
//				g2.dispose();
//				BufferedImage crop = new BufferedImage(fin.getWidth() / 2,fin.getHeight(), BufferedImage.TYPE_INT_ARGB);
//				Graphics2D g2dd = crop.createGraphics();
//				g2dd.drawImage(fin, null, 0, 0);
//				g2dd.dispose();
//				return crop;
//			} else {
//				AffineTransform tran = new AffineTransform();
//				tran.translate(
//						img.getWidth() /2,
//						img.getHeight() / 256
//				);
//				tran.rotate(Math.PI);
//				Path2D p2d = new Path2D.Double();
//				p2d.moveTo(-img.getHeight(), 0);
//				p2d.lineTo(0, 0);
//				p2d = addCurvePath(img.getHeight(), img.getWidth() / 2, p2d);
//				p2d.lineTo(-img.getWidth() / 2, -img.getHeight());
//				p2d.closePath();
//				Shape sp = p2d.createTransformedShape(tran);
//				Graphics2D g2 = fin.createGraphics();
//				g2.setColor(new Color(0, true));
//				g2.fillRect(0, 0, img.getWidth(), img.getHeight());
//				g2.setStroke(new BasicStroke(6.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
//				g2.setClip(sp);
//				g2.drawImage(img, null, 0, 0);
//				g2.dispose();
//				BufferedImage crop = new BufferedImage((int)(fin.getWidth() * (0.68)),fin.getHeight(), BufferedImage.TYPE_INT_ARGB);
//				Graphics2D g2dd = crop.createGraphics();
//				g2dd.drawImage(fin, null, (int)(-fin.getWidth() / 2 ), 0);
//				g2dd.dispose();
//				return crop;
//			}
//		}
}
