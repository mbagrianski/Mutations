import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main{
	
	static ArrayList<String> geneList = new ArrayList<>();
    public static void main(String[] args) throws FileNotFoundException{
    	
        Scanner scan = new Scanner(new File("src/InputFile.txt"));
        Main instance = new Main(); //Used to access the methods in this class
        
        int length = Integer.parseInt(scan.nextLine());
        int validGenes = Integer.parseInt(scan.nextLine());
        int diseaseGenes = Integer.parseInt(scan.nextLine());
        
        //The total number of genes is # valid genes + # diseased genes
        int totalGenes = validGenes + diseaseGenes;
        
        for (int i = 0; i < totalGenes; i++) {
        	geneList.addNode(scan.nextLine());
        }
        
        instance.mergeSort(0, totalGenes); 
        
        int maxMutations = Integer.parseInt(scan.nextLine());
        int genesToTest = Integer.parseInt(scan.nextLine());
        
        for (int i = 0; i < genesToTest; i++){
            String initial = scan.next();
            String mutated = scan.next();
            double ans = instance.BFS(initial, mutated, maxMutations);
            
            System.out.println(ans == -1 ? "NO" : "YES\n" + ans);
        }
    }

    public double BFS(String start, String end, int M){
        double ans = -1;
        Queue<Gene> q = new Queue<>();
        q.enqueue(new Gene(start, 1, 0));
        while (!q.isEmpty()){
            Gene cur = q.dequeue();
            //If number of mutations exceeds limit
            if (cur.z > M) continue;
            if (cur.x.equals(end)){
                //Found valid answer; update final answer
                ans = Math.max(ans, cur.y);
                continue;
            }
            //RULE 1
            String swapped = cur.x.charAt(cur.x.length()-1) + cur.x.substring(1, cur.x.length()-1) + cur.x.charAt(0);
            //If the modified gene exists, add to the queue
            if (BS(swapped)) q.enqueue(new Gene(swapped, cur.y * 0.02, cur.z + 1));
            //RULE 2
            ArrayList<Integer> indices = new ArrayList<>();
            for (int i = 0; i < cur.x.length() - 1; i++){
                if (cur.x.charAt(i) == cur.x.charAt(i+1)) indices.addNode(i);
            }
            for (int i = 0; i < indices.size(); i++){
                int index = indices.getNode(i);
                String beginning = cur.x.substring(0, index), ending = cur.x.substring(index + 2);
                String addA = beginning + "A" + ending;
                String addC = beginning + "C" + ending;
                String addG = beginning + "G" + ending;
                String addT = beginning + "T" + ending;
                if (BS(addA)) q.enqueue(new Gene(addA, cur.y * 0.06, cur.z + 1));
                if (BS(addC)) q.enqueue(new Gene(addC, cur.y * 0.06, cur.z + 1));
                if (BS(addG)) q.enqueue(new Gene(addG, cur.y * 0.06, cur.z + 1));
                if (BS(addT)) q.enqueue(new Gene(addT, cur.y * 0.06, cur.z + 1));
            }
            //RULE 3
            indices = new ArrayList<>(); //Clear the ArrayList
            for (int i = 0; i < cur.x.length() - 1; i++){
                //Check if any part of the gene has "GT" or "TG" in it
                if (cur.x.charAt(i) == 'G' && cur.x.charAt(i + 1) == 'T') indices.addNode(i);
                if (cur.x.charAt(i) == 'T' && cur.x.charAt(i + 1) == 'G') indices.addNode(i);
            }
            for (int i = 0; i < indices.size(); i++){
                int index = indices.getNode(i);
                String beginning = cur.x.substring(0, index + 1), ending = cur.x.substring(index + 1);
                String addA = beginning + "A" + ending;
                String addC = beginning + "C" + ending;
                String addG = beginning + "G" + ending;
                String addT = beginning + "T" + ending;
                if (BS(addA)) q.enqueue(new Gene(addA, cur.y * 0.08, cur.z + 1));
                if (BS(addC)) q.enqueue(new Gene(addC, cur.y * 0.08, cur.z + 1));
                if (BS(addG)) q.enqueue(new Gene(addG, cur.y * 0.08, cur.z + 1));
                if (BS(addT)) q.enqueue(new Gene(addT, cur.y * 0.08, cur.z + 1));
            }
        }
        return ans;
    }

    private void mergeSort(int start, int end){
        if (end - start > 1){
            int middle = (start + end) / 2;
            mergeSort(start, middle);
            mergeSort(middle, end);
            CombineList(start, end);
        }
    }

    private void CombineList(int start, int end){
        int middle = (start + end) / 2, pointX = start, pointY = middle;
        ArrayList<String> array = new ArrayList<>();
        while (pointX < middle && pointY < end){
            String first = geneList.getNode(pointX), second = geneList.getNode(pointY);
            if (first.compareTo(second) <= 0){
                array.addNode(first);
                pointX++;
            }else{
                array.addNode(second);
                pointY++;
            }
        }
        while (pointX < middle) {
        	array.addNode(geneList.getNode(pointX++));
        }
        while (pointY < end) {
        	array.addNode(geneList.getNode(pointY++));
        }
        for (int i = 0; i < end - start; i++) {
        	geneList.switchNode(array.getNode(i), i + start);
        }
    }

    private boolean BS(String s){
        int l = 0, r = geneList.size() - 1;
        while (l <= r){
            int m = (l + r) / 2;
            String temp = geneList.getNode(m);
            if (s.compareTo(temp) == 0) return true;
            else if (s.compareTo(temp) > 0) l = m + 1;
            else r = m - 1;
        }
        return false;
    }
}
