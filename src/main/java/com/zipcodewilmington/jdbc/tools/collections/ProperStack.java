package com.zipcodewilmington.jdbc.tools.collections;

import java.util.*;


/**
 * Created by leon on 3/13/18.
 */
public class ProperStack<E> implements Iterable<E> {
    private Stack<E> stack;

    public ProperStack(List<E> list) {
        stack = new Stack<>();
        stack.addAll(list);
    }

    public ProperStack() {
        this(new ArrayList<>());
    }

    public void push(E... e) {
        stack.addAll(Arrays.asList(e));
    }

    public E pop() {
        return stack.pop();
    }

    public E peek() {
        return stack.peek();
    }

    public boolean isEmpty() {
        return stack.isEmpty();
    }

    public int size() {
        return stack.size();
    }

    @Override
    public Iterator<E> iterator() {
        return stack.iterator();
    }
}
