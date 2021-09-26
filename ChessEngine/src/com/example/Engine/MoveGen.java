package com.example.Engine;

import static com.example.Engine.Evaluate.*;
import static com.example.Game.*;
import static com.example.Engine.BitBoard.*;
import static com.example.Engine.GameBoard.*;
import static com.example.Engine.Constants.*;
import static com.example.Engine.Search.*;

public abstract class MoveGen {
    //bitboard functions







    //pawn pushes




    public static long wPawnSingle(long empty, long wPawn) {
        return northOne(wPawn) & empty;
    }
    public static long wPawnDouble(long empty, long wPawn) {
        long rank4 = 0x00000000FF000000L;
        long single = wPawnSingle(empty, wPawn);
        return northOne(single) & rank4 & empty;

    }

    public static long bPawnSingle(long empty, long bPawn) {
        return southOne(bPawn) & empty;
    }
    public static long bPawnDouble(long empty, long bPawn) {
        long rank5 = 0x000000FF00000000L;
        long single = bPawnSingle(empty, bPawn);
        return southOne(single) & rank5 & empty;
    }
    //pushable pawns
    public static long wPawnsAbleSglPush(long empty, long wPawn) {
        return wPawn & southOne(empty);
    }
    public static long wPawnsAbleDblPush(long empty, long wPawn) {
        long rank4 = 0x00000000FF000000L;
        long rank34empty = southOne(rank4 & empty) & empty;
        return wPawn & southOne(rank34empty);

    }
    public static long bPawnsAbleSglPush(long empty, long bPawn) {
        return bPawn & northOne(empty);
    }
    public static long bPawnsAbleDblPush(long empty, long bPawn) {
        long rank5 = 0x000000FF00000000L;
        long _34empty = empty&northOne(rank5 & empty);
        return bPawn & northOne(_34empty);
    }

    //pawn attacks squares
    public static long wPawnEastAttackSquares(long wPawn) {
        return neOne(wPawn & notHFile);
    }
    public static long wPawnWestAttackSquares(long wPawn) {
        return nwOne(wPawn & notAFile);
    }
    public static long wPawnAllAttackSquares(long wPawn) {
        return wPawnEastAttackSquares(wPawn) | wPawnWestAttackSquares(wPawn);
    }
    public static long wPawnSingleAttackSquares(long wPawn) {
        return wPawnEastAttackSquares(wPawn) ^ wPawnWestAttackSquares(wPawn);
    }
    public static long wPawnDblAttackSquares(long wPawn) {
        return wPawnEastAttackSquares(wPawn) & wPawnWestAttackSquares(wPawn);
    }
    public static long bPawnEastAttackSquares(long bPawn) {
        return seOne(bPawn & notHFile);
    }
    public static long bPawnWestAttackSquares(long bPawn) {
        return swOne(bPawn & notAFile);
    }
    public static long bPawnAllAttackSquares(long bPawn) {
        return bPawnEastAttackSquares(bPawn) | bPawnWestAttackSquares(bPawn);
    }
    public static long bPawnSingleAttackSquares(long bPawn) {
        return bPawnEastAttackSquares(bPawn) ^ bPawnWestAttackSquares(bPawn);
    }
    public static long bPawnDblAttackSquares(long bPawn) {
        return bPawnEastAttackSquares(bPawn) & bPawnWestAttackSquares(bPawn);
    }

    //pawn capture targets

    public static long wPawnAbleCaptureEast(long wPawn, long bPiece) {
        return wPawn & bPawnWestAttackSquares(bPiece);
    }
    public static long wPawnAbleCaptureWest(long wPawn, long bPiece) {
        return wPawn & bPawnEastAttackSquares(bPiece);
    }
    public static long wPawnAbleCaptureAny(long wPawn, long bPiece) {
        return wPawnAbleCaptureEast(wPawn, bPiece) | wPawnAbleCaptureWest(wPawn, bPiece);
    }
    public static long bPawnAbleCaptureEast(long bPawn, long wPiece) {
        return bPawn & wPawnWestAttackSquares(wPiece);
    }
    public static long bPawnAbleCaptureWest(long bPawn, long wPiece) {
        return bPawn & wPawnEastAttackSquares(wPiece);
    }
    public static long bPawnAbleCaptureAny(long bPawn, long wPiece) {
        return bPawnAbleCaptureEast(bPawn, wPiece) | bPawnAbleCaptureWest(bPawn, wPiece);
    }
    public static long wPawnSafeSquares(long wPawn, long bPawn) {
        long wPawnDblAttackSquares = wPawnDblAttackSquares(wPawn);
        long wPawnSingleAttackSquares = wPawnSingleAttackSquares(wPawn);
        long bPawnDblAttackSquares = bPawnDblAttackSquares(bPawn);
        long bPawnAnyAttackSquares = bPawnAllAttackSquares(bPawn);
        return wPawnDblAttackSquares | ~bPawnAnyAttackSquares | (wPawnSingleAttackSquares & ~bPawnDblAttackSquares);
    }

    //knight moves

    public static long knightAttacks(long knights) {
        long l1 = (knights >>> 1) & 0x7f7f7f7f7f7f7f7fL;
        long l2 = (knights >>> 2) & 0x3f3f3f3f3f3f3f3fL;
        long r1 = (knights << 1) & 0xfefefefefefefefeL;
        long r2 = (knights << 2) & 0xfcfcfcfcfcfcfcfcL;
        long h1 = l1 | r1;
        long h2 = l2 | r2;
        return (h1<<16) | (h1>>>16) | (h2<<8) | (h2>>>8);
    }

    //king attacks
    public static long kingAttacksBB(long king) {
        long attacks = eastOne(king & notHFile)  | westOne(king & notAFile);
        king    |= attacks;
        return attacks | (northOne(king) | southOne(king));
    }


    //generate king tables
//    public static void generateKingArr() {
//        long sqBB = 1L;
//        for(int sq = 0; sq < 64; sqBB <<= 1) {
//            kingAttacks[sq] = kingAttacksBB(sqBB);
//            sq++;
//        }
//    }
    //init leaper attack arrays
    public static void initLeaperAttacks() {
        pawnAttacks[0] = new long[64];

        long sqBB = 1L;
        for(int sq = 0; sq < 64; sqBB <<= 1) {
            kingAttacks[sq] = kingAttacksBB(sqBB);
            knightAttacks[sq] = knightAttacks(sqBB);
            pawnAttacks[0][sq] = bPawnAllAttackSquares(sqBB);
            pawnAttacks[1][sq] = wPawnAllAttackSquares(sqBB);
            sq++;
        }

    }

