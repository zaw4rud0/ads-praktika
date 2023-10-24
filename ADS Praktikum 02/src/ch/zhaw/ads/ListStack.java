package ch.zhaw.ads;

import java.util.ArrayList;
import java.util.List;

public class ListStack implements Stack {

    private List<Object> data;

    public ListStack() {
        data = new ArrayList<>();
        removeAll();
    }

    @Override
    public void push(Object x) throws StackOverflowError {
        if (isFull()) {
            throw new StackOverflowError();
        }
        data.add(x);
    }

    @Override
    public Object pop() {
        if (isEmpty()) return null;

        return data.remove(data.size() - 1);
    }

    @Override
    public boolean isEmpty() {
        return data.isEmpty();
    }

    @Override
    public Object peek() {
        if (data.isEmpty()) return null;

        return data.get(data.size() - 1);
    }

    @Override
    public void removeAll() {
        data = new ArrayList<>();
    }

    @Override
    public boolean isFull() {
        return data.size() == Integer.MAX_VALUE;
    }
}