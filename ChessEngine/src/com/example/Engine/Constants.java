package com.example.Engine;


import java.util.HashMap;
import java.util.Map;

public abstract class Constants {


    static {


        final int noPc = 12;
        final int P = 0;
        final int N = 1;
        final int B = 2;
        final int R = 3;
        final int Q = 4;
        final int K = 5;
        final int p = 6;
        final int n = 7;
        final int b = 8;
        final int r = 9;
        final int q = 10;
        final int k = 11;

        Map<String, Integer> PieceToInt= new HashMap<>();
        Map<String, Integer> CoordsToInt= new HashMap<>();








    }
    public static int randStartSeed = 1804289383;

    public static final int OPENING = 0;
    public static final int ENDGAME = 0;
    public static final int INFINITY = 50000;
    public static final String startFEN = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";
    public static final int checkMate = 1;
    public static final int staleMate = 2;
    public static final int noMate = 0;
    public static final String empty_board = "8/8/8/8/8/8/8/8 w - - 0 0 ";
    public static final String start_position = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1 ";
    public static final String tricky_position = "r3k2r/p1ppqpb1/bn2pnp1/3PN3/1p2P3/2N2Q1p/PPPBBPPP/R3K2R w KQkq - 0 1 ";
    public static final String killer_position = "rnbqkb1r/pp1p1pPp/8/2p1pP2/1P1P4/3P3P/P1P1P3/RNBQKBNR w KQkq e6 0 1";
    public static final String very_hard_position = "r2q1rk1/ppp2ppp/2n1bn2/2b1p3/3pP3/3P1NPP/PPP1NPB1/R1BQ1RK1 b - - 0 9 ";
    public static final int BLACK = 0;
    public static final int WHITE = 1;
    public static final boolean BISHOP = true;
    public static final boolean ROOK = false;
    public static final int MAX_PLY = 64;
    public static final int ENDGAME_MATERIAL = 2800;
    //tables
    public static long[] kingAttacks = new long[64];
    public static long[][] pawnAttacks = new long[2][64];
    public static long[] knightAttacks = new long[64];
    public static long[] bishopAttackMasks = new long[64];
    public static long[] rookAttackMasks = new long[64];
    public static long[][] bishopAttackTables = new long[64][512];
    public static long[][] rookAttackTables = new long[64][4096];
    public static final int a1=0;
    public static final int b1=1;
    public static final int c1=2;
    public static final int d1=3;
    public static final int e1=4;
    public static final int f1=5;
    public static final int g1=6;
    public static final int h1=7;
    public static final int a2=8;
    public static final int b2=9;
    public static final int c2=10;
    public static final int d2=11;
    public static final int e2=12;
    public static final int f2=13;
    public static final int g2=14;
    public static final int h2=15;
    public static final int a3=16;
    public static final int b3=17;
    public static final int c3=18;
    public static final int d3=19;
    public static final int e3=20;
    public static final int f3=21;
    public static final int g3=22;
    public static final int h3=23;
    public static final int a4=24;
    public static final int b4=25;
    public static final int c4=26;
    public static final int d4=27;
    public static final int e4=28;
    public static final int f4=29;
    public static final int g4=30;
    public static final int h4=31;
    public static final int a5=32;
    public static final int b5=33;
    public static final int c5=34;
    public static final int d5=35;
    public static final int e5=36;
    public static final int f5=37;
    public static final int g5=38;
    public static final int h5=39;
    public static final int a6=40;
    public static final int b6=41;
    public static final int c6=42;
    public static final int d6=43;
    public static final int e6=44;
    public static final int f6=45;
    public static final int g6=46;
    public static final int h6=47;
    public static final int a7=48;
    public static final int b7=49;
    public static final int c7=50;
    public static final int d7=51;
    public static final int e7=52;
    public static final int f7=53;
    public static final int g7=54;
    public static final int h7=55;
    public static final int a8=56;
    public static final int b8=57;
    public static final int c8=58;
    public static final int d8=59;
    public static final int e8=60;
    public static final int f8=61;
    public static final int g8=62;
    public static final int h8=63;
    public static final int noSq=64;

