package com.example.Engine;
import static com.example.Engine.BitBoard.*;
import static com.example.Engine.GameBoard.*;
import static com.example.Engine.Constants.*;
import static com.example.Engine.MoveGen.*;
import static com.example.Engine.Search.*;

public class Evaluate {
    private static int openingValue;
    private static int endgameValue;

    private static void eval() {
        openingValue = 0;
        endgameValue = 0;
        for(int piece = P; piece <= k; piece++) {
            long bitboard = bitboards[piece];

            while(bitboard != 0) {
                int square = getLs1bIndex(bitboard);
                openingValue += pieceScoreEvalTable[piece];
                endgameValue += pieceScoreEvalTable[piece];
                switch (piece){
                    case p: openingValue -= blackPawnScore[square]; endgameValue -= blackPawnScore[square]; break;
                    case P: openingValue += whitePawnScore[square]; endgameValue += whitePawnScore[square]; break;
                    case n: openingValue -= blackKnightScore[square]; endgameValue -= blackKnightScore[square]; break;
                    case N: openingValue += whiteKnightScore[square]; endgameValue += whiteKnightScore[square]; break;
                    case b: openingValue -= blackBishopScore[square]; endgameValue -= blackBishopScore[square]; break;
                    case B: openingValue += whiteBishopScore[square]; endgameValue += whiteBishopScore[square]; break;
                    case r: openingValue -= blackRookScore[square]; endgameValue -= blackRookScore[square]; break;
                    case R: openingValue += whiteRookScore[square]; endgameValue += whiteRookScore[square]; break;
                    case k: openingValue -= blackKingScoreO[square]; endgameValue -= blackKingScoreE[square]; break;
                    case K: openingValue += whiteKingScoreO[square]; endgameValue += whiteKingScoreE[square]; break;
                }

                bitboard = pop_bit(bitboard, square);
            }
        }
    }


    public static int evaluate() {
        int value;

        int pawnPhase = 0;
        int knightPhase = 1;
        int bishopPhase = 1;
        int rookPhase = 2;
        int queenPhase = 4;
        int totalPhase = knightPhase * 4 + bishopPhase * 4 + rookPhase * 4 + queenPhase * 2;

        int phase = totalPhase;

        //if pawnphase is not zero than do same as below
        phase -= count_bit(bitboards[N])*knightPhase;
        phase -= count_bit(bitboards[B])*bishopPhase;
        phase -= count_bit(bitboards[R])*rookPhase;
        phase -= count_bit(bitboards[Q])*queenPhase;
        phase -= count_bit(bitboards[n])*knightPhase;
        phase -= count_bit(bitboards[b])*bishopPhase;
        phase -= count_bit(bitboards[r])*rookPhase;
        phase -= count_bit(bitboards[q])*queenPhase;

        phase = (phase * 256 + (totalPhase / 2)) / totalPhase;

        eval();
        value = ((openingValue * (256 - phase)) + (endgameValue * phase))/256;
        return (side==WHITE)?value:-value;
    }
//    public static int scoreQuiescenceMove(int move) {
//        if(decodeEnPs(move)==1) {
//            return MVVLVA[P][P] + 10000;
//        }
//        int targetSquare = decodeTo(move);
//        int pawnForSide;
//        int kingForSide;
//        if(side==WHITE) {
//            pawnForSide = p;
//            kingForSide = k;
//        } else {
//            pawnForSide = P;
//            kingForSide = K;
//        }
//        for (int i = pawnForSide; i <= kingForSide; i++) {
//            if (get_bit(bitboards[i], targetSquare) != 0) {
//                return MVVLVA[decodePiece(move)][i] + 10000;
//            }
//        }
//
//        return 0;
//
//    }



    public static int scoreMove(int move) {
        if(followingPVLine) {
            if (principalVariation[0][ply] == move) {
                scoringPV = true;
                return 20000;
            }
        }

        if(decodeCap(move) == 1) {
            if(decodeEnPs(move)==1) {
                return MVVLVA[P][P] + 10000;
            }
            int targetSquare = decodeTo(move);
            int pawnForSide;
            int kingForSide;
            if(side==WHITE) {
                pawnForSide = p;
                kingForSide = k;
            } else {
                pawnForSide = P;
                kingForSide = K;
            }
            for (int i = pawnForSide; i <= kingForSide; i++) {
                if (get_bit(bitboards[i], targetSquare) != 0) {
                    return MVVLVA[decodePiece(move)][i] + 10000;
                }
            }
        }
        else {
            if(killerMoves[0][ply] == move) {
                return 9000;
            } else if(killerMoves[1][ply] == move) {
                return 8000;
            } else {
                return historyMoves[decodePiece(move)][decodeTo(move)];
            }
        }
        return 0;
    }
}
