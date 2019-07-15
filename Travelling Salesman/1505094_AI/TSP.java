/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tsp;

import java.util.ArrayList;
import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;
import java.util.Scanner;
import java.util.Stack;
import java.util.Vector;

/**
 *
 * @author User
 */
public class TSP {

    /**
     * @param args the command line arguments
     */
    public double distance(Nodes a, Nodes b) {
        double x1 = a.x - b.x;
        double y1 = a.y - b.y;
        return (double) Math.sqrt((x1 * x1) + (y1 * y1));
    }

    public int NearestUnvisited(int v, double[][] mat, boolean[] visited) {
        double min = 100000;
        int idx = 0;
        int n = visited.length;
        for (int i = 1; i < n; i++) {
            if (visited[i] == true) {
                continue;
            }
            if (mat[v][i] < min) {
                min = mat[v][i];
                idx = i;
            }

        }

        return idx;
    }

    public int MostSavingUnvisited(int v, double[][] mat, boolean[] visited) {
        double max = -100000;
        int idx = 0;
        int n = visited.length;
        for (int i = 1; i < n; i++) {
            if (visited[i] == true) {
                continue;
            }
            if (mat[v][i] > max) {
                max = mat[v][i];
                idx = i;
            }

        }

        return idx;
    }

    public Vector NearestNeighbourAlgo(double[][] matrix, int source) {
        Stack<Integer> st = new Stack<>();
        Vector<Integer> vec = new Vector<>();
        st.push(source);
        vec.add(source);
        int n = matrix.length;
        boolean[] visited = new boolean[n];
        for (int i = 1; i < n; i++) {
            visited[i] = false;
        }
        visited[source] = true;
        double min = 10000;
        int nxt = 0;
        boolean flag = false;
        while (!st.isEmpty()) {
            int a = st.peek();
            for (int i = 1; i < n; i++) {
                if (min > matrix[a][i] && visited[i] == false) {
                    min = matrix[a][i];
                    nxt = i;
                    flag = true;
                }
                //st.push(nxt);
            }
            if (flag == true) {
                st.push(nxt);
                vec.add(nxt);
                visited[nxt] = true;
                min = 10000;
                flag = false;
                continue;
            }
            st.pop();

        }
        vec.add(source);
        return vec;
    }

    public int bestNeighbour(int v, double[][] mat, boolean[] visited) {
        Vector<Integer> vec = new Vector<>();
        int idx = 0;
        int cnt = 0;
        int randpick = 0;
        int gg = 0;
        for (int i = 1; i < visited.length; i++) {
            if (visited[i] == false) {
                cnt++;
            }
        }
        if (cnt >= 5) {
            for (int i = 0; i < 5; i++) {
                idx = NearestUnvisited(v, mat, visited);
                visited[idx] = true;
                vec.add(idx);
            }
            Random rand = new Random();
            //int rr = rand.nextInt(vec.size()+1);
            //System.out.println(rr);
            for (int i = 0; i < vec.size(); i++) {
                visited[vec.elementAt(i)] = false;
            }
            randpick = vec.elementAt(rand.nextInt(vec.size()));

            gg = randpick;
        } else if (cnt < 5) {
            //for (int i = 0; i < cnt; i++) {
            idx = NearestUnvisited(v, mat, visited);
            //visited[idx] = true;

            //}
            gg = idx;
        }
        return gg;
    }

    public int bestSavings(int v, double[][] mat, boolean[] visited) {
        Vector<Integer> vec = new Vector<>();
        //Vector<Integer> vvv = new Vector<>();
        int idx = 0;
        int cnt = 0;
        int randpick = 0;
        //int r = 0;
        int gg = 0;
        for (int i = 1; i < visited.length; i++) {
            if (visited[i] == false) {
                cnt++;
            }
        }
        if (cnt >= 5) {
            for (int i = 0; i < 5; i++) {
                idx = MostSavingUnvisited(v, mat, visited);
                visited[idx] = true;
                vec.add(idx);
            }
            Random rand = new Random();
            //int rr = rand.nextInt(vec.size()+1);
            //System.out.println(rr);
            for (int i = 0; i < vec.size(); i++) {
                visited[vec.elementAt(i)] = false;
            }
            randpick = vec.elementAt(rand.nextInt(vec.size()));

            gg = randpick;
        } else if (cnt < 5) {
            //for (int i = 0; i < cnt; i++) {
            idx = MostSavingUnvisited(v, mat, visited);
            //visited[idx] = true;

            //}
            //gg = r;
            gg = idx;
        }
        //System.out.println(gg);
        return gg;
    }

