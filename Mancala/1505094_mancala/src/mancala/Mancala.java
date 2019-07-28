/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mancala;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import javafx.util.Pair;

/**
 *
 * @author User
 */
public class Mancala {

    public int Heuristic1(Board board, int id) {
        return board.getScore(id) - board.getScore(1 - id);
    }

    public int Heuristic2(Board board, int W1, int W2, int id) {
        int yy = board.getScore(id) - board.getScore(1 - id);
        int xx = board.totStoneOnSide(id) - board.totStoneOnSide(1 - id);
        return W1 * yy + W2 * xx;
    }

    public int Heuristic3(Board board, int W1, int W2, int W3, int id) {
        int xx = board.getScore(id) - board.getScore(1 - id);
        int yy = board.totStoneOnSide(id) - board.totStoneOnSide(1 - id);
        int zz = board.additionalMovesEarned(id);
        return (W1 * xx) + (W2 * yy) + (W3 * zz);

    }
    
    public int Heuristic4(Board board, int W1, int W2, int W3,int W4, int id) {
        int xx = board.getScore(id) - board.getScore(1 - id);
        int yy = board.totStoneOnSide(id) - board.totStoneOnSide(1 - id);
        int zz = board.additionalMovesEarned(id);
        int hh = board.pots.elementAt(6).numOfStones + board.pots.elementAt(13).numOfStones;
        return (W1 * xx) + (W2 * yy) + (W3 * zz) + (W4*hh);

    }
    
    

    public int HeuristicController(Board board, int PlayerID) {
        int uu = 0;
        Random rand = new Random();
        int W1 = rand.nextInt(100) + 1;
        int W2 = 100 - W1;
        int W3 = rand.nextInt(50);
        int W4 = rand.nextInt(80);
        int rs = 0;// rand.nextInt(4);
        if (rs == 0) {
            switch (PlayerID) {
                case 0:
                    uu = Heuristic4(board,W1,W2,W3,W4, PlayerID);
                case 1:
                    uu = Heuristic2(board,W1,W2, PlayerID);
            }
        } else if (rs == 1) {
            switch (PlayerID) {
                case 1:
                    uu = Heuristic1(board, PlayerID);
                case 0:
                    uu = Heuristic3(board, W1, W2, W3, PlayerID);
            }
        } else if(rs == 2){
            switch (PlayerID) {
                case 1:
                    uu = Heuristic2(board, W1, W2, PlayerID);
                case 0:
                    uu = Heuristic3(board, W1, W2, W3, PlayerID);
            }
        }
        else{
            switch (PlayerID) {
                case 1:
                    uu = Heuristic4(board, W1, W2,W3,W4, PlayerID);
                case 0:
                    uu = Heuristic3(board, W1, W2, W3, PlayerID);
            }
        }
        return uu;
    }

    public int UTILITY(Board board, int PlayerID) //heuristic no 1,2,3 or 4
    {
        
        return HeuristicController(board, PlayerID);
    }

    boolean Terminal_test(Board board, int depth, int PlayerID) {
        if (depth == 1) {
            return true;
        }
        int player = board.totStoneOnSide(PlayerID);
        int Opponent = board.totStoneOnSide(1 - PlayerID);

        if (player == 0 || Opponent == 0) {
            return true;
        } else {
            return false;
        }
    }

    public int max(int a, int b) {
        if (a > b) {
            return a;
        } else {
            return b;
        }
    }

    public int min(int a, int b) {
        if (a < b) {
            return a;
        } else {
            return b;
        }
    }

    Pair MAX(Board board, int alpha, int beta, int depth, int PlayerID) {
        Pair<Integer, Integer> p = new Pair<>(-999999, -1);
        if (Terminal_test(board, depth, PlayerID) == true) {
            return new Pair(UTILITY(board, PlayerID), -1);
        }
        Board b = new Board();
        //b = board.copyBoard();
        int nxtPlayer = -1;
        for (int i = 0; i < 6; i++) {
            //int nxtPlayer = -1;
            //Board b ;//= new Board();
            //b = board.copyBoard();
            if (b.pots.elementAt(i).numOfStones > 0) {
                if (PlayerID == 0) {
                    nxtPlayer = b.makeAMove(i, PlayerID);
                } else {
                    nxtPlayer = b.makeAMove(i, PlayerID);
                }
            }

            // b.printBoard();
            //System.out.println(nxtPlayer);
            int val = 0;
            if (nxtPlayer == PlayerID) {
                val = (int) MAX(b, alpha, beta, depth - 1, PlayerID).getKey();
                //System.out.println(val);
            } else {
                val = (int) MIN(b, alpha, beta, depth - 1, 1 - PlayerID).getKey();
                //System.out.println(val);
            }
            if (p.getKey() < val) {
                p = new Pair<>(val, i);
            }
            if (p.getKey() >= beta) {
                return p;

            }
            alpha = max(alpha, p.getKey());
            //b = board;
        }
        return p;

    }

