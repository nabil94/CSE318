/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mcbackup;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;
import java.util.Vector;

/**
 *
 * @author User
 */
public class MCProblem {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        int missionaries, cannibals, capacity;
        Scanner m = new Scanner(System.in);
        missionaries = m.nextInt();
        Scanner c = new Scanner(System.in);
        cannibals = c.nextInt();
        Scanner b = new Scanner(System.in);
        capacity = b.nextInt();
        if ((cannibals > missionaries) || capacity == 0) {
            System.out.println("cannot go to other bank");
        } else {
            State s = new State(missionaries, cannibals, 1, 0, 0, capacity);
            System.out.println("BFS Solve");
            long t1 = System.currentTimeMillis();
            State n = BFSSolution(s);
            if (n == null) {
                System.out.println("Not Solvable");
            } else {
                n.printPath();
            }
            long t2 = System.currentTimeMillis();
            //if((t2 - t1) < 30000){
            System.out.println("time needed " + (t2 - t1) + " milliseconds");
            //}
            
            System.out.println();
            System.out.println("DFS Solve");
            long t3 = System.currentTimeMillis();
            //DFSSolution(s);
            State nn = DFSSolution(s);
            if (nn == null) {
                System.out.println("Not Solvable");
            } else {
                nn.printPath();
            }
            long t4 = System.currentTimeMillis();
            //if((t4 - t3) < 30000){
            System.out.println("time needed " + (t4 - t3) + " milliseconds");
            //}
            
        }

    }

    public static State BFSSolution(State source) {
        Queue<State> q = new LinkedList<>();
        Vector<State> visited = new Vector<>();
        q.add(source);
        //Pair pr = new Pair(source_State,"Initial State");
        visited.addElement(source);
        //System.out.println(q.peek().state.toString()+" "+q.peek().edge);
        long t = System.currentTimeMillis() + 120000;
        while (true) {
            if (q.isEmpty() || q.size() > 99999) {
                return null;
                //break;
            }
            
            if(System.currentTimeMillis() > t){
                System.out.println("Time limit crossed");
                return null;
            }
            State n = q.peek();
            //System.out.println(n.state.toString());
            q.remove();
            Vector<State> vec = n.getChildStates();
            for (int i = 0; i < vec.size(); i++) {
                State p = vec.elementAt(i);
                //String s = vec.elementAt(i).edge;
                for (int j = 0; j < visited.size(); j++) {
                    if (!visited.contains(p)) {
                        State newState = new State(p.mlb,p.clb,p.boat,p.mrb,p.crb,p.boatCapacity,n);
                        if (p.IsGoalNode()) {
                            //System.out.println(q.size());
                            return newState;
                            //break;
                        }
                        q.add(newState);
                        visited.addElement(p);

                    }
                }
            }
        }

    }

    public static State DFSSolution(State source) {
        Stack<State> st = new Stack<>();
        st.push(source);
        Vector<State> visited = new Vector<>();
        visited.addElement(source);
        long t = System.currentTimeMillis() + 120000;
        while (true) {
            if (st.empty() || st.size() > 99999) {
                return null;
                //System.out.println("No solution");
                //break;
            }
            if(System.currentTimeMillis() > t){
                System.out.println("Time limit crossed");
                return null;
            }
            State n = st.peek();
            //System.out.println(n.state.toString());
            st.pop();
            Vector<State> vec = n.getChildStates();
            for (int i = 0; i < vec.size(); i++) {
                State p = vec.elementAt(i);
                //String s = vec.elementAt(i).edge;
                for (int j = 0; j < visited.size(); j++) {
                    if (!visited.contains(p)) {
                        State newState = new State(p.mlb,p.clb,p.boat,p.mrb,p.crb,p.boatCapacity,n);
                        if (p.IsGoalNode()) {
                           // System.out.println(st.size());
                            return newState;
                        }
                        st.push(newState);
                        visited.addElement(p);

                    }
                }
            }
        }

    }
    
}