    public Vector NearestNeighbourRand(double[][] matrix, int source) {
        Stack<Integer> st = new Stack<>();
        Vector<Integer> vec = new Vector<>();
        st.push(source);
        vec.add(source);
        int n = matrix.length;
        boolean[] visited = new boolean[n];
        for (int i = 1; i < n; i++) {
            visited[i] = false;
        }
        visited[source] = true;
        int nxt = 0;
        //boolean flag = false;
        while (vec.size() < n - 1) {
            int a = st.peek();
            nxt = bestNeighbour(a, matrix, visited);

            st.push(nxt);
            vec.add(nxt);
            visited[nxt] = true;

            //st.pop();
        }
        vec.add(source);
        return vec;
    }

    public Vector SavingsHeuristics(double[][] matrix, int source, int centre) {
        Deque<Integer> dq = new LinkedList<>();
        Vector<Integer> vec = new Vector<>();
        int n = matrix.length;
        boolean[] visited = new boolean[n];
        for (int i = 1; i < n; i++) {
            visited[i] = false;
        }
        //dq.add(centre);
        visited[centre] = true;
        double[][] savings = new double[n][n];
        for (int i = 1; i < n; i++) {
            for (int j = 1; j < n; j++) {
                if (i != j && i != centre && j != centre) {
                    savings[i][j] = matrix[i][centre] + matrix[j][centre] - matrix[i][j];
                } else {
                    savings[i][j] = -10000;
                }

            }
        }
        double maxi = -1;
        int a = 0;
        int b = 0;
        for (int i = 1; i < n; i++) {
            for (int j = 1; j < n; j++) {
                if (maxi < savings[i][j]) {
                    maxi = savings[i][j];
                    a = i;
                    b = j;
                }
            }
        }
        dq.addFirst(a);
        visited[a] = true;
        dq.addLast(b);
        visited[b] = true;

        while (dq.size() < n - 2) {
            double max1 = -1000;
            double max2 = -1000;
            int head = dq.getFirst();
            int tail = dq.getLast();
            int aa = 0;
            int bb = 0;
            for (int i = 1; i < n; i++) {
                if (max1 < savings[head][i] && visited[i] == false) {
                    max1 = savings[head][i];
                    aa = i;
                }

                if (max2 < savings[tail][i] && visited[i] == false) {
                    max2 = savings[tail][i];
                    bb = i;
                }
            }
            if (max1 > max2) {
                dq.addFirst(aa);
                visited[aa] = true;
            } else {
                dq.addLast(bb);
                visited[bb] = true;
            }
        }
        dq.addFirst(centre);
        System.out.println(dq.size());
        Iterator iterator = dq.iterator();
        while (iterator.hasNext()) {
            vec.add((Integer) iterator.next());
        }
        vec.add(centre);
        return vec;
    }

