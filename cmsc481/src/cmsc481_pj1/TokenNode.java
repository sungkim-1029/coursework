package cmsc481_pj1;

/**
 * This is a class representing a node. It has both a server and a client
 * functionality. They are executed as threads.
 * 
 * @author Sung Ho Kim
 * @project CMSC481-02 Fall 2015 Token Ring Assignment
 * @due Nov. 15, 2015
 */
public class TokenNode implements Runnable {

	private int listeningPort, askingPort;
	private String host;
	private TokenFrame tm;
	private boolean token;
	private int nodeNum;
	private int nextNodeNum;
	private boolean stopped;
	private TokenNodeServer ts;
	private TokenNodeClient tc;

	/**
	 * Constructor initiating a node
	 * 
	 * @param nd
	 *            a node information object to make the node
	 */
	public TokenNode(NodeInfo nd) {
		this.nodeNum = nd.getThisNodeNum();
		this.listeningPort = nd.getListeningPort();
		this.host = nd.getHost();
		this.askingPort = nd.getAskingPort();
		this.nextNodeNum = nd.getNextNodeNum();
		tm = null;
		token = false;
		stopped = false;

		/* Objects for the server and the client in the node */
		ts = new TokenNodeServer(this, listeningPort);
		tc = new TokenNodeClient(this, host, askingPort);
	}

	/**
	 * Making two objects run as threads
	 */
	@Override
	public void run() {
		Thread tserver = new Thread(ts);
		Thread tclient = new Thread(tc);
		tserver.start();
		tclient.start();

		try {
			tserver.join();
			tclient.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Tests if the client is connected to the next node
	 * 
	 * @return true when the connection is made. Otherwise, false.
	 */
	public boolean isConnected() {
		return tc.isConnected();
	}

	public int getNodeNum() {
		return nodeNum;
	}

	/**
	 * Creates a frame having a message.
	 * 
	 * @param msg
	 *            a string message
	 * @param destNodeNum
	 *            a port number where the message is sent to
	 */
	public void createTokenMsg(String msg, int destNodeNum) {
		tm = new TokenFrame(nodeNum, msg, destNodeNum, nodeNum);
		System.out.println(
				"Node " + nodeNum + " has created the message (final destination: " + destNodeNum + ") - " + msg);
		token = true;
	}

	/**
	 * Reads a message from the frame. It ignores the frame if it is empty. If
	 * not, it is marked as it has a token or a message. If the destination of
	 * the frame is the same with the current node, the program is ended.
	 * 
	 * @param tm
	 *            a TokenFrame object with the message
	 */
	public void readMsg(TokenFrame tm) {
		if (!tm.isEmpty()) {
			this.tm = tm;

			/*
			 * Stops the node and this thread when the destination of the
			 * received message is this node
			 */
			if (tm.getDestNode() == nodeNum) {
				/* Stop this thread to stop the program*/
				done();

			} else {
				printGetMsg(tm);
				token = true;
			}
		}
	}

	public boolean isStopped() {
		return stopped;
	}

	public boolean hasToken() {
		return token;
	}

	/**
	 * Sends the frame to the client in the node and makes the client send it to
	 * the next node.
	 * 
	 * @return TokenFrame object
	 */
	public TokenFrame writeMsg() {
		TokenFrame temp = tm;
		temp.setStopOverNode(nodeNum);
		tm = null;
		token = false;
		printSentMsg(temp);
		return temp;
	}

	public void printSentMsg(TokenFrame tm) {
		System.out.println("Node " + nodeNum + " has sent the message to Node " + nextNodeNum);
	}

	public void printGetMsg(TokenFrame tm) {
		System.out.println("Node " + nodeNum + " has received the message from Node " + tm.getStopOverNode() + " (msg: "
				+ tm.getMsg() + ")");
	}

	public void printConnectedMsg() {
		System.out.println("Node " + nodeNum + " connected to Node " + nextNodeNum);
	}

	public void printWaitMsg() {
		System.out.println("Node " + nodeNum + " is waiting for Node " + nextNodeNum + " for 2s...");
	}

	/**
	 * Stops this thread.
	 */
	public void done() {
		stopped = true;
		Thread.currentThread().interrupt();
	}
}
