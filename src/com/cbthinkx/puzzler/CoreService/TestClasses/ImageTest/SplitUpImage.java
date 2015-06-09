package com.cbthinkx.puzzler.CoreService.TestClasses.ImageTest;

import com.cbthinkx.puzzler.CoreService.Enum.PuzzleSkill;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

public class SplitUpImage extends JFrame {
    private int peiceWidth;
    private int peiceHeight;
    private int row;
    private int col;
    private int origWidth;
    private int origHeight;
    private static final long serialVersionUID = 1L;
    BufferedImage orig;
    public SplitUpImage() {
        super("SplitUpImage");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        add(new MyJPanel());

        pack();
        setLocationRelativeTo(null);
        try {
            orig = ImageIO.read(new File("res/colors3.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        col = (orig.getWidth()/ PuzzleSkill.ADULT.getVal());
        System.out.println(col);
        origWidth = orig.getWidth();
        origHeight = orig.getHeight();   
        peiceWidth = orig.getWidth()/col;
        row = (int) (orig.getHeight()/peiceWidth);
        System.out.println(row);
        peiceHeight = peiceWidth;
        row = orig.getHeight()/peiceHeight;
        peiceHeight = orig.getHeight()/row;
        System.out.println(row);        
        orig = offSetImage(orig);
        try {
            ImageIO.write(orig, "png", new File("newImage.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        private ArrayList<rectClass> rect;
        private boolean booleano = true;
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
            this.count++;
            if (!(this.count < xarr.size()+1)) {
                count--;
            }
            repaint();
        };
        private ActionListener prev = ae -> {
            this.count--;
            if (count < 0) {
                count ++;
            }
            repaint();
        };

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(1024, 768);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (booleano) {
                booleano = false;
                setUpArraylist();
            }
            Graphics2D g2d = (Graphics2D) g.create();
            AffineTransform gat = new AffineTransform();
            gat.translate(
                    getWidth() / 2.0,
                    getHeight() / 2.0
            );
            gat.scale(1.0, -1.0);
            g2d.transform(gat);
            g2d.setColor(Color.blue);
            g2d.setStroke(new BasicStroke(4.5f));
            g2d.drawImage(orig, null, -orig.getWidth() / 2, -orig.getHeight() / 2);
            g2d.drawRect(-orig.getWidth() / 2, -orig.getHeight() / 2, orig.getWidth(), orig.getHeight());
            ArrayList<Color> color = new ArrayList<>();
            color.add(Color.BLACK);
            color.add(Color.BLUE);
            color.add(Color.RED);
            color.add(Color.GREEN);
//            Collections.shuffle(color);
            for (int x = 0; x < this.count; x++) {
//                g2d.setColor(new Color((int) (Math.random() * 255), (int) (Math.random() * 255), (int) (Math.random() * 255)));
                g2d.setColor(color.get(x));
                g2d.drawRect(xarr.get(x), yarr.get(x), warr.get(x), harr.get(x));
//                g2d.drawRect(rect.get(x).getX(), rect.get(x).getY(), rect.get(x).getPwidth(), rect.get(x).getPheight());
            }
            g2d.dispose();
        }

        public void setUpArraylist() {
            xarr = new ArrayList<>();
            yarr = new ArrayList<>();
            harr = new ArrayList<>();
            warr = new ArrayList<>();
            rect = new ArrayList<>();
            int width = orig.getWidth();
            int height = orig.getHeight();
            for (int i = 0; i < 2; i++) {
                for (int j = 0; j < 2; j++) {
                    int offSetW = (peiceWidth / 3);
                    int offSetH = (peiceHeight / 3);
                    int pWidth = peiceWidth+2*offSetW;
                    int pHeight = peiceHeight+2*offSetH;
                    int x = (j * ((origWidth) /col)-offSetW) - origWidth / 2;
                    int y = (i * ((origHeight) / row)-offSetH) - origHeight / 2;
                    System.out.print("OffSetW: " + offSetW);
                    System.out.print(" OffSetH: " + offSetH);
                    System.out.print(" X: " + x);
                    System.out.print(" Y: " + y);
                    System.out.print(" pWidth: " + pWidth);
                    System.out.print(" pHeight: " + pHeight);
                    System.out.print("  origWidth"+origWidth);
                    System.out.print("  origHeight"+origHeight);
                    System.out.print("  width"+width);
                    System.out.print("  height" + height);
                    System.out.print("  peiceWidth" + peiceWidth);
                    System.out.print("  peiceHeight" + peiceHeight);
                    System.out.println("  row" + row);
//                    rectClass rect2 = new rectClass(x, y, pWidth, pHeight);
//                    rect.add(rect2);
////                    rect.add(new rectClass(x, y, pWidth, pHeight));
                    xarr.add(x);
                    yarr.add(y);
                    warr.add(pWidth);
                    harr.add(pHeight);
                }

            }
            Collections.shuffle(rect);
        }
    }
    private class rectClass {
        public int getX() {
            return x;
        }

        public void setX(int x) {
            this.x = x;
        }

        public int getY() {
            return y;
        }

        public void setY(int y) {
            this.y = y;
        }

        public int getPwidth() {
            return pwidth;
        }

        public void setPwidth(int pwidth) {
            this.pwidth = pwidth;
        }

        public int getPheight() {
            return pheight;
        }

        public void setPheight(int pheight) {
            this.pheight = pheight;
        }

        int x;
        int y;
        int pwidth;
        int pheight;

        public rectClass(int x, int y, int pwidth, int pheight) {
            this.x = x;
            this.y = y;
            this.pheight = pheight;
            this.pwidth = pwidth;
        }

    }
}