    public Vector SavingsHeuristicsRand(double[][] matrix, int source, int centre) {
        Deque<Integer> dq = new LinkedList<>();
        Vector<Integer> vec = new Vector<>();
        int n = matrix.length;
        boolean[] visited = new boolean[n];
        for (int i = 1; i < n; i++) {
            visited[i] = false;
        }
        //dq.add(centre);
        visited[centre] = true;
        double[][] savings = new double[n][n];
        for (int i = 1; i < n; i++) {
            for (int j = 1; j < n; j++) {
                if (i != j && i != centre && j != centre) {
                    savings[i][j] = matrix[i][centre] + matrix[j][centre] - matrix[i][j];
                } else {
                    savings[i][j] = -10000;
                }

            }
        }
        double maxi = -1;
        int a = 0;
        int b = 0;
        for (int i = 1; i < n; i++) {
            for (int j = 1; j < n; j++) {
                if (maxi < savings[i][j]) {
                    maxi = savings[i][j];
                    a = i;
                    b = j;
                }
            }
        }
        dq.add(a);
        visited[a] = true;
        dq.addLast(b);
        visited[b] = true;
        /* int head = dq.getFirst();
        int tail = dq.getLast();
        System.out.println(head);
        System.out.println(tail);
        int idx = bestSavings(a,savings,visited);
        int yy = bestSavings(b,savings,visited);*/

        while (dq.size() < n - 2) {
            double max1 = -1000;
            double max2 = -1000;
            int head = dq.getFirst();
            int tail = dq.getLast();
            int aa = bestSavings(head, savings, visited);
            int bb = bestSavings(tail, savings, visited);
            //for (int i = 1; i < n; i++) {
            if (max1 < savings[head][aa] && visited[aa] == false) {
                max1 = savings[head][aa];
                //aa = i;
            }

            if (max2 < savings[tail][bb] && visited[bb] == false) {
                max2 = savings[tail][bb];
                //bb = i;
            }
            //}
            if (max1 > max2) {
                dq.addFirst(aa);
                visited[aa] = true;
            } else {
                dq.addLast(bb);
                visited[bb] = true;
            }
        }
        dq.addFirst(centre);
        System.out.println(dq.size());
        Iterator iterator = dq.iterator();
        while (iterator.hasNext()) {
            vec.add((Integer) iterator.next());
        }
        vec.add(centre);

        return vec;
    }
    
    public Vector TwoOptSwap(Vector<Integer> vec, int a, int b) {
        Vector<Integer> vect = new Vector<>();
        for (int i = 0; i < a; i++) {
            vect.add(vec.elementAt(i));
        }
        for (int i = b; i >= a; i--) {
            vect.add(vec.elementAt(i));
        }
        for (int i = b + 1; i < vec.size(); i++) {
            vect.add(vec.elementAt(i));
        }

        return vect;
    }

    public Vector TwoOptHeuristicAlgoNNH(double[][] matrix, Vector<Integer> vec) {
        //Vector<Integer> vec = new Vector<>();
        Vector<Integer> vect = new Vector<>();
        //vec = NearestNeighbourAlgo(matrix, source);

        while (true) {
            double bestCost = calcCost(vec, matrix);
            boolean flag = false;
            for (int i = 1; i < vec.size() - 2; i++) {
                for (int j = i + 1; j < vec.size() - 1; j++) {
                    vect = TwoOptSwap(vec, i, j);
                    double newCost = calcCost(vect, matrix);
                    if (newCost < bestCost) {
                        vec = vect;
                        flag = true;
                        break;
                    }
                }
                if (flag == true) {
                    break;
                }
            }
            if (flag == false) {
                break;
            }
        }

        return vec;
    }
    
    
    double calcCost(Vector<Integer> v, double[][] mat) {
        double cost = (double) 0.0;
        for (int i = 0; i < v.size() - 1; i++) {
            cost = cost + mat[v.elementAt(i)][v.elementAt(i + 1)];
        }
        return cost;
    }

    public void printVector(Vector<Integer> v, double[][] mat) {
        for (int i = 0; i < v.size(); i++) {
            System.out.print(v.elementAt(i) + " ");
        }
        System.out.println();
        System.out.println("Cost is " + calcCost(v, mat));

    }

    public Vector BestTour(Tour[] t) {
        Vector<Integer> vec = new Vector<>();
        int idx = 0;
        double min = 10000000;
        for (int i = 0; i < t.length; i++) {
            if (min > t[i].costOfTour) {
                min = t[i].costOfTour;
                vec = t[i].route;
                idx = t[i].s;
            }
        }
        System.out.print("Best Route : ");
        for (int i = 0; i < vec.size(); i++) {
            System.out.print(vec.elementAt(i) + " ");
        }
        System.out.println();
        System.out.println("Cost : " + min);
        System.out.println("Source : " + idx);
        return vec;
    }

