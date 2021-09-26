package com.example;


import com.example.Engine.BitBoard;
import com.example.Engine.MoveList;

import com.example.Engine.Search;
import com.example.GUI.GUI;
import com.example.Engine.Evaluate;
import com.example.GUI.UCI;

import javax.swing.*;

import static com.example.Engine.Evaluate.*;
import static com.example.Engine.MoveGen.*;
import static com.example.Engine.GameBoard.*;
import static com.example.Engine.Constants.*;
import static com.example.Engine.BitBoard.*;
import static com.example.Engine.Search.*;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Stack;

public class Game {

    private static int playerColor = BLACK;
    public static int computerColor = playerColor^1;
    private static boolean gameOver = false;
    public static volatile boolean moveMade = false;
    public static int searchDepth = 9;//e4 e5 nodes 32835908 depth 9 comp = black no mvvlva quisence 45-50 secs
    public static int userTarget = 64;//e4 e5 nodes 32833274
    public static int userStart = 64;
    public static Stack<Integer> stack = new Stack<>();
    public static int score = 0;
    public static GUI gui = new GUI();
    public static boolean playerLeaveTurnGUI = false;
    public static boolean newFEN = false;




    public static void debug() {


    }



    public static void AIvsAI() {
        gui.chessBoard.updateBoard();

        printState();

        int count = 1000;
        gui.updateDepth(searchDepth);

        while(!gameOver && count!=0) {
            int score = searchPosition(searchDepth);
            gui.updateScore((float)(score)/100*((side==WHITE)?1:-1));
            makeMove(principalVariation[0][0], allMoves);
            gui.chessBoard.updateBoard();

            if(isInMate(side) == staleMate || isInMate(side) == checkMate) {
                gameOver = true;
            }
            count--;

        }

    }

    public static void run() {
        gui.chessBoard.updateBoard();
        printState();

        int count = 1000;
        gui.updateDepth(searchDepth);

        while(!gameOver && count!=0) {
            if(side == computerColor) {
                int score = searchPosition(searchDepth);
                gui.updateScore((float)(score)/100*((side==WHITE)?1:-1));
                makeMove(principalVariation[0][0], allMoves);
                gui.chessBoard.updateBoard();


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
        done_:
        while(!done) {
            while (!moveMade) {
                Thread.onSpinWait();
                if(newFEN) {
                    break done_;
                }
            }

            for(int i = 0; i < playerList.count; i++) {
                if(makeMove(playerList.moves[i].move, allMoves)) {
                    if (decodeFrom(playerList.moves[i].move) == userStart && decodeTo(playerList.moves[i].move) == userTarget && (decodePromPiece(playerList.moves[i].move) == Q || decodePromPiece(playerList.moves[i].move) == q || decodePromPiece(playerList.moves[i].move) == noPc)) {
                        done = true;
                        break;
                    } else {
                        unmakeMove(playerList.moves[i].move, allMoves);
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
            playerTurn();
        }
    }
    //    "8/8/8/8/8/8/8/8 w - - 0 1"
//    "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1 "
//    "r3k2r/p1ppqpb1/bn2pnp1/3PN3/1p2P3/2N2Q1p/PPPBBPPP/R3K2R w KQkq - 0 1 "
//    "rnbqkb1r/pp1p1pPp/8/2p1pP2/1P1P4/3P3P/P1P1P3/RNBQKBNR w KQkq e6 0 1"
//    "r2q1rk1/ppp2ppp/2n1bn2/2b1p3/3pP3/3P1NPP/PPP1NPB1/R1BQ1RK1 b - - 0 9 "

    public static void main(String[] args) {
        loadFEN(very_hard_position);
        computerColor = BLACK;
        playerColor = WHITE;
        initAll();
        gui.chessBoard.updateBoard();
        printState();
        run();


    }
}