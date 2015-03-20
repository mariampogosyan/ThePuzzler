package com.cbthinkx.puzzler.CoreService;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.edit.PDPageContentStream;
import org.apache.pdfbox.pdmodel.graphics.xobject.PDPixelMap;
import org.apache.pdfbox.pdmodel.graphics.xobject.PDXObjectImage;

public class PDFGenerator {
	public PDFGenerator (ArrayList<BufferedImage> arrlist) throws Exception {
		doIt(arrlist);
	}
	private void doIt(ArrayList<BufferedImage> arrList) throws Exception {
		// Create a document and add a page to it
		PDDocument document = new PDDocument();
		PDPage page = new PDPage();
		document.addPage(page);
		// Start a new content stream which will "hold" the to be created content
		PDPageContentStream contentStream = new PDPageContentStream(document, page);
		int imageNum = 0;
//		int row = 0;
		for (int i = 0; i < arrList.size(); i++) {
//			for(int j = 0; j < 3; j++) {
			     try {
			    	 	BufferedImage img = arrList.get(i);
			            PDXObjectImage ximage = new PDPixelMap(document, img);
			            float scale = 1.0f; // alter this value to set the image size
//			            int x = 100 + (j * img.getWidth());
//			            int y = 400 + (row * img.getHeight());
			            contentStream.drawXObject(ximage, 50, 50, ximage.getWidth()*scale, ximage.getHeight()*scale);
                     contentStream.close();
                     PDPage nPage = new PDPage();
                     document.addPage(nPage);
                     contentStream = new PDPageContentStream(document, nPage);
			        } catch (FileNotFoundException fnfex) {
			            System.out.println("No image for you");
			        }
//			}
//			row++;
		}

		// Make sure that the content stream is closed:
		contentStream.close();
		// Save the results and ensure that the document is properly closed:
		document.save( "Hello World.pdf");
		document.close();
	}

}
