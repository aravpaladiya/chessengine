package com.example.Engine;
import com.example.Game;

import static com.example.Engine.Constants.*;

import static com.example.Engine.Evaluate.evaluate;
import static com.example.Engine.GameBoard.*;
import static com.example.Engine.MoveGen.*;
import static com.example.Game.gui;
import static com.example.Game.searchDepth;


public class Search {
    public static int searchNodes  = 0;

    private static int ply = 0;
    public static int bestMove;

    public static int searchPosition(int depth) {
        return negamax(depth, -infinity, infinity);


    }

    public static int quiescence(int alpha, int beta) {
        int evaluate = evaluate();
        if(evaluate >= beta) {
            return beta;
        }
        if(evaluate > alpha) {
            alpha = evaluate;
        }
        MoveList searchList = new MoveList();
        generateMoves(searchList);
        for(int i = 0; i < searchList.count; i++) {

            if(makeMove(searchList.moves[i], capMoves)) {
                ply++;
                int score = -quiescence(-beta, -alpha);
                ply--;
                unmakeMove(searchList.moves[i], allMoves);
                if(score >= beta) {
                    return beta;
                }
                if(score > alpha) {
                    alpha = score;
                }
            }
        }
        return alpha;
    }

    public static int negamax(int depth, int alpha, int beta) {
        int score;
        if(depth == 0) {
            searchNodes++;
            return quiescence(alpha, beta);
        }
        int legalMoveCount = 0;

        MoveList searchList = new MoveList();
        generateMoves(searchList);
        for(int i = 0; i < searchList.count; i++) {

            if(makeMove(searchList.moves[i], allMoves)) {

                ply++;
                legalMoveCount++;
                score = -negamax(depth-1, -beta, -alpha);

                ply--;

                unmakeMove(searchList.moves[i], allMoves);
                if(score >= beta) {
                    return beta;
                }
                if(ply==0) {
                    if(searchDepth != depth) {
                        return 51000;
                    }                }
                if(score > alpha) {
                    alpha = score;
                    if(ply==0) {

//                        System.out.println(searchList.count);
//                        printMoveList(searchList);
                        System.out.println();
                        System.out.println(squareToCoords[decodeFrom(searchList.moves[i])] + squareToCoords[decodeTo(searchList.moves[i])]);
                        bestMove = searchList.moves[i];
                        gui.updateScore((float)(score)/100*((Game.computerColor ==WHITE)?1:-1));
//                        printMove(bestMove);
                    }
                }



            }
        }
        if(legalMoveCount == 0) {

            if(isSquareAttacked(BitBoard.getLs1bIndex(bitboards[side==WHITE?K:k]), side^1)) {
                return -49000+ply;
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
