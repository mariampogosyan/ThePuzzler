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
				gat.scale(-1.0, 1.0);
				g2d.transform(gat);
				Path2D p2d = new Path2D.Double();
				AffineTransform tran = new AffineTransform();
				tran.rotate( Math.PI);
				p2d.moveTo(0, 0);
				p2d.lineTo(width, 0);
				p2d.lineTo(width, height);
				p2d.lineTo(0, height);
				p2d.closePath();
				int k = 1;
				//bottom
				p2d.moveTo(3*width/15, 3*height/15);
				p2d.lineTo(6*width/15, 3*height/15);
				p2d.lineTo(6*width/15, 3*height/15 - k*height/15);
				p2d.lineTo(5.5*width/15, 3*height/15- 2*k*height/15);
				p2d.curveTo(5.5*width/15, 3*height/15 - 2*k*height/15, 7.5*width/15, 3*height/15-k*3*height/15, 9.5*width/15, 3*height/15 - 2*k*height/15);
				p2d.lineTo( 9*width/15, 3*height/15 - k*height/15);
				p2d.lineTo(9*width/15, 3*height/15);
				p2d.lineTo(12*width/15, 3*height/15);
			
//				//right
				p2d.lineTo(12*width/15, 6*height/15);
				p2d.lineTo(12*width/15 + k*width/15, 6*height/15);
				p2d.lineTo(12*width/15 + k*2*width/15, 5.5*height/15);
				p2d.curveTo(12*width/15 + k*2*width/15, 5.5*height/15, 12*width/15 + k*3*width/15, 7.5*height/15, 12*width/15 + k*2*width/15, 9.5*height/15);
				p2d.lineTo(12*width/15 + k*width/15, 9*height/15);
				p2d.lineTo(12*width/15, 9*height/15);
				p2d.lineTo(12*width/15, 12*height/15);
//				
//				//top
				p2d.lineTo(9*width/15, 12*height/15);
				p2d.lineTo(9*width/15, 12*height/15 + k*height/15);
				p2d.lineTo(9.5*width/15, 12*height/15 + 2*k*height/15);
				p2d.curveTo(9.5*width/15, 12*height/15 + 2*k*height/15, 7.5*width/15, 12*height/15+k*3*height/15, 5.5*width/15, 12*height/15 + 2*k*height/15);
				p2d.lineTo(6*width/15, 12*height/15 + k*height/15);
				p2d.lineTo(6*width/15, 12*height/15);
				p2d.lineTo(3*width/15, 12*height/15);
				
//				//left
				p2d.lineTo(3*width/15, 9*height/15);
				p2d.lineTo(3*width/15 - k*width/15, 9*height/15);
				p2d.lineTo(3*width/15 - k*2*width/15, 9.5*height/15);
				p2d.curveTo(3*width/15 - k*2*width/15, 9.5*height/15, 3*width/15 - k*3*width/15, 7.5*height/15, 3*width/15 - k*2*width/15, 5.5*height/15);
				p2d.lineTo(3*width/15 - k*width/15, 6*height/15);
				p2d.lineTo(3*width/15, 6*height/15);
				p2d.lineTo(3*width/15, 3*height/15);
				
				
				p2d.closePath();
				
				p2d.transform(tran);
				g2d.setStroke(new BasicStroke(4.5f));
				g2d.draw(p2d);
				g2d.dispose();

			}
		}
}
