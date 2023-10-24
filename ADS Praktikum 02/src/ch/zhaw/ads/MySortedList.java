package ch.zhaw.ads;

public class MySortedList extends MyList {

    @Override
    public boolean add(Object data) {
        ListNode newNode = new ListNode(data);

        if (isEmpty()) {
            head = newNode;
            tail = newNode;
        } else if (newNode.compareTo(head) <= 0) {
            insertBefore(newNode, head);
            head = newNode;
        } else if (newNode.compareTo(tail) >= 0) {
            insertAfter(newNode, tail);
            tail = newNode;
        } else {
            ListNode current = head;
            while (newNode.compareTo(current) > 0) {
                current = current.next;
            }
            insertBefore(newNode, current);
        }
        return true;
    }

    private void insertBefore(ListNode newNode, ListNode currentNode) {
        newNode.prev = currentNode.prev;
        newNode.next = currentNode;
        if (currentNode.prev != null) {
            currentNode.prev.next = newNode;
        }
        currentNode.prev = newNode;
    }

    private void insertAfter(ListNode newNode, ListNode currentNode) {
        newNode.next = currentNode.next;
        newNode.prev = currentNode;
        if (currentNode.next != null) {
            currentNode.next.prev = newNode;
        }
        currentNode.next = newNode;
    }
}