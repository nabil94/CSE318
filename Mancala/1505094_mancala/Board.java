/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mancala;

import java.util.HashMap;
import java.util.Vector;

/**
 *
 * @author User
 */
public class Board {
    public Vector<Storage> pots;
    public HashMap<Storage,Storage> map;
    public boolean player1;
    public boolean player2;
    public int PLAY1Score;
    public int PLAY2Score;
    public int addMovesPlayer1 = 0;
    public int addMovesPlayer2 = 0;
    public int playerID;
    
    public Board(){
        pots = new Vector<Storage>();
        for(int i = 0; i < 6; i++){
            pots.add(new Storage(4));
            pots.elementAt(i).isPlayer1 = true;
        }
        pots.add(new Storage(0));
        pots.elementAt(6).isMancala = true;
        pots.elementAt(6).isPlayer1 = true;
        PLAY1Score = pots.elementAt(6).numOfStones;
        for(int i = 7; i < 13; i++){
            pots.add(new Storage(4));
            pots.elementAt(i).isPlayer2 = true;
        }
        pots.add(new Storage(0));
        pots.elementAt(13).isMancala = true;
        PLAY2Score = pots.elementAt(13).numOfStones;
        map = new HashMap<Storage,Storage>();
        for(int i = 0; i < 6; i++ ){
            map.put(pots.elementAt(i), pots.elementAt(12 - i));
        }
        for(int i = 0; i < 6; i++ ){
            map.put(pots.elementAt(12 - i), pots.elementAt(i));
        }
        
        player1 = true;
        player2 = false;
    }
    
    public boolean isGameOver(){
        
        if(isPlayer1SideEmpty() || isPlayer2SideEmpty()){
            gameOver();
            return true;
        }
        else return false;
    }
    
    public int getScore(int id){
        int score=0;
        if(id == 0){
            score = pots.elementAt(6).numOfStones;
        }
        else if(id == 1){
            score = pots.elementAt(13).numOfStones;
        }
        return score;
    }
    
    public int totStoneOnSide(int id){
        if(id == 0){
            return getTotalStonesAtPlayer1Side();
        }
        else return getTotalStonesAtPlayer2Side();
    }
    
    public int additionalMovesEarned(int id){
        if(id == 0){
            return addMovesPlayer1;
        }
        else return addMovesPlayer2;
    }
    
    public void gameOver(){
        if(isPlayer1SideEmpty() == true){
            pots.elementAt(13).numOfStones = pots.elementAt(13).numOfStones + getTotalStonesAtPlayer2Side();
            PLAY2Score = pots.elementAt(13).numOfStones;
            for(int i = 7; i < 13; i++){
                pots.elementAt(i).clearPot();
            }
        }
        else if(isPlayer2SideEmpty() == true){
            pots.elementAt(6).numOfStones = pots.elementAt(6).numOfStones + getTotalStonesAtPlayer1Side();
            PLAY1Score = pots.elementAt(6).numOfStones;
            for(int i = 0; i < 6; i++){
                pots.elementAt(i).clearPot();
            }
        }
        int tt = 48 - (pots.elementAt(13).numOfStones + pots.elementAt(6).numOfStones);
        if(pots.elementAt(13).numOfStones > pots.elementAt(6).numOfStones){
            pots.elementAt(6).numOfStones = pots.elementAt(6).numOfStones + tt;
            PLAY1Score = pots.elementAt(6).numOfStones;
        }
        else{
            pots.elementAt(13).numOfStones = pots.elementAt(13).numOfStones + tt;
            PLAY2Score = pots.elementAt(13).numOfStones;
        }
        
    }
    
    public boolean isPlayer1SideEmpty(){
        int c = 0;
        for(int i = 0; i < 6; i++){
            if(pots.elementAt(i).numOfStones == 0){
                c++;
            }
        }
        if(c == 6) return true;
        else return false;
    }
    
    public boolean isPlayer2SideEmpty(){
        int c = 0;
        for(int i = 7; i < 13; i++){
            if(pots.elementAt(i).numOfStones == 0){
                c++;
            }
        }
        if(c == 6) return true;
        else return false;
    }
    
    public int getTotalStonesAtPlayer1Side(){
        int tot = 0;
        for(int i = 0; i < 6; i++){
            tot = tot + pots.elementAt(i).numOfStones;
        }
        return tot;
    }
    
    public int getTotalStonesAtPlayer2Side(){
        int tot = 0;
        for(int i = 7; i < 13; i++){
            tot = tot + pots.elementAt(i).numOfStones;
        }
        return tot;
    }
    
    public void togglePlayer(){
        if(player1 == true && player2 == false){
            player1 = false;
            player2 = true;
            playerID = 1;
        }
        else if(player1 == false && player2 == true){
            player2 = false;
            player1 = true;
            playerID = 0;
        }
    }
    
    public int makeAMove(int currentStorage,int player){
        if(player == 1){
            currentStorage = currentStorage + 7;
        }
        int hh = currentStorage + pots.elementAt(currentStorage).numOfStones;
        for(int i = currentStorage+1; i <= hh; i++){
            int k = i%14;
            if(player1 == true && k == 13){
                //continue;
                //int uu = hh - 13;
                pots.elementAt((hh+1)%14).addStones();
                continue;
            }
            else if(player2 == true && i%14 == 6){
                //int uu = hh - 6;
                pots.elementAt((hh+1)%14).addStones();
                continue;
            }
            pots.elementAt(k).addStones();
            
        }
        pots.elementAt(currentStorage).clearPot();
        int m = hh%14;
        if(pots.elementAt(m).numOfStones == 1){
                if(player1 == true && pots.elementAt(m).isMancala == false && pots.elementAt(m).isPlayer1 == true && player == 0){
                    pots.elementAt(6).numOfStones = pots.elementAt(6).numOfStones + map.get(pots.elementAt(m)).numOfStones;
                    map.get(pots.elementAt(m)).clearPot();
                }else if(player2 == true && pots.elementAt(m).isMancala == false && pots.elementAt(m).isPlayer2 == true && player == 1){
                    pots.elementAt(13).numOfStones = pots.elementAt(13).numOfStones + map.get(pots.elementAt(m)).numOfStones;
                    map.get(pots.elementAt(m)).clearPot();
                }
            }
        if(m == 6 && player1 == true && player == 0){
            addMovesPlayer1++;
        }
        else if( m == 13 && player2 == true && player == 1){
             addMovesPlayer2++;
        }
        else togglePlayer();
        return playerID;
    }
    
    
    
    void printBoard(){
        System.out.print("  ");
        for(int i = 12; i >=7; i--){
            System.out.print(pots.elementAt(i).numOfStones + " ");
        }
        System.out.println(); 
        System.out.println(pots.elementAt(13).numOfStones+ "\t      " +pots.elementAt(6).numOfStones);
        System.out.print("  ");
        for(int i = 0; i <=5; i++){
            System.out.print(pots.elementAt(i).numOfStones + " ");
        }
        System.out.println(); 
    }
    
    
}
