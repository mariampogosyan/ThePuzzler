package com.cbthinkx.puzzler.CoreService;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

public class SplitUpImage extends JFrame {
    private static final long serialVersionUID = 1L;
    BufferedImage orig;
    public SplitUpImage() {
        super("SplitUpImage");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        add(new MyJPanel());
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        try {
            orig = ImageIO.read(new File("newImage.png"));
        } catch (Exception e) {

        }
    }
    public static void main(String[] sa) {
        EventQueue.invokeLater(
                () -> new SplitUpImage()
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
            g2d.drawImage(orig, null, -orig.getWidth() / 2, -orig.getHeight() / 2);
            g2d.drawRect(-orig.getWidth() / 2, -orig.getHeight() / 2, orig.getWidth(), orig.getHeight());
            System.out.println("Image Width: " + orig.getWidth() + " Height: " + orig.getHeight());
            int width = orig.getWidth();
            int height = orig.getHeight();
            int row = (orig.getWidth()/ 144);
            int col = (orig.getHeight()/ 144);
            int peiceWidth = orig.getWidth() / row;
            int peiceHeight = orig.getHeight() / col;
            for (int i = 0; i < row; i++) {
                for (int j = 0; j < col; j++) {
                    int offSetW = peiceWidth/3;
                    int offSetH = peiceHeight/3;
                    int x = (j * (width-offSetW) / row) - orig.getHeight()/2;
                    int y = (i * (height-offSetH) / col) - orig.getWidth()/2;
                    int pWidth = (width+offSetW) / row;
                    int pHeight = (height+offSetH) / col;
                    g2d.drawRect(x,y,pWidth,pHeight);
                    System.out.println("X: " + x + " Y: " + y + " Width: " + pWidth + " Height: " + pHeight);
                }
            }
            g2d.dispose();
        }
    }
}

