package com.example.Engine;


import static com.example.Engine.Constants.*;

public abstract class GameBoard {
    public static long[] bitboards = new long[13];
    public static long[] occupancies = new long[] {
            0L, 0L, 0L
    };
    public static final int occBIdx = 0;
    public static final int occWIdx = 1;
    public static final int occBothIdx = 2;

    public static final int noPc = 0;
    public static final int P = 1;
    public static final int N = 2;
    public static final int B = 3;
    public static final int R = 4;
    public static final int Q = 5;
    public static final int K = 6;
    public static final int p = 7;
    public static final int n = 8;
    public static final int b = 9;
    public static final int r = 10;
    public static final int q = 11;
    public static final int k = 12;

    public static int castling = 0b0;
    public static int enPs = noSq;
    public static int side = WHITE;
    public static int halfMoveClock = 0;
    public static int fullMoveCount = 0;

    public static long[] bitboardsCopy = new long[12];
    public static long[] occupanciesCopy = new long[3];

    public static int side_copy, enpassant_copy, castle_copy;



    public static int KsWCas = 0b1000;
    public static int QsWCas = 0b100;
    public static int KsBCas = 0b10;
    public static int QsBCas = 0b1;




    public static void printState() {
        char[] BBIDS = new char[] {'e', '♟', '♞', '♝', '♜', '♛', '♚', '♙', '♘', '♗', '♖', '♕', '♔'};
        String[] allSBoards = new String[13];


//        System.out.println(Integer.toString(gameState.otherInfo, 2));

        for(int i = 7; i >= 0; i -- ) {
            for(int j = 0; j < 8; j++) {
                for(int k = P; k < bitboards.length; k ++) {

                    if(BitBoard.get_bit(bitboards[k], i*8+j) != 0) {
                        System.out.print(BBIDS[k]);
                        break;
                    }
                    if(k==bitboards.length-1) {
                        System.out.print(" · ");
                    }
                }


                System.out.print("  ");
            }
            System.out.println();
        }
        System.out.println("castling " + Integer.toBinaryString(castling));
        System.out.println("en passant " + enPs);
        System.out.println("turn " + side);

    }


