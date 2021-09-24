package com.example.Engine;



public abstract class Constants {
    public static int randStartSeed = 1804289383;
    static {
        a1=0; b1=1; c1=2; d1=3; e1=4; f1=5; g1=6; h1=7;
        a2=8; b2=9; c2=10;d2=11;e2=12;f2=13;g2=14;h2=15;
        a3=16;b3=17;c3=18;d3=19;e3=20;f3=21;g3=22;h3=23;
        a4=24;b4=25;c4=26;d4=27;e4=28;f4=29;g4=30;h4=31;
        a5=32;b5=33;c5=34;d5=35;e5=36;f5=37;g5=38;h5=39;
        a6=40;b6=41;c6=42;d6=43;e6=44;f6=45;g6=46;h6=47;
        a7=48;b7=49;c7=50;d7=51;e7=52;f7=53;g7=54;h7=55;
        a8=56;b8=57;c8=58;d8=59;e8=60;f8=61;g8=62;h8=63;
        noSq=64;


    }

    public static final int infinity = 50000;
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
    public static final boolean bishop = true;
    public static final boolean rook = false;
    public static final int maxPly = 64;
    //tables
    public static long[] kingAttacks = new long[64];
    public static long[][] pawnAttacks = new long[2][64];
    public static long[] knightAttacks = new long[64];
    public static long[] bishopAttackMasks = new long[64];
    public static long[] rookAttackMasks = new long[64];
    public static long[][] bishopAttackTables = new long[64][512];
    public static long[][] rookAttackTables = new long[64][4096];
    public static final int
            a8, b8, c8, d8, e8, f8, g8, h8,
            a7, b7, c7, d7, e7, f7, g7, h7,
            a6, b6, c6, d6, e6, f6, g6, h6,
            a5, b5, c5, d5, e5, f5, g5, h5,
            a4, b4, c4, d4, e4, f4, g4, h4,
            a3, b3, c3, d3, e3, f3, g3, h3,
            a2, b2, c2, d2, e2, f2, g2, h2,
            a1, b1, c1, d1, e1, f1, g1, h1,
            noSq;

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
   /* 7 */  0,   0,   0,   -10, -10, 0,   0,   0,
   /* 8 */  0,   0,   0,   0,   0,   0,   0,   0
    };

    public static final int[] whitePawnScore = new int[] {
          //A    B    C    D    E    F    G    H

   /* 1 */  0,   0,   0,   0,   0,   0,   0,   0,
   /* 2 */  0,   0,   0,   -10, -10, 0,   0,   0,
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
            0, 30, 0, 0, 0, 0, 30, 0,
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
        0,  30,   0,   0,   0,   0,  30,   0,
        0,   0, -10,   0,   0, -10,   0,   0

    };

    public static final int[] blackRookScore = new int[]    {
        50,  50,  50,  50,  50,  50,  50,  50,
        50,  50,  50,  50,  50,  50,  50,  50,
        0,   0,  10,  20,  20,  10,   0,   0,
        0,   0,  10,  20,  20,  10,   0,   0,
        0,   0,  10,  20,  20,  10,   0,   0,
        0,   0,  10,  20,  20,  10,   0,   0,
        0,   0,  10,  20,  20,  10,   0,   0,
        0,   0,   0,  20,  20,   0,   0,   0

    };

    public static final int[] whiteRookScore = new int[] {
            0, 0,  0, 20, 20, 0, 0, 0,
            0, 0, 10, 20, 20, 10, 0, 0,
            0, 0, 10, 20, 20, 10, 0, 0,
            0, 0, 10, 20, 20, 10, 0, 0,
            0, 0, 10, 20, 20, 10, 0, 0,
            0, 0, 10, 20, 20, 10, 0, 0,
            50, 50, 50, 50, 50, 50, 50, 50,
            50, 50, 50, 50, 50, 50, 50, 50,

    };

    public static final int[] blackKingScore = new int[] {

        0,   0,   0,   0,   0,   0,   0,   0,
        0,   0,   5,   5,   5,   5,   0,   0,
        0,   5,   5,  10,  10,   5,   5,   0,
        0,   5,  10,  20,  20,  10,   5,   0,
        0,   5,  10,  20,  20,  10,   5,   0,
        0,   0,   5,  10,  10,   5,   0,   0,
        0,   5,   5,  -5,  -5,   0,   5,   0,
        0,   0,   5,   0, -15,   0,  10,   0
    };

    public static final int[] whiteKingScore = new int[] {
            0,  0,  5,  0,-15,  0, 10,  0,
            0,  5,  5, -5, -5,  0,  5,  0,
            0,  0,  5, 10, 10,  5,  0,  0,
            0,  5, 10, 20, 20, 10,  5,  0,
            0,  5, 10, 20, 20, 10,  5,  0,
            0,  5,  5, 10, 10,  5,  5,  0,
            0,  0,  5,  5,  5,  5,  0,  0,
            0,  0,  0,  0,  0,  0,  0,  0
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

}
