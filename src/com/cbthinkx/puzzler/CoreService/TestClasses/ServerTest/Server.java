package com.cbthinkx.puzzler.CoreService.TestClasses.ServerTest;


import com.cbthinkx.puzzler.CoreService.Jigsaw;
import com.cbthinkx.puzzler.CoreService.PDFGenerator;
import com.cbthinkx.puzzler.CoreService.PuzzleData;
import com.cbthinkx.puzzler.CoreService.Square;
import org.apache.pdfbox.pdmodel.PDDocument;

import javax.imageio.ImageIO;

import java.awt.image.BufferedImage;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class Server {
    public static void main(String[] args) throws IOException {
        new Server().doit(25565);
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
        private Socket socket;
        ClientThread(Socket ss) {
            this.socket = ss;
        }
        @Override
        public void run() {
            new Thread(
                    () -> recievePDInfo(socket)
            ).start();
        }
        public byte[] getSizeInBytes(int s) {
            return ByteBuffer.allocate(4).order(ByteOrder.BIG_ENDIAN).putInt(s).array();
        }
        public int getSizeFromBuf(byte[] buf) {
            return ByteBuffer.wrap(buf).order(ByteOrder.BIG_ENDIAN).getInt();
        }
        private void recievePDInfo(Socket ss) {
            try (
                InputStream is = ss.getInputStream()
            ) {
                String data;
                PuzzleData pd;
                byte[] stringSize = new byte[4];
                //gets the string size
                is.read(stringSize);
                System.out.println("stringSize: " + stringSize.toString());
                //creates buf to hold string
                System.out.println("stringSizeInt: " + getSizeFromBuf(stringSize));
                byte[] stringBuf = new byte[getSizeFromBuf(stringSize)];
                //gets the string
                is.read(stringBuf);
                //creates the string
                data = new String(stringBuf);
                System.out.println(data);
                // new pd for imageTail
                pd = new PuzzleData(data, null);
                // new image from stream
                System.out.println(pd.toString());
                BufferedImage image = ImageIO.read(is);
                // saves the image to a file
                ImageIO.write(image, pd.getImgTail(), new File("RecievedImageServer." + pd.getImgTail()));
                //creates new puzzleData
                pd = new PuzzleData(data, image);
                ss.shutdownInput();
                switch(pd.getShapeType()) {
                    case SQUARE: {
                        Square sq = new Square(pd);
                        PDFGenerator pdfGen = new PDFGenerator(sq.getPieces());
                        pdfGen.getfinalPuzzle().save(new File("FinalePDF.pdf"));
                        handleOutPut(ss, pdfGen.getfinalPuzzle());
                        pdfGen.getfinalPuzzle().close();
                        break;
                    }
                    case JIGSAW: {
                        System.out.println("Lets get JIGGY");
                        Jigsaw jiggy = new Jigsaw(pd);
                        PDFGenerator pdfGen = new PDFGenerator(jiggy.getJigsawPieces());
                        pdfGen.getfinalPuzzle().save(new File("FinalePDF.pdf"));
                        handleOutPut(ss, pdfGen.getfinalPuzzle());
                        pdfGen.getfinalPuzzle().close();
                        break;
                    }
                    default:
                        break;
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        public void handleOutPut(Socket ss, PDDocument puzzle) {
            try (
                OutputStream out = ss.getOutputStream()
            ){
                // save pdf to output stream
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                puzzle.save(outputStream);
                outputStream.flush();
                outputStream.close();
                // creates a byte[] to hole the pdf
                byte[] pdfBuf = outputStream.toByteArray();
                System.out.println("pdfBuf: " + pdfBuf.length);
                // size of the pdf byteArray
                byte[] pdfSize = getSizeInBytes(pdfBuf.length);
                System.out.println("pdfSize: " + pdfSize.toString());
                //writes the size of the pdf
                out.write(pdfSize);
                //writes the pdf
                out.write(pdfBuf);
                out.flush();
                //closes the socket
                ss.shutdownOutput();
                ss.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
