package com.cbthinkx.puzzler.CoreService.TestClasses.ServerTest;

import com.cbthinkx.puzzler.CoreService.Jigsaw;
import com.cbthinkx.puzzler.CoreService.PDFGenerator;
import com.cbthinkx.puzzler.CoreService.PuzzleData;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * Created by Robert on 4/29/15.
 */
public class PuzzleServer {
    public static void main(String[] sa) throws IOException {
        new PuzzleServer().doit();
    }
    public void doit() {
        new PuzzleServerThread().start();
    }
    public class PuzzleServerThread extends Thread {
        private final static int PORT_NUMBER = 25565;
        private Boolean isReceiving = true;
        private Boolean isSending = true;
        protected DatagramSocket socket = null;

        public PuzzleServerThread() {
            this("PuzzleServerThread");
        }
        public PuzzleServerThread(String s) {
            super(s);
            try {
                socket = new DatagramSocket(PORT_NUMBER);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        public void run() {
            DatagramPacket packet = null;
            while (isReceiving) {
                try {
                    byte[] buf = new byte[256];

                    // receive puzzle data string
                    packet = new DatagramPacket(buf, buf.length);
                    socket.receive(packet);

                    // figure out response
                    String received = new String(packet.getData(), 0, packet.getLength());
                    PuzzleData pd = new PuzzleData(received, null);
                    System.out.println("Received: " + received);

                    byte[] imgbuf = new byte[socket.getReceiveBufferSize()];
                    packet = new DatagramPacket(imgbuf, imgbuf.length);
                    socket.receive(packet);
                    // turn packet into stream
                    InputStream is = new ByteArrayInputStream(packet.getData());
                    BufferedImage img = ImageIO.read(is);
                    ImageIO.write(img, pd.getImgTail(), new File("RECIEVED." + pd.getImgTail()));
                    pd = new PuzzleData(received, img);
                    // create pdf
                    Jigsaw jiggy = new Jigsaw(pd);
                    PDFGenerator pdfGenerator = new PDFGenerator(jiggy.getJigsawPieces());
                    // save pdf to output stream
                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    pdfGenerator.getfinalPuzzle().save(outputStream);
                    outputStream.flush();
                    InetAddress address = packet.getAddress();
                    int port = packet.getPort();
                    byte[] pdfBuf = outputStream.toByteArray();
                    packet = new DatagramPacket(pdfBuf, pdfBuf.length, address, port);
                    socket.send(packet);

                    // send the response to the client at "address" and "port"
//                    InetAddress address = packet.getAddress();
//                    int port = packet.getPort();
//                    packet = new DatagramPacket(buf, buf.length, address, port);
//                    socket.send(packet);

                    // done receiving
                    isReceiving = false;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            while (isSending) {
                try {
                    String sending = "we got dis shit";
                    byte[] buf = sending.getBytes();
                    InetAddress address = packet.getAddress();
                    int port = packet.getPort();
                    packet = new DatagramPacket(buf, buf.length, address, port);
                    socket.send(packet);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                isSending = false;
            }
            socket.close();
        }
    }
}
