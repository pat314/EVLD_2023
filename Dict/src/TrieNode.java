import java.util.Arrays;

public class TrieNode {
    private final int ALPHABET_SIZE = 26;
    //Store substring at node. Eg. abstract:
    //a-> ab-> abs-> abst->...-> abstract
    private StringBuffer data = new StringBuffer();

    //Pointer to next node by the alphabetical order.
    //Eg. this.data = a => this.Children[0].data = aa and so on.
    private TrieNode[] Children = new TrieNode[ALPHABET_SIZE];

    //Boolean variable checking if the data at current node is a word.
    //private boolean isAWord = false;

    /**
     * counter.
     */
    private int childrenNum = 0;
    public int getChildrenNum() {
        return childrenNum;
    }
    //Methods
    /**
     * Default constructor.
     */
    public TrieNode() {;
        Arrays.fill(Children, null);
    }
    /**
     * Checking whether current node has a full word.
     * Instead of using a boolean to check in each node, I attempt to create a trie.
     * with its nodes' data will be blank if it haven't completed yet.
     * @return return true if current node has a full word (eg. abstraction)
     *         return false otherwise (eg. abst)
     */
    public boolean isWord() {
        return !this.data.isEmpty();
    }
    /**
     * set data for the current node.
     * @param data the given data being about to be set for the current node
     */
    public void setData(StringBuffer data) {
        this.data = data;
    }

    /**
     * get the data of the current node.
     * @return data of the current node
     */
    public StringBuffer getData() {
        return this.data;
    }

    public TrieNode[] getChildren() {
        return this.Children;
    }
}