    //sliding pieces

    //mask bishop attacks
    public static long maskBishopAttacks(int sq) {
        long returnAttack = 0L;

        int file = sq % 8;

        int rank = sq / 8;


        for(int r = rank + 1, f = file + 1; f <=6 && r <=6; r++, f++) {
            returnAttack |= (1L << (r*8+f));
        }
        for(int r = rank - 1, f = file + 1; f <=6 && r >=1; r--, f++) {
            returnAttack |= (1L << (r*8+f));
        }
        for(int r = rank - 1, f = file - 1; f >=1 && r >=1; r--, f--) {
            returnAttack |= (1L << (r*8+f));
        }
        for(int r = rank + 1, f = file - 1; f >=1 && r <=6; r++, f--) {
            returnAttack |= (1L << (r*8+f));
        }
        return returnAttack;
    }


    //mask rook attacks

    public static long maskRookAttacks(int sq) {
        int rank = sq / 8;
        int file = sq % 8;

        long returnAttack = 0L;

        for(int f = file - 1; f >=1; f--) {
            returnAttack |= (1L << (rank *8+f));
        }
        for(int f = file + 1; f <=6; f++) {
            returnAttack |= 1L << rank *8+f;
        }
        for(int r = rank - 1; r >=1; r--) {
            returnAttack |= 1L << r*8+ file;
        }
        for(int r = rank + 1; r <=6; r++) {
            returnAttack |= 1L << r*8+ file;
        }


        return returnAttack;


    }

    //bishop attacks on the fly
    public static long bishopAttacksOnTheFly(int sq, long block) {
        long returnAttack = 0L;

        int file = sq % 8;

        int rank = sq / 8;


        for(int r = rank + 1, f = file + 1; f <=7 && r <=7; r++, f++) {
            returnAttack |= (1L << (r*8+f));
            if(((1L << (r*8+f)) & block) !=0) {
                break;
            }
        }
        for(int r = rank - 1, f = file + 1; f <=7 && r >=0; r--, f++) {
            returnAttack |= (1L << (r * 8 + f));
            if (((1L << (r * 8 + f)) & block) != 0) {
                break;

            }
        }
        for(int r = rank - 1, f = file - 1; f >=0 && r >=0; r--, f--) {
            returnAttack |= (1L << (r * 8 + f));
            if (((1L << (r * 8 + f)) & block) != 0) {
                break;

            }
        }
        for(int r = rank + 1, f = file - 1; f >=0 && r <=7; r++, f--) {
            returnAttack |= (1L << (r*8+f));
            if(((1L << (r*8+f)) & block) !=0) {
                break;
            }
        }
        return returnAttack;

    }



    //rook attacks on the fly

    public static long rookAttacksOnTheFly(int sq, long block) {
        int rank = sq / 8;
        int file = sq % 8;

        long returnAttack = 0L;

        for(int f = file - 1; f >=0; f--) {
            returnAttack |= (1L << (rank *8+f));
            if(((1L << (rank *8+f)) & block) !=0) {
                break;
            }
        }
        for(int f = file + 1; f <=7; f++) {
            returnAttack |= 1L << rank *8+f;
            if(((1L << (rank *8+f)) & block) !=0) {
                break;
            }
        }
        for(int r = rank - 1; r >=0; r--) {
            returnAttack |= 1L << r*8+ file;
            if(((1L << (r*8+ file)) & block) !=0) {
                break;
            }
        }
        for(int r = rank + 1; r <=7; r++) {
            returnAttack |= 1L << r*8+ file;
            if(((1L << (r*8+ file)) & block) !=0) {
                break;
            }
        }


        return returnAttack;
    }

    //set occupancy

    public static long setOccupancy(int index, int bitCount, long relOccupancy) {
        long returnAttacks = 0L;
        for(int i = 0; i < bitCount; i++) {
            int square = getLs1bIndex(relOccupancy);
            if((index & 1L << i) !=0) {
                returnAttacks |= 1L<<square;
            }
            relOccupancy &= (relOccupancy-1);
        }

        return returnAttacks;
    }



    //magic numbers

    //random

    public static int genRandom32Bit() {
        int number = randStartSeed;

        number ^= number << 13;
        number ^= number >>> 17;
        number ^= number << 5;

        randStartSeed = number;

        return number;
    }
    public static long genRandom64Bit() {
        long u1, u2, u3, u4;

        u1 = genRandom32Bit() & 0xFFFF;
        u2 = genRandom32Bit() & 0xFFFF;
        u3 = genRandom32Bit() & 0xFFFF;
        u4 = genRandom32Bit() & 0xFFFF;

        return u1 | (u2 << 16) | (u3 << 32) | (u4 << 48);
    }
    public static long genLowBitRand() {
        return genRandom64Bit() & genRandom64Bit() & genRandom64Bit();
    }
    //making magic numbers

    public static int transformMagicToIndex(long magic, int relBits, long occupancy) {
        return (int)((magic * occupancy) >>> (64-relBits));
    }

    public static long genMagicNumber(int sq, int relBitCount, boolean bishop) {
        long[] attacks =     new long[4096];
        long[] used =        new long[4096];
        long[] occupancies = new long[4096];

        long attackMask = bishop ? maskBishopAttacks(sq) : maskRookAttacks(sq);
        for(int i = 0; i < (1L << relBitCount); i++) {
            occupancies[i] = setOccupancy(i, relBitCount, attackMask);
            attacks[i] = bishop ? bishopAttacksOnTheFly(sq, occupancies[i]) : rookAttacksOnTheFly(sq, occupancies[i]);
        }

        //gen magic


        for(int random =0; random < 1000000000; random++) {
            long magic = genLowBitRand();
            if(count_bit((magic * attackMask) & 0xFF00000000000000L) < 6) {
                continue;
            }
            for(int i =0; i < 4096; i++) {
                used[i] = 0L;
            }
            int magicIndex;
            int i;
            boolean fail;
            for(i = 0, fail = false; (!fail) && i < (1 << relBitCount); i++) {
                magicIndex = transformMagicToIndex(magic, relBitCount, occupancies[i]);
                if(used[magicIndex] == 0L) {
                    //works
                    used[magicIndex] = attacks[i];
                } else if(used[magicIndex] != attacks[i]){
                    fail = true;
                }
            }
            if(!fail) {
                return magic;
            }

        }
        System.out.println("fail, some error");
        return 0;
    }
    //initializing sliding piece attacks

