package com.example.GUI.UCI;

import com.example.Engine.Constants;
import com.example.Engine.GameBoard;

import java.util.Scanner;

import static com.example.Engine.Constants.*;
import static com.example.Engine.MoveGen.decodePromPiece;
import static com.example.Engine.Search.*;
import static com.example.Game.*;


/**
 *
 */
public class UCI {

    public static Scanner scanner = new Scanner(System.in);
    public static volatile boolean positionSetUp = false;


    public UCI() {
        uciLoop();
    }

    private void uciLoop() {
        System.out.print("id name MLCChess\n");
        System.out.print("id author AravP\n");
        System.out.print("uciok\n");
        ReadSTDIN read = new ReadSTDIN();
        read.start();
    }

    /**
     *
     * @param command command containing information about time remaining for both sides
     */
    static void parseGo(String command) {
        if(!stopSearch) {
            return;
        }
        while (!positionSetUp) {
            Thread.onSpinWait();
        }

        stopSearch = false;

        searchPosition(maxDepth);
        stopSearch = true;
        positionSetUp = false;


    }
    static void parsePos(String command) {
        positionSetUp = false;
        int i = 0;
        if(command.regionMatches(9, "startpos", 0, 8)) {
            GameBoard.loadFEN(Constants.startFEN);
        } else if(command.regionMatches(9, "fen", 0, 3)){
            String FEN;
//            int FENLength;
//            int numSlashes = 0;
//            char[] FENArr = new char[100];
//            int i = 0;
//            do {
//                if (FENArr[i] == '/') {
//                    numSlashes++;
//
//                }
//            } while (numSlashes != 7);
            FEN = command.substring(13);
            System.out.println(FEN);
            GameBoard.loadFEN(FEN);
        }
//        try {
//            Thread.sleep(1000);
//        } catch(Exception e) {
//
//        }


        positionSetUp = true;

    }

    static void makeMoveFromString(String move) {
        int promPiece = noPc;
        if(move.length() == 5) {
            promPiece = PieceToInt.get(Character.toString(move.charAt(4)));
            if((promPiece==q || promPiece==r || promPiece==b || promPiece==n) && GameBoard.side==WHITE) {
                if(promPiece == q) {
                    promPiece = PieceToInt.get("Q");
                } else if(promPiece == r) {
                    promPiece = PieceToInt.get("R");
                } else if(promPiece == b) {
                    promPiece = PieceToInt.get("B");
                } else if(promPiece == n) {
                    promPiece = PieceToInt.get("N");
                }
            }
        }
        //need to check if everything before last move is the same to save time unless ucinewgame is called.
        int startSquare = CoordsToInt.get(move.substring(0, 1));
        int targetSquare = CoordsToInt.get(move.substring(2, 3));




    }


}
