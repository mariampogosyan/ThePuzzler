package com.cbthinkx.puzzler.CoreService;

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
                String data = "";
                BufferedImage c = null;
                while ((data = br.readLine()) != null) {
                    System.out.println(data);
                    break;
                }
                byte[] bytes = null;
                ByteArrayInputStream bais;
                try {
                    BufferedInputStream bs = new BufferedInputStream(is);
                    DataInputStream dis = new DataInputStream(bs);
                    int length = dis.available();
                    System.out.println(length);
                    bytes = new byte[length];
                    dis.readFully(bytes);
                    bs.close();
                } catch (Exception e) {
                    System.out.println("failed here");
                }

                try {
                    bais = new ByteArrayInputStream(bytes);
                    c = ImageIO.read(bais);
                    System.out.println(c.toString());
                } catch (IOException e) {
                    System.out.println("error");
                    e.printStackTrace();
                }

                try {
                    File outputfile = new File("image.jpg");
                    outputfile.createNewFile();
                    ImageIO.write(c, "jpg", outputfile);
                } catch (Exception e) {
                    System.out.println("kjhkjlhjh");
                }

                try {
                    PuzzleData pd = new PuzzleData(data, c);
                    System.out.println(pd.toString());
                } catch (Exception e) {
                    System.out.println("hello");
                }

                br.close();
            } catch (Exception ex) {
                System.err.println("Server failed to read socket");
                System.err.println(ex.toString());
                return;
            }
        }
    }

}
