package cmsc481_pj1;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * This is a class representing a client in a node. It connects to another node
 * and sends the frame with the message.
 * 
 * @author Sung Ho Kim
 * @project CMSC481-02 Fall 2015 Token Ring Assignment
 * @due Nov. 15, 2015 *
 */
public class TokenNodeClient implements Runnable {

	private Socket socket;
	private String host;
	private int port;
	private boolean connect;
	private TokenNode tn;

	/**
	 * Constructor making a client object
	 * 
	 * @param tn
	 *            a TokenNode object having this client
	 * @param host
	 *            an IP of the next node
	 * @param port
	 *            a port number for the next node
	 */
	public TokenNodeClient(TokenNode tn, String host, int port) {
		this.tn = tn;
		this.host = host;
		this.port = port;
		connect = false;
	}

	public boolean isConnected() {
		return connect;
	}

	@Override
	public void run() {
		ObjectOutputStream printWriter = null;

		// Keeps trying to connect the assigned next node until it succeeds
		while (!connect) {
			try {
				// Making a socket connection
				socket = new Socket(host, port);
				printWriter = new ObjectOutputStream(socket.getOutputStream());
				tn.printConnectedMsg();
				connect = true;
			} catch (IOException e) {
				// Wait for 2 seconds until the next node is ready
				tn.printWaitMsg();
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}
		}
		try {
			// Keeps running this thread until it is interrupted - when the node
			// receives the frame whose destination is the current node
			while (!Thread.currentThread().isInterrupted()) {
				// Stops the client thread if the node receives the frame whose destination is the current node
				if (tn.isStopped())
					Thread.currentThread().interrupt();
				// Sends the frame to the next node if the node has the frame
				if (tn.hasToken()) {
					printWriter.writeObject(tn.writeMsg());
					printWriter.flush();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (socket != null) {
				try {
					socket.close();
					connect = false;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
