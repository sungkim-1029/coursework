package cmsc481_pj1;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * This is a class representing a server inside the node. It keeps listening
 * from the connected previous node. When it receives the message frame, it
 * sends the message to the node.
 * 
 * @author Sung Ho Kim
 * @project CMSC481-02 Fall 2015 Token Ring Assignment
 * @due Nov. 15, 2015
 */
public class TokenNodeServer implements Runnable {
	private ServerSocket serverSocket;
	private Socket socket;
	private int port;
	private TokenNode tn;

	/**
	 * Constructor making a server object
	 * 
	 * @param n
	 *            a TokenNode object having this client
	 * @param port
	 *            a port number for the server
	 */
	public TokenNodeServer(TokenNode n, int port) {
		this.tn = n;
		this.port = port;
	}

	@Override
	public void run() {
		ObjectInputStream reader = null;
		try {
			// Makes the server ready with the port number
			serverSocket = new ServerSocket(port);
			// Listens the frame from the connected previous node
			socket = serverSocket.accept();
			reader = new ObjectInputStream(socket.getInputStream());

			// Keeps running this thread until it is interrupted - It is
			// interrupted when the node is stopped.
			while (!Thread.currentThread().isInterrupted()) {
				if (tn.isStopped())
					Thread.currentThread().interrupt();
				// Passes the token frame to the node
				tn.readMsg((TokenFrame) reader.readObject());
			}
		} catch (EOFException e) {
			/*
			 * Stops a node and its thread if reading fails (disconnected socket
			 * found)
			 */
			tn.done();
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (socket != null) {
				try {
					socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