    public static final int[] pieceScoreEvalTable = new int[] {
        100,
        300,
        350,
        500,
        1000,
        10000,
        -100,
        -300,
        -350,
        -500,
        -1000,
        -10000,
        0
    };

    public static Map<String, Integer> PieceToInt= new HashMap<>();
    public static Map<String, Integer> CoordsToInt= new HashMap<>();


    public static final int occBIdx = 0;
    public static final int occWIdx = 1;
    public static final int occBothIdx = 2;

    public static final int noPc = 12;
    public static final int P = 0;
    public static final int N = 1;
    public static final int B = 2;
    public static final int R = 3;
    public static final int Q = 4;
    public static final int K = 5;
    public static final int p = 6;
    public static final int n = 7;
    public static final int b = 8;
    public static final int r = 9;
    public static final int q = 10;
    public static final int k = 11;



    public static final int[] blackPawnScore = new int[] {
          //A    B    C    D    E    F    G    H

   /* 1 */  90,  90,  90,  90,  90,  90,  90,  90,
   /* 2 */  30,  30,  30,  40,  40,  30,  30,  30,
   /* 3 */  20,  20,  20,  30,  30,  30,  20,  20,
   /* 4 */  10,  10,  10,  20,  20,  10,  10,  10,
   /* 5 */  5,   5,   10,  20,  20,  5,   5,   5,
   /* 6 */  0,   0,   0,   5,   5,   0,   0,   0,
   /* 7 */  5,   0,   0,   -10, -10, 0,   0,   5,
   /* 8 */  0,   0,   0,   0,   0,   0,   0,   0
    };

    public static final int[] whitePawnScore = new int[] {
          //A    B    C    D    E    F    G    H

   /* 1 */  0,   0,   0,   0,   0,   0,   0,   0,
   /* 2 */  5,   5,   0,   -10, -10, 0,   5,   5,
   /* 3 */  0,   0,   0,   5,   5,   0,   0,   0,
   /* 4 */  5,   5,   10,  20,  20,  5,   5,   5,
   /* 5 */  10,  10,  10,  20,  20,  10,  10,  10,
   /* 6 */  20,  20,  20,  30,  30,  20,  20,  20,
   /* 7 */  30,  30,  30,  40,  40,  30,  30,  30,
   /* 8 */  90,  90,  90,  90,  90,  90,  90,  90

        };



    public static final int[] blackKnightScore = new int[] {
        -5,   0,   0,   0,   0,   0,   0,  -5,
        -5,   0,   0,  10,  10,   0,   0,  -5,
        -5,   5,  20,  20,  20,  20,   5,  -5,
        -5,  10,  20,  30,  30,  20,  10,  -5,
        -5,  10,  20,  30,  30,  20,  10,  -5,
        -5,   5,  20,  10,  10,  20,   5,  -5,
        -5,   0,   0,   0,   0,   0,   0,  -5,
        -5, -10,   0,   0,   0,   0, -10,  -5
    };

    public static final int[] whiteKnightScore = new int[] {
            -5,   -10,   0,   0,   0,   0,   -10,  -5,
            -5,   0,   0,  0,  0,   0,   0,  -5,
            -5,   5,  20,  10,  10,  20,   5,  -5,
            -5,  10,  20,  30,  30,  20,  10,  -5,
            -5,  10,  20,  30,  30,  20,  10,  -5,
            -5,   5,  20,  20,  20,  20,   5,  -5,
            -5,   0,   0,  10,   10,   0,   0,  -5,
            -5, 0,   0,   0,   0,   0, 0,  -5
    };

