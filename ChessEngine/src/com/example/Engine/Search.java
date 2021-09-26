package com.example.Engine;
import com.example.Game;

import java.util.Arrays;

import static com.example.Engine.Constants.*;

import static com.example.Engine.Evaluate.evaluate;
import static com.example.Engine.Evaluate.scoreMove;
import static com.example.Engine.GameBoard.*;
import com.example.Engine.MoveList;
import static com.example.Engine.MoveGen.*;
import static com.example.Game.gui;
import static com.example.Game.searchDepth;


public class Search {
    public static int searchNodes  = 0;

    public static int ply = 0;
    public static boolean scoringPV = false;
    public static boolean followingPVLine = false;

    public static int[][] killerMoves = new int[2][maxPly];
    public static int[][] principalVariation = new int[maxPly][maxPly];
    public static int[] PVLength = new int[maxPly];
    public static int[][] historyMoves = new int[12][64];


    public static int searchPosition(int depth) {
        int score = 0;
        killerMoves = new int[2][maxPly];
        principalVariation = new int[64][64];
        PVLength = new int[maxPly];
        historyMoves = new int[12][64];
        long s = System.nanoTime();
        for(int i = 1; i <=depth; i++) {
            followingPVLine = true;
            searchNodes = 0;
            long start = System.nanoTime();
            score = negamax(i, -infinity, infinity);
            long finish = System.nanoTime();
            System.out.println("\ntime: " + (float)(finish-start)/1000000000 + " depth: " + i + " score: " + (float)(score)/100 + " move: " + getShortMove(principalVariation[0][0]) + " nodes: " + searchNodes);
            System.out.print("principal variation:");
            for (int j = 0; j < i; j++) {
                if (principalVariation[0][j] == 0 || ((score < 0) ? infinity + score == j : infinity - score == j)) {
                    System.out.print("#");
                    break;
                }
                System.out.print(" " +getShortMove(principalVariation[0][j]));
            }
            System.out.println();
        }
        System.out.println("total time: " + (float)(System.nanoTime()-s)/1000000000);


        return score;

    }

    public static int quiescence(int alpha, int beta) {
        searchNodes++;
        int evaluate = evaluate();
        if(evaluate >= beta) {
            return beta;
        }
        if(evaluate > alpha) {
            alpha = evaluate;
        }


        MoveList searchList = new MoveList();
        generateCapMoves(searchList);

        for(int i = 0; i < searchList.count; i++) {

            if(makeMove(searchList.getNextMove(i).move, allMoves)) {

                ply++;
                int score = -quiescence(-beta, -alpha);
                ply--;
                unmakeMove(searchList.moves[i].move, allMoves);

                if (score >= beta) {
                    return beta;
                }
                if (score > alpha) {
                    alpha = score;
                }
            }
        }
        return alpha;
    }

    public static int negamax(int depth, int alpha, int beta) {
        int score;
        boolean foundPV = false;
        PVLength[ply] = ply;
        if(depth == 0) {
            return quiescence(alpha, beta);
        }

        if(ply > maxPly-1) {
            //too deep
            return evaluate();
        }
        searchNodes++;
        boolean inCheck = isSquareAttacked(BitBoard.getLs1bIndex(bitboards[side==WHITE?K:k]), side^1);
        int legalMoveCount = 0;

        if (inCheck) {
            depth++;
        }



        MoveList searchList = new MoveList();
        scoringPV = false;
        generateMoves(searchList);
        for(int i = 0; i < searchList.count; i++) {

            if(makeMove(searchList.getNextMove(i).move, allMoves)) {

                ply++;
                legalMoveCount++;
                if(foundPV) {
                    score = -negamax(depth-1, -alpha-1, -alpha);
                    if(score > alpha && score < beta) {
                        score = -negamax(depth-1, -beta, -alpha);
                    }
                } else {
                    score = -negamax(depth - 1, -beta, -alpha);
                }

                ply--;

                unmakeMove(searchList.moves[i].move, allMoves);
                if(score >= beta) {
                    //killer
                    if(decodeCap(searchList.moves[i].move)==0) {
                        killerMoves[1][ply] = killerMoves[0][ply];
                        killerMoves[0][ply] = searchList.moves[i].move;
                    }

                    return beta;
                }

                if(score > alpha) {
                    alpha = score;
                    foundPV = true;
                    if(decodeCap(searchList.moves[i].move)==0) {
                        historyMoves[decodePiece(searchList.moves[i].move)][decodeTo(searchList.moves[i].move)] = 1 << depth;
                    }
                    principalVariation[ply][ply] = searchList.moves[i].move;
                    for(int nextPly = ply+1; nextPly < PVLength[ply+1]; nextPly++) {
                        //pv
                        principalVariation[ply][nextPly] = principalVariation[ply+1][nextPly];
                    }
                    PVLength[ply] = PVLength[ply+1];
                }



            }
        }
        if(legalMoveCount == 0) {

            if(isSquareAttacked(BitBoard.getLs1bIndex(bitboards[side==WHITE?K:k]), side^1)) {
                return -infinity+ply;
            } else {
                return 0;
            }
        }

        return alpha;
    }

//    public static int maxi(int depth) {
//        int score;
//        int max = -50000;
//        if(depth == 0) {
//            return evaluate();
//        }
//        MoveList searchList = new MoveList();
//        generateMoves(searchList);
//        for(int i = 0; i < searchList.count; i++) {
//            if(makeMove(searchList.moves[i], allMoves)) {
//                ply++;
//                score = mini(depth-1);
//                unmakeMove(searchList.moves[i], allMoves);
//                ply--;
//                if(score > max) {
//                    max = score;
//                    if(ply==0) {
//                        System.out.println("0 ply");
//                        bestMove = searchList.moves[i];
//                    }
//                }
//                if(ply == 0) {
//                    System.out.println("normal 0 ply");
//                }
//            }
//        }
//        if(ply == 0) {
//            return bestMove;
//        }
//        return max;
//    }
}
