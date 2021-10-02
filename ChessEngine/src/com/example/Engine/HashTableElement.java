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


    public void clear() {
        this.hashKey = 0;
        this.depth = 0;
        this.flag = 0;
        this.score = 0;
        this.move = new Move(0, 0);
    }
}
