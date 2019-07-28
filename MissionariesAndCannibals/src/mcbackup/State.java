/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mcbackup;

import java.util.Vector;

/**
 *
 * @author User
 */
public class State {

    int mlb, clb;
    int mrb, crb;
    int boat;
    int boatCapacity;
    State ParentState;
    int path_cost;

    public State(int mlb, int clb, int boat, int mrb, int crb, int boatCapacity) {
        this.mlb = mlb;
        this.mrb = mrb;
        this.clb = clb;
        this.crb = crb;
        this.boat = boat;
        this.boatCapacity = boatCapacity;
        ParentState = null;
        path_cost = 0;
    }
    
    public State(int mlb, int clb, int boat, int mrb, int crb, int boatCapacity, State ParentState) {
        this.mlb = mlb;
        this.mrb = mrb;
        this.clb = clb;
        this.crb = crb;
        this.boat = boat;
        this.boatCapacity = boatCapacity;
        this.ParentState = ParentState;
        path_cost = ParentState.path_cost + 1;
    }

    public boolean IsGoalNode() {
        if (mlb == 0 && clb == 0 && boat == 0) {
            return true;
        } else {
            return false;
        }
    }

    public String toString() {
        return "(" + mlb + " " + clb + " " + boat + " " + mrb + " " + crb + ")";
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof State)) {
            return false;
        }
        State s = (State) obj;
        return (s.mlb == mlb && s.clb == clb && s.boat == boat
                && s.crb == crb && s.mrb == mrb);
    }

    void AddStates(Vector<State> v, State s) {
        //State s = p.state;
        if (s.mrb >= 0 && s.mlb >= 0 && s.crb >= 0 && s.clb >= 0 && (s.mlb == 0 || s.mlb >= s.clb) && (s.mrb == 0 || s.mrb >= s.crb)) {
            v.addElement(s);
        }

    }

    public Vector getChildStates() {
        Vector<State> v = new Vector<>();
        /* if (boat == 1) {
            AddStates(v, new State(mlb - 1, clb - 1, 0, mrb + 1, crb + 1,boatCapacity));
            AddStates(v, new State(mlb, clb - 2, 0, mrb, crb + 2, boatCapacity));
            AddStates(v, new State(mlb - 2, clb, 0, mrb + 2, crb, boatCapacity));
            AddStates(v, new State(mlb - 1, clb, 0, mrb + 1, crb, boatCapacity));
            AddStates(v, new State(mlb, clb - 1, 0, mrb, crb + 1, boatCapacity));
        } else {
            AddStates(v, new State(mlb + 1, clb + 1, 1, mrb - 1, crb - 1, boatCapacity));
            AddStates(v, new State(mlb, clb + 2, 1, mrb, crb - 2, boatCapacity));
            AddStates(v, new State(mlb + 2, clb, 1, mrb - 2, crb, boatCapacity));
            AddStates(v, new State(mlb + 1, clb, 1, mrb - 1, crb, boatCapacity));
            AddStates(v, new State(mlb, clb + 1, 1, mrb, crb - 1, boatCapacity));
        }*/
        for (int i = 0; i < boatCapacity + 1; i++) {
            for (int j = 0; j <= boatCapacity - i; j++) {
                if (i != 0 || j != 0) {
                    if (boat == 1) {
                        AddStates(v, new State(mlb - i, clb - j, 0, mrb + i, crb + j, boatCapacity));
                    } else {
                        AddStates(v, new State(mlb + i, clb + j, 1, mrb - i, crb - j, boatCapacity));
                    }
                }
            }
        }

        return v;
    }
    
    public void printPath() {
         if (ParentState != null)
            ParentState.printPath();
         System.out.println("Step " + path_cost +  " : " + toString());
      }

}

