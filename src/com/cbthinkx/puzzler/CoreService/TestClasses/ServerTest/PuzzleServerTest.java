package com.cbthinkx.puzzler.CoreService.TestClasses.ServerTest;


import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import javax.imageio.ImageIO;

import com.cbthinkx.puzzler.CoreService.Jigsaw;
import com.cbthinkx.puzzler.CoreService.PDFGenerator;
import com.cbthinkx.puzzler.CoreService.PuzzleData;
import com.cbthinkx.puzzler.CoreService.Square;
import org.apache.pdfbox.pdmodel.PDDocument;

public class PuzzleServerTest {
    public static void main(String[] args) throws IOException {
        new PuzzleServerTest().doit(25565);
    }
    public void doit(int portNumber) {
        try {
            boolean done = false;
            ServerSocket ss = new ServerSocket(portNumber);
            System.out.println("Server started successfully");
            while (!done) {
                Socket sock = ss.accept();
                new ClientThread(sock).start();
            }
            ss.close();
        } catch (Throwable t) {
            System.err.println("Server failed to start...");
        }
    }
    public class ClientThread extends Thread {
        private InputStream is;
        private OutputStream out;
        ClientThread(Socket ss) {
            try {
				this.is = ss.getInputStream();
	            this.out = ss.getOutputStream();
   			} catch (IOException ex) {
				ex.printStackTrace();
			}
        }
        @Override
        public void run() {
        	recievePDInfo();
        }
        public byte[] getSizeInBytes(int s) {
            return ByteBuffer.allocate(4).order(ByteOrder.BIG_ENDIAN).putInt(s).array();
        }
        public int getSizeFromBuf(byte[] buf) {
            return ByteBuffer.wrap(buf).order(ByteOrder.BIG_ENDIAN).getInt();
        }
        private void recievePDInfo() {
            try {
                String data;
                PuzzleData pd;
                byte[] stringSize = new byte[4];
                is.read(stringSize);
                byte[] stringBuf = new byte[getSizeFromBuf(stringSize)];
                is.read(stringBuf);
                data = new String(stringBuf);
                BufferedImage image = ImageIO.read(is);
                pd = new PuzzleData(data, image);
                switch(pd.getShapeType()) {
                    case SQUARE: {
                        Square sq = new Square(pd);
                        System.out.println("SQUARE");
                        PDFGenerator pdfGen = new PDFGenerator(sq.getPieces());
                        pdfGen.getfinalPuzzle().save(new File("FinalePDF.pdf"));
                        handleOutPut(pdfGen.getfinalPuzzle());
                        break;
                    }
                    case JIGSAW: {
                        System.out.println("Lets get JIGGY");
                        Jigsaw jiggy = new Jigsaw(pd);
                        PDFGenerator pdfGen = new PDFGenerator(jiggy.getJigsawPieces());
                        pdfGen.getfinalPuzzle().save(new File("FinalePDF.pdf"));
                        handleOutPut(pdfGen.getfinalPuzzle());
                        break;
                    }
                    default:
                        break;
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        public void handleOutPut(PDDocument puzzle) {
            try {
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                puzzle.save(outputStream);
                outputStream.flush();
                outputStream.close();
                byte[] pdfBuf = outputStream.toByteArray();
                byte[] pdfSize = getSizeInBytes(pdfBuf.length);
                System.out.println("*** " + pdfBuf.length);
                out.write(pdfSize);
                out.write(pdfBuf);
                out.flush();
            } catch (Exception e) {
            	System.out.println("BAD NEWS");
                e.printStackTrace();
            }
        }
    }
}
