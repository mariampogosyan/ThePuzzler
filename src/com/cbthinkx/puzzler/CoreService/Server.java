package com.cbthinkx.puzzler.CoreService;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;

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
                String data = "";
                while ((data = br.readLine()) != null) {
                    break;
                }
                byte[] sizeAr = new byte[4];
                is.read(sizeAr);
                int size = ByteBuffer.wrap(sizeAr).asIntBuffer().get();
                byte[] imageAr = new byte[size];
                is.read(imageAr);
                BufferedImage image = ImageIO.read(new ByteArrayInputStream(imageAr));
                ImageIO.write(image, "jpg", new File("test2.jpg"));
                PuzzleData pd = new PuzzleData(data, image);
                System.out.println(pd.toString());
                
                if (pd.getShapeType() == PieceShape.SQUARE) {
                	new Square(pd);
                }
                //should decided weather to send to jigsaw or square
                //
                //should get back a pdf

                //should take pdf and start handleOutPutThread
                //
                //then it should close this socket
            } catch (Exception ex) {
                System.err.println("Server failed to read socket");
                System.err.println(ex.toString());
                return;
            }
        }
        public void handleOutPut(Socket ss) {
            //should also take in a pdf
            //send back PDF after generated
        }
    }
}
