package com.example.Engine;


import static com.example.Engine.Constants.*;

public class MoveList {
    public Move[] moves = new Move[256];
    public int count;

    public MoveList() {
        this.count = 0;
    }

    public Move getNextMove(int count) {
        Move bestMove = new Move(0, -infinity);
        int bestNum = count;
        for(int i = count; i < this.count; i++) {
            if(this.moves[i].score > bestMove.score) {
                bestMove = this.moves[i];
                bestNum = i;
//                Move temp = list.moves[i];
//                list.moves[i] = list.moves[j];
//                list.moves[j] = temp;
            }
        }

        Move temp = this.moves[count];
        this.moves[count] = this.moves[bestNum];
        this.moves[bestNum] = temp;




        return bestMove;
    }
}
