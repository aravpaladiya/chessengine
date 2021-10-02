package com.example.Engine;

import static com.example.Engine.Constants.*;

import static com.example.Engine.Evaluate.evaluate;
import static com.example.Engine.GameBoard.*;
import static com.example.Engine.MoveGen.*;
import static com.example.Game.*;


public class Search {
    public static int searchNodes  = 0;

    public static int ply = 0;
    public static boolean scoringPV = false;
    public static boolean followingPVLine = false;

    public static int[][] killerMoves = new int[2][MAX_PLY];
    public static int[][] principalVariation = new int[MAX_PLY][MAX_PLY];
    public static int[] PVLength = new int[MAX_PLY];
    public static int[][] historyMoves = new int[12][64];
    public static long timeAtMoveOver = 0;
    public static Boolean stopSearch = true;

    private static boolean checkForStop() {
        if(timeAtMoveOver - System.nanoTime() < 0 || stopSearch) {
            stopSearch = true;
            return true;
        }
        return false;
    }

    public static int searchPosition(int depth) {
        int score = 0;
        killerMoves = new int[2][MAX_PLY];
        principalVariation = new int[64][64];
        PVLength = new int[MAX_PLY];
        historyMoves = new int[12][64];
        long searchStartNs = System.nanoTime();
        if(timeAssigned == Long.MAX_VALUE) {
            timeAtMoveOver = timeAssigned;
        }

        timeAtMoveOver = searchStartNs + timeAssigned;

        int bestMove = 0;
        int[] prevPV = new int[MAX_PLY];
        searchNodes = 0;
        for(int i = 1; i <= depth; i++) {
            followingPVLine = true;
            score = negamax(i, -INFINITY, INFINITY);

            long finish = System.nanoTime();
            if(stopSearch) {
                stopSearch = false;
                break;
            } else {
                //uci print
                System.out.print("info score cp " + score + "  depth " + i + " nodes " + searchNodes + " time " + (finish-searchStartNs)/1000000 + " pv ");
                for (int j = 0; j < i; j++) {
                    if (prevPV[j] == 0 || ((score < 0) ? 49000 + score == j : 49000 - score == j)) {
                        break;
                    }
                    System.out.print(getShortMove(prevPV[j]) + " ");
                }
                System.out.print("\n");
            }
            prevPV = principalVariation[0];
            bestMove = principalVariation[0][0];
        }



        return bestMove;

    }
    /*
        LMR tests

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

        another


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
    public static int quiescence(int alpha, int beta) {
        searchNodes++;
        if((searchNodes & 2047) == 0) {
            if(checkForStop()) {
                return 0;
            }
        }
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

            if(makeMove(searchList.getNextMove(i).move, ALL_MOVES)) {

                ply++;
                int score = -quiescence(-beta, -alpha);

                ply--;
                unmakeMove(searchList.moves[i].move, ALL_MOVES);
                if(stopSearch) {
                    return 0;
                }

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
        PVLength[ply] = ply;
        if(depth == 0) {
            return quiescence(alpha, beta);
        }

        if(ply >= MAX_PLY) {
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
        /*
        info score cp 40  depth 1 nodes 25 time 2 pv
info score cp 10  depth 2 nodes 102 time 30 pv d2d4 d7d5
info score cp 40  depth 3 nodes 305 time 49 pv d2d4 d7d5 b1c3
info score cp 10  depth 4 nodes 2080 time 72 pv d2d4 d7d5 b1c3 b8c6
info score cp 40  depth 5 nodes 4543 time 87 pv d2d4 d7d5 b1c3 b8c6 g1f3
info score cp 20  depth 6 nodes 29992 time 184 pv b1c3 d7d5 e2e4 d5e4 c3e4 g8f6
info score cp 30  depth 7 nodes 82324 time 347 pv d2d4 b8c6 g1f3 d7d5 b1c3 g8f6 c1f4
info score cp 25  depth 8 nodes 265672 time 931 pv g1f3 b8c6 d2d4 d7d5 b1c3 e7e6 e2e4 g8f6
info score cp 25  depth 9 nodes 462287 time 1349 pv g1f3 d7d5 d2d4 b8c6 c1f4 g8f6 b1c3 c8f5 e2e3
info score cp 30  depth 10 nodes 1855703 time 2477 pv e2e4 b8c6 g1f3 e7e6 b1c3 d7d5 e4d5 e6d5 d1e2 g8e7
info score cp 30  depth 11 nodes 3611703 time 3323 pv e2e4 b8c6 g1f3 e7e6 b1c3 d7d5 d2d4 g8f6 e4d5 e6d5 c1f4


         */
        //info score cp 35  depth 11 nodes 10555139 time 5516 pv e2e4 b8c6 d2d4 e7e6 g1f3 d7d5 e4e5 f7f6 b1c3 f6e5 d4e5
        //null move pruning
        if(isOkToNullMove(inCheck, depth, ply)) {
            ply++;
            side ^= 1;
            hashKey ^= zobristSideKey;
            int prevEnPs = enPs;
            if(enPs != NO_SQ) {
                hashKey ^= zobristEnPsKeys[enPs];
            }
            enPs = NO_SQ;
            score = -negamax(depth-3, -beta, -beta+1);

            side ^= 1;
            hashKey ^= zobristSideKey;
            enPs = prevEnPs;
            if(enPs != NO_SQ) {
                hashKey ^= zobristEnPsKeys[enPs];
            }
            ply--;
            if(score >= beta) {
                return beta;
            }
        }

