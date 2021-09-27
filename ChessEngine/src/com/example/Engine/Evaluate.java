package com.example.Engine;
import static com.example.Engine.BitBoard.*;
import static com.example.Engine.GameBoard.*;
import static com.example.Engine.Constants.*;
import static com.example.Engine.MoveGen.*;
import static com.example.Engine.Search.*;

public class Evaluate {
    public static int evaluate() {
        int value = 0;
        for(int piece = P; piece <= k; piece++) {
            long bitboard = bitboards[piece];

            while(bitboard != 0) {
                int square = getLs1bIndex(bitboard);
                value += pieceScoreEvalTable[piece];
                switch (piece){
                    case p: value -= blackPawnScore[square]; break;
                    case P: value += whitePawnScore[square]; break;
                    case n: value -= blackKnightScore[square]; break;
                    case N: value += whiteKnightScore[square]; break;
                    case b: value -= blackBishopScore[square]; break;
                    case B: value += whiteBishopScore[square]; break;
                    case r: value -= blackRookScore[square]; break;
                    case R: value += whiteRookScore[square]; break;
                    case k: value -= blackKingScore[square]; break;
                    case K: value += whiteKingScore[square]; break;

                }

                bitboard = pop_bit(bitboard, square);
            }



        }
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
