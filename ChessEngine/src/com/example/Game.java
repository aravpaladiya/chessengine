package com.example;


import com.example.GUI.UCI.UCI;

import static com.example.Engine.MoveGen.*;
import static com.example.Engine.GameBoard.*;
import static com.example.Engine.Constants.*;

import java.util.Stack;

public class Game {

    private static int playerColor = BLACK;
    public static int computerColor = playerColor^1;
    private static boolean gameOver = false;
    public static volatile boolean moveMade = false;
    public static long timeAssigned = 3500000000L;
    public static int maxDepth = 64;
    public static int userTarget = 64;
    public static int userStart = 64;
    public static Stack<Integer> stack = new Stack<>();
    public static int score = 0;
//    public static GUI gui = new GUI();
    public static boolean playerLeaveTurnGUI = false;
    public static boolean newFEN = false;




    public static void debug() {


    }

//
//
//    public static void AIvsAI() {
//        gui.chessBoard.updateBoard();
//
//        printState();
//
//        int count = 1000;
//        gui.updateDepth(maxDepth);
//
//        while(!gameOver && count!=0) {
//            int score = searchPosition(maxDepth);
//            gui.updateScore((float)(score)/100*((side==WHITE)?1:-1));
//            makeMove(principalVariation[0][0], allMoves);
//            gui.chessBoard.updateBoard();
//
//            if(isInMate(side) == staleMate || isInMate(side) == checkMate) {
//                gameOver = true;
//            }
//            count--;
//
//        }
//
//    }
//
//    public static void run() {
//        gui.chessBoard.updateBoard();
//        printState();
//
//        int count = 1000;
//        gui.updateDepth(maxDepth);
//
//        while(!gameOver && count!=0) {
//            if(side == computerColor) {
//                int score = searchPosition(maxDepth);
//                gui.updateScore((float)(score)/100*((side==WHITE)?1:-1));
//                makeMove(principalVariation[0][0], allMoves);
//                gui.chessBoard.updateBoard();
//
//
//            } else if(side == playerColor) {
//                playerTurn();
//            }
//
//            if(isInMate(side) == staleMate || isInMate(side) == checkMate) {
//                gameOver = true;
//            }
//            count--;
//
//        }
//    }

//    public static void playerTurn() {
//        MoveList playerList = new MoveList();
//        generateMoves(playerList);
//        boolean done = false;
//        done_:
//        while(!done) {
//            while (!moveMade) {
//                Thread.onSpinWait();
//                if(newFEN) {
//                    break done_;
//                }
//            }
//
//            for(int i = 0; i < playerList.count; i++) {
//                if(makeMove(playerList.moves[i].move, allMoves)) {
//                    if (decodeFrom(playerList.moves[i].move) == userStart && decodeTo(playerList.moves[i].move) == userTarget && (decodePromPiece(playerList.moves[i].move) == Q || decodePromPiece(playerList.moves[i].move) == q || decodePromPiece(playerList.moves[i].move) == noPc)) {
//                        done = true;
//                        break;
//                    } else {
//                        unmakeMove(playerList.moves[i].move, allMoves);
//                    }
//                }
//            }
//            gui.chessBoard.updateBoard();
//            userTarget = 64;
//            userStart = 64;
//            moveMade = false;
//        }
//        if(newFEN) {
//            newFEN = false;
//            playerTurn();
//        }
//    }

    public static void main(String[] args) {
        //loadFEN(startFEN);//not needed for UCI, done in position command
        initAll();

        new UCI();


    }
}