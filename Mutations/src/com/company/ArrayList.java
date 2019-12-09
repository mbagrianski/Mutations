public class ArrayList<T>{
    private int size = 20001;
    private int filledLength = 0;
    
    private Node<T>[] array = new Node[size];    

    public void addNode(T n){
        if (filledLength >= size) doubleSize();
        array[filledLength++] = new Node<>(n);
    }
    
    public void removeNode(int i){
        for (int j = i; j < filledLength; j++) {
        	array[j] = array[j + 1];
        }
        filledLength = Math.max(filledLength - 1, 0);
    }

    public void switchNode(T n, int i){
    	Node<T> node = new Node<>(n);
        array[i] = node;
    }

    public T getNode(int i){
        return array[i].getValue();
    }

    public int size(){
        return filledLength;
    }
    
    
    private void doubleSize(){ //Double the size of the array if there are too many elements
        size *= 2; 
        Node<T>[] temp = new Node[size];
        for (int i = 0; i <= filledLength-1; i++) {
        	temp[i] = array[i];
        }
        array = temp;
    }
}
