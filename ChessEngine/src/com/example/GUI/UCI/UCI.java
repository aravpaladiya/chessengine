package com.example.GUI.UCI;

import com.example.Engine.Constants;
import com.example.Engine.GameBoard;

import java.util.Scanner;

import static com.example.Engine.Constants.*;
import static com.example.Engine.MoveGen.*;
import static com.example.Engine.Search.*;
import static com.example.Game.*;
import static com.example.Engine.BitBoard.*;
import static com.example.Engine.GameBoard.*;


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

        int bestMove = searchPosition(maxDepth);
        stopSearch = true;
        positionSetUp = false;
        System.out.print("bestmove " + getShortMove(bestMove) + "\n");
    }
    public static int previousPosCommandLen = 0;
    public static String previousPosCommand = "";
    static void parsePos(String command) {
        positionSetUp = false;
        int i = 0;
        char[] commandArr = command.toCharArray();
        int moveStartIndex = 0;
        if(previousPosCommandLen != 0 && command.regionMatches(0, previousPosCommand, 0, previousPosCommandLen-1)) {
            moveStartIndex = previousPosCommandLen-6;
        } else {
            if (command.regionMatches(9, "startpos", 0, 8)) {
                GameBoard.loadFEN(Constants.startFEN);
                moveStartIndex = 18;
            } else if (command.regionMatches(9, "fen", 0, 3)) {
                String FEN;
                FEN = command.substring(13);
                moveStartIndex = loadFEN(FEN)+14;
            }
        }
        moveStartIndex+=6;
        while(moveStartIndex < commandArr.length) {
            i = moveStartIndex;

            while (commandArr[moveStartIndex] != ' ') {
                System.out.println(commandArr[moveStartIndex]);
                moveStartIndex++;
                if(moveStartIndex==commandArr.length) {
                    break;
                }
            }
            moveStartIndex++;
            String moveStr = command.substring(i, moveStartIndex-1);
            makeMoveFromString(moveStr);
            System.out.println(side);
        }
        previousPosCommandLen = moveStartIndex;
        previousPosCommand = command;
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
                } else {
                    promPiece = PieceToInt.get("N");
                }
            }
        }
        //need to check if everything before last move is the same to save time unless ucinewgame is called.
        int startSquare = CoordsToInt.get(move.substring(0, 2));
        int targetSquare = CoordsToInt.get(move.substring(2, 4));
        int capture = 0;
        int enps = 0;
        int piece = noPc;
        int dblpush = 0;
        int castle = 0;

        for(int i = side==WHITE?P:p; i <= (side==WHITE?K:k); i++) {
            if(get_bit(bitboards[i], startSquare)!=0) {
                piece = i;
                break;
            }
        }

        if(get_bit(occupancies[occBothIdx], targetSquare)!=0) {
            capture = 1;
        } else if(enPs == targetSquare) {
            enps = 1;
            capture = 1;
        } else {
            if(side==WHITE) {
                if(piece==P) {
                    if(targetSquare==startSquare+16) {
                        dblpush = 1;
                    }
                } else if(piece==K) {
                    if((targetSquare==c1 || targetSquare==g1) && startSquare==e1) {
                        castle = 1;
                    }
                }
            } else if(side==BLACK) {
                if(piece==p) {
                    if(targetSquare==startSquare-16) {
                        dblpush = 1;
                    }
                } else if(piece==k) {
                    if((targetSquare==c8 || targetSquare==g8) && startSquare==e8) {
                        castle = 1;
                    }
                }
            }
        }




        makeMove(encodeMove(startSquare, targetSquare, piece, promPiece, capture, dblpush, enps, castle), allMoves);
    }


}
