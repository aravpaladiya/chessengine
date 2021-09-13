package com.example.Engine;

public abstract class BitBoard {
    //bitboards
    public static long universal = 0xFFFFFFFFFFFFFFFFL;
    public static long notHFile = 0x7F7F7F7F7F7F7F7FL;
    public static long notAFile = 0xFEFEFEFEFEFEFEFEL;

    public static long southOne (long b) {
        return  b >>> 8;
    }
    public static long northOne (long b) {
        return  b << 8;
    }
    public static long westOne (long b) {
        return b >>>1;
    }
    public static long eastOne (long b) {
        return b << 1;
    }
    public static long neOne (long b) {
        return b << 9;
    }
    public static long seOne (long b) {
        return b >>> 7;
    }
    public static long swOne (long b) {
        return b >>> 9;
    }
    public static long nwOne (long b) {
        return b << 7;
    }
    public static long get_bit(long BB, int square) {
        return (1L<<square) & BB;
    }
    public static long set_bit(long BB, int square) {
        return BB |= (1L << square);
    }
    public static long pop_bit(long BB, int square) {
        return BB &= ~(1L << square);
    }
    public static int count_bit(long BB) {
        return Long.bitCount(BB);
//        int count = 0;
//        while(BB != 0) {
//            BB &=BB-1;
//            count++;
//        }
//        return count;
    }
    public static long toggle_bit(long BB, int square) {
        return BB ^ (1L << (square));
    }
    public static int toggle_bit(int BB, int square) {
        return BB ^ (1 << square);
    }
    public static int getLs1bIndex(long BB) {

        long isolated = BB&(-BB);
        return count_bit(isolated-1);
    }

    public static void printBitBoard(long bitboard) {
        int l = 0;

        char[] temp = String.format("%64s", Long.toBinaryString(Long.reverse(bitboard))).replace(' ', '0').toCharArray();
        for(int j = 7; j >= 0; j--) {

            for(int k = 0; k < 8; k ++) {
                System.out.print(Character.toString(temp[(j*8)+k]).replace("0", "·"));
                System.out.print("  ");
                l++;
            }
            System.out.println();
        }

        System.out.println();
    }


    public static void main(String[] args) {

    }


}
