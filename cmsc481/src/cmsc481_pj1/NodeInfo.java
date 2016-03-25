package cmsc481_pj1;

/**
 * This is a class representing a node information. It is used to transfer the
 * information from {@link NodeTable} to {@link TokenNode}
 * 
 * @see NodeTable
 * @see TokenNode
 * @author Sung Ho Kim
 * @project CMSC481-02 Fall 2015 Token Ring Assignment
 * @due Nov. 15, 2015
 */
public class NodeInfo {
	private String host;
	private int listeningPort, askingPort;
	private int thisNodeNum, nextNodeNum;

	/**
	 * Constructor
	 * 
	 * @param thisNodeNum
	 *            a node number
	 * @param listeningPort
	 *            a port number for the server
	 * @param nextNodeNum
	 *            a node number of the next node
	 * @param host
	 *            an IP of the next node
	 * @param askingPort
	 *            a port number for the next node
	 */
	public NodeInfo(int thisNodeNum, int listeningPort, int nextNodeNum, String host, int askingPort) {
		this.host = host;
		this.listeningPort = listeningPort;
		this.askingPort = askingPort;
		this.thisNodeNum = thisNodeNum;
		this.nextNodeNum = nextNodeNum;
	}

	public String getHost() {
		return host;
	}

	public int getListeningPort() {
		return listeningPort;
	}

	public int getAskingPort() {
		return askingPort;
	}

	public int getThisNodeNum() {
		return thisNodeNum;
	}

	public int getNextNodeNum() {
		return nextNodeNum;
	}
}