        MoveList searchList = new MoveList();
        scoringPV = false;
        generateMoves(searchList);
        int movesSearched = 0;
        for(int i = 0; i < searchList.count; i++) {

            if(makeMove(searchList.getNextMove(i).move, ALL_MOVES)) {

                ply++;
                legalMoveCount++;
                if(movesSearched == 0) {
                    //closed window
//                    score = -negamax(depth-1, -alpha-1, -alpha);
//                    if(score > alpha && score < beta) {
                    //open window
                    score = -negamax(depth-1, -beta, -alpha);
//                    }
                } else {
                    //lmr
                    if(depth >= 3 && movesSearched >= 4 && !inCheck && decodeCap(searchList.moves[i].move) == 0 && decodePromPiece(searchList.moves[i].move)== NO_PC) {
                        score = -negamax(depth-2, -alpha - 1, -alpha);

                    } else {
                        //increment score slightly to search again if important move
                        score = alpha+1;
                    }
                    if(score > alpha) {
                        //research with closed window since we can assume move is good
                        score = -negamax(depth - 1, -alpha-1, -alpha);
                        if(score > alpha && score < beta) {
                            //if move is somehow bad still then search normally since the move is unconventional
                            score = -negamax(depth-1, -beta, -alpha);
                        }
                    }

                }

                ply--;

                unmakeMove(searchList.moves[i].move, ALL_MOVES);
                if((searchNodes & 2047) == 0) {
                    if(checkForStop()) {
                        return 0;
                    }
                }
                movesSearched++;
                if(score >= beta) {
                    //update killer
                    if(decodeCap(searchList.moves[i].move)==0) {
                        killerMoves[1][ply] = killerMoves[0][ply];
                        killerMoves[0][ply] = searchList.moves[i].move;
                    }

                    return beta;
                }

                if(score > alpha) {
                    alpha = score;
                    //update history
                    if(decodeCap(searchList.moves[i].move)==0) {
                        historyMoves[decodePiece(searchList.moves[i].move)][decodeTo(searchList.moves[i].move)] = 1 << depth;
                    }
                    principalVariation[ply][ply] = searchList.moves[i].move;
                    for(int nextPly = ply+1; nextPly < PVLength[ply+1]; nextPly++) {
                        //update pv
                        principalVariation[ply][nextPly] = principalVariation[ply+1][nextPly];
                    }
                    PVLength[ply] = PVLength[ply+1];
                }



            }
        }
        if(legalMoveCount == 0) {
            //if no legal moves ie checkmate or stalemate return mating score
            if(isSquareAttacked(BitBoard.getLs1bIndex(bitboards[side==WHITE?K:k]), side^1)) {
                return -49000+ply;
            } else {
                return 0;
            }
        }

        return alpha;
    }

    private static boolean isOkToNullMove(boolean inCheck, int depth, int ply) {
        int pawnForSide;
        int kingForSide;
        int occForSide;
        if((inCheck || depth < 3) || ply < 1) {
            return false;
        }

        if(side==WHITE) {
            pawnForSide = P;
            kingForSide = K;
            occForSide = occWIdx;
        } else {
            pawnForSide = p;
            kingForSide = k;
            occForSide = occBIdx;
        }
        if((bitboards[pawnForSide] | bitboards[kingForSide]) == occupancies[occForSide]) {
            return false;
        }
        return true;
    }

}