    public static void loadFEN(String FEN) {
        char[] FENSplit= FEN.toCharArray();
        for(int i =P; i < bitboards.length; i++) {
            bitboards[i] = 0L;
        }
        for(int i = 0; i < occupancies.length; i++) {
            occupancies[i] = 0L;
        }
        castling = 0b0;
        enPs = noSq;
        halfMoveClock = 0;
        fullMoveCount = 0;

        int charPos = 0;
        int skipLoop = 0;
        for(int rank = 8; rank >=1; rank --) {
            for(int file = 1; file <9; file ++) {
                switch(FENSplit[charPos]) {
                    case 'r':
                        bitboards[r] ^= (1L<<((8*(rank-1)+file))-1);
//                        this.nBlack^= (1L<<((8*(rank-1)+file))-1);
                        break;
                    case 'R':
                        bitboards[R] ^= (1L <<((8*(rank-1)+file))-1);
//                        this.nWhite^= (1L<<((8*(rank-1)+file))-1);
                        break;
                    case 'n':
                        bitboards[n] ^= (1L<<((8*(rank-1)+file))-1);
//                        this.nBlack^= (1L<<((8*(rank-1)+file))-1);
                        break;
                    case 'N':
                        bitboards[N] ^= (1L <<((8*(rank-1)+file))-1);
//                        this.nWhite^= (1L<<((8*(rank-1)+file))-1);
                        break;
                    case 'b':
                        bitboards[b] ^= (1L<<((8*(rank-1)+file))-1);
//                        this.nBlack^= (1L<<((8*(rank-1)+file))-1);
                        break;
                    case 'B':
                        bitboards[B] ^= (1L <<((8*(rank-1)+file))-1);
//                        this.nWhite^= (1L<<((8*(rank-1)+file))-1);
                        break;
                    case 'q':
                        bitboards[q] ^= (1L<<((8*(rank-1)+file))-1);
//                        this.nBlack^= (1L<<((8*(rank-1)+file))-1);
                        break;
                    case 'Q':
                        bitboards[Q] ^= (1L <<((8*(rank-1)+file))-1);
//                        this.nWhite^= (1L<<((8*(rank-1)+file))-1);
                        break;
                    case 'k':
                        bitboards[k] ^= (1L<<((8*(rank-1)+file))-1);
//                        this.nBlack^= (1L<<((8*(rank-1)+file))-1);
                        break;
                    case 'K':
                        bitboards[K] ^= (1L <<((8*(rank-1)+file))-1);
//                        this.nWhite^= (1L<<((8*(rank-1)+file))-1);
                        break;
                    case 'p':
                        bitboards[p] ^= (1L<<((8*(rank-1)+file))-1);
//                        this.nBlack^= (1L<<((8*(rank-1)+file))-1);
                        break;
                    case 'P':
                        bitboards[P] ^= (1L <<((8*(rank-1)+file))-1);
//                        this.nWhite^= (1L<<((8*(rank-1)+file))-1);
                        break;
                    case '1':


                    case ' ':

                        break;
                    case '2':
                        file++;
                        break;
                    case '3':
                        file+=2;
                        break;
                    case '4':
                        file+=3;
                        break;
                    case '5':
                        file+=4;
                        break;
                    case '6':
                        file+=5;
                        break;
                    case '7':
                        file+=6;
                        break;
                    case '8':

                        file+=7;
                        break;

                }
                charPos+= 1;
            }
            charPos+=1;
        }
        occupancies[WHITE] = 0;
        occupancies[occBothIdx] = 0;
        for(int i = 0; i <= K; i++) {
            occupancies[WHITE] |= bitboards[i];
            occupancies[occBothIdx] |= bitboards[i];
        }
        occupancies[BLACK] = 0;
        for(int i = p; i <= k; i++) {
            occupancies[BLACK] |= bitboards[i];
            occupancies[occBothIdx] |= bitboards[i];
        }
        switch(FENSplit[charPos]) {
            //format: turn w ks w qs b ks b qs eps 6 bit 50 move rule total move
            case 'w':
                side = WHITE;
                break;
            case 'b':
                side = BLACK;
                break;

        }
        charPos+=2;
        while(FENSplit[charPos]!=' ') {
            switch(FENSplit[charPos]) {
                case '-':
                    break;
                case 'K':
//                    castling +=8;
                    castling += KsWCas;
                    break;
                case 'Q':
//                    castling +=4;
                    castling += QsWCas;
                    break;
                case 'k':
//                    castling +=2;
                    castling += KsBCas;
                    break;
                case 'q':
//                    castling +=1;
                    castling += QsBCas;
                    break;
            }
            charPos++;
        }
        charPos++;

        //eps
        if(FENSplit[charPos] != '-') {
            int file;
            if(FENSplit[charPos] == 'a') {
                file = files.a.ordinal();
            } else if(FENSplit[charPos] == 'b') {
                file = files.b.ordinal();
            } else if(FENSplit[charPos] == 'c') {
                file = files.c.ordinal();
            } else if(FENSplit[charPos] == 'd') {
                file = files.d.ordinal();
            } else if(FENSplit[charPos] == 'e') {
                file = files.e.ordinal();
            } else if(FENSplit[charPos] == 'f') {
                file = files.f.ordinal();
            } else if(FENSplit[charPos] == 'g') {
                file = files.g.ordinal();
            } else {
                file = files.h.ordinal();
            }
            charPos++;
            int rank = Character.getNumericValue(FENSplit[charPos]);
            rank--;
            enPs = rank*8+file;


        }
        charPos+=2;
        String halfMove = "";
        while(FENSplit[charPos] != ' ') {
            halfMove = halfMove + FENSplit[charPos];
            charPos++;
        }
        halfMoveClock = Integer.parseInt(halfMove);
        charPos++;
        halfMove = "";
        while(charPos < FENSplit.length) {
            if(FENSplit[charPos] != ' ') {
                halfMove = halfMove + FENSplit[charPos];

            }
            charPos++;
        }
        fullMoveCount = Integer.parseInt(halfMove);


    }

}
