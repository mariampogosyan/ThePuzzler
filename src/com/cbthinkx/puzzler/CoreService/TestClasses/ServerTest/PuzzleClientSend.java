package com.cbthinkx.puzzler.CoreService.TestClasses.ServerTest;

import com.cbthinkx.puzzler.CoreService.PuzzleData;
import org.apache.pdfbox.pdmodel.PDDocument;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;

/**
 * Created by Robert on 4/29/15.
 */
public class PuzzleClientSend {
    private final static String SERVER_ADDRESS = "127.0.0.1";
    private final static int PORT_NUMBER = 25565;
    public PuzzleClientSend(PuzzleData pd) {
        try (
            // get a datagram socket
            DatagramSocket socket = new DatagramSocket()
        ) {
//            byte[] buf = new byte[256];
//            // receive request for size of image
//            DatagramPacket packet = new DatagramPacket(buf, buf.length);
//            socket.receive(packet);

            // figure out response
            byte[] pdBuf = pd.toString().getBytes();

            // send the response to the client at "address" and "port"
            InetAddress address = InetAddress.getByName(SERVER_ADDRESS);
            int port = PORT_NUMBER;
            DatagramPacket packet = new DatagramPacket(pdBuf, pdBuf.length, address, port);
            socket.send(packet);
            // send request for pdf size
            ByteArrayOutputStream boas = new ByteArrayOutputStream();
            ImageIO.write(pd.getImage(), pd.getImgTail(), boas);
            boas.flush();
            byte[] imgBuf = boas.toByteArray();
            packet = new DatagramPacket(imgBuf, imgBuf.length, address, port);
            socket.send(packet);

            byte[] pdfBuff = new byte[socket.getReceiveBufferSize()];
            packet = new DatagramPacket(pdfBuff, pdfBuff.length);
            socket.receive(packet);

            InputStream is = new ByteArrayInputStream(packet.getData());
            PDDocument pdf = PDDocument.load(is);
            pdf.save(new File("GOTAPDF.pdf"));
            // receive request for size of image
            byte[] buf = new byte[256];
            packet = new DatagramPacket(buf, buf.length);
            socket.receive(packet);
            String received = new String(packet.getData(), 0, packet.getLength());
            System.out.println("Received: " + received);

            socket.close();
        } catch (Exception e) {
           e.printStackTrace();
        }
    }
}
