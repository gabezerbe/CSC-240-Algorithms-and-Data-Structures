import java.io.*;

public class SpellDriver {

    /**
     * Builds our Trie so the main method is cleaner
     * @return Trie
     */
    public static Trie createTrie(String filename){
        Trie trie = new Trie();
        BufferedReader r;
        try{
            InputStream input = SpellDriver.class.getClassLoader().getResourceAsStream(filename);
            r = new BufferedReader(new InputStreamReader(input));
            String line = r.readLine();

            while(line != null){
                trie.insert(line);
                line = r.readLine();
            }
            r.close();
        } catch(IOException e){
            e.printStackTrace();
        }
        return trie;
    }

    /**
     * spellCheck function that will read in a file word by word and spell check those words
     * using the dictionary Trie. If the word does not exist in the dictionary it will be printed
     * with a star next to it to denote it is not spelled correctly
     * @param filename
     * @param dictionary
     */

    public static void spellCheck(String filename, Trie dictionary){

        BufferedReader r;
        try{
            InputStream input = SpellDriver.class.getClassLoader().getResourceAsStream(filename);
            r = new BufferedReader(new InputStreamReader(input));
            String line = r.readLine();

            while(line != null){
                if(line.equals("")){ line = r.readLine(); }

                String[] words = line.split("[\\p{Punct}\\s]+");
                for(String word : words){
                    if(dictionary.containsNode(word)){
                       // System.out.println(word);
                    } else {
                      //  System.out.println(word + "*");
                    }
                }
                line = r.readLine();
            }
            r.close();
        } catch (IOException e){
            e.printStackTrace();
        }

    }

    public static void main(String[] args) throws IOException {
        Trie dictionary = createTrie("large.txt");
        System.out.println("The dictionary contains " + dictionary.size() + " nodes.");
        System.out.println("The dictionary contains " + dictionary.getWordCount() + " words.");
        spellCheck("mmg.txt", dictionary);
    }
}
