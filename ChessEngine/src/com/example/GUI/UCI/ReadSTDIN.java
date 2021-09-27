package com.example.GUI.UCI;

import java.util.Scanner;

import static com.example.Engine.Search.stopSearch;
import static com.example.GUI.UCI.UCI.*;

public class ReadSTDIN extends Thread{
    private Thread thread;


    @Override
    public void run() {
        Scanner scanner = new Scanner(System.in);
        String command = scanner.nextLine();
        ReadSTDIN read = new ReadSTDIN();
        read.start();
        parseCommand(command);
    }

    public void start() {
        if(thread == null) {
            thread = new Thread(this);
            thread.start();
        }
    }

    public void parseCommand(String command) {
        System.out.println(command);
        if(command.equals("isready")) {
            System.out.print("readyok\n");
        }
        else if(command.contains("go")) {
            parseGo(command);
        }
        else if(command.equals("stop")) {
            stopSearch = true;
        } else if(command.equals("register")) {
            System.out.print("register name Arav P\n");
        } else if(command.equals("quit")) {
            System.exit(0);
        }
    }
}
