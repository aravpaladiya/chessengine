package com.example.Engine;

public class HashTableElement {
    long hashKey;
    int depth;
    int flag;
    int score;
    Move move;

    public HashTableElement(long hashKey, int depth, int flag, int score, Move move) {
        this.hashKey = hashKey;
        this.depth = depth;
        this.flag = flag;
        this.score = score;
        this.move = move;
    }

}
