/**Sasha Ilinskaya
 * 10/28/2021
 *  MyLinkedList is a class meant to work just like the java program for LinkedLists
 *  except my version takes specifically String types.
 */

package SpellChecker;

public class MyLinkedList {

    private String val;
    private MyLinkedList next = null;

/**Constructor takes in a String value to create a new LinkedList, this is a simplified
 * version of a LinkedList that also creates a node -- I omit that by including it
 * in the functions that need it.
 */
    public MyLinkedList(String val) {
        this.val = val;
    }

 /** The add function creates a node of this MyLinkedList class type and uses it
  * to traverse the list to find an empty spot at the end where it can place a new
  * String value.
  */
    public void add(String val) {
        var node = this;
        while (node.next != null) {
            node = node.next;
        }
        node.next = new MyLinkedList(val);
    }

/**The find function works similarly to add, except here it compares every value
 * in the list to the one we're looking for and once it's found it returns true.
 * Otherwise it's false
 */
    public boolean find(String val) {
        var node = this;
        while (node != null) {
            if (node.val.equals(val)) {
                return true;
            }
            node = node.next;
        }
        return false;
    }

 /**Remove function has three cases it takes care of. First: if the list is already
  * empty. Second: if the list only has one value that's equal to what we're looking for.
  * And Third: if the String we want deleted is somewhere in a list whose size is bigger
  * than one.
  */
    public static MyLinkedList remove(MyLinkedList list, String val){
        if(list == null){
            return null;
        }
        if(list.val == val){
            return list.next;
        }
        /**A new node is created and it checks every item in the list if it's what we're
         * looking for. Once it's found what we want to delete, it reassigns the next pointer
         * to be the pointer of the one before it.*/
        var node = list;
        while (node.next != null) {
            if (node.next.val == val) {
                node.next = node.next.next;
                return list;
            }
            node = node.next;
        }
        return list;
    }

/**Size function traverses our list and uses a counter to count how many times we were
 * able to go to the next item without it being null.
 * */
    public static int size(MyLinkedList list){
        int count = 0;
        var node = list;
        while(node != null){
            count++;
            node = node.next;
        }
        return count;
    }

/**The print function traverses the list and prints out every value with an arrow after it.
 * This method was used as a tester for how this class works as a whole.*/
    public void print() {
        var node = this;
        while (node != null) {
            System.out.print(node.val + " --> ");
            node = node.next;
        }
        System.out.println("null");
    }

/**toArray function takes a list and puts all of its values into an array of the same size.
 * */
    public String[] toArray(){
        int n = MyLinkedList.size(this);
        var arr = new String[n];

        int i = 0;
        var node = this;
        while(node != null){
            arr[i] = node.val;
            i++;
            node = node.next;
        }
        return arr;
    }

    /**This main function serves as a testing area to see how this class works*/
//    public static void main(String args[]) {
//        var ll = new MyLinkedList("one");
//        ll.add("two");
//        ll.add("three");
//        System.out.println(ll.find("five"));
//
//    }

}