    public static void initSlidingAttacks() {
        for(int i = 0; i < 64; i++) {
            bishopAttackMasks[i] = maskBishopAttacks(i);
            rookAttackMasks[i] = maskRookAttacks(i);
        }
        for(int i = 0; i < 64; i ++) {
            //rook
            for(int j = 0; j < (1L << rookAttackCounts[i]); j++) {
                long occupancy = setOccupancy(j, rookAttackCounts[i], rookAttackMasks[i]);
                rookAttackTables[i][transformMagicToIndex(rookMagics[i], rookAttackCounts[i], occupancy)] =
                        rookAttacksOnTheFly(i, occupancy);
            }
            //bishop
            for(int k = 0; k < (1L << bishopAttackCounts[i]); k++) {
                long occupancy = setOccupancy(k, bishopAttackCounts[i], bishopAttackMasks[i]);
                bishopAttackTables[i][transformMagicToIndex(bishopMagics[i], bishopAttackCounts[i], occupancy)] =
                        bishopAttacksOnTheFly(i, occupancy);

            }

        }

    }

    public static long getBishopAttacks(int sq, long occupancy) {
        long relOcc = occupancy & bishopAttackMasks[sq];

        int magicIndex = (int)((bishopMagics[sq] * relOcc) >>> (64-bishopAttackCounts[sq]));
//        System.out.println(magicIndex);
        return bishopAttackTables[sq][magicIndex];
    }
    public static long getRookAttacks(int sq, long occupancy) {
        long relOcc = occupancy & rookAttackMasks[sq];

        int magicIndex = (int)((rookMagics[sq] * relOcc) >>> (64-rookAttackCounts[sq]));
        return rookAttackTables[sq][magicIndex];
    }
    public static long getQueenAttacks(int sq, long occupancy) {
        long bishopOcc = bishopAttackMasks[sq] & occupancy;
        long rookOcc = rookAttackMasks[sq] & occupancy;
        long bishopMIndex = (bishopMagics[sq] * bishopOcc) >>> (64-bishopAttackCounts[sq]);
        long rookMIndex = (rookMagics[sq] * rookOcc) >>> (64-rookAttackCounts[sq]);

        long result = 0L;

        result = bishopAttackTables[sq][(int)bishopMIndex] | rookAttackTables[sq][(int)rookMIndex];

        return result;
    }

    //check if square is attacked by a given side

    public static boolean isSquareAttacked(int sq, int side) {
        //attacked by white pawn?
        if(side == 1 && (pawnAttacks[BLACK][sq] & bitboards[P]) != 0) {
            return true;
        }
        //attacked by black pawn?
        if(side == 0 && (pawnAttacks[WHITE][sq] & bitboards[p]) != 0) {

            return true;
        }
        //attacked by white knight?
        if(side == 1 && (knightAttacks[sq] & bitboards[N]) !=0) {

            return true;
        }
        //attacked by black knight?
        if(side == 0 && (knightAttacks[sq] & bitboards[n]) !=0) {
            return true;
        }
        //attacked by white king?
        if(side == 1 && (kingAttacks[sq] & bitboards[K]) !=0) {
            return true;
        }
        //attacked by black king?
        if(side == 0 && (kingAttacks[sq] & bitboards[k]) !=0) {
            return true;
        }
        //attacked by white bishop?
        if(side == 1 && (getBishopAttacks(sq, occupancies[occBothIdx]) & bitboards[B]) != 0) {
            return true;
        }
        //attacked by black bishop?
        if(side == 0 && (getBishopAttacks(sq, occupancies[occBothIdx]) & bitboards[b]) != 0) {
            return true;
        }
        //attacked by white rook?
        if(side == 1 && (getRookAttacks(sq, occupancies[occBothIdx]) & bitboards[R]) != 0) {
            return true;
        }
        //attacked by black rook?
        if(side == 0 && (getRookAttacks(sq, occupancies[occBothIdx]) & bitboards[r]) != 0) {

            return true;
        }
        //attacked by white queen?
        if(side == 1 && (getQueenAttacks(sq, occupancies[occBothIdx]) & bitboards[Q]) != 0) {
            return true;
        }
        //attacked by black queen?
        if(side == 0 && (getQueenAttacks(sq, occupancies[occBothIdx]) & bitboards[q]) != 0) {
            return true;
        }
        //if none return false
        return false;
    }

    public static void initAll() {
        initLeaperAttacks();
        initSlidingAttacks();
    }



    //MOVE GENERATION






    /*
    move representation
    start square        0000 0000 0000 0000 0011 1111
    target square       0000 0000 0000 1111 1100 0000
    piece index         0000 0000 1111 0000 0000 0000
    promoted piece      0000 1111 0000 0000 0000 0000
    capture flag        0001 0000 0000 0000 0000 0000
    double push flag    0010 0000 0000 0000 0000 0000
    enps flag           0100 0000 0000 0000 0000 0000
    castle flag         1000 0000 0000 0000 0000 0000


     */

    public static final int startSqMask = 0x3F;
    public static final int toSqMask = 0xFC0;
    public static final int pcIndexMask = 0xF000;
    public static final int promPieceMask = 0xF0000;
    public static final int capMask = 0x1FFFFF;
    public static final int dblPushMask = 0x2FFFFF;
    public static final int enPsMask = 0x4FFFFF;
    public static final int casMask = 0x8FFFFF;

    public static int decodeFrom(int move) {
        return move & startSqMask;
    }
    public static int decodeTo(int move) {
        return (move & toSqMask) >>> 6;
    }
    public static int decodePiece(int move) {
        return (move & pcIndexMask) >>> 12;
    }
    public static int decodePromPiece(int move) {
        return (move & promPieceMask) >>> 16;
    }
    public static int decodeCap(int move) {
        return (move & capMask) >>> 20;
    }
    public static int decodeDblPush(int move) {
        return (move & dblPushMask) >>> 21;
    }
    public static int decodeEnPs(int move) {
        return (move & enPsMask) >>> 22;
    }
    public static int decodeCas(int move) {
        return (move & casMask) >>> 23;
    }




    //encode move

