package com.example.Engine;

import java.util.Arrays;

import static com.example.Engine.Constants.*;

public class HashTable {
    public HashTableElement[] table;

    public HashTable() {
        table = new HashTableElement[HASH_TABLE_SIZE];
    }

    public HashReturn read(long hashKey, int depth, int alpha, int beta) {
        //bitwise and hashkey with table size minus 1 to get index(for power of 2 table sizes only)
        HashTableElement element = table[(int) (hashKey & ((long) (HASH_TABLE_SIZE - 1)))];
        if(element == null) {
            return NO_HASH_TABLE_ENTRY;
        }
        if(element.hashKey == hashKey) {//check for collision,
            // if the stored hash key is not the inputted hash key, this element was overwritten somewhere
            if(element.depth >= depth) {
                if(element.flag == ALPHA_HASH_FLAG && element.score <= alpha) {
                    //return lower bound
                    return new HashReturn(alpha);
                } else if(element.flag == BETA_HASH_FLAG && element.score >= beta) {
                    //return upper bound
                    return new HashReturn(beta);
                } else if(element.flag == EXACT_HASH_FLAG){
                    //return exact score
                    return new HashReturn(element.score);
                }
            } else {
                if(element.flag == ALPHA_HASH_FLAG && element.score <= alpha) {
                    //return lower bound
                    return new HashReturn(alpha, element.move.move);
                } else if(element.flag == BETA_HASH_FLAG && element.score >= beta) {
                    //return upper bound
                    return new HashReturn(beta, element.move.move);
                } else if(element.flag == EXACT_HASH_FLAG){
                    //return exact score
                    return new HashReturn(element.score, element.move.move);
                }
            }
        }
        return NO_HASH_TABLE_ENTRY;
    }

    public void write(long hashKey, int depth, int flag, int score, Move move) {
        table[(int) (hashKey & HASH_TABLE_SIZE-1)] = new HashTableElement(hashKey, depth, flag, score, move);
    }

    public void clearTable() {
        table = new HashTableElement[HASH_TABLE_SIZE];
    }

    public static class HashReturn {
        public int move;
        public int score;
        public boolean exact;

        public HashReturn(int score) {
            move = 0;
            this.score = score;
            this.exact = true;
        }
        public HashReturn(int move, int score) {
            this.move = move;
            this.score = score;
            this.exact = false;
        }

    }

}
