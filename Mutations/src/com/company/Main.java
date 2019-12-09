package com.company;

import java.io.File;
import java.util.Scanner;

public class Main{
    private ArrayList<String> genes = new ArrayList<>();
    private ArrayList<Boolean> vis = new ArrayList<>();
    private double R1 = 0.02, R2 = 0.06, R3 = 0.08; //The probability that each rule takes place.
    public static void main(String[] args) throws Exception{
        Scanner scan = new Scanner(new File("Test3.txt"));
        Main CLASS = new Main(); //Used to access the methods in this class
        int L = Integer.parseInt(scan.nextLine());
        int V = Integer.parseInt(scan.nextLine());
        int D = Integer.parseInt(scan.nextLine());
        //The total number of genes is # valid genes + # diseased genes
        for (int i = 0; i < V + D; i++){
            CLASS.genes.addNode(scan.nextLine());
            CLASS.vis.addNode(false); //For each gene initialized, set the visited boolean to false.
        }
        CLASS.SORT(0, V + D); //Merge Sort the genes; prepare for binary search later on
        int M = Integer.parseInt(scan.nextLine());
        int G = Integer.parseInt(scan.nextLine());
        for (int i = 0; i < G; i++){
            for (int j = 0; j < V + D; j++) CLASS.vis.replaceNode(false, j); //Reset the array to false
            String P = scan.next();
            String Q = scan.next();
            //For each query, we run BFS to see if a path exists.
            double ans = CLASS.BFS(P, Q, M);
            System.out.println(ans == -1 ? "NO" : "YES\n" + ans);
        }
    }

    public double BFS(String start, String end, int M){
        Queue<Triple> q = new Queue<>();
        //Default probability is 1 and default # steps is 0.
        q.enqueue(new Triple(start, 1, 0));
        //First, we mark the starting gene as "visited", so a cycle does not occur later on.
        int firstIndex = BS(start);
        vis.replaceNode(true, firstIndex);
        while (!q.isEmpty()){
            //Get the top-most element
            Triple cur = q.dequeue();
            //If number of mutations exceeds limit
            if (cur.x.equals(end)) return cur.y; //Found valid answer; update final answer
            //Since every rule used after requires one more step, we skip as soon as the number of steps reaches M.
            if (cur.z >= M) continue;
            //RULE 1
            String swapped = cur.x.substring(1, cur.x.length() - 1);
            swapped = cur.x.charAt(cur.x.length() - 1) + swapped + cur.x.charAt(0);
            //If the modified gene exists, add to the queue
            int found = BS(swapped);
            if (found != -1 && !vis.getNode(found)){
                q.enqueue(new Triple(swapped, cur.y * R1, cur.z + 1));
                vis.replaceNode(true, found); //Mark as visited
            }
            //RULE 2
            for (int i = 0; i < cur.x.length() - 1; i++){
                if (cur.x.charAt(i) != cur.x.charAt(i + 1)) continue; //Consecutive letters must match
                String beginning = cur.x.substring(0, i), ending = cur.x.substring(i + 2);
                //Following rule 2, we replace identical consecutive elements with one element.
                String addA = beginning + "A" + ending;
                String addC = beginning + "C" + ending;
                String addG = beginning + "G" + ending;
                String addT = beginning + "T" + ending;
                //Check if the newly created gene exists; binary search
                int findA = BS(addA);
                int findC = BS(addC);
                int findG = BS(addG);
                int findT = BS(addT);
                //Note that for each new string created, we must check if:
                //1) The created string exists,
                //2) We have not visited this gene.
                if (findA != -1 && !vis.getNode(findA)){
                    vis.replaceNode(true, findA);
                    q.enqueue(new Triple(addA, cur.y * R2, cur.z + 1));
                }
                if (findC != -1 && !vis.getNode(findC)){
                    vis.replaceNode(true, findC);
                    q.enqueue(new Triple(addC, cur.y * R2, cur.z + 1));
                }
                if (findG != -1 && !vis.getNode(findG)){
                    vis.replaceNode(true, findG);
                    q.enqueue(new Triple(addG, cur.y * R2, cur.z + 1));
                }
                if (findT != -1 && !vis.getNode(findT)){
                    vis.replaceNode(true, findT);
                    q.enqueue(new Triple(addT, cur.y * R2, cur.z + 1));
                }
            }
            //RULE 3
            for (int i = 0; i < cur.x.length() - 1; i++){
                //Skip the index if it does not form "GT" or "TG"
                if (cur.x.charAt(i) == 'G' && cur.x.charAt(i + 1) != 'T') continue;
                if (cur.x.charAt(i) == 'T' && cur.x.charAt(i + 1) != 'G') continue;
                String beginning = cur.x.substring(0, i + 1), ending = cur.x.substring(i + 1);
                //If the gene contains "G" and "T" next to each other, add on all the possible
                //genes that follow rule 3. (Insert singular gene in between "GT" or "TG")
                String addA = beginning + "A" + ending;
                String addC = beginning + "C" + ending;
                String addG = beginning + "G" + ending;
                String addT = beginning + "T" + ending;
                //Check if the newly created gene exists; binary search
                int findA = BS(addA);
                int findC = BS(addC);
                int findG = BS(addG);
                int findT = BS(addT);
                //Note that for each new string created, we must check if:
                //1) The created string exists,
                //2) We have not visited this gene.
                if (findA != -1 && !vis.getNode(findA)){
                    vis.replaceNode(true, findA);
                    q.enqueue(new Triple(addA, cur.y * R3, cur.z + 1));
                }
                if (findC != -1 && !vis.getNode(findC)){
                    vis.replaceNode(true, findC);
                    q.enqueue(new Triple(addC, cur.y * R3, cur.z + 1));
                }
                if (findG != -1 && !vis.getNode(findG)){
                    vis.replaceNode(true, findG);
                    q.enqueue(new Triple(addG, cur.y * R3, cur.z + 1));
                }
                if (findT != -1 && !vis.getNode(findT)){
                    vis.replaceNode(true, findT);
                    q.enqueue(new Triple(addT, cur.y * R3, cur.z + 1));
                }
            }
        }
        return -1; //If this line is ever reached, then there does not exist a path.
    }

