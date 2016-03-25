package cmsc481_pj1;

import java.io.Serializable;

/**
 * This is a class representing a frame having a message.
 * 
 * @see TokenNode#readMsg(TokenFrame)
 * @see TokenNode#printGetMsg(TokenFrame)
 * @see TokenNode#printSentMsg(TokenFrame)
 * @author Sung Ho Kim
 * @project CMSC481-02 Fall 2015 Token Ring Assignment
 * @due Nov. 15, 2015
 */
@SuppressWarnings("serial")
public class TokenFrame implements Serializable {
	private int sourceNodeNum;
	private int destNodeNum;
	private int stopOver;
	private String msg;
	private boolean empty;

	/**
	 * Constructor making an empty frame
	 */
	public TokenFrame() {
		/* -1 is for no data */
		this(-1, null, -1, -1);
		empty = true;
	}

	/**
	 * Constructor making a frame with these parameters.
	 * 
	 * @param sourceNodeNum
	 *            a node number originally sending this frame
	 * @param msg
	 *            a message to transfer
	 * @param destNodeNum
	 *            a node number for the destination
	 * @param stopOver
	 *            a node number previously stop over
	 */
	public TokenFrame(int sourceNodeNum, String msg, int destNodeNum, int stopOver) {
		empty = false;
		this.sourceNodeNum = sourceNodeNum;
		this.msg = msg;
		this.destNodeNum = destNodeNum;
		this.stopOver = stopOver;
	}

	public String getMsg() {
		return msg;
	}

	public void setSourceNode(int nodeNum) {
		this.sourceNodeNum = nodeNum;
	}

	public int getStopOverNode() {
		return stopOver;
	}

	public void setStopOverNode(int nodeNum) {
		this.stopOver = nodeNum;
	}

	public int getSourceNode() {
		return sourceNodeNum;
	}

	public int getDestNode() {
		return destNodeNum;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public boolean isEmpty() {
		return empty;
	}
}
