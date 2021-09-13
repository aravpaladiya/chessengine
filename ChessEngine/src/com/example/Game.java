package com.example;


import com.example.Engine.BitBoard;
import com.example.Engine.MoveList;
import com.example.Engine.Search;
import com.example.GUI.GUI;
import com.example.Engine.Evaluate;

import javax.swing.*;

import static com.example.Engine.Evaluate.*;
import static com.example.Engine.MoveGen.*;
import static com.example.Engine.GameBoard.*;
import static com.example.Engine.Constants.*;
import static com.example.Engine.BitBoard.*;
import static com.example.Engine.Search.*;

import java.util.Stack;

public class Game {

    private static final int playerColor = WHITE;
    public static final int computerColor = BLACK;
    private static boolean gameOver = false;
    public static volatile boolean moveMade = false;
    public static int searchDepth = 5;
    public static int userTarget = 64;
    public static int userStart = 64;
    public static MoveList moveList = new MoveList();
    public static Stack<Integer> stack = new Stack<>();
    public static int score = 0;
    public static GUI gui = new GUI();
    public static boolean playerLeaveTurnGUI = false;
    public static boolean newFEN = false;


    public static void run() {
        gui.chessBoard.updateBoard();

        printState();
        System.out.println(evaluate());

        int count = 1000;
        gui.updateDepth(searchDepth);

        while(!gameOver && count!=0) {
            if(side == computerColor) {
                long start = System.nanoTime();
                int score = searchPosition(searchDepth);//start position takes 1.2 sec at depth 5
                if(score == 51000) {
                    score = searchPosition(searchDepth);
                }
                long finish = System.nanoTime();
                System.out.println();
                System.out.println((float)(finish-start)/1000000000);
                System.out.println("score " + (float)(score)/100);
                System.out.println("move " + squareToCoords[decodeFrom(bestMove)] + squareToCoords[decodeTo(bestMove)]);
                makeMove(bestMove, allMoves);
                System.out.println("searched");

                gui.chessBoard.updateBoard();
                gui.updateScore((float)(score)/100*((computerColor==WHITE)?1:-1));


            } else if(side == playerColor) {
                playerTurn();
            }

            if(isInMate(side) == staleMate || isInMate(side) == checkMate) {
                gameOver = true;
            }
            count--;

        }
    }

    public static void playerTurn() {
        MoveList playerList = new MoveList();
        generateMoves(playerList);
        boolean done = false;
        while(!done) {
            while (!moveMade) {
//                Thread.onSpinWait();
                if(newFEN) {
                    break;
                }
            }
            if(newFEN) {
                break;
            }
            System.out.println("checking move");
            for(int i = 0; i < playerList.count; i++) {
                if(makeMove(playerList.moves[i], allMoves)) {
                    if (decodeFrom(playerList.moves[i]) == userStart && decodeTo(playerList.moves[i]) == userTarget && (decodePromPiece(playerList.moves[i]) == Q || decodePromPiece(playerList.moves[i]) == q || decodePromPiece(playerList.moves[i]) == noPc)) {
                        done = true;
                        break;
                    } else {
                        unmakeMove(playerList.moves[i], allMoves);
                    }
                }
            }
            System.out.println("done checking move");
            gui.chessBoard.updateBoard();
            userTarget = 64;
            userStart = 64;
            moveMade = false;
        }
        if(newFEN) {
            newFEN = false;
            playerTurn();
        }
    }
    //    "8/8/8/8/8/8/8/8 w - - 0 1"
//    "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1 "
//    "r3k2r/p1ppqpb1/bn2pnp1/3PN3/1p2P3/2N2Q1p/PPPBBPPP/R3K2R w KQkq - 0 1 "
//    "rnbqkb1r/pp1p1pPp/8/2p1pP2/1P1P4/3P3P/P1P1P3/RNBQKBNR w KQkq e6 0 1"
//    "r2q1rk1/ppp2ppp/2n1bn2/2b1p3/3pP3/3P1NPP/PPP1NPB1/R1BQ1RK1 b - - 0 9 "

    public static void main(String[] args) {
//        loadFEN("8/8/k7/8/3p4/8/K7/8 w - - 0 1");
        loadFEN("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1 ");
        initAll();
//        MoveList moveList = new MoveList();
//        long start;
//        start = System.nanoTime();
//        long finish;
//        nMoves = 0;
//        findNodesForPosPerft(5);
//        Search.maxi(4);
//        finish = System.nanoTime();
//        System.out.println(nMoves);
//        System.out.println((finish-start)/1000000);
        run();




    }
}