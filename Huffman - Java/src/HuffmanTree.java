import java.util.Comparator;

/**
 * A HuffmanTree represents a variable-length code such that the shorter the
 * bit pattern associated with a character, the more frequently that character
 * appears in the text to be encoded.
 */

public class HuffmanTree {
  
  class Node {
    protected char key;
    protected int priority;
    protected Node left, right;
    
    public Node(int priority, char key) {
      this(priority, key, null, null);
    }
    
    public Node(int priority, Node left, Node right) {
      this(priority, '\0', left, right);
    }
    
    public Node(int priority, char key, Node left, Node right) {
      this.key = key;
      this.priority = priority;
      this.left = left;
      this.right = right;
    }
    
    public boolean isLeaf() {
      return left == null && right == null;
    }
  }
  
  protected Node root;
  
  /**
   * Creates a HuffmanTree from the given frequencies of letters in the
   * alphabet using the algorithm described in lecture.
   */
  FrequencyTable frequencies = new FrequencyTable();
  public HuffmanTree(FrequencyTable charFreqs) {
    Comparator<Node> comparator = (x, y) -> {
      /**
       *  x comes before y if x's priority is less than y's priority
       */
    	
    	if(x.priority < y.priority) {
    		return -1;
    	}
    	else if(x.priority > y.priority) {
    		return 1;
    	}
    	else {
    		return 0;	
    	}   
    };  
    PriorityQueue<Node> forest = new Heap<Node>(comparator);
    frequencies = charFreqs;

    /**
     * Start by populating forest with leaves.
     */
    for (char ch : charFreqs.keySet()){
    	int freq = charFreqs.get(ch);
    	if(freq > 0) {
    		forest.insert(new Node(freq, ch));
    	}
    }
    root = forest.peek();
    while(forest.size() > 1) {	
    		int firstPrio = forest.peek().priority;
    		Node first = forest.delete();
    		int secondPrio = forest.peek().priority;
    		Node second = forest.delete();
    		root = new Node(firstPrio + secondPrio,
    	    		first, second);
    		forest.insert(root);		
    }
  }
  
  /**
   * Returns the character associated with the prefix of bits.
   * 
   * @throws DecodeException if bits does not match a character in the tree.
   */
  public char decodeChar(String bits) {
	  int length = bits.length();
	  if(length == 0) {
		  throw new DecodeException("There is no valid character.");
	  }
	  Node tempNode = root;
	  for (int i = 0; i < length; i++) {
		  if(bits.charAt(i) == '1'){
			  if(!tempNode.isLeaf()) {
				  tempNode = tempNode.right;
			  }
		  }
		  else {
			  if(!tempNode.isLeaf()) {
				  tempNode = tempNode.left;
			  }
		  }
	  }
    return tempNode.key;
  }
    
  /**
   * Returns the bit string associated with the given character. Must
   * search the tree for a leaf containing the character. Every left
   * turn corresponds to a 0 in the code. Every right turn corresponds
   * to a 1. This function is used by CodeBook to populate the map.
   * 
   * @throws EncodeException if the character does not appear in the tree.
   */
  String helper = "";
  public String lookup(char ch) {
	  int freq = frequencies.get(ch);
		if(freq == 0) {
			throw new EncodeException(ch);
		}
		else {
			lookupHelper(root, ch, "");
			return helper;
		}
  }
  
  
  public void lookupHelper(Node n, char ch, String returnString) {
	  if (!n.isLeaf()) {
			  lookupHelper(n.right, ch, returnString + "1");
			  lookupHelper(n.left, ch, returnString + "0");	  
	  }
	  else if(n.key == ch) {
		 helper = returnString; 
	  }  
  }
}



