    public static final int[] whiteBishopScore = new int[] {
            0,  0, -10, 0, 0, -10, 0, 0,
            0, 15, 0, 0, 0, 0, 15, 0,
            0, 10, 0, 0, 0, 0, 10, 0,
            0, 0, 10, 20, 20, 10, 0, 0,
            0, 0, 10, 20, 20, 10, 0, 0,
            0, 0, 0, 10, 10, 0, 0, 0,
            0,  0,  0,  0,  0,  0,  0,  0,
            0,  0,  0,  0,  0,  0,  0,  0,
    };

    public static final int[] blackBishopScore = new int[]    {
        0,   0,   0,   0,   0,   0,   0,   0,
        0,   0,   0,   0,   0,   0,   0,   0,
        0,   0,   0,  10,  10,   0,   0,   0,
        0,   0,  10,  20,  20,  10,   0,   0,
        0,   0,  10,  20,  20,  10,   0,   0,
        0,  10,   0,   0,   0,   0,  10,   0,
        0,  15,   0,   0,   0,   0,  15,   0,
        0,   0, -10,   0,   0, -10,   0,   0

    };

    public static final int[] blackRookScore = new int[]    {
        35,  35,  35,  35,  35,  35,  35,  35,
        35,  35,  35,  35,  35,  35,  35,  35,
        0,   0,  10,  20,  20,  10,   0,   0,
        0,   0,  10,  20,  20,  10,   0,   0,
        0,   0,  10,  20,  20,  10,   0,   0,
        0,   0,  10,  20,  20,  10,   0,   0,
        0,   0,  10,  20,  20,  10,   0,   0,
        0,   0,   10,  20,  20, 10,   0,   0

    };

    public static final int[] whiteRookScore = new int[] {
            0, 0,  10, 20, 20, 10, 0, 0,
            0, 0, 10, 20, 20, 10, 0, 0,
            0, 0, 10, 20, 20, 10, 0, 0,
            0, 0, 10, 20, 20, 10, 0, 0,
            0, 0, 10, 20, 20, 10, 0, 0,
            0, 0, 10, 20, 20, 10, 0, 0,
            35,  35,  35,  35,  35,  35,  35,  35,
            35,  35,  35,  35,  35,  35,  35,  35,
    };

    public static final int[] blackKingScoreO = new int[] {

            -70, -70, -70, -70, -70, -70, -70, -70,
            -70, -70, -70, -70, -70, -70, -70, -70,
            -70, -70, -70, -70, -70, -70, -70, -70,
            -70, -70, -70, -70, -70, -70, -70, -70,
            -70, -70, -70, -70, -70, -70, -70, -70,
            -30, -30, -30, -30, -30, -30, -30, -30,
            -10, -10, -10, -10, -10, -10, -10, -10,
            0,   5,   5,   -10, -10,  0,   10,  5
    };

    public static final int[] whiteKingScoreO = new int[] {
            0,   5,   5,   -10, -10, 0,   10,   5,
            -10, -10, -10, -10, -10, -10, -10, -10,
            -30, -30, -30, -30, -30, -30, -30, -30,
            -70, -70, -70, -70, -70, -70, -70, -70,
            -70, -70, -70, -70, -70, -70, -70, -70,
            -70, -70, -70, -70, -70, -70, -70, -70,
            -70, -70, -70, -70, -70, -70, -70, -70,
            -70, -70, -70, -70, -70, -70, -70, -70,

    };

    public static final int[] blackKingScoreE = new int[] {
            -50, -20,0,0,0,0,-20,-50,
            -20,0,20,20,20,20,0,-20,
             0,20,40,40,40,40,20,0,
             0,20,40,50,50,40,20,0,
             0,20,40,50,50,40,20,0,
             0,20,40,40,40,40,20,0,
            -20,0,20,20,20,20,0,-20,
            -50,-20,0,0,0,0,-20,-50,

    };

