/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package npuzzleproblem;

import java.util.Comparator;

/**
 *
 * @author User
 */
public class PriorityOrderMan implements Comparator<State> {

    @Override
    public int compare(State o1, State o2) {
        // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        int a = o1.totCostManhattan;
        int b = o2.totCostManhattan;
        if (a > b) {
            return 1;
        } else if (a < b) {
            return -1;
        } else {
            return 0;
        }

    }

}
