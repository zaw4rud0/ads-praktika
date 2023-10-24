package ch.zhaw.ads;

import java.util.AbstractList;

public class MyList extends AbstractList<Object> {

    protected ListNode head;
    protected ListNode tail;

    public MyList() {
        this.head = null;
        this.tail = null;
    }

    @Override
    public boolean add(Object data) {
        ListNode newNode = new ListNode(data);

        // List is empty
        if (head == null) {
            head = newNode;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
        }
        tail = newNode;
        return true;
    }

    @Override
    public boolean remove(Object data) {
        ListNode currentNode = head;

        while (currentNode != null) {
            if (currentNode.data.equals(data)) {
                if (currentNode == head) {
                    head = currentNode.next;
                    if (head != null) {
                        head.prev = null;
                    } else {
                        tail = null;
                    }
                } else if (currentNode == tail) {
                    tail = currentNode.prev;
                    tail.next = null;
                } else {
                    currentNode.prev.next = currentNode.next;
                    currentNode.next.prev = currentNode.prev;
                }
                return true;
            }
            currentNode = currentNode.next;
        }
        return false;
    }

    @Override
    public Object get(int index) {
        if (index < 0) {
            throw new IndexOutOfBoundsException("Index shouldn't be negative!");
        }

        int count = 0;

        ListNode currentNode = head;

        while (currentNode != null) {
            if (count == index) {
                return currentNode.data;
            }
            count++;
            currentNode = currentNode.next;
        }
        throw new IndexOutOfBoundsException("Index is out of bounds!");
    }

    @Override
    public boolean isEmpty() {
        return head == null;
    }

    @Override
    public int size() {
        int count = 0;

        ListNode currentNode = head;

        while (currentNode != null) {
            count++;
            currentNode = currentNode.next;
        }
        return count;
    }

    @Override
    public void clear() {
        head = null;
        tail = null;
    }
}

class ListNode implements Comparable<ListNode> {

    ListNode prev;
    ListNode next;
    Object data;

    public ListNode(Object data) {
        this.data = data;
    }

    @Override
    public int compareTo(ListNode otherNode) {
        if (this.data == null && otherNode.data == null) {
            return 0;
        } else if (this.data == null) {
            return -1;
        } else if (otherNode.data == null) {
            return 1;
        }
        return ((Comparable<Object>) this.data).compareTo(otherNode.data);
    }
}