    public static final int[] whiteKingScoreE = new int[] {
            -50, -20,0,0,0,0,-20,-50,
            -20,0,20,20,20,20,0,-20,
            0,20,40,40,40,40,20,0,
            0,20,40,50,50,40,20,0,
            0,20,40,50,50,40,20,0,
            0,20,40,40,40,40,20,0,
            -20,0,20,20,20,20,0,-20,
            -50, -20,0,0,0,0,-20,-50,
    };

    public static final int[] castleRights = new int[] {
            11, 15, 15, 15,  3, 15, 15,  7,
            15, 15, 15, 15, 15, 15, 15, 15,
            15, 15, 15, 15, 15, 15, 15, 15,
            15, 15, 15, 15, 15, 15, 15, 15,
            15, 15, 15, 15, 15, 15, 15, 15,
            15, 15, 15, 15, 15, 15, 15, 15,
            15, 15, 15, 15, 15, 15, 15, 15,
            14, 15, 15, 15, 12, 15, 15, 13,

    };

    public static final int[] PIECE_SIDE = new int[] {
            1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0
    };

    //attacker first

    public static int[][] MVVLVA= new int[][] {
            {105, 205, 305, 405, 505, 605, 105, 205, 305, 405, 505, 605},
            {104, 204, 304, 404, 504, 604, 104, 204, 304, 404, 504, 604},
            {103, 203, 303, 403, 503, 603, 103, 203, 303, 403, 503, 603},
            {102, 202, 302, 402, 502, 602, 102, 202, 302, 402, 502, 602},
            {101, 201, 301, 401, 501, 601, 101, 201, 301, 401, 501, 601},
            {100, 200, 300, 400, 500, 600, 100, 200, 300, 400, 500, 600},
            {105, 205, 305, 405, 505, 605, 105, 205, 305, 405, 505, 605},
            {104, 204, 304, 404, 504, 604, 104, 204, 304, 404, 504, 604},
            {103, 203, 303, 403, 503, 603, 103, 203, 303, 403, 503, 603},
            {102, 202, 302, 402, 502, 602, 102, 202, 302, 402, 502, 602},
            {101, 201, 301, 401, 501, 601, 101, 201, 301, 401, 501, 601},
            {100, 200, 300, 400, 500, 600, 100, 200, 300, 400, 500, 600}

    };



    public enum files {
        a,
        b,
        c,
        d,
        e,
        f,
        g,
        h

    }

