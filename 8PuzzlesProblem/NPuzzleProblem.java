/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package npuzzleproblem;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.Vector;

/**
 *
 * @author User
 */
public class NPuzzleProblem {

    /**
     * @param args the command line arguments
     */
    public int InversionCount(Integer[] array) {
        int count = 0;
        int n = array.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = i + 1; j < n; j++) {
                if (array[i] > array[j]) {
                    count++;
                }
            }
        }
        return count;
    }

    public int ZerothRow(int[][] matrix) {
        int c = 0;
        int n = matrix.length;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] == 0) {
                    c = i;
                    break;
                }
            }
        }
        return c;
    }

    public int inv(int[][] matrix) {
        int n = matrix.length;
        List<Integer> arr = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] != 0) {
                    arr.add(matrix[i][j]);
                }
            }
        }
        Integer[] arr2 = new Integer[arr.size()];
        arr.toArray(arr2);
        int k = InversionCount(arr2);
        return k;
    }

    public boolean IsSolvable(int[][] matrix) {
        int n = matrix.length;
        List<Integer> arr = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] != 0) {
                    arr.add(matrix[i][j]);
                }
            }
        }
        Integer[] arr2 = new Integer[arr.size()];
        arr.toArray(arr2);
        int k = InversionCount(arr2);
        if (n % 2 == 1) {
            if (k % 2 == 1) {
                return false;
            } else {
                return true;
            }
        } else {
            int z = ZerothRow(matrix);
            if ((k + z) % 2 == 1) {
                return true;
            } else {
                return false;
            }
        }

    }

    public State AStarSearchHamming(State source) {
        PriorityOrderHam order = new PriorityOrderHam();
        PriorityQueue<State> PQ = new PriorityQueue<>(order);
        Vector<State> visited = new Vector<>();
        PQ.add(source);
        visited.addElement(source);
        //System.out.println(source.toString());

        long t = System.currentTimeMillis() + 180000;
        while (true) {
            if (System.currentTimeMillis() > t) {
                //System.out.println("Time limit crossed");
                return null;
            }

            State s = PQ.poll();
            //PQ.remove(s);
            //System.out.println(s.toString());
            //System.out.println(s.totCostManhattan + "\n");
            //System.out.println(inv(s.board));
            Vector<State> vec = s.getChildNodes();
            for (int i = 0; i < vec.size(); i++) {
                State st = vec.elementAt(i);
                for (int j = 0; j < visited.size(); j++) {
                    if (!visited.contains(st)) {
                        State newState = new State(st.board, s);
                        if (st.IsGoalNode() == true) {
                            // System.out.println(st.toString());
                            //System.out.println(st.totCostManhattan + "\n");
                            //System.out.println(inv(st.board));
                            return newState;
                            //break;

                        }
                        PQ.add(newState);
                        visited.addElement(st);

                    }
                }
            }
        }
    }

    public State AStarSearchManhattan(State source) {
        PriorityOrderMan order = new PriorityOrderMan();
        PriorityQueue<State> PQ = new PriorityQueue<>(order);
        Vector<State> visited = new Vector<>();
        PQ.add(source);
        visited.addElement(source);
        //System.out.println(source.toString());

        long t = System.currentTimeMillis() + 180000;
        while (true) {
            if (System.currentTimeMillis() > t) {
                //System.out.println("Time limit crossed");
                return null;
            }

            State s = PQ.poll();
            //PQ.remove(s);
            //System.out.println(s.toString());
            //System.out.println(s.totCostManhattan + "\n");
            //System.out.println(inv(s.board));
            Vector<State> vec = s.getChildNodes();
            for (int i = 0; i < vec.size(); i++) {
                State st = vec.elementAt(i);
                for (int j = 0; j < visited.size(); j++) {
                    if (!visited.contains(st)) {
                        State newState = new State(st.board, s);
                        if (st.IsGoalNode() == true) {
                            // System.out.println(st.toString());
                            //System.out.println(st.totCostManhattan + "\n");
                            //System.out.println(inv(st.board));
                            return newState;
                            //break;

                        }
                        PQ.add(newState);
                        visited.addElement(st);

                    }
                }
            }
        }
    }

    public State AStarSearchLinear(State source) {
        PriorityOrderLinear order = new PriorityOrderLinear();
        PriorityQueue<State> PQ = new PriorityQueue<>(order);
        Vector<State> visited = new Vector<>();
        PQ.add(source);
        visited.addElement(source);
        //System.out.println(source.toString());
        long t = System.currentTimeMillis() + 180000;
        while (true) {
            if (System.currentTimeMillis() > t) {
                //System.out.println("Time limit crossed");
                return null;
            }
            State s = PQ.poll();

            Vector<State> vec = s.getChildNodes();
            for (int i = 0; i < vec.size(); i++) {
                State st = vec.elementAt(i);
                for (int j = 0; j < visited.size(); j++) {
                    if (!visited.contains(st)) {
                        State newState = new State(st.board, s);
                        if (st.IsGoalNode() == true) {
                            // System.out.println(st.toString());
                            //System.out.println(st.totCostManhattan + "\n");
                            //System.out.println(inv(st.board));
                            return newState;
                            //break;

                        }
                        PQ.add(newState);
                        visited.addElement(st);

                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        // TODO code application logic here
        int n;
        Scanner sc = new Scanner(System.in);
        n = sc.nextInt();
        Scanner s = new Scanner(System.in);
        int[][] initial = new int[n][n];//= {{4,3,2,1},{9,5,6,8},{13,10,7,11},{14,15,0,12}};
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                initial[i][j] = s.nextInt();
            }
        }
        NPuzzleProblem p = new NPuzzleProblem();
        // public boolean b = p.IsSolvable(initial);
        /* if(p.IsSolvable(initial)){
           System.out.println("true");
       }
       else System.out.println("false");*/
        if (p.IsSolvable(initial)) {
            State s1 = new State(initial);
            //State d = p.AStarSearchManhattan(s);
            // State d = p.AStarSearchHamming(s);
            System.out.println("Solution using Linear Conflict");
            long t1 = System.currentTimeMillis();
            State d = p.AStarSearchLinear(s1);
            if (d == null) {
                System.out.println("Cannot solve with in time limit");
            } else {
                d.printPath();
            }
            long t2 = System.currentTimeMillis();
            System.out.println("time needed " + (t2 - t1) + " milliseconds");
            System.out.println();
            System.out.println("Solution using Manhattan heuristics");
            long t3 = System.currentTimeMillis();
            State d1 = p.AStarSearchManhattan(s1);
            if (d1 == null) {
                System.out.println("Cannot solve with in time limit");
            } else {
                d1.printPath();
            }
            long t4 = System.currentTimeMillis();
            System.out.println("time needed " + (t4 - t3) + " milliseconds");
            System.out.println();
            System.out.println("Solution using Hamming heuristics");
            long t5 = System.currentTimeMillis();
            State d2 = p.AStarSearchManhattan(s1);
            if (d2 == null) {
                System.out.println("Cannot solve with in time limit");
            } else {
                d2.printPath();
            }
            long t6 = System.currentTimeMillis();
            System.out.println("time needed " + (t6 - t5) + " milliseconds");

        } else {
            System.out.println("Cannot Solve");
        }
        //s.
    }

}
