package com.cbthinkx.puzzler.CoreService;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class SplitUpImage extends JFrame {
    private int peiceWidth;
    private int peiceHeight;
    private int row;
    private int col;
    private static final long serialVersionUID = 1L;
    BufferedImage orig;
    public SplitUpImage() {
        super("SplitUpImage");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        add(new MyJPanel());
        pack();
        setLocationRelativeTo(null);
        try {
            orig = ImageIO.read(new File("res/colors.jpg"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        row = (orig.getWidth()/ PuzzleSkill.BABY.getVal());
        col = (orig.getHeight()/ PuzzleSkill.BABY.getVal());
        peiceWidth = orig.getWidth() / row;
        peiceHeight = orig.getHeight() / col;
        orig = offSetImage(orig);
        setVisible(true);

    }
    private BufferedImage offSetImage(BufferedImage img) {
        BufferedImage offSet = new BufferedImage(
                img.getWidth() + (peiceWidth/3)*2,
                img.getHeight() + (peiceHeight/3)*2,
                BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = offSet.createGraphics();
        g2d.drawImage(img, null, peiceWidth / 3, peiceHeight / 3);
        g2d.dispose();
        return offSet;
    }
    public static void main(String[] sa) {
        EventQueue.invokeLater(
                () -> new SplitUpImage()
        );
    }
    private class MyJPanel extends JPanel {
        private ArrayList<Integer> xarr;
        private ArrayList<Integer> yarr;
        private ArrayList<Integer> warr;
        private ArrayList<Integer> harr;
        private int count = 0;
        private static final long serialVersionUID = 1L;

        public MyJPanel() {
            super();
            JButton button = new JButton("Previous");
            button.addActionListener(prev);
            button.setSize(100, 40);
            add(button);
            JButton button2 = new JButton("Next");
            button2.addActionListener(next);
            button2.setSize(100, 40);
            add(button2);
            setBackground(Color.WHITE);
        }

        private ActionListener next = ae -> {
            System.out.println("HI");
            this.count++;
            System.out.println(this.count);
            repaint();
        };
        private ActionListener prev = ae -> {
            System.out.println("HI2");
            this.count--;
            System.out.println(this.count);
            repaint();
        };

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(1024, 768);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            setUpArraylist();
            Graphics2D g2d = (Graphics2D) g.create();
            AffineTransform gat = new AffineTransform();
            gat.translate(
                    getWidth() / 2.0,
                    getHeight() / 2.0
            );
            gat.scale(1.0, -1.0);
            g2d.transform(gat);
            g2d.setColor(Color.blue);
            g2d.drawImage(orig, null, -orig.getWidth() / 2, -orig.getHeight() / 2);
            g2d.drawRect(-orig.getWidth() / 2, -orig.getHeight() / 2, orig.getWidth(), orig.getHeight());
            System.out.println(this.count);
            for (int x = 0; x < this.count; x++) {
                System.out.println("X: " + x);
                g2d.setColor(new Color((int) (Math.random() * 255), (int) (Math.random() * 255), (int) (Math.random() * 255)));
                g2d.drawRect(xarr.get(x), yarr.get(x), warr.get(x), harr.get(x));
            }
            g2d.dispose();
        }

        public void setUpArraylist() {
            xarr = new ArrayList<>();
            yarr = new ArrayList<>();
            harr = new ArrayList<>();
            warr = new ArrayList<>();
            int width = orig.getWidth();
            int height = orig.getHeight();
            for (int i = 0; i < row; i++) {
                for (int j = 0; j < col; j++) {
                    int offSetW = peiceWidth / 3;
                    int offSetH = peiceHeight / 3;
                    int x = (j * (width - offSetW) / row) - orig.getWidth() / 2;
                    int y = (i * (height - offSetH) / col) - orig.getHeight() / 2;
                    int pWidth = (width + offSetW * 2) / row;
                    int pHeight = (height + offSetH * 2) / col;
                    xarr.add(x);
                    yarr.add(y);
                    warr.add(pWidth);
                    harr.add(pHeight);
                }

            }
        }
    }
}