    public static final String[] intToPiece = new String[] {
            "P", "N", "B", "R", "Q", "K", "p", "n", "b", "r", "q", "k", "none"
    };
    public static final String[] squareToCoords = new String[] {
            "a1", "b1", "c1", "d1", "e1", "f1", "g1", "h1",
            "a2", "b2", "c2", "d2", "e2", "f2", "g2", "h2",
            "a3", "b3", "c3", "d3", "e3", "f3", "g3", "h3",
            "a4", "b4", "c4", "d4", "e4", "f4", "g4", "h4",
            "a5", "b5", "c5", "d5", "e5", "f5", "g5", "h5",
            "a6", "b6", "c6", "d6", "e6", "f6", "g6", "h6",
            "a7", "b7", "c7", "d7", "e7", "f7", "g7", "h7",
            "a8", "b8", "c8", "d8", "e8", "f8", "g8", "h8"
    };
    public static final int[] bishopAttackCounts = {
            6, 5, 5, 5, 5, 5, 5, 6,
            5, 5, 5, 5, 5, 5, 5, 5,
            5, 5, 7, 7, 7, 7, 5, 5,
            5, 5, 7, 9, 9, 7, 5, 5,
            5, 5, 7, 9, 9, 7, 5, 5,
            5, 5, 7, 7, 7, 7, 5, 5,
            5, 5, 5, 5, 5, 5, 5, 5,
            6, 5, 5, 5, 5, 5, 5, 6
    };
    public static final int[] rookAttackCounts = {
            12, 11, 11, 11, 11, 11, 11, 12,
            11, 10, 10, 10, 10, 10, 10, 11,
            11, 10, 10, 10, 10, 10, 10, 11,
            11, 10, 10, 10, 10, 10, 10, 11,
            11, 10, 10, 10, 10, 10, 10, 11,
            11, 10, 10, 10, 10, 10, 10, 11,
            11, 10, 10, 10, 10, 10, 10, 11,
            12, 11, 11, 11, 11, 11, 11, 12
    };
    public static final long[] bishopMagics= new long[]{
            0x40040844404084L,
            0x2004208a004208L,
            0x10190041080202L,
            0x108060845042010L,
            0x581104180800210L,
            0x2112080446200010L,
            0x1080820820060210L,
            0x3c0808410220200L,
            0x4050404440404L,
            0x21001420088L,
            0x24d0080801082102L,
            0x1020a0a020400L,
            0x40308200402L,
            0x4011002100800L,
            0x401484104104005L,
            0x801010402020200L,
            0x400210c3880100L,
            0x404022024108200L,
            0x810018200204102L,
            0x4002801a02003L,
            0x85040820080400L,
            0x810102c808880400L,
            0xe900410884800L,
            0x8002020480840102L,
            0x220200865090201L,
            0x2010100a02021202L,
            0x152048408022401L,
            0x20080002081110L,
            0x4001001021004000L,
            0x800040400a011002L,
            0xe4004081011002L,
            0x1c004001012080L,
            0x8004200962a00220L,
            0x8422100208500202L,
            0x2000402200300c08L,
            0x8646020080080080L,
            0x80020a0200100808L,
            0x2010004880111000L,
            0x623000a080011400L,
            0x42008c0340209202L,
            0x209188240001000L,
            0x400408a884001800L,
            0x110400a6080400L,
            0x1840060a44020800L,
            0x90080104000041L,
            0x201011000808101L,
            0x1a2208080504f080L,
            0x8012020600211212L,
            0x500861011240000L,
            0x180806108200800L,
            0x4000020e01040044L,
            0x300000261044000aL,
            0x802241102020002L,
            0x20906061210001L,
            0x5a84841004010310L,
            0x4010801011c04L,
            0xa010109502200L,
            0x4a02012000L,
            0x500201010098b028L,
            0x8040002811040900L,
            0x28000010020204L,
            0x6000020202d0240L,
            0x8918844842082200L,
            0x4010011029020020L
    };
    public static final long[] rookMagics = new long[] {
            0x8a80104000800020L,
            0x140002000100040L,
            0x2801880a0017001L,
            0x100081001000420L,
            0x200020010080420L,
            0x3001c0002010008L,
            0x8480008002000100L,
            0x2080088004402900L,
            0x800098204000L,
            0x2024401000200040L,
            0x100802000801000L,
            0x120800800801000L,
            0x208808088000400L,
            0x2802200800400L,
            0x2200800100020080L,
            0x801000060821100L,
            0x80044006422000L,
            0x100808020004000L,
            0x12108a0010204200L,
            0x140848010000802L,
            0x481828014002800L,
            0x8094004002004100L,
            0x4010040010010802L,
            0x20008806104L,
            0x100400080208000L,
            0x2040002120081000L,
            0x21200680100081L,
            0x20100080080080L,
            0x2000a00200410L,
            0x20080800400L,
            0x80088400100102L,
            0x80004600042881L,
            0x4040008040800020L,
            0x440003000200801L,
            0x4200011004500L,
            0x188020010100100L,
            0x14800401802800L,
            0x2080040080800200L,
            0x124080204001001L,
            0x200046502000484L,
            0x480400080088020L,
            0x1000422010034000L,
            0x30200100110040L,
            0x100021010009L,
            0x2002080100110004L,
            0x202008004008002L,
            0x20020004010100L,
            0x2048440040820001L,
            0x101002200408200L,
            0x40802000401080L,
            0x4008142004410100L,
            0x2060820c0120200L,
            0x1001004080100L,
            0x20c020080040080L,
            0x2935610830022400L,
            0x44440041009200L,
            0x280001040802101L,
            0x2100190040002085L,
            0x80c0084100102001L,
            0x4024081001000421L,
            0x20030a0244872L,
            0x12001008414402L,
            0x2006104900a0804L,
            0x1004081002402L

    };