    public int WorstTour(Tour[] t) {
        Vector<Integer> vec = new Vector<>();
        int idx = 0;
        double max = 0;
        for (int i = 0; i < t.length; i++) {
            if (max < t[i].costOfTour) {
                max = t[i].costOfTour;
                vec = t[i].route;
                idx = t[i].s;
            }
        }
        System.out.print("Worst Route : ");
        for (int i = 0; i < vec.size(); i++) {
            System.out.print(vec.elementAt(i) + " ");
        }
        System.out.println();
        System.out.println("Cost : " + max);
        System.out.println("Source : " + idx);
        return idx;
    }

    public void AverageCost(Tour[] t) {
        double cost = 0;
        double avg = 0;
        for (int i = 0; i < t.length; i++) {
            cost = cost + t[i].costOfTour;
        }
        avg = cost / t.length;
        System.out.println("Average Cost : " + avg);
    }

    void bubbleSort(Tour[] arr) {
        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (arr[j].costOfTour > arr[j + 1].costOfTour) {
                    Tour temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }
    }

    public static void main(String[] args) {
        // TODO code application logic here
        System.out.println("Enter the number of Nodes : ");
        Scanner sc = new Scanner(System.in);
        int numberOfNodes = sc.nextInt();
        Nodes[] cp = new Nodes[numberOfNodes + 1];
        double posx, posy;
        int xx;
        //ArrayList<Nodes> nn = new ArrayList<>();
        for (int i = 1; i <= numberOfNodes; i++) {
            xx = sc.nextInt();
            posx = sc.nextDouble();
            posy = sc.nextDouble();
            cp[i] = new Nodes();
            cp[i].city = xx;
            cp[i].x = posx;
            cp[i].y = posy;
        }
        /*for(int i = 1; i <= numberOfNodes; i++){
            System.out.println(cp[i].city+" "+cp[i].x + " "+ cp[i].y);
        }*/
        double[][] mat = new double[numberOfNodes + 1][numberOfNodes + 1];
        TSP tsp = new TSP();
        for (int i = 1; i < numberOfNodes + 1; i++) {
            for (int j = 1; j < numberOfNodes + 1; j++) {
                if (i == j) {
                    mat[i][j] = 1000;
                } else {
                    mat[i][j] = tsp.distance(cp[i], cp[j]);
                }
            }
        }
        //float a =  TSP.distance(l1, l2);
        for (int i = 1; i < numberOfNodes + 1; i++) {
            for (int j = 1; j < numberOfNodes + 1; j++) {
                System.out.print(mat[i][j] + "\t");
            }
            System.out.println();
        }
        Random rand = new Random();
        Vector<Integer> NNH = new Vector<>();
        System.out.println("*****Nearest Neighbour******");
        Tour[] t = new Tour[5];
        Vector<Integer>[] anArray = (Vector<Integer>[]) new Vector[5];
        for (int i = 0; i < anArray.length; i++) {
            anArray[i] = new Vector<Integer>();
        }
        for (int i = 0; i < anArray.length; i++) {
            anArray[i] = tsp.NearestNeighbourAlgo(mat, rand.nextInt(numberOfNodes) + 1);
            t[i] = new Tour();
            t[i].route = anArray[i];
            t[i].costOfTour = tsp.calcCost(t[i].route, mat);
            t[i].s = t[i].route.elementAt(0);
            tsp.printVector(anArray[i], mat);
        }
        Vector<Integer> tt1 = tsp.BestTour(t);
        int ttw = tsp.WorstTour(t);
        tsp.AverageCost(t);
        //NNH.add(tt1);

        System.out.println("*****Nearest Neighbour Random******");
        Vector<Integer>[] vec = (Vector<Integer>[]) new Vector[10];
        Tour[] t1 = new Tour[10];
        for (int i = 0; i < vec.length; i++) {
            vec[i] = new Vector<Integer>();
        }
        for (int i = 0; i < vec.length; i++) {
            vec[i] = tsp.NearestNeighbourRand(mat, 10);
            t1[i] = new Tour();
            t1[i].route = vec[i];
            t1[i].costOfTour = tsp.calcCost(vec[i], mat);
            t1[i].s = t1[i].route.elementAt(0);
            tsp.printVector(vec[i], mat);
        }
        tsp.BestTour(t1);
        tsp.WorstTour(t1);
        tsp.AverageCost(t1);
        tsp.bubbleSort(t1);
        System.out.println("Two-Opt NNH");
        Vector<Integer>[] task_thr_vec = (Vector<Integer>[]) new Vector[4];
        Tour[] task3 = new Tour[4];
        for (int i = 0; i < task_thr_vec.length; i++) {
            task_thr_vec[i] = new Vector<Integer>();
            task3[i] = new Tour();
        }
        for (int i = 0; i < 3; i++) {
            task_thr_vec[i] = tsp.TwoOptHeuristicAlgoNNH(mat, t1[i].route);
            
            task3[i].route = task_thr_vec[i];
            task3[i].costOfTour = tsp.calcCost(task_thr_vec[i], mat);
            task3[i].s = task3[i].route.elementAt(0);
            tsp.printVector(task_thr_vec[i], mat);
        }
        task3[3].route = tsp.TwoOptHeuristicAlgoNNH(mat, tt1);
        task3[3].costOfTour = tsp.calcCost(task3[3].route, mat);
        task3[3].s = task3[3].route.elementAt(0);
        tsp.printVector(task3[3].route, mat);
        tsp.BestTour(task3);
        tsp.WorstTour(task3);
        tsp.AverageCost(task3);
        
        

        System.out.println("*****Savings Heuristics******");
        Tour[] t2 = new Tour[5];
        Vector<Integer>[] vecc = (Vector<Integer>[]) new Vector[5];
        for (int i = 0; i < vecc.length; i++) {
            vecc[i] = new Vector<Integer>();
        }
        for (int i = 0; i < vecc.length; i++) {
            vecc[i] = tsp.SavingsHeuristics(mat, 6, rand.nextInt(numberOfNodes) + 1);
            t2[i] = new Tour();
            t2[i].route = vecc[i];
            t2[i].costOfTour = tsp.calcCost(vecc[i], mat);
            t2[i].s = t2[i].route.elementAt(0);
            tsp.printVector(vecc[i], mat);
        }
        Vector<Integer> tt2 = tsp.BestTour(t2);
        tsp.WorstTour(t2);
        tsp.AverageCost(t2);
        System.out.println("*****Savings Random Heuristics******");
        Tour[] t3 = new Tour[10];
        Vector<Integer>[] hh = (Vector<Integer>[]) new Vector[10];
        for (int i = 0; i < hh.length; i++) {
            hh[i] = new Vector<Integer>();
        }
        for (int i = 0; i < hh.length; i++) {
            hh[i] = tsp.SavingsHeuristicsRand(mat, 6, 10);
            t3[i] = new Tour();
            t3[i].route = hh[i];
            t3[i].costOfTour = tsp.calcCost(hh[i], mat);
            t3[i].s = t3[i].route.elementAt(0);
            tsp.printVector(hh[i], mat);
        }
        tsp.BestTour(t3);
        int tttw = tsp.WorstTour(t3);
        tsp.AverageCost(t3);
        tsp.bubbleSort(t3);
        
        Vector<Integer>[] task_fr_vec = (Vector<Integer>[]) new Vector[4];
        Tour[] task4 = new Tour[4];
        for (int i = 0; i < task_fr_vec.length; i++) {
            task_fr_vec[i] = new Vector<Integer>();
            task4[i] = new Tour();
        }
        for (int i = 0; i < 3; i++) {
            task_fr_vec[i] = tsp.TwoOptHeuristicAlgoNNH(mat, t3[i].route);
            
            task4[i].route = task_fr_vec[i];
            task4[i].costOfTour = tsp.calcCost(task_fr_vec[i], mat);
            task4[i].s = task_fr_vec[i].elementAt(0);
            tsp.printVector(task4[i].route, mat);
        }
        task4[3].route = tsp.TwoOptHeuristicAlgoNNH(mat, tt2);
        task4[3].costOfTour = tsp.calcCost(task4[3].route, mat);
        task4[3].s = task4[3].route.elementAt(0);
        tsp.printVector(task4[3].route, mat);
        tsp.BestTour(task4);
        tsp.WorstTour(task4);
        tsp.AverageCost(task4);

    }

}
