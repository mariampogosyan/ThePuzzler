package com.cbthinkx.puzzler.CoreService;

import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.edit.PDPageContentStream;
import org.apache.pdfbox.pdmodel.graphics.xobject.PDPixelMap;
import org.apache.pdfbox.pdmodel.graphics.xobject.PDXObjectImage;

public class PDFGenerator {
    private static int IMAGE_SPACER = 50;
    private PDDocument finalPuzzle;
    private int maxY;
    private int maxX;
    private int x;
    private int y;
    private boolean newPage = false;
    public PDFGenerator (ArrayList<BufferedImage> arrlist) {
        try {
            createPuzzle(arrlist);
        } catch (Exception e) {
            System.out.println("Could Not Create Puzzle");
            e.printStackTrace();
        }
	}
	private void createPuzzle(ArrayList<BufferedImage> arrList) throws Exception {
		Collections.shuffle(arrList);
		PDDocument document = new PDDocument();
		PDPage page = new PDPage();
		document.addPage(page);
		PDPageContentStream contentStream = new PDPageContentStream(document, page);
        maxX = (int) page.getMediaBox().getUpperRightX();
        maxY = (int) page.getMediaBox().getUpperRightY();
        System.out.println("maxX: " + maxX + " maxY: " + maxY);
        x = IMAGE_SPACER;
        y = (int) page.getMediaBox().getUpperRightY() - IMAGE_SPACER;
		for (int i = 0; i < arrList.size(); i++) {
             try {
                 BufferedImage img = arrList.get(i);
                 PDXObjectImage ximage = new PDPixelMap(document, img);
                 float scale = 1.0f;
                 y = y - ximage.getHeight();
                 contentStream.drawXObject(ximage, x, y, ximage.getWidth()*scale, ximage.getHeight()*scale);
                 System.out.println("X: " + x + " Y: " + y);
                 y = y + ximage.getHeight();
                 if (!newXYPoints(ximage.getWidth(), ximage.getHeight())) {
                     System.out.println("IMAGE IS TOO BIG THROW ERROR");
                     throw new Exception("Image is too big");
                 }
                 if (newPage) {
                     contentStream.close();
                     PDPage nPage = new PDPage();
                     document.addPage(nPage);
                     contentStream = new PDPageContentStream(document, nPage);
                     newPage = false;
                 }
             } catch (FileNotFoundException fnfex) {
                 System.out.println("No image for you");
             } catch (Exception e) {
                 e.printStackTrace();
                 break;
             }
		}
		contentStream.close();
        this.finalPuzzle = document;
	}
    private boolean newXYPoints(int width, int height) {
        int newX = x + (width) + IMAGE_SPACER;
        int newY = y;
        if (!checkXBounds(newX, width)) {
            newY = y - (height) - IMAGE_SPACER;
            newX = IMAGE_SPACER;
            if (!checkYBounds(newY, height)) {
                newPage = true;
                newX = IMAGE_SPACER;
                newY = maxY - IMAGE_SPACER;
            }
            if (!checkXBounds(newX, width)) {
                System.out.println("Image Will not fit on page");
                return false;
                //Handle puzzle size to big
            }
        }
        x = newX;
        y = newY;
        return true;
    }
    private boolean checkXBounds(int x, int width) {
        if ((x+width) > maxX) {
            return false;
        }
        return true;
    }
    private boolean checkYBounds(int y, int height) {
        if ((y-height) < 0) {
            return false;
        }
        return true;
    }
    public PDDocument getfinalPuzzle() {
        return finalPuzzle;
    }
    public void setfinalPuzzle(PDDocument document) {
        this.finalPuzzle = document;
    }

}
