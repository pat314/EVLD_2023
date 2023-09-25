public class Node {
    /**
     * The string that this Node is representing.
     */
    private StringBuffer data = new StringBuffer();
    /**
     * Determines whether this node represents a complete word.
     */
    private boolean isWord = false;
    /**
     * The array stores the child Nodes of this Node.
     */
    public int[] nextNode = new int[25];
    /**
     * The number of strings whose prefix string is represented by this Node.
     */
    private int cnt = 0;

    /**
     * Returns the string this Node is representing.
     * @return a string of type StringBuffer
     */
    public StringBuffer getData() {
        return this.data;
    }

    /**
     * Returns the isWord property.
     * @return a boolean type
     */
    public boolean getIsWord() {
        return this.isWord;
    }

    /**
     * Returns the number of strings whose prefix string is represented by this Node.
     * @return an integer type
     */
    public int getCnt() {
        return this.cnt;
    }

    /**
     * Change the value of the data attribute to the value of the input parameter.
     * @param data input string
     */
    public void setData(StringBuffer data) {
        this.data = data;
    }

    /**
     * Change the value of property isWord to the value of the input parameter.
     * @param isWord input parameter
     */
    public void setIsWord(boolean isWord) {
        this.isWord = isWord;
    }

    /**
     * Change the value of the cnt property to the value of the input parameter.
     * @param cnt input integer
     */
    public void setCnt(int cnt) {
        this.cnt = cnt;
    }

    /**
     * Default constructor without parameter.
     */
    public Node() {

    }

    /**
     * Constructor with parameter.
     * @param data input for data attribute
     */
    public Node(StringBuffer data) {
        this.data = data;
    }
}