    public static int encodeMove(int from, int to, int pieceIndex, int promotedPiece, int capture, int dblPush, int enps, int castle) {
        return from | (to << 6) | (pieceIndex << 12) | (promotedPiece << 16) |
                (capture << 20) | (dblPush << 21) | (enps << 22) | (castle << 23);

    }





    //move list

    public static void addMove(int move, MoveList list) {
        list.moves[list.count] = new Move(move, scoreMove(move));
        list.count++;

    }

    public static void addCapMove(int move, MoveList list) {
        list.moves[list.count] = new Move(move, scoreMove(move));
        list.count++;
    }
    public static void printShortMove(int move) {
        System.out.println(squareToCoords[decodeFrom(move)] + squareToCoords[decodeTo(move)] + ((decodePromPiece(move)!=noPc)?intToPiece[decodePromPiece(move)]:""));
    }

    public static String getShortMove(int move) {
        return squareToCoords[decodeFrom(move)] + squareToCoords[decodeTo(move)] + ((decodePromPiece(move)!=noPc)?intToPiece[decodePromPiece(move)]:"");
    }
    public static void printMove(int move) {
        System.out.println("piece     |  from      |  to        |  promotion |  capture   |  dbl push  |  en pass   |  castle");
        String print = intToPiece[decodePiece(move)] + "         |  " + squareToCoords[decodeFrom(move)] + "        |  "
                + squareToCoords[decodeTo(move)] + "        |  " +
                ((intToPiece[decodePromPiece(move)] == "none") ? "-         |  " : intToPiece[decodePromPiece(move)] +
                        "         |  ") + ((decodeCap(move) == 1) ? "yes" : "no ") + "       |  " +
                ((decodeDblPush(move) == 1) ? "yes" : "no ") + "       |  " +
                ((decodeEnPs(move) == 1) ? "yes" : "no ") + "       |  " + ((decodeCas(move) == 1) ? "yes" : "no " + "        " + move);

        System.out.println(print + "\n");

    }
    public static void printMoveList(MoveList l) {
        String print;
        System.out.println("piece     |  from      |  to        |  promotion |  capture   |  dbl push  |  en pass   |  castle\n");

        for(int i = 0; i < l.count; i++) {
            int move = l.moves[i].move;
            print = intToPiece[decodePiece(move)] + "         |  " + squareToCoords[decodeFrom(move)] + "        |  "
                    + squareToCoords[decodeTo(move)] + "        |  " +
                    ((intToPiece[decodePromPiece(move)] == "none") ? "-         |  " : intToPiece[decodePromPiece(move)] +
                            "         |  ") + ((decodeCap(move) == 1) ? "yes" : "no ") + "       |  " +
                    ((decodeDblPush(move) == 1) ? "yes" : "no ") + "       |  " +
                    ((decodeEnPs(move) == 1) ? "yes" : "no ") + "       |  " + ((decodeCas(move) == 1) ? "yes" : "no " + "        " + move);

            System.out.println(print);
        }
    }



    //                                addMove(encodeMove(), moveList1));



    //gen moves and update a given movelist

