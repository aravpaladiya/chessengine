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
            System.out.println("\ntime: " + (float)(finish-start)/1000000000 + " depth: " + i + " score: " +
                    (float)(score)/100 + " move: " + getShortMove(principalVariation[0][0]) + " nodes: " + searchNodes);
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
    /*

    start

    without LMR

    time: 5.0111427 depth: 9 score: 0.2 move: e2e4 nodes: 14449083
    principal variation: e2e4 b8c6 b1c3 g8f6 g1f3 d7d5 e4d5 f6d5 d2d4
    total time: 7.954784

    with LMR first move closed window

    time: 1.3425611 depth: 9 score: 0.2 move: e2e4 nodes: 1468712
    principal variation: e2e4 b8c6 g1f3 g8f6 b1c3 d7d5 e4d5 f6d5 d2d4
    total time: 2.5058706

    with lmr first move open window

    time: 0.8653254 depth: 9 score: 0.2 move: e2e4 nodes: 743947
    principal variation: e2e4 b8c6 g1f3 g8f6 b1c3 d7d5 e4d5 f6d5 d2d4
    total time: 2.3860834



    tricky

    without lmr

    time: 6.655765 depth: 8 score: -0.2 move: e2a6 nodes: 19370500
    principal variation: e2a6 e6d5 c3d5 b6d5 a6b7 a8b8 e4d5 b8b7
    total time: 10.098823

    with lmr first move closed window

    time: 3.190954 depth: 9 score: -0.7 move: e2a6 nodes: 8380761
    principal variation: e2a6 e6d5 c3d5 b6d5 a6b7 a8b8 b7d5 e7e5 d2f4
    total time: 5.1331296

    with lmr first move open window

    time: 1.646847 depth: 9 score: -0.7 move: e2a6 nodes: 5128279
    principal variation: e2a6 e6d5 c3d5 b6d5 a6b7 a8b8 b7d5 e7e5 d2f4
    total time: 3.074274

    very


    without lmr

    time: 10.802674 depth: 8 score: 0.05 move: d8c8 nodes: 30950392
    principal variation: d8c8 g3g4 f8d8 f1e1 h7h6 c2c3 b7b5 c3d4
    total time: 14.487848

    with lmr first move closed window

    time: 3.1350038 depth: 9 score: 0.05 move: d8c8 nodes: 9852189
    principal variation: d8c8 g3g4 f8d8 f1e1 h7h5 g4g5 f6e8 h3h4 e8d6
    total time: 6.471384

    with lmr first move open window

    time: 3.1474054 depth: 9 score: 0.05 move: d8c8 nodes: 9090827
    principal variation: d8c8 g3g4 f8d8 f1e1 h7h6 c2c3 a7a5 c3d4 e5d4
    total time: 5.3554525
     */

    public static int negamax(int depth, int alpha, int beta) {
        int score;
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
        boolean foundPV = false;
        int legalMoveCount = 0;

        if (inCheck) {
            depth++;
        }



        MoveList searchList = new MoveList();
        scoringPV = false;
        generateMoves(searchList);
        int movesSearched = 0;
        for(int i = 0; i < searchList.count; i++) {

            if(makeMove(searchList.getNextMove(i).move, allMoves)) {

                ply++;
                legalMoveCount++;
                if(movesSearched == 0) {
//                    score = -negamax(depth-1, -alpha-1, -alpha);
//                    if(score > alpha && score < beta) {
                        score = -negamax(depth-1, -beta, -alpha);
//                    }
                } else {
                    if(depth >= 3 && movesSearched >= 4 && !inCheck && decodeCap(searchList.moves[i].move) == 0 && decodePromPiece(searchList.moves[i].move)==noPc) {
                        score = -negamax(depth-2, -alpha - 1, -alpha);

                    } else {
                        score = alpha+1;
                    }
                    if(score > alpha) {
                        score = -negamax(depth - 1, -alpha-1, -alpha);
                        if(score > alpha && score < beta) {
                            score = -negamax(depth-1, -beta, -alpha);
                        }
                    }

                }

                ply--;

                unmakeMove(searchList.moves[i].move, allMoves);
                movesSearched++;
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
