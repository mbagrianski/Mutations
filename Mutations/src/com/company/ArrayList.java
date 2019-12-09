package com.company;

public class ArrayList<T>{
    private int size = 20001; //Default size
    private int length = 0; //Tracks the actual length of the ArrayList
    private int fix = 0;
    private Node<T>[] array = new Node[size];

    public void addNode(T n){
        array[fix + length++] = new Node<>(n); //Convert n into a Node
    }

    public void removeFront(){
        //Removes the first element in the ArrayList
        fix++;
        length = Math.max(length - 1, 0);
    }

    public void replaceNode(T n, int i){
        //Change the value of the Node
        array[i + fix] = new Node<>(n);
    }

    public T getNode(int i){
        //Returns the value at the given index
        return array[i + fix].getValue();
    }

    public int size(){
        //Returns the length of the ArrayList
        return length;
    }
}