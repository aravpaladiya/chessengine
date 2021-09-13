package com.example.Engine;
import static com.example.Engine.BitBoard.getLs1bIndex;
import static com.example.Engine.BitBoard.pop_bit;
import static com.example.Engine.GameBoard.*;
import static com.example.Engine.Constants.*;

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
}
