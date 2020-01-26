package com.twoforboth.connected;

public class Connected {

    public static void main(String[] args) {

        Process process = new Process();

        if (args.length != 3) {
            System.out.println("Usage: Process filename city city");
        }
        else {
            process.process(args[0], args[1], args[2]);
            System.out.println(process.getStatus());
        }

    }
}