    public static void generateMoves(MoveList moveList1) {
        int startSquare, targetSquare;
        long bitboard;
        for(int piece = P; piece <= k; piece++) {
            bitboard = bitboards[piece];
            //white pawn and castle
            if(side == WHITE) {
                //pawn
                if(piece == P) {

                    while(bitboard != 0L) {
                        startSquare = getLs1bIndex(bitboard);
                        targetSquare = startSquare+8;
                        //quiet move
                        if(targetSquare < 64 && get_bit(occupancies[occBothIdx], targetSquare) == 0) {
                            //promotion
                            if(targetSquare >= a8 && targetSquare <= h8) {
                                addMove(encodeMove(startSquare, targetSquare, P, N, 0, 0, 0, 0), moveList1);
                                addMove(encodeMove(startSquare, targetSquare, P, B, 0, 0, 0, 0), moveList1);
                                addMove(encodeMove(startSquare, targetSquare, P, R, 0, 0, 0, 0), moveList1);
                                addMove(encodeMove(startSquare, targetSquare, P, Q, 0, 0, 0, 0), moveList1);


                            }
                            //single push
                            else {
                                addMove(encodeMove(startSquare, targetSquare, P, noPc, 0, 0, 0, 0), moveList1);
                                //dbl push
                                if(startSquare >= a2 && startSquare <= h2 && ((1L<< (startSquare + 8)) & occupancies[occBothIdx]) == 0 && ((1L<< (startSquare + 16)) & occupancies[occBothIdx]) == 0) {
                                    targetSquare +=8;
                                    addMove(encodeMove(startSquare, targetSquare, P, noPc, 0, 1, 0, 0), moveList1);
                                }
                            }
                        }
                        //pawn attacks
                        long attacks = pawnAttacks[WHITE][startSquare] & occupancies[BLACK];
                        //enps
                        if(enPs != noSq) {
                            if((pawnAttacks[1][startSquare] & (1L << enPs)) != 0) {
                                addMove(encodeMove(startSquare, enPs, P, noPc, 1, 0, 1, 0), moveList1);

                            }
                        }
                        while(attacks !=0) {
                            targetSquare = getLs1bIndex(attacks);
                            if(targetSquare >=a8 && targetSquare <= h8) {
                                //promotion
                                addMove(encodeMove(startSquare, targetSquare, P, N, 1, 0, 0, 0), moveList1);
                                addMove(encodeMove(startSquare, targetSquare, P, B, 1, 0, 0, 0), moveList1);
                                addMove(encodeMove(startSquare, targetSquare, P, R, 1, 0, 0, 0), moveList1);
                                addMove(encodeMove(startSquare, targetSquare, P, Q, 1, 0, 0, 0), moveList1);

                            } else {
                                //normal attack
                                addMove(encodeMove(startSquare, targetSquare, P, noPc, 1, 0, 0, 0), moveList1);

                            }


                            attacks = pop_bit(attacks, targetSquare);
                        }
                        bitboard = pop_bit(bitboard, startSquare);
                    }

                }
                //castle

                if(piece == K) {
                    long king = bitboards[K];
                    //ks
                    if((castling & KsWCas) !=0) {
                        //can castle ks
                        if(get_bit(occupancies[occBothIdx], f1) ==0 && get_bit(occupancies[occBothIdx], g1) ==0 && !isSquareAttacked(e1, BLACK) && !isSquareAttacked(f1, BLACK)) {
                            addMove(encodeMove(e1, g1, K, noPc, 0, 0, 0, 1), moveList1);
                        }
                    }
                    if((castling & QsWCas) != 0) {
                        if(get_bit(occupancies[occBothIdx], d1) ==0 && get_bit(occupancies[occBothIdx], c1) ==0 && get_bit(occupancies[occBothIdx], b1) ==0 && !isSquareAttacked(e1, BLACK) && !isSquareAttacked(d1, BLACK)) {
                            addMove(encodeMove(e1, c1, K, noPc, 0, 0, 0, 1), moveList1);
                        }
                    }
                }

            }
            //black pawn and castle
            else  {
                //pawn
                if(piece == p) {
                    while(bitboard != 0L) {
                        startSquare = getLs1bIndex(bitboard);
                        targetSquare = startSquare-8;
                        //quiet move
                        if(targetSquare >= 0 && get_bit(occupancies[occBothIdx], targetSquare) == 0) {
                            //promotion
                            if(targetSquare >= a1 && targetSquare <= h1) {
                                addMove(encodeMove(startSquare, targetSquare, p, n, 0, 0, 0, 0), moveList1);
                                addMove(encodeMove(startSquare, targetSquare, p, b, 0, 0, 0, 0), moveList1);
                                addMove(encodeMove(startSquare, targetSquare, p, r, 0, 0, 0, 0), moveList1);
                                addMove(encodeMove(startSquare, targetSquare, p, q, 0, 0, 0, 0), moveList1);

                            }
                            //single push
                            else {
                                addMove(encodeMove(startSquare, targetSquare, p, noPc, 0, 0, 0, 0), moveList1);

                                //dbl push
                                if(startSquare >= a7 && startSquare <= h7 && ((1L<< (startSquare - 8)) & occupancies[occBothIdx]) == 0 && ((1L<< (startSquare - 16)) & occupancies[occBothIdx]) == 0) {
                                    targetSquare -=8;
                                    addMove(encodeMove(startSquare, targetSquare, p, noPc, 0, 1, 0, 0), moveList1);

                                }
                            }
                        }
                        //pawn attacks
                        long attacks = pawnAttacks[BLACK][startSquare] & occupancies[WHITE];
                        //enps
                        if(enPs != noSq) {
                            if((pawnAttacks[0][startSquare] & (1L << enPs)) != 0) {
                                addMove(encodeMove(startSquare, enPs, p, noPc, 1, 0, 1, 0), moveList1);

                            }
                        }
                        while(attacks !=0) {
                            targetSquare = getLs1bIndex(attacks);
                            if(targetSquare >=a1 && targetSquare <= h1) {
                                //promotion
                                addMove(encodeMove(startSquare, targetSquare, p, n, 1, 0, 0, 0), moveList1);
                                addMove(encodeMove(startSquare, targetSquare, p, b, 1, 0, 0, 0), moveList1);
                                addMove(encodeMove(startSquare, targetSquare, p, r, 1, 0, 0, 0), moveList1);
                                addMove(encodeMove(startSquare, targetSquare, p, q, 1, 0, 0, 0), moveList1);

                            } else {
                                //normal attack
                                addMove(encodeMove(startSquare, targetSquare, p, noPc, 1, 0, 0, 0), moveList1);


                            }


                            attacks = pop_bit(attacks, targetSquare);
                        }
                        bitboard = pop_bit(bitboard, startSquare);
                    }

                }
                //castle

                if(piece == k) {
                    long king = bitboards[k];
                    //ks
                    if((castling & KsBCas) !=0) {
                        //can castle ks
                        if(get_bit(occupancies[occBothIdx], f8) ==0 && get_bit(occupancies[occBothIdx], g8) ==0 && !isSquareAttacked(e8, WHITE) && !isSquareAttacked(f8, WHITE)) {
                            addMove(encodeMove(e8, g8, k, noPc, 0, 0, 0, 1), moveList1);

                        }
                    }
                    if((castling & QsBCas) != 0) {
                        if(get_bit(occupancies[occBothIdx], d8) ==0 && get_bit(occupancies[occBothIdx], c8) ==0 && get_bit(occupancies[occBothIdx], b8) ==0 && !isSquareAttacked(e8, WHITE) && !isSquareAttacked(d8, WHITE)) {
                            addMove(encodeMove(e8, c8, k, noPc, 0, 0, 0, 1), moveList1);
                        }
                    }
                }


            }
            //gen knight moves
            if((side == WHITE)? piece == N : piece == n) {
                bitboard = (piece == N) ? bitboards[N] : bitboards[n];
                while(bitboard !=0) {
                    startSquare = getLs1bIndex(bitboard);
                    long knightAttack = knightAttacks[startSquare] & ((piece == N) ? ~occupancies[WHITE] : ~occupancies[BLACK]);
                    while(knightAttack != 0) {
                        targetSquare = getLs1bIndex(knightAttack);
                        if(((1L << targetSquare) & ((piece == N)? occupancies[BLACK] : occupancies[WHITE])) != 0) {
                            addMove(encodeMove(startSquare, targetSquare, piece, noPc, 1, 0, 0, 0), moveList1);

                        } else {
                            //quiet
                            addMove(encodeMove(startSquare, targetSquare, piece, noPc, 0, 0, 0, 0), moveList1);
                        }
                        knightAttack = pop_bit(knightAttack, targetSquare);
                    }

                    bitboard = pop_bit(bitboard, startSquare);
                }
            }
            //bishop
            else if((side == WHITE)?piece == B : piece == b) {
                bitboard = bitboards[piece];
                while(bitboard != 0) {
                    startSquare = getLs1bIndex(bitboard);
                    long attacks = getBishopAttacks(startSquare, occupancies[occBothIdx]);
                    while(attacks != 0) {
                        int attack = getLs1bIndex(attacks);
                        if(((1L << attack) & ((side==WHITE)?occupancies[occWIdx] : occupancies[occBIdx])) == 0) {
                            if(((1L << attack) & ((side==WHITE)?occupancies[occBIdx] : occupancies[occWIdx])) != 0) {
                                addMove(encodeMove(startSquare, attack, piece, noPc, 1, 0, 0, 0), moveList1);

                            } else {
                                addMove(encodeMove(startSquare, attack, piece, noPc, 0, 0, 0, 0), moveList1);

                            }
                        }
                        attacks = pop_bit(attacks, attack);
                    }

                    bitboard = pop_bit(bitboard, startSquare);
                }
            }
            //rook
            else if((side == WHITE)?piece == R : piece == r) {
                bitboard = bitboards[piece];
                while(bitboard != 0) {
                    startSquare = getLs1bIndex(bitboard);
                    long attacks = getRookAttacks(startSquare, occupancies[occBothIdx]);
                    while(attacks != 0) {
                        int attack = getLs1bIndex(attacks);
                        if(((1L << attack) & ((side==WHITE)?occupancies[occWIdx] : occupancies[occBIdx])) == 0) {
                            if(((1L << attack) & ((side==WHITE)?occupancies[occBIdx] : occupancies[occWIdx])) != 0) {
                                addMove(encodeMove(startSquare, attack, piece, noPc, 1, 0, 0, 0), moveList1);
                            } else {
                                addMove(encodeMove(startSquare, attack, piece, noPc, 0, 0, 0, 0), moveList1);
                            }
                        }
                        attacks = pop_bit(attacks, attack);
                    }

                    bitboard = pop_bit(bitboard, startSquare);
                }
            }
            //queen
            else if((side == WHITE)?piece == Q : piece == q) {
                bitboard = bitboards[piece];
                while(bitboard != 0) {
                    startSquare = getLs1bIndex(bitboard);
                    long attacks = getQueenAttacks(startSquare, occupancies[occBothIdx]);
                    while(attacks != 0) {
                        int attack = getLs1bIndex(attacks);
                        if(((1L << attack) & ((side==WHITE)?occupancies[occWIdx] : occupancies[occBIdx])) == 0) {
                            if(((1L << attack) & ((side==WHITE)?occupancies[occBIdx] : occupancies[occWIdx])) != 0) {
                                addMove(encodeMove(startSquare, attack, piece, noPc, 1, 0, 0, 0), moveList1);

                            } else {
                                addMove(encodeMove(startSquare, attack, piece, noPc, 0, 0, 0, 0), moveList1);
                            }
                        }
                        attacks = pop_bit(attacks, attack);
                    }

                    bitboard = pop_bit(bitboard, startSquare);
                }
            }
            //king
            else if((side == WHITE)?piece == K : piece == k) {
                bitboard = bitboards[piece];
//                printBitBoard(bitboards[k]);

                startSquare = getLs1bIndex(bitboard);
                //some problem in next line? related to startsquare
                long attacks = kingAttacks[startSquare] & ~occupancies[(side==WHITE)?occWIdx : occBIdx];
                while(attacks !=0) {
                    int attack = getLs1bIndex(attacks);
                    if(((1L << attack) & ((side==WHITE)?occupancies[occBIdx] : occupancies[occWIdx])) != 0) {
                        addMove(encodeMove(startSquare, attack, piece, noPc, 1, 0, 0, 0), moveList1);

                    } else {
                        addMove(encodeMove(startSquare, attack, piece, noPc, 0, 0, 0, 0), moveList1);

                    }

                    attacks = pop_bit(attacks, attack);
                }
            }
        }

        if(!scoringPV) {
            followingPVLine = false;
        }
    }

