package com.example.Engine;

import static com.example.Engine.Constants.*;

public class HashTable {
    public HashTableElement[] table = new HashTableElement[HASH_TABLE_SIZE];

    public HashTable() {
        for(int i = 0; i < table.length; i++) {
            table[i] = new HashTableElement(0, 0, 0, 0, new Move(0, 0));
        }
    }
}
