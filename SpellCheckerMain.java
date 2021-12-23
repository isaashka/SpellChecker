/**Sasha Ilinskaya
 10/28/2021
 SpellChecker is the main program in this project, it implements MyLikedList and MyHashTable in
 order to become a user spell checker. The main purpose is for the user to enter a word which this
 program will check to see if it exists, if it doesn't, or if it has some words it could've been
 (the user misspelled the word).*/

package SpellChecker;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Scanner;
import SpellChecker.MyLinkedList;
import SpellChecker.MyHashTable;

public class SpellCheckerMain {
    private char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    private MyHashTable ht = null;

/**The constructor takes in a size and initializes a new MyHashTable of that size.*/
    public SpellChecker(int size){
        this.ht = new MyHashTable(size);
    }

/**contains checks if the hashtable has a certain word.*/
    public boolean contains(String word){
        return this.ht.contains(word);
    }

/**deleteLetter is a function that analyzes the case where the user added a letter
 * by accident to see if there are any words that exist by deleting a letter from this word.*/
    private String[] deleteLetter(String word){
        int n = word.length();
        String[] out = new String[n];
        for(int i = 0; i < n; i++){
            //the second substring starts with the next index after the letter we want gone
            out[i] = word.substring(0, i) + word.substring(i+1, n);
        }
        return out;
    }

/**insertLetter analyzes if the user missed a letter in the word they typed. The function
 * adds each letter of the alphabet at every possible place in the word (from the first to the
 * last place).*/
    private String[] insertLetter(String word){
        int n = word.length();
        int m = this.alphabet.length * (n+1);
        String[] out = new String[m];

        int j = 0;

        for(int i = 0; i <= n; i++){
            for(char c : this.alphabet){
                out[j] = word.substring(0, i) + c + word.substring(i, n);
                j++;
            }
        }
        return out;
    }

/**swapLetters checks if the user accidentally swaped two neighboring letters in the word.
 * By swapping all neighboring letters with each other we can find if those words exist in
 * the dictionary.*/
    private String[] swapLetters(String word){
        int n = word.length();
        String[] out = new String[n-1];

        //in order to swap the letters I first turned the word into a char array which makes it easier to
        //use indices for the characters
        for(int i = 0; i < n-1; i++){
            //these are created new every time because we want the original word, just different sets of letters swapping
            char[] wordChar = word.toCharArray();
            char tempChar = wordChar[i];
            wordChar[i] = wordChar[i+1];
            wordChar[i+1] = tempChar;
            out[i] = String.valueOf(wordChar);
        }
        return out;
    }

/**replaceLetter checks if any letter in the word was typed wrong, so instead of one letter
 * it turned out to be another. So the function swaps out every singled letter of the word
 * with every letter of the alphabet to find any real words.*/
    private String[] replaceLetter(String word){
        int n = word.length();
        int m = this.alphabet.length * n;
        String[] out = new String[m];

        int j = 0;

        //swaps every letter of the words with every letter of the alphabet
        for(int i = 0; i < n; i++){
            for(char c : this.alphabet){
                out[j] = word.substring(0, i) + c + word.substring(i+1, n);
                j++;
            }
        }
        return out;
    }

/**insertSpace checks if the user's word was actually two words written together, it divides
 * the String into two separate ones and checks if both of them are a word -- if they are then
 * both are added as possible near misses.*/
    private String[] insertSpace(String wordPair){
        int n = wordPair.length();

        //initialized with wordPair because it's difficult to work around this any other way, I delete it later
        MyLinkedList list = new MyLinkedList(wordPair);;

        for(int i = 1; i < n; i++){
            String word1 = wordPair.substring(0, i);
            String word2 = wordPair.substring(i, n);
            //checks if both exist, otherwise it can take part of the word and count it as a near miss while the other part is discarded
            if(checkBothWords(word1, word2)){
                list.add(word1);
                list.add(word2);
            }
        }
        MyLinkedList.remove(list, wordPair);

        return list.toArray();
    }

/**checkBothWords is a helper function for insertSpace that checks if both of the words created
 * by separating them are actual words and if they are then the value is returned as true and both
 * of them get added to the list of possibilities, if not then it's returned as false and they aren't
 * added.*/
    private boolean checkBothWords(String word1, String word2) {
        if (this.ht.contains(word1) && this.ht.contains(word2)) {
            return true;
        }
        return false;
    }

/**nearMisses is called when a word doesn't exist in the dictionary but has the possibility of
 * being misspelled. This function goes through every possible type of misspelling (all functions
 * above) and creates a hashtable to put all the near misses in to. It then converts the hash table
 * into an array.*/
    private String[] nearMisses(String word){
        //Does not include cases where the user typed special chars, unicode symbols, or numbers

        //A case for if the user typed nothing
        if(word.equals("")){
            return new String[0];
        }

        //New hashtable of words that are possible near misses.
        MyHashTable ht2 = new MyHashTable(10);

        var words1 = deleteLetter(word);
        checkInDictionary(words1, ht2);

        var words2 = insertLetter(word);
        checkInDictionary(words2, ht2);

        var words3 = swapLetters(word);
        checkInDictionary(words3, ht2);

        var words4 = replaceLetter(word);
        checkInDictionary(words4, ht2);

        var words5 = insertSpace(word);
        checkInDictionary(words5, ht2);

        //changes the hashtable into an array so it's easily printed later
        var arr = ht2.toArray();
        Arrays.sort(arr);
        return arr;
    }

/**checkInDictionary works with nearMisses to check if all the possibilties of near misses
 * actually exist in the dictionary and if they do then they're added to the hashtable called ht2.*/
    private void checkInDictionary(String[] words, MyHashTable ht2){
        int i = 0;
        while(i < words.length){
            if(this.ht.contains(words[i])){
                ht2.add(words[i]);
            }
            i++;
        }
    }

/**uploadData is a function that read the file WordList.txt and puts all the values in it into
 * a hashtable. This function reduces clutter inside main.*/
    private void uploadData(String fileName){
        try {
            InputStream stream = this.getClass().getClassLoader().getResourceAsStream(fileName);
            Scanner myReader = new Scanner(stream);
            //By using the scanner we're adding all the words into the hashtable
            while (myReader.hasNextLine()) {
                String word = myReader.nextLine();
                this.ht.add(word);
            }
            //This was used for counting collisions
            //System.out.println("collision count: " + ht.COLLISION_COUNT);

            myReader.close();
        } catch (Exception e) {
            System.out.println("Error of uploading data.");
            e.printStackTrace();
        }
    }

/**spellChecking checks if the user's word exists in the dictionary, doesn't exist in the dictionary,
 * or if there are similar words to it (near misses). */
    public static void spellChecking(String word, SpellChecker s){
        if (s.contains(word)) {
            System.out.println("Ok");
        }
        else{
            //Checks if there are no near misses then the word doesn't exist
            var words = s.nearMisses(word);
            if(words.length == 0){
                System.out.println("Not found");
            }
            //Prints out all the words that are near misses
            else{
                int index = 1;
                System.out.print("These are similar to what you wrote: ");
                for(String x : words){
                    if(index == words.length){
                        System.out.print(x);
                    }
                    else{
                        System.out.print(x + ", ");
                    }
                    index++;
                }
                System.out.println();
            }
        }
    }

/**The main function creates a SpellChecker of size 1000, it's then populated by the words inside
 * the WordList.txt file. Then the program asks the user to type in a word and then it checks
 * if it's real or not. The program stops asking for a new word when the user types 'END'.*/
    public static void main(String[] vars){
        var s = new SpellChecker(1000);
        s.uploadData("WordList.txt");

        Scanner input = new Scanner(System.in);
        System.out.print("Type in a word: ");

        String word = input.next();

        while(!word.equals("END")){
            spellChecking(word, s);
            System.out.print("Type in a word: ");
            word = input.next();
        }
    }
}