    public static void generateCapMoves(MoveList moveList1) {
        int startSquare, targetSquare;
        long bitboard;
        for(int piece = P; piece <= k; piece++) {
            bitboard = bitboards[piece];
            //white pawn and castle
            if(side == WHITE) {
                //pawn
                if(piece == P) {

                    while(bitboard != 0L) {
                        startSquare = getLs1bIndex(bitboard);

                        //pawn attacks
                        long attacks = pawnAttacks[WHITE][startSquare] & occupancies[BLACK];
                        //enps
                        if(enPs != noSq) {
                            if((pawnAttacks[1][startSquare] & (1L << enPs)) != 0) {
                                addCapMove(encodeMove(startSquare, enPs, P, noPc, 1, 0, 1, 0), moveList1);

                            }
                        }
                        while(attacks !=0) {
                            targetSquare = getLs1bIndex(attacks);
                            if(targetSquare >=a8 && targetSquare <= h8) {
                                //promotion
                                addCapMove(encodeMove(startSquare, targetSquare, P, N, 1, 0, 0, 0), moveList1);
                                addCapMove(encodeMove(startSquare, targetSquare, P, B, 1, 0, 0, 0), moveList1);
                                addCapMove(encodeMove(startSquare, targetSquare, P, R, 1, 0, 0, 0), moveList1);
                                addCapMove(encodeMove(startSquare, targetSquare, P, Q, 1, 0, 0, 0), moveList1);

                            } else {
                                //normal attack
                                addCapMove(encodeMove(startSquare, targetSquare, P, noPc, 1, 0, 0, 0), moveList1);

                            }


                            attacks = pop_bit(attacks, targetSquare);
                        }
                        bitboard = pop_bit(bitboard, startSquare);
                    }

                }

            }
            //black pawn and castle
            else  {
                //pawn
                if(piece == p) {
                    while(bitboard != 0L) {
                        startSquare = getLs1bIndex(bitboard);
                        //pawn attacks
                        long attacks = pawnAttacks[BLACK][startSquare] & occupancies[WHITE];
                        //enps
                        if(enPs != noSq) {
                            if((pawnAttacks[0][startSquare] & (1L << enPs)) != 0) {
                                addCapMove(encodeMove(startSquare, enPs, p, noPc, 1, 0, 1, 0), moveList1);

                            }
                        }
                        while(attacks !=0) {
                            targetSquare = getLs1bIndex(attacks);
                            if(targetSquare >=a1 && targetSquare <= h1) {
                                //promotion
                                addCapMove(encodeMove(startSquare, targetSquare, p, n, 1, 0, 0, 0), moveList1);
                                addCapMove(encodeMove(startSquare, targetSquare, p, b, 1, 0, 0, 0), moveList1);
                                addCapMove(encodeMove(startSquare, targetSquare, p, r, 1, 0, 0, 0), moveList1);
                                addCapMove(encodeMove(startSquare, targetSquare, p, q, 1, 0, 0, 0), moveList1);

                            } else {
                                //normal attack
                                addCapMove(encodeMove(startSquare, targetSquare, p, noPc, 1, 0, 0, 0), moveList1);


                            }


                            attacks = pop_bit(attacks, targetSquare);
                        }
                        bitboard = pop_bit(bitboard, startSquare);
                    }

                }


            }
            //gen knight moves
            if((side == WHITE)? piece == N : piece == n) {
                bitboard = bitboards[piece];
                while(bitboard !=0) {
                    startSquare = getLs1bIndex(bitboard);
                    long attacks = knightAttacks[startSquare];
                    while(attacks != 0) {
                        long attack = attacks & (-attacks);
                        int nu = Long.numberOfTrailingZeros(attack);
                        if((attack & ((side==WHITE)?occupancies[occBIdx] : occupancies[occWIdx])) != 0) {
                            addCapMove(encodeMove(startSquare, nu, piece, noPc, 1, 0, 0, 0), moveList1);
                        }
                        attacks = pop_bit(attacks, nu);
                    }

                    bitboard = pop_bit(bitboard, startSquare);
                }
            }
            //bishop
            else if((side == WHITE)?piece == B : piece == b) {
                bitboard = bitboards[piece];
                while(bitboard != 0) {
                    startSquare = getLs1bIndex(bitboard);
                    long attacks = getBishopAttacks(startSquare, occupancies[occBothIdx]);
                    while(attacks != 0) {
                        long attack = attacks & (-attacks);
                        int nu = Long.numberOfTrailingZeros(attack);
                        if((attack & ((side==WHITE)?occupancies[occBIdx] : occupancies[occWIdx])) != 0) {
                            addCapMove(encodeMove(startSquare, nu, piece, noPc, 1, 0, 0, 0), moveList1);
                        }
                        attacks = pop_bit(attacks, nu);
                    }

                    bitboard = pop_bit(bitboard, startSquare);
                }
            }
            //rook
            else if((side == WHITE)?piece == R : piece == r) {
                bitboard = bitboards[piece];
                while(bitboard != 0) {
                    startSquare = getLs1bIndex(bitboard);
                    long attacks = getRookAttacks(startSquare, occupancies[occBothIdx]);
                    while(attacks != 0) {
                        long attack = attacks & (-attacks);
                        int nu = Long.numberOfTrailingZeros(attack);
                        if((attack & ((side==WHITE)?occupancies[occBIdx] : occupancies[occWIdx])) != 0) {
                            addCapMove(encodeMove(startSquare, nu, piece, noPc, 1, 0, 0, 0), moveList1);
                        }
                        attacks = pop_bit(attacks, nu);
                    }

                    bitboard = pop_bit(bitboard, startSquare);
                }
            }
            //queen
            else if((side == WHITE)?piece == Q : piece == q) {
                bitboard = bitboards[piece];
                while(bitboard != 0) {
                    startSquare = getLs1bIndex(bitboard);
                    long attacks = getQueenAttacks(startSquare, occupancies[occBothIdx]);
                    while(attacks != 0) {
                        long attack = attacks & (-attacks);
                        int nu = Long.numberOfTrailingZeros(attack);
                        if((attack & ((side==WHITE)?occupancies[occBIdx] : occupancies[occWIdx])) != 0) {
                            addCapMove(encodeMove(startSquare, nu, piece, noPc, 1, 0, 0, 0), moveList1);
                        }
                        attacks = pop_bit(attacks, nu);
                    }

                    bitboard = pop_bit(bitboard, startSquare);
                }
            }
            //king
            else if((side == WHITE)?piece == K : piece == k) {
                bitboard = bitboards[piece];
//                printBitBoard(bitboards[k]);

                startSquare = getLs1bIndex(bitboard);
                //some problem in next line? related to startsquare
                long attacks = kingAttacks[startSquare];
                while(attacks != 0) {
                    long attack = attacks & (-attacks);
                    int nu = Long.numberOfTrailingZeros(attack);
                    if((attack & ((side==WHITE)?occupancies[occBIdx] : occupancies[occWIdx])) != 0) {
                        addCapMove(encodeMove(startSquare, nu, piece, noPc, 1, 0, 0, 0), moveList1);
                    }
                    attacks = pop_bit(attacks, nu);
                }
            }
        }

        if(!scoringPV) {
            followingPVLine = false;
        }
    }