    public static void initConsts() {
        CoordsToInt.put("a1", 0);
        CoordsToInt.put("b1", 1);
        CoordsToInt.put("c1", 2);
        CoordsToInt.put("d1", 3);
        CoordsToInt.put("e1", 4);
        CoordsToInt.put("f1", 5);
        CoordsToInt.put("g1", 6);
        CoordsToInt.put("h1", 7);
        CoordsToInt.put("a2", 8);
        CoordsToInt.put("b2", 9);
        CoordsToInt.put("c2", 10);
        CoordsToInt.put("d2", 11);
        CoordsToInt.put("e2", 12);
        CoordsToInt.put("f2", 13);
        CoordsToInt.put("g2", 14);
        CoordsToInt.put("h2", 15);
        CoordsToInt.put("a3", 16);
        CoordsToInt.put("b3", 17);
        CoordsToInt.put("c3", 18);
        CoordsToInt.put("d3", 19);
        CoordsToInt.put("e3", 20);
        CoordsToInt.put("f3", 21);
        CoordsToInt.put("g3", 22);
        CoordsToInt.put("h3", 23);
        CoordsToInt.put("a4", 24);
        CoordsToInt.put("b4", 25);
        CoordsToInt.put("c4", 26);
        CoordsToInt.put("d4", 27);
        CoordsToInt.put("e4", 28);
        CoordsToInt.put("f4", 29);
        CoordsToInt.put("g4", 30);
        CoordsToInt.put("h4", 31);
        CoordsToInt.put("a5", 32);
        CoordsToInt.put("b5", 33);
        CoordsToInt.put("c5", 34);
        CoordsToInt.put("d5", 35);
        CoordsToInt.put("e5", 36);
        CoordsToInt.put("f5", 37);
        CoordsToInt.put("g5", 38);
        CoordsToInt.put("h5", 39);
        CoordsToInt.put("a6", 40);
        CoordsToInt.put("b6", 41);
        CoordsToInt.put("c6", 42);
        CoordsToInt.put("d6", 43);
        CoordsToInt.put("e6", 44);
        CoordsToInt.put("f6", 45);
        CoordsToInt.put("g6", 46);
        CoordsToInt.put("h6", 47);
        CoordsToInt.put("a7", 48);
        CoordsToInt.put("b7", 49);
        CoordsToInt.put("c7", 50);
        CoordsToInt.put("d7", 51);
        CoordsToInt.put("e7", 52);
        CoordsToInt.put("f7", 53);
        CoordsToInt.put("g7", 54);
        CoordsToInt.put("h7", 55);
        CoordsToInt.put("a8", 56);
        CoordsToInt.put("b8", 57);
        CoordsToInt.put("c8", 58);
        CoordsToInt.put("d8", 59);
        CoordsToInt.put("e8", 60);
        CoordsToInt.put("f8", 61);
        CoordsToInt.put("g8", 62);
        CoordsToInt.put("h8", 63);

        PieceToInt.put("P", P);
        PieceToInt.put("N", N);
        PieceToInt.put("B", B);
        PieceToInt.put("R", R);
        PieceToInt.put("Q", Q);
        PieceToInt.put("K", K);
        PieceToInt.put("p", p);
        PieceToInt.put("n", n);
        PieceToInt.put("b", b);
        PieceToInt.put("r", r);
        PieceToInt.put("q", q);
        PieceToInt.put("k", k);
    }

}
