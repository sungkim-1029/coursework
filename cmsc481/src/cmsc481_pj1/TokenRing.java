package cmsc481_pj1;

/**
 * It is a driver class running the whole program. It creates five nodes as
 * threads and makes the message frame to send. When all the threads are stopped
 * because of the returned frame to where it is sent, the program stops.
 * 
 * @author Sung Ho Kim
 * @project CMSC481-02 Fall 2015 Token Ring Assignment
 * @due Nov. 15, 2015
 */
public class TokenRing {

	private static int NUM_NODES = 5;
	private static int NODE_1 = 1;

	public static void main(String[] args) {

		// Creates five nodes with its information (host, port, and etc.)
		NodeTable nt = new NodeTable();
		TokenNode myTokenNode[] = new TokenNode[NUM_NODES];
		for (int i = 0; i < NUM_NODES; i++) {
			myTokenNode[i] = new TokenNode(nt.getNode(i + 1));
		}

		// Creates threads of nodes
		Thread myThreads[] = new Thread[NUM_NODES];
		for (int i = 0; i < NUM_NODES; i++) {
			myThreads[i] = new Thread(myTokenNode[i]);
			myThreads[i].start();
		}

		// Waits until all nodes are connected
		boolean flag = false;
		while (!flag) {
			flag = true;
			for (int i = 0; i < NUM_NODES; i++) {
				flag = flag && myTokenNode[i].isConnected();
			}
		}

		// Create a message from Node 1 and send it to itself via other nodes
		myTokenNode[0].createTokenMsg("Hello World!!", NODE_1);

		// Waits for all the threads stop 
		for (int i = 0; i < NUM_NODES; i++) {
			try {
				myThreads[i].join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		// Prints the good bye message when the program stops
		System.out.println("Bye, bye!!!!");
	}
}
