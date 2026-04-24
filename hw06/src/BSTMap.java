import edu.princeton.cs.algs4.BST;

import java.util.Iterator;
import java.util.Set;

public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {

    BSTNode root;
    private int count;

    /** Constructor **/
    BSTMap() {
        super();
        count = 0;
    }
    /**
     * Associates the specified value with the specified key in this map.
     * If the map already contains the specified key, replaces the key's mapping
     * with the value specified.
     *
     * @param key the key of key-value couple entry.
     * @param value the value of the key-value couple entry.
     */
    @Override
    public void put(K key, V value) {
        root = put(key, value, root);
    }

    /**
     * A helper method for put(), putting the key-value couple to the tree with
     * _root being its root.
     * @param key the key of couple
     * @param value the value of couple
     * @param _root the root of the tree to be put into
     */
    private BSTNode put(K key, V value, BSTNode _root) {
        if (_root == null) {
            count++;
            return new BSTNode(key, value, null, null);
        }
        if (key.compareTo(_root.key) < 0) {
            _root.left = put(key, value, _root.left);
        }
        else if (key.compareTo(_root.key) == 0) {
            _root.val = value;
        }
        else if (key.compareTo(_root.key) > 0) {
            _root.right = put(key, value, _root.right);
        }
        return _root;
    }

    /**
     * Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     *
     * @param key the key to be searched.
     */
    @Override
    public V get(K key) {
        if (root == null || root.get(key) == null) return null;
        return root.get(key).val;
    }

    /**
     * Returns whether this map contains a mapping for the specified key.
     *
     * @param key the key to be searched.
     */
    @Override
    public boolean containsKey(K key) {
        if (root == null) return false;
        return root.get(key) != null;
    }

    /**
     * Returns the number of key-value mappings in this map.
     */
    @Override
    public int size() {
        return count;
    }

    /**
     * Removes every mapping from this map.
     */
    @Override
    public void clear() {
        count = 0;
        root = null;
    }

    /**
     * Returns a Set view of the keys contained in this map. Not required for Lab 7.
     * If you don't implement this, throw an UnsupportedOperationException.
     */
    @Override
    public Set<K> keySet() {
        throw new UnsupportedOperationException();
    }

    /**
     * Removes the mapping for the specified key from this map if present,
     * or null if there is no such mapping.
     * Not required for Lab 7. If you don't implement this, throw an
     * UnsupportedOperationException.
     *
     * @param key
     */
    @Override
    public V remove(K key) {
        throw new UnsupportedOperationException();
    }

    /**
     * Returns an iterator over elements of type {@code K}.
     *
     * @return an Iterator.
     */
    @Override
    public Iterator<K> iterator() {
        return null;
    }

    /**
     * A class representing an entry of the map. Includes Key, Value, Left and Right.
     */
    private class BSTNode {

        K key;
        V val;
        BSTNode left;
        BSTNode right;

        /**
         * Constructor for a BSTNode.
         * @param _key the key of the node
         * @param _value the value of the node
         * @param _left  the left child node of the node
         * @param _right the right child node of the node
         */
        BSTNode(K _key, V _value, BSTNode _left, BSTNode _right) {
            key = _key;
            val = _value;
            left = _left;
            right = _right;
        }

        /**
         * A method to get the BSTNode whose key is _key.
         * @param _key the key to search
         * @return the BSTNode whose key is _key. Returns null if _key is null, or it
         * doesn't exist in the map.
         */
        public BSTNode get(K _key) {
            if (_key == null) {
                return null;
            }
            else if (_key.compareTo(key) == 0) {
                return this;
            }
            else if (left != null && _key.compareTo(key) < 0) {
                return left.get(_key);
            }
            else if (right != null && _key.compareTo(key) > 0) {
                return right.get(_key);
            }
            return null;
        }

    }
}