    //MAKE MOVE
    public static int allMoves = 0;
    public static int capMoves = 1;

    public static boolean makeMove(int move, int flag) {
        if(flag == allMoves) {
            //make move
            int stackItem = 0;

            int startSquare = decodeFrom(move);
            int targetSquare = decodeTo(move);
            int piece = decodePiece(move);//
            int promPiece = decodePromPiece(move);//
            int capture = decodeCap(move);//
            int castle = decodeCas(move);//
            int enPass = decodeEnPs(move);
            int dblPush = decodeDblPush(move);//

            //update piece bitboards
//            if(piece == 15) {
//            }
            bitboards[piece] = pop_bit(bitboards[piece], startSquare);
            bitboards[piece] = set_bit(bitboards[piece], targetSquare);

            if(capture == 1) {
                int pawnForSide;
                int kingForSide;
                //if capture
                if(side==WHITE) {
                    pawnForSide = p;
                    kingForSide = k;
                } else {
                    pawnForSide = P;
                    kingForSide = K;
                }
                if(enPass == 0) {
                    for (int i = pawnForSide; i <= kingForSide; i++) {
                        if (get_bit(bitboards[i], targetSquare) != 0) {
                            stackItem = i;
                            bitboards[i] = pop_bit(bitboards[i], targetSquare);
                            break;
                        }
                    }
                } else {
                    if(side == WHITE) {
                        bitboards[p] = pop_bit(bitboards[p], targetSquare-8);
                    } else {
                        bitboards[P] = pop_bit(bitboards[P], targetSquare+8);
                    }
                }
            }
            stackItem |= (castling << 4);
            stackItem |= (enPs << 8);
            enPs = noSq;
//            if(piece == K) {
//                castling &= 0b0011;
//            } else if(piece == k) {
//                castling &= 0b1100;
//            } else if(piece == R) {
//                if(startSquare == h1) {
//                    castling &= 0b0111;
//                } else if(startSquare == a1) {
//                    castling &= 0b1011;
//                }
//            } else if(piece == r) {
//                if(startSquare == h8) {
//                    castling &= 0b1101;
//                } else if(startSquare == a8) {
//                    castling &= 0b1110;
//                }
//            }

            castling &= castleRights[startSquare];
            castling &= castleRights[targetSquare];


            if(dblPush == 1) {
                if(side == WHITE) {
                    enPs = targetSquare -8;
                } else {
                    enPs = targetSquare +8;
                }
            }
            if(promPiece !=noPc) {
                if(side == WHITE) {
                    bitboards[P] = pop_bit(bitboards[P], targetSquare);
                } else {
                    bitboards[p] = pop_bit(bitboards[p], targetSquare);
                }
                bitboards[promPiece] = set_bit(bitboards[promPiece], targetSquare);

            }
            if(castle ==1) {
                if(targetSquare == g1) {
                    bitboards[R] = pop_bit(bitboards[R], h1);
                    bitboards[R] = set_bit(bitboards[R], f1);
                }

                else if(targetSquare == c1) {
                    bitboards[R] = pop_bit(bitboards[R], a1);
                    bitboards[R] = set_bit(bitboards[R], d1);
                }

                else if(targetSquare == g8) {
                    bitboards[r] = pop_bit(bitboards[r], h8);
                    bitboards[r] = set_bit(bitboards[r], f8);
                }

                else {//c8
                    bitboards[r] = pop_bit(bitboards[r], a8);
                    bitboards[r] = set_bit(bitboards[r], d8);
                }


            }
            occupancies[0] = 0L;
            occupancies[1] = 0L;
            occupancies[2] = 0L;

            for(int i = P; i <= K; i++) {
                occupancies[WHITE] = occupancies[WHITE] | bitboards[i];
            }
            for(int i = p; i <= k; i++) {

                occupancies[BLACK] = occupancies[BLACK] | bitboards[i];
            }
            occupancies[occBothIdx] = occupancies[WHITE] | occupancies[BLACK];

            side ^=1;
            stack.push(stackItem);
            if(isSquareAttacked(getLs1bIndex(((side==WHITE)?bitboards[k] : bitboards[K])), side)) {
                unmakeMove(move, allMoves);
                return false;
            } else {
                return true;
            }


        }

        else if(flag == capMoves) {
            //if cap, make normal move for now
            if(decodeCap(move)==1) {
                return makeMove(move, allMoves);
            } else {
                return false;
            }

        }

        else {
            //not a move?

            return false;
        }


    }
    // Unmake move, inverse of make move, irreversible aspects from stack

