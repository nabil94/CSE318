/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mancala;

import java.util.Vector;

/**
 *
 * @author User
 */
public class Storage {

    //public Vector<Integer> pot;
    public int numOfStones;
    public boolean isMancala;
    public boolean isPlayer1;
    public boolean isPlayer2;
    

    public Storage(int numOfStones){
        //this.pot = new Vector<Integer>();
        this.numOfStones = numOfStones;
        //pot.add(numOfStones);
        this.isMancala = false;
        this.isPlayer1 = false;
        this.isPlayer2 = false;
    }
    
    public void isStorageMancala() {
        isMancala=true;
    }
    
    public int getNumOfStones(){
        return numOfStones;
    }
    
    public void clearPot(){
        numOfStones = 0;
    }
    
    public void addStones(){
        numOfStones++;
    }
    
     public void removeStones(){
        numOfStones--;
    }
    
    
    
}
