package com.cbthinkx.puzzler.CoreService;
import java.awt.*;
import java.awt.geom.*;

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
				Path2D.Double p2dd = new Path2D.Double();
				p2dd.moveTo (0.0, 0.0);
				p2dd.lineTo(0.0, -4*height/9);
				p2dd.lineTo(width/9, -4*height/9);
				p2dd.lineTo(2*width/9, -3*height/9);
				p2dd.curveTo(2*width/9, -3*height/9, 5*width/9, -5*height/9, 2*width/9, -7*height/9);
				p2dd.lineTo(width/9, -6*height/9);
				p2dd.lineTo(0.0, -6*height/9);

				
				p2dd.lineTo(0.0, -height);
				p2dd.lineTo(-width,-height);
				p2dd.lineTo(-width, 0);
				p2dd.closePath();
				p2dd.moveTo (0.0, 0.0);
				g2d.setStroke(new BasicStroke(4.5f));
				g2d.draw(p2dd);
	            g2d.dispose();
			}
		}
}