    public static boolean unmakeMove(int move, int flag) {
        if(flag == allMoves) {
            //unmake move
            /*
                stackItem encode:
                cap Piece = 0000 0000 0000 1111;
                castle    = 0000 0000 1111 0000;
                enps sq   = 0111 1111 0000 0000;

             */
            int stackItem = stack.peek();
            stack.pop();

            int startSquare = decodeFrom(move);//
            int targetSquare = decodeTo(move);//
            int piece = decodePiece(move);//
            int promPiece = decodePromPiece(move);//
            int capture = decodeCap(move);//
            int castle = decodeCas(move);//
            int enPass = decodeEnPs(move);

            side ^= 1;
            //update piece bitboards/
            bitboards[piece] = pop_bit(bitboards[piece], targetSquare);
            bitboards[piece] = set_bit(bitboards[piece], startSquare);
            //needs stack//done
            if(capture == 1) {
                //if capture
                int capPiece = stackItem & 0b1111;
//                int pawnForSide = (side == WHITE)?p : P;
//                int kingForSide = (side == WHITE)?k : K;
                if(enPass == 0) {
                    bitboards[capPiece] = set_bit(bitboards[capPiece], targetSquare);
                } else {
                    if(side == WHITE) {
                        bitboards[p] = set_bit(bitboards[p], targetSquare-8);
                    } else {
                        bitboards[P] = set_bit(bitboards[P], targetSquare+8);
                    }
                }
            }
            enPs = (stackItem & 0b111111100000000) >>> 8;
            //needs stack//done
            //update castle rights
            castling = (stackItem & 0b11110000) >>> 4;


            //
            if(promPiece !=0) {
                bitboards[promPiece] = pop_bit(bitboards[promPiece], targetSquare);
            }
            //
            if(castle ==1) {
                if(targetSquare == g1) {
                    bitboards[R] = set_bit(bitboards[R], h1);
                    bitboards[R] = pop_bit(bitboards[R], f1);
                }

                else if(targetSquare == c1) {
                    bitboards[R] = set_bit(bitboards[R], a1);
                    bitboards[R] = pop_bit(bitboards[R], d1);
                }

                else if(targetSquare == g8) {
                    bitboards[r] = set_bit(bitboards[r], h8);
                    bitboards[r] = pop_bit(bitboards[r], f8);
                }

                else {//c8
                    bitboards[r] = set_bit(bitboards[r], a8);
                    bitboards[r] = pop_bit(bitboards[r], d8);
                }


            }
            //
            occupancies[0] = 0L;
            occupancies[1] = 0L;
            occupancies[2] = 0L;
            for(int i = P; i <= K; i++) {
                occupancies[WHITE] = occupancies[WHITE] | bitboards[i];
            }
            for(int i = p; i <= k; i++) {
                occupancies[BLACK] = occupancies[BLACK] | bitboards[i];
            }
            occupancies[occBothIdx] = occupancies[WHITE] | occupancies[BLACK];

//            if(isSquareAttacked(getLs1bIndex(((side==WHITE)?bitboards[k] : bitboards[K])), side)) {
//                unmakeMove(move, allMoves);
//                return false;
//            } else {
//                return true;
//            }


        }

//        else if(flag == capMoves) {
//            //if cap, make normal move for now
//            return makeMove(move, allMoves);
//
//        }

        else {
            //not a move?

            return false;
        }

        return true;
    }

    public static int isInMate(int side) {
        int king = getLs1bIndex(bitboards[side==BLACK?k:K]);
        MoveList list = new MoveList();
        generateMoves(list);


        for(int i = 0; i < list.count; i++) {
            if(makeMove(list.moves[i].move, allMoves)) {
                unmakeMove(list.moves[i].move, allMoves);
                return noMate;
            }
        }
        if(isSquareAttacked(king, side^1)) {
            return checkMate;
        } else {
            return staleMate;
        }
    }



    //PERFT(performance test)
    public static long nMoves = 0;

    public static void findNodesForPosPerft(int depth) {
        if(depth == 0) {
            nMoves++;
            return;
        }

        MoveList moveList = new MoveList();
        generateMoves(moveList);
        for (int i = 0; i < moveList.count; i++) {
            if(makeMove(moveList.moves[i].move, allMoves)) {
                findNodesForPosPerft(depth-1);
                unmakeMove(moveList.moves[i].move, allMoves);
            }



        }


        return;
    }
    private static long perftNodes;




    public static void main(String[] args) {

    }
}