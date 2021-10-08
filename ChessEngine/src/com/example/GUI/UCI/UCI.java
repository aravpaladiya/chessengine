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
        while(true) {
            String uci = scanner.nextLine();
            if (uci.equals("uci")) {
                break;
            } else if(uci.equals("debugengine")) {
                debug();
            }
        }
        System.out.print("id name MLCChess\n");
        System.out.print("id author AravP\n");
        System.out.print("uciok\n");
        System.out.print("option name Book type check default true\n");
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
        if(command.equals("go infinite")) {
            timeAssigned = Long.MAX_VALUE;
            stopSearch = false;

            int bestMove = searchPosition(maxDepth);
            stopSearch = true;
            positionSetUp = false;
            System.out.print("bestmove " + getShortMove(bestMove) + "\n");

            return;
        }
        int commandIndex = 3;
        char[] commandArr = command.toCharArray();
        int depth = -1;
        String depthStr = "";
        int wtime = -1;
        String wtimeStr = "";
        int btime = -1;
        String btimeStr = "";
        int binc = 0;
        String bincStr = "";
        int winc = 0;
        String wincStr = "";
        int movetime = -1000;
        String movetimeStr = "";
        int movesToGo = 30;
        String movesToGoStr = "";

        //example command
        //go depth 6 wtime 180000 btime 100000 winc 1000 binc 1000 movetime 1000 movestogo 40
        if(command.regionMatches(commandIndex, "depth", 0, 5)) {
            commandIndex+=6;
            while(commandArr[commandIndex]!= ' ') {
                depthStr = depthStr + commandArr[commandIndex];
                commandIndex++;
                if(commandIndex >= commandArr.length) {
                    break;
                }
            }
            commandIndex++;
            depth = Integer.parseInt(depthStr);
        }
        if(command.regionMatches(commandIndex, "wtime", 0, 5)) {
            commandIndex += 6;
            while (commandArr[commandIndex] != ' ') {
                wtimeStr = wtimeStr + commandArr[commandIndex];
                commandIndex++;
                if (commandIndex >= commandArr.length) {
                    break;
                }
            }
            commandIndex++;
            wtime = Integer.parseInt(wtimeStr);
        }

        if(command.regionMatches(commandIndex, "btime", 0, 5)) {
            commandIndex+=6;
            while(commandArr[commandIndex]!= ' ') {
                btimeStr = btimeStr + commandArr[commandIndex];
                commandIndex++;
                if(commandIndex >= commandArr.length) {
                    break;
                }
            }
            commandIndex++;
            btime = Integer.parseInt(btimeStr);
        }
        if(command.regionMatches(commandIndex, "winc", 0, 4)) {
            commandIndex+=5;
            while(commandArr[commandIndex]!= ' ') {
                wincStr = wincStr + commandArr[commandIndex];
                commandIndex++;
                if(commandIndex >= commandArr.length) {
                    break;
                }
            }
            commandIndex++;
            winc = Integer.parseInt(wincStr);
        }
        if(command.regionMatches(commandIndex, "binc", 0, 4)) {
            commandIndex+=5;
            while(commandArr[commandIndex]!= ' ') {
                bincStr = bincStr + commandArr[commandIndex];
                commandIndex++;
                if(commandIndex >= commandArr.length) {
                    break;
                }
            }
            commandIndex++;
            binc = Integer.parseInt(bincStr);
        }
        if(command.regionMatches(commandIndex, "movetime", 0, 8)) {
            commandIndex+=9;
            while(commandArr[commandIndex]!= ' ') {
                movetimeStr = movetimeStr + commandArr[commandIndex];
                commandIndex++;
                if(commandIndex >= commandArr.length) {
                    break;
                }
            }
            commandIndex++;
            movetime = Integer.parseInt(movetimeStr);
        }
        if(command.regionMatches(commandIndex, "movestogo", 0, 9)) {
            commandIndex+=10;
            while(commandArr[commandIndex]!= ' ') {
                movesToGoStr = movesToGoStr + commandArr[commandIndex];
                commandIndex++;
                if(commandIndex >= commandArr.length) {
                    break;
                }
            }
            movesToGo = Integer.parseInt(movesToGoStr);
        }
        if(movetime != -1000) {
            timeAssigned = movetime;
        } else {
            if(side==WHITE) {
                movetime = wtime/movesToGo;
                if(wtime > (movetime+winc)) {
                    movetime += winc;
                } else {
                    movetime = wtime;
                }
            } else {
                movetime = btime/movesToGo;
                if(btime > movetime+binc) {
                    movetime += binc;
                } else {
                    movetime = btime;
                }
            }

        }
        movetime-=25;

        timeAssigned = movetime* 1000000L;//change to 1/30 of available time later
        while (!positionSetUp) {
            Thread.onSpinWait();
        }

        stopSearch = false;

        int bestMove = searchPosition(depth==-1?maxDepth:depth);
        stopSearch = true;
        positionSetUp = false;
        System.out.print("bestmove " + getShortMove(bestMove) + "\n");
    }
    public static int previousPosCommandLen = 0;
    public static String previousPosCommand = "";
    static void parsePos(String command) {
        positionSetUp = false;
        int i;
        char[] commandArr = command.toCharArray();
        int moveStartIndex = 0;
        if(previousPosCommandLen != 0 && command.regionMatches(0, previousPosCommand, 0, previousPosCommandLen-1)) {
            moveStartIndex = previousPosCommandLen-6;
        } else {
            if (command.regionMatches(9, "startpos", 0, 8)) {

                loadFEN(startFEN);
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
                moveStartIndex++;
                if(moveStartIndex==commandArr.length) {
                    break;
                }
            }

            moveStartIndex++;
            String moveStr = command.substring(i, moveStartIndex-1);
            makeMoveFromString(moveStr);
            historyPly++;
            repetitionTable[historyPly] = hashKey;
        }
        previousPosCommandLen = moveStartIndex;
        previousPosCommand = command;
        positionSetUp = true;
    }

    static void makeMoveFromString(String move) {
        int promPiece = NO_PC;
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
        int piece = NO_PC;
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




        makeMove(encodeMove(startSquare, targetSquare, piece, promPiece, capture, dblpush, enps, castle), ALL_MOVES);
    }


}
