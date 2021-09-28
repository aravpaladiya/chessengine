package com.example.GUI.UCI;

import java.util.Scanner;

import static com.example.Engine.Search.stopSearch;

public class ReadSTDIN extends Thread{
//    private Thread thread;

    @Override
    public void run() {
        super.run();
        Scanner scanner = new Scanner(System.in);
        while(true) {
            String command = scanner.nextLine();
            new Thread(new CommandThread(command)).start();
        }
    }

    public class CommandThread implements Runnable {
        String command;

        public CommandThread(String command) {
            this.command = command;
        }

        public void run() {
            try{
                if(command.regionMatches(0, "go", 0, 2)) {
                    UCI.parseGo(command);
                } else if(command.regionMatches(0, "position", 0, 8)) {
                    UCI.parsePos(command);
                } else if (command.equals("isready")) {
                    System.out.print("readyok\n");
                } else if (command.equals("stop")) {
                    stopSearch = true;
                } else if (command.equals("register")) {
                    System.out.print("register name Arav P\n");
                } else if (command.equals("quit")) {
                    System.exit(0);
                }
            }
            catch (Exception e){
                System.out.println(e.getCause().toString());
            }
        }
    }

}
