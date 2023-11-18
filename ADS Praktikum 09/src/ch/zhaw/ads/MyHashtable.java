package ch.zhaw.ads;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

@SuppressWarnings("unchecked")
public class MyHashtable<K, V> implements Map<K, V> {
    private K[] keys;
    private V[] values;

    /**
     * Constructs a new MyHashtable with a specified size.
     *
     * @param size The initial size of the hashtable.
     */
    public MyHashtable(int size) {
        keys = (K[]) new Object[size];
        values = (V[]) new Object[size];
        for (int i = 0; i < size; i++) {
            keys[i] = null;
            values[i] = null;
        }
    }

    private int hash(Object k) {
        int h = Math.abs(k.hashCode());
        return h % keys.length;
    }

    /**
     * Clears all key-value pairs from this hashtable.
     * After this call returns, the hashtable will be empty.
     */
    public void clear() {
        for (int i = 0; i < keys.length; i++) {
            keys[i] = null;
            values[i] = null;
        }
    }

    /**
     * Associates the specified value with the specified key in this hashtable.
     * If the hashtable previously contained a mapping for the key, the old value is replaced.
     *
     * @param key   The key with which the specified value is to be associated.
     * @param value The value to be associated with the specified key.
     * @return The previous value associated with the key, or null if there was no mapping for the key.
     */
    public V put(K key, V value) {
        int index = hash(key);
        if (keys[index] != null && (keys[index] == key)) {
            values[index] = value;
            return value;
        }
        index = findPos(key);
        keys[index] = key;
        values[index] = value;
        return value;
    }

    /**
     * Returns the value to which the specified key is mapped,
     * or null if this map contains no mapping for the key.
     *
     * @param key The key whose associated value is to be returned.
     * @return The value to which the specified key is mapped, or null if this map contains no mapping for the key.
     */
    public V get(Object key) {
        int index = hash(key);
        if (keys[index] != key) {
            index = findPos(key);
        }
        return values[index];
    }

    /**
     * Removes the mapping for a key from this hashtable if it is present.
     * Returns the value to which this map previously associated the key, or null if the map contained no mapping for the key.
     *
     * @param key The key whose mapping is to be removed from the map.
     * @return The previous value associated with the key, or null if there was no mapping for the key.
     */
    public V remove(Object key) {
        if (this.size() == 0) {
            return null;
        }
        MyHashtable<K, V> newHashtable = new MyHashtable<>(keys.length);
        int index = hash(key);
        V oldValue;
        if (keys[index] != key) {
            index = findPos(key);
        }
        oldValue = values[index];
        keys[index] = null;
        values[index] = null;
        if (this.size() == 0) {
            return oldValue;
        }
        for (int i = 0; i < keys.length; i++) {
            if (keys[i] != null) {
                newHashtable.put(keys[i], values[i]);
            }
        }
        keys = newHashtable.keys;
        values = newHashtable.values;

        return oldValue;
    }

    /**
     * Returns the number of key-value mappings in this hashtable.
     */
    public int size() {
        int count = 0;
        for (K key : keys) {
            if (key != null) {
                count++;
            }
        }
        return count;
    }

    private int findPos(Object k) {
        int collision = 0;
        int index = hash(k);

        while (keys[index] != null && !keys[index].equals(k)) {
            index = (index + collision * collision) % keys.length;
            collision++;

            if (index < 0) {
                resize();
                index = hash(k);
            }
        }
        return index;
    }

    private void resize() {
        // Check if the current size is already very large
        if (keys.length > Integer.MAX_VALUE / 2) {
            throw new IllegalStateException("Hashtable too large to resize");
        }

        int newSize = Math.min(keys.length * 2, Integer.MAX_VALUE);
        MyHashtable<K, V> newHashtable = new MyHashtable<>(newSize);

        for (int i = 0; i < keys.length; i++) {
            if (keys[i] != null) {
                newHashtable.put(keys[i], values[i]);
            }
        }

        keys = newHashtable.keys;
        values = newHashtable.values;
    }

    // UnsupportedOperationException ===================================================================
    // Returns a collection view of the values contained in this map.
    public Collection<V> values() {
        throw new UnsupportedOperationException();
    }

    // Returns true if this map contains a mapping for the specified key.
    public boolean containsKey(Object key) {
        throw new UnsupportedOperationException();
    }

    // Returns true if this map maps one or more keys to the specified value.
    public boolean containsValue(Object value) {
        throw new UnsupportedOperationException();
    }

    // Returns a set view of the mappings contained in this map.
    public Set<Map.Entry<K, V>> entrySet() {
        throw new UnsupportedOperationException();
    }

    // Compares the specified object with this map for equality.
    public boolean equals(Object o) {
        throw new UnsupportedOperationException();
    }

    // Copies all of the mappings from the specified map to this map (optional operation).
    public void putAll(Map<? extends K, ? extends V> t) {
        throw new UnsupportedOperationException();
    }

    // Returns the hash code value for this map.
    public int hashCode() {
        throw new UnsupportedOperationException();
    }

    // Returns true if this map contains no key-value mappings.
    public boolean isEmpty() {
        throw new UnsupportedOperationException();
    }

    // Returns a set view of the keys contained in this map.
    public Set<K> keySet() {
        throw new UnsupportedOperationException();
    }
}