    Pair MIN(Board board, int alpha, int beta, int depth, int PlayerID) {
        Pair<Integer, Integer> p = new Pair<>(999999, -1);
        if (Terminal_test(board, depth, PlayerID) == true) {
            return new Pair(UTILITY(board, PlayerID), -1);
        }
        Board b = new Board();
        //b = board.copyBoard();
        int nxtPlayer = -1;
        for (int i = 0; i < 6; i++) {
            //int nxtPlayer = -1;
            //Board b;// = new Board();
            b = board.copyBoard();
            if (b.pots.elementAt(i).numOfStones > 0) {
                if (PlayerID == 0) {
                    nxtPlayer = b.makeAMove(i, PlayerID);
                } else {
                    nxtPlayer = b.makeAMove(i, PlayerID);
                }
            }

            //b.printBoard();
            int val = 0;
            if (nxtPlayer == PlayerID) {
                val = (int) MIN(b, alpha, beta, depth - 1, PlayerID).getKey();
                //System.out.println(val);
            } else {
                val = (int) MAX(b, alpha, beta, depth - 1, 1 - PlayerID).getKey(); //min is opposite sided player for MAX FUNCTION
                //System.out.println(val);
            }
            if (p.getKey() > val) {
                p = new Pair(val, i);
                //System.out.println(val);
            }
            if (p.getKey() <= alpha) {
                return p;
            }
            beta = min(beta, p.getKey());
            //b = board;
        }
        return p;

    }

    public int MIN_MAX(Board board, int PlayerID) {
        int alpha = -999999;
        int beta = 999999;
        int depth = 12;
        Pair< Integer, Integer> p = MAX(board, alpha, beta, depth, PlayerID);
        return p.getValue();

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here

        Mancala m = new Mancala();
        Board b = new Board();
        b.printBoard();
        int playerId = 0;
        //int move = 0;
        int move = m.MIN_MAX(b, playerId);

        //System.out.println(move);
        while (true) {
            //int move = m.MIN_MAX(b,playerId);
            /* if(b.pots.elementAt(move).numOfStones == 0){
                continue;
            }*/
            int idx = 0;
            if (b.pots.elementAt(move).numOfStones > 0) {

                System.out.println("Player" + (playerId + 1) + " gives " + move + "th move");
                playerId = b.makeAMove(move, playerId);
                b.printBoard();
            } else {
                if (playerId == 0) {
                    for (int i = 0; i < 6; i++) {
                        if (b.pots.elementAt(i).numOfStones != 0) {
                            idx = i;
                        }
                    }
                    System.out.println("Player" + (playerId + 1) + " gives " + idx + "th move");
                    playerId = b.makeAMove(idx, playerId);
                    b.printBoard();
                }else{
                    for (int i = 0; i < 6; i++) {
                        if (b.pots.elementAt(i+7).numOfStones != 0) {
                            idx = i;
                        }
                    }
                    System.out.println("Player" + (playerId + 1) + " gives " + idx + "th move");
                    playerId = b.makeAMove(idx, playerId);
                    b.printBoard();
                }
            }
            
            //System.out.println(playerId);

            //b.printBoard();
            if (b.isGameOver()) {
                b.gameOver();
                System.out.println("Game Over");
                b.printBoard();
                break;
            }
            move = m.MIN_MAX(b, playerId);

            //System.out.println(move);
        }
        System.out.println("Player 1 Score : " + b.getScore(0));
        System.out.println("Player 2 Score : " + b.getScore(1));
        if (b.getScore(0) > b.getScore(1)) {
            System.out.println("Player1 wins");
        } else if (b.getScore(0) < b.getScore(1)) {
            System.out.println("Player2 wins");
        } else {
            System.out.println("Draw");
        }
    }

}
