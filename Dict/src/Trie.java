import java.util.*;
public class Trie {
    //Attributes

    //Store number of characters in the alphabet
    private final int ALPHABET_SIZE = 26;
    //A node which is a root of trie
    private TrieNode root;
    private Word word;
    private int childrenNum = 0;
    public static Scanner Scan = new Scanner(System.in);
    //Methods
    /**
     * Default constructor for Trie data structure.
     */
    public Trie() {
        root = new TrieNode();
    }

    private static int charOrder(char c) {
        if (c == ' ' || c == '"') {
            return 0;
        }
        else if ('a' <= c && c <= 'z') {
            return c - 'a' + 1; // Lowercase letters 'a' to 'z' are encoded as 1 to 26
        }
        else {
            return 27; // All other characters are encoded as 27
        }
    }

    /**
     * Add a word to the tree.
     */
    public void addAWord() {
        TrieNode iterator = root;
        StringBuffer wordTarget = new StringBuffer();
        StringBuffer wordExplain = new StringBuffer();
        Word newWord = new Word();
        newWord.setWordTarget(wordTarget);
        newWord.setWordExplain(wordExplain);
        for (char x : word.toCharArray()) {
            int index = charOrder(word.charAt(x));
            if (iterator.getChildren()[index] == null) {
                iterator.getChildren()[index] = new TrieNode();
            }
            iterator = iterator.getChildren()[index];
        }
        iterator.setData(word);
    }

    /**Note: this method is used in other methods rather than using directly.
     * Search the node that its virtual data match with the input string.
     * Eg. Input: ab
     * children[0]->children[1]
     * => we will return the node at root.getChildren()[0].getChildren()[1];
     * In fact, at that node, due to its incomplete word, the data is equals to ""
     * but we are assuming that the data we get when traveling to that node is ab.
     * @param input input from users
     * @return the node reach the requirement
     */
    private TrieNode searchNodeMatchingInput(String input) {
        TrieNode iterator = root;
        input = input.toLowerCase();

        for (char x : input.toCharArray()) {
            int index = (int)(x - 'a');
            if (iterator.getChildren()[index] == null) {
                return null;
            }
            iterator = iterator.getChildren()[index];
        }
        return  iterator;
    }

    /**Note: this method is used in other methods rather than using directly.
     * Get all words that match with the input string.
     * @param Node the node corresponds to the input string
     * @return a string which has all right/complete words match the prefix
     *          each word in each seperate line.
     */
    private String findAllWordsFromANode(TrieNode Node) {
        if (Node == null) {
            return "";
        }
        String result;
        if (!Node.isWord()) result = "";
        else result = Node.getData() + "\n";

        for (int i = 0; i < ALPHABET_SIZE; i++) {
            if (Node.getChildren()[i] != null) {
                result += findAllWordsFromANode(Node.getChildren()[i]);
            }
        }
        return result;
    }

    //2 hàm trên phục vụ cho 2 phần việc ở hàm này
    //hàm này trả về 1 list các từ match với cụm nhập vào
    /**
     * get the list of all words matching the current input string.
     * @param input input string from users
     * @return the list of all words matching the current input string
     */
    public List<String> allMatchingResults(String input) {
        input = input.toLowerCase();
        TrieNode node = this.searchNodeMatchingInput(input);
        String[] resultsArray = findAllWordsFromANode(node).split("\n");
        List<String> results = Arrays.asList(resultsArray);
        return results;
    }

    /**
     * get the list of all words in source.
     * @return list of all words in source
     */
    public List<String> allWords() {
        String[] resultsArray = findAllWordsFromANode(this.root).split("\n");
        List<String> results = Arrays.asList(resultsArray);
        return results;
    }


    /**Note: this method is used in other methods rather than using directly.
     * Check whether the given node still exists children or not
     * @param node the given node
     * @return true if it contains no children, false otherwise
     */
    private boolean haveNoChildren(TrieNode node) {
        for (int i = 0; i < ALPHABET_SIZE; i++) {
            if (node.getChildren()[i] != null) return false;
        }
        return true;
    }

    /**Note: this method is used in the next method as it can't use directly.
     * Delete a required word with backtracking algorithm
     * @param node -
     * @param word -
     * @param height -
     * @return -
     */
    private TrieNode deleteWord (TrieNode node, String word, int height) {
        //Firstly, we make all characters in word to lowercase for simplicity.
        word = word.toLowerCase();

        //If the input node has nothing, so we have not to do anything
        if (node == null) {
            return node;
        }

        //If we have already delete to the last character of the given word
        //(or reach to the node contains complete word),
        //we set the word at that node equals to blank string ""
        //and examine if it has no children so do delete that node (make it equal to null)
        if (height == word.length()) {
            word = "";
            if (haveNoChildren(node)) {
                node = null;
            }
            return node;
        }

        //If not having reached to the last character, go to the next node matching
        //the index representing the order character in the alphabet

        //This line is to get the index of the ith character in the string of word.
        int index = (int)(word.charAt(height) - 'a');

        //Recursion
        deleteWord (node.getChildren()[index], word, height + 1);

        //Backtracking: delete the branch of the trie that
        //only connecting to the deleted word
        //(strictly speaking, the node contains the word having been required to delete).
        if (haveNoChildren(node) &&  !node.isWord()) {
            node = null;
        }

        return node;
    }

    /**
     * A method that implement delete word action by using the above method.
     * @param word the word that is required to delete
     */
    public void implementDelete(String word) {
        deleteWord(root, word, 0);
    }
}