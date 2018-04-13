package linkedmap;

import java.io.*;
import java.util.*;

/* We can keep two separate data structures. A HashMap with (Key,Pointer) pairs and a doubly linked
  list which will work as the priority queue for deletion and store the Values. From the HashMap,
  we can point to an element in the doubly linked list and update its' retrieval time. Because we
  go directly from the HashMap to the item in the list, our time complexity remains at O(1)
*/
public class LRUCacheDemo {
    public static void main(String args[]) {
        LRUCache<Integer, String> lru = new LRUCache<>(3);

        Scanner reader = new Scanner(System.in);
        while(reader.hasNextLine()){

            for(int i=1; i<=3; ++i){
                String nova = reader.nextLine();
                lru.put(i, nova);
            }
            System.out.println("LRU  : " + lru);
        }

        lru.get(8);
        /* for(Map.Entry<Integer, Integer> entry : lru.entrySet()) {
            System.out.printf("\n%1$-5s  %2$-5s", entry.getKey(), entry.getValue());
        } */


    }
}

class LRUCache<K, V> extends LinkedHashMap<K, V> {
    private final int CACHE_SIZE;

    //NOTE : LinkedHashMap have already given implementation for LRU, this class has just used those method
    //See http://grepcode.com/file/repository.grepcode.com/java/root/jdk/openjdk/6-b14/java/util/LinkedHashMap.java#LinkedHashMap

    // LinkedHashMap(int initialCapacity, float loadFactor, boolean accessOrder)
    // accessOrder - to maintain in order of elements from least-recently accessed to most-recently.
    LRUCache(final int sizeIn) {
        super(sizeIn, 0.75F, true);
        this.CACHE_SIZE = sizeIn;
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry<K,V> eldest) {
        return size() > this.CACHE_SIZE;
        /* Returns true if this map should remove its eldest entry. This method is invoked by put and putAll after
           inserting a new entry into the map. It provides the implementor with the opportunity to remove the eldest
           entry each time a new one is added. This is useful if the map represents a cache.
        */
    }
}
