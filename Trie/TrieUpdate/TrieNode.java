public class TrieNode {
    //Declaration: Instead of using a boolean to check in each node,
    //I attempt to create a trie with its nodes' data will be blank 
    //if it haven't completed yet. 
    //Attributes

    //Store number of characters in the alphabet
    private final int ALPHABET_SIZE = 26;

    //Store substring at node. Eg. abstract:
    //a-> ab-> abs-> abst->...-> abstract
    private String data;
    
    //Pointer to next node by the alphabetical order.
    //Eg. this.data = a => this.Children[0].data = aa and so on.
    private TrieNode[] Children;
    
    //Boolean variable checking if the data at current node is a word.
    //private boolean isAWord;

    //Methods
    /**
     * Default constructor.
     */
    public TrieNode() {
        data = "";
        Children = new TrieNode[ALPHABET_SIZE];
        for (int index = 0; index < Children.length; index++) {
            Children[index] = null;
        }
        //isAWord = false;
    }

    /**
     * Checking whether current node has a full word.
     * Instead of using a boolean to check in each node, I attempt to create a trie.
     * with its nodes' data will be blank if it haven't completed yet. 
     * @return return true if current node has a full word (eg. abstraction)
     *         return false otherwise (eg. abst)
     */
    public boolean isAWord() {
        return !this.data.equals("");
    }

    /**
     * set data for the current node.
     * @param data the given data being about to be set for the current node
     */
    public void setData(String data) {
        this.data = data;
    }
    
    /**
     * get the data of the current node.
     * @return data of the current node
     */
    public String getData() {
        return this.data;
    }

    public TrieNode[] getChildren() {
        return this.Children;
    }
}