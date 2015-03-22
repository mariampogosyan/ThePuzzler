package com.cbthinkx.puzzler.CoreService;


import org.apache.pdfbox.pdmodel.PDDocument;

import javax.imageio.ImageIO;

import java.awt.image.BufferedImage;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) throws IOException {
        new Server().doit(25565);
    }
    public void doit(int portNumber) {
        try {
            boolean done = false;
//            new MasterThread().start();
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
                    () -> handleInput(socket)
            ).start();
        }
        private void handleInput(Socket ss) {
            try {
                InputStream is = ss.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                PuzzleData pd;
                String data;
                while ((data = br.readLine()) != null) {
                    break;
                }
                pd = new PuzzleData(data, null);
                BufferedImage image = ImageIO.read(is);
                ImageIO.write(image, pd.getImgTail(), new File("RecievedImage." + pd.getImgTail()));
                pd = new PuzzleData(data, image);
                switch(pd.getShapeType()) {
                    case SQUARE: {
                        Square sq = new Square(pd);
                        PDFGenerator pdfGen = new PDFGenerator(sq.getPieces());
                        System.out.println(pdfGen.getfinalPuzzle().getNumberOfPages());
                       /* new Thread(
                                () -> handleOutPut(socket, pdfGen.getfinalPuzzle())
                        ).start();*/
//                        try {
//                            pdfGen.getfinalPuzzle().save(new File("goodPDF.pdf"));
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
                        break;
                    }
                    case JIGSAW: {
                        break;
                    }
                    default:
                        break;
                }
            } catch (Exception ex) {
                System.err.println("Server failed to read socket");
                System.err.println(ex.toString());
                return;
            }
        }
        public void handleOutPut(Socket ss, PDDocument puzzle) {
            System.out.println("getToOutPut");
            try {
                // send the puzzle yeaaaa
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
