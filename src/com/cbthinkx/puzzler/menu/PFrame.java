package com.cbthinkx.puzzler.menu;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.cbthinkx.puzzler.CoreService.PuzzleData;

public class PFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	public static final String Upload_Screen = "upload";
	public static final String Puzzle_settings = "puzzle";
    private static final String hostName = "localhost";
    private static final int portNumber = 25565;
	private JPanel puzzler;
    private int height = 750;
    private int width = 750;
    private CardLayout layout;
    private PuzzleData data = new PuzzleData();
	public PFrame() {
		super("The Puzzler");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(width, height);
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);
		setBackground(Color.WHITE);
		puzzler = new JPanel();
		layout = new CardLayout();
		puzzler.setLayout(layout);
		puzzler.add(new UploadScreen(this), Upload_Screen);
		puzzler.add(new PuzzleSettings(this), Puzzle_settings);
		add(puzzler);
	}	
	public void setView(String v) {
		layout.show(puzzler, v); 
	}
	public PuzzleData getData(){
		 return this.data;
	}
	public static void main(String[] sa) {
		EventQueue.invokeLater(
				() -> new PFrame()
		);
	};
    public boolean sendPuzzle() {
        try (
                Socket socket = new Socket(hostName, portNumber);
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                OutputStream outputStream = socket.getOutputStream()
        ) {
            String userInput = getData().toString();
            out.println(userInput);
            ImageIO.write(getData().getImage(), getData().getImgTail(), outputStream);
//            wait for pdf to be returned
//
//
//            before closing the socket
            socket.close();
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + hostName);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " +
                    hostName);
            System.exit(1);
        }
        return true;
    }
}


