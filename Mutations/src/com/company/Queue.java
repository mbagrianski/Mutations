package com.company;

public class Queue<T>{
    private ArrayList<T> array = new ArrayList<>(); //Implement queue with ArrayList

    public void enqueue(T node){
        //Add node; for this queue, we add to the end of the ArrayList
        array.addNode(node);
    }

    public T dequeue(){
        //For this queue, we remove elements from the beginning of the ArrayList
        T temp = array.getNode(0); //Get the value
        array.removeFront(); //Remove node
        return temp;
    }

    public boolean isEmpty(){
        //Checks if the queue is empty or not
        return array.size() == 0;
    }
}