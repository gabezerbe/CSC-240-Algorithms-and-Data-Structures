import java.util.HashMap;
import java.util.Map;

class TrieNode {
    private final Map<Character, TrieNode> children = new HashMap<>();
    private boolean endOfWord;
    private int childCount;
    private int wordCount;

    Map<Character, TrieNode> getChildren() {
        return children;
    }

    boolean isEndOfWord() {
        return endOfWord;
    }

    void setEndOfWord(boolean endOfWord) {
        this.endOfWord = endOfWord;
    }

    /**
     * Takes in a given TrieNode and calculates the numbers of "words" stored in the Trie based on the
     * number of leaf nodes in the Trie.
     * @param current The current TrieNode of a Trie to check and recurse through
     * @return The number of words as an integer
     */
    int wordCount(TrieNode current){
        Map <Character, TrieNode> children = current.getChildren();
        for(Map.Entry<Character, TrieNode> entry : children.entrySet()){
            if(entry.getValue().getChildren() != null){
                if(entry.getValue().isEndOfWord()){
                    wordCount++;
                }
                wordCount(entry.getValue());
            }
        }
        return wordCount;
    }

    /**
     * Recursively works its way through the children of a given TrieNode incrementing the childCount
     * integer to tell us the total number of nodes a given Trie contains
     * @param current The current TrieNode of a Trie to count and recurse through
     * @return The number of nodes in a given TrieNode as an integer
     */
    int size(TrieNode current){

        Map <Character, TrieNode> children = current.getChildren();
        for(Map.Entry<Character, TrieNode> entry : children.entrySet()){
            childCount++;
            if(entry.getValue().getChildren() != null){
                size(entry.getValue());
            }
        }

        return childCount;
    }
}