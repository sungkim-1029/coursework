package cmsc481_pj1;

import java.util.HashMap;
import java.util.Map;

/**
 * This is a class containing the information of all nodes to connect each
 * other.
 * 
 * @author Sung Ho Kim
 * @project CMSC481-02 Fall 2015 Token Ring Assignment
 * @due Nov. 15, 2015
 */
public class NodeTable {
	private Map<Integer, NodeInfo> map;

	/**
	 * Constructor making a map to save node information. The map has five
	 * tuples (Node number, Node information)
	 * 
	 * @see NodeInfo#NodeInfo(int, int, int, String, int)
	 */
	public NodeTable() {
		map = new HashMap<Integer, NodeInfo>();
		map.put(1, new NodeInfo(1, 4000, 2, "127.0.0.1", 4001));
		map.put(2, new NodeInfo(2, 4001, 3, "127.0.0.1", 4002));
		map.put(3, new NodeInfo(3, 4002, 4, "127.0.0.1", 4003));
		map.put(4, new NodeInfo(4, 4003, 5, "127.0.0.1", 4004));
		map.put(5, new NodeInfo(5, 4004, 1, "127.0.0.1", 4000));
	}

	/**
	 * Gets a NodeInfo object from the map
	 * 
	 * @param nodeNum
	 *            a node number, which is also a key of the map
	 * @return NodeInfo as an object
	 */
	public NodeInfo getNode(int nodeNum) {
		return (NodeInfo) map.get(nodeNum);
	}

	public int getSize() {
		return map.size();
	}
}
