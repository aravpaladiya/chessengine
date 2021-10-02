package com.example;


import com.example.Engine.HashTable;
import com.example.Engine.MoveList;
import com.example.GUI.GUI;
import com.example.GUI.UCI.UCI;

import static com.example.Engine.MoveGen.*;
import static com.example.Engine.GameBoard.*;
import static com.example.Engine.Constants.*;
import static com.example.Engine.Search.*;

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
    public static Stack<Integer> makeMoveStack = new Stack<>();
    public static long hashKey = 0L;
    public static int score = 0;
    public static HashTable hashTable = new HashTable();
//    public static GUI gui = new GUI();
    public static boolean playerLeaveTurnGUI = false;
    public static boolean newFEN = false;





    public static void AIvsAI() {
        GUI gui = new GUI();
        gui.chessBoard.updateBoard();

        printState();

        int count = 1000;
        gui.updateDepth(maxDepth);

        while(!gameOver && count!=0) {
            int score = searchPosition(maxDepth);
            gui.updateScore((float)(score)/100*((side==WHITE)?1:-1));
            makeMove(principalVariation[0][0], ALL_MOVES);
            gui.chessBoard.updateBoard();

            if(isInMate(side) == STALE_MATE || isInMate(side) == CHECK_MATE) {
                gameOver = true;
            }
            count--;

        }

    }

    public static void run() {
        GUI gui = new GUI();

        gui.chessBoard.updateBoard();
        printState();

        int count = 1000;
        gui.updateDepth(maxDepth);

        while(!gameOver && count!=0) {
            if(side == computerColor) {
                int score = searchPosition(maxDepth);
                gui.updateScore((float)(score)/100*((side==WHITE)?1:-1));
                makeMove(principalVariation[0][0], ALL_MOVES);
                gui.chessBoard.updateBoard();


            } else if(side == playerColor) {
                playerTurn(gui);
            }

            if(isInMate(side) == STALE_MATE || isInMate(side) == CHECK_MATE) {
                gameOver = true;
            }
            count--;

        }
    }

    public static void playerTurn(GUI gui) {
        MoveList playerList = new MoveList();
        generateMoves(playerList);
        boolean done = false;
        done_:
        while(!done) {
            while (!moveMade) {
                Thread.onSpinWait();
                if(newFEN) {
                    break done_;
                }
            }

            for(int i = 0; i < playerList.count; i++) {
                if(makeMove(playerList.moves[i].move, ALL_MOVES)) {
                    if (decodeFrom(playerList.moves[i].move) == userStart && decodeTo(playerList.moves[i].move) == userTarget && (decodePromPiece(playerList.moves[i].move) == Q || decodePromPiece(playerList.moves[i].move) == q || decodePromPiece(playerList.moves[i].move) == NO_PC)) {
                        done = true;
                        break;
                    } else {
                        unmakeMove(playerList.moves[i].move, ALL_MOVES);
                    }
                }
            }
            gui.chessBoard.updateBoard();
            userTarget = 64;
            userStart = 64;
            moveMade = false;
        }
        if(newFEN) {
            newFEN = false;
            playerTurn(gui);
        }
    }





    public static void debug() {
        loadFEN(startFEN);
        timeAssigned = Long.MAX_VALUE;
        stopSearch = false;
        searchPosition(11);

    }



    public static void main(String[] args) {
        //loadFEN(startFEN);//not needed for UCI, done in position command

        initAll();
        boolean isDebugging = false;

        if(isDebugging) {
            debug();
        } else {
            new UCI();
        }


    }
}