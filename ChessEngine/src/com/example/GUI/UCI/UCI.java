package com.example.GUI.UCI;

import java.util.Scanner;

import static com.example.Engine.Search.*;
import static com.example.Game.*;



public class UCI {

    public static Scanner scanner = new Scanner(System.in);


    public UCI() {
        uciLoop();
    }

    private static void uciLoop() {
        System.out.print("uciok\n");
        ReadSTDIN read = new ReadSTDIN();
        read.start();
    }


    static void parseGo(String command) {
        stopSearch = false;
        searchPosition(maxDepth);
    }
    private static void parsePos() {

    }


}