    private void SORT(int start, int end){
        //Merge Sort
        if (end - start > 1){
            int middle = (start + end) / 2;
            SORT(start, middle); //Sort the first half
            SORT(middle, end); //Sort the second half
            CombineList(start, end);
        }
    }

    private void CombineList(int start, int end){
        int middle = (start + end) / 2, pointX = start, pointY = middle;
        ArrayList<String> array = new ArrayList<>();
        while (pointX < middle && pointY < end){
            String first = genes.getNode(pointX), second = genes.getNode(pointY);
            if (first.compareTo(second) <= 0){
                //The first string is (alphabetically) smaller than the second
                array.addNode(first);
                pointX++;
            }else{
                //The second string is (alphabetically) smaller than the first
                array.addNode(second);
                pointY++;
            }
        }
        //Handle the leftover genes
        while (pointX < middle) array.addNode(genes.getNode(pointX++));
        while (pointY < end) array.addNode(genes.getNode(pointY++));
        //Copy the sorted elements into the original array
        for (int i = 0; i < end - start; i++) genes.replaceNode(array.getNode(i), i + start);
    }

    private int BS(String s){
        int l = 0, r = genes.size() - 1;
        while (l <= r){
            //Keep on narrowing down the search range
            int m = (l + r) / 2;
            String temp = genes.getNode(m);
            if (s.compareTo(temp) == 0) return m; //Found element
            else if (s.compareTo(temp) > 0) l = m + 1;
                //The given string, "s", is larger than current string; narrow down search range to right half
            else r = m - 1;
            //The given string, "s", is less than current string; narrow down search range to left half
        }
        return -1; //If this line is reached, then the element does not exist.
    }
}