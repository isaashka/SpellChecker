/**Sasha Ilinskaya
10/28/2021
 MyHashTable is a class that's meant to create a hashtable of Linked Lists which can do
 various things such as have things added or removed.*/

package SpellChecker;
import SpellChecker.MyLinkedList;

public class MyHashTable {
    private long size;
    private MyLinkedList [] ht;
    public int COLLISION_COUNT;

/**This constructor takes in a long value for the size of the table (so as to prevent
 * negative numbers interfering) and then initiates the size and an array of linked lists.*/
    public MyHashTable(long size){
        this.size = size;
        this.ht = new MyLinkedList[(int) size];
    }

/**This hash function works based off of specific codes of letters. Each letter has it's own number
 * in the computer language, so I used that to create a unique number for every word.*/
    public int hash(String key){
        char[] chars = key.toCharArray();
        var hash = 13;
        for(char c : chars){
            hash += 31 * (int)c;
        }
        return hash % (int)this.size;
    }
/**Second type of a hashing function that randomly generates a number.*/
//    public int hash2(String key){
//        int hash = (int) (Math.random()*(size));
//        return hash % (int)this.size;
//    }


/**The function add has two cases. One: if the list that we're hashing our value to hasn't been
 * initiated. And two: if it has been initiated. In both situation the String val is added to some
 * list in the hash table.*/
    public void add(String val){
        int index = hash(val);
        if(this.ht[index] == null){
            this.ht[index] = new MyLinkedList(val);
        }
        else if(!this.ht[index].find(val)){
            this.ht[index].add(val);
            COLLISION_COUNT++;
        }
    }

/**contains function checks if the hashtable contains a specific String value and returns either
 * a true or false boolean.*/
    public boolean contains(String val){
        int index = hash(val);
        if(this.ht[index] != null && this.ht[index].find(val)){
            return true;
        }
        return false;
    }

/**The function remove takes an item out of the hashtable, it uses MyLinkedList function remove
 * to do that more easily.*/
    public void remove(String val){
        int index = hash(val);
        this.ht[index] = MyLinkedList.remove(this.ht[index], val);
    }

/**The function size counts all the Strings in the hashtable. It uses the MyLinkedList function
 * size that counts how many words there are in a specific list. Then this function uses that and
 * adds up all the totals of lists into one big one for the whole hash table.*/
    public int size(){
        int count = 0;
        for(MyLinkedList list : this.ht){
            count += MyLinkedList.size(list);
        }
        return count;
    }

/**The print function prints out the whole hashtable by printing out each linked list (function
 * found in the MyLinkedList class).*/
    public void print(){
        for(MyLinkedList list : this.ht){
            if(list == null){
                System.out.println("Null list");
                continue;
            }
            list.print();
        }
    }

/**toArray uses MyLinkedList's function toArray which transforms the list into an array. Here
 * the loop goes through every list in the hashtable and adds each individual word into a big
 * array.*/
    public String[] toArray(){
        int n = this.size();

        int i = 0;
        var arr = new String[n];
        for(MyLinkedList list : this.ht){
            if(list != null) {
                for (String w : list.toArray()) {
                    arr[i] = w;
                    i++;
                }
            }
        }
        return arr;
    }

/**The following main method was used to test this class to see how it works.*/
//    public static void main(String args[]){
//        var ht = new MyHashTable(10);
//        //ht.hash("azAZ09");
//        //System.out.println(ht.hash("g"));
//        String[] arr = {"hi", "sasha", "boromir", "mom", "dad"};
//        for(String x : arr){
//            ht.add(x);
//        }
//        var arr2 = ht.toArray();
//
//        for(String w : arr2){
//            System.out.print(w + " ");
//        }
//
//    }


}
