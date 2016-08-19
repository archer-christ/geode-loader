package com.sm.geode_ref;


import com.sm.geode_ref.loader.CreateHandler;
import com.sm.geode_ref.loader.RemoveHandler;

import java.util.Scanner;

/**
 * Created by smanvi on 8/9/16.
 */
public class Launcher {

    CreateHandler createHandler = new CreateHandler();
    RemoveHandler removeHandler = new RemoveHandler();

//    static Properties properties = new Properties().;
    public static void main(String args[]) {
        Launcher launcher = new Launcher();
        launcher.start();
    }

    private void start() {
        printHelp();
        Scanner scanInput = new Scanner(System.in);

        while (true){
            String command = scanInput.nextLine();
            if(!validCommand(command)){
                System.err.println("INVALID COMMAND");
                printHelp();
            }

            if(command.startsWith("create")){
                createHandler.handle(command);
            }else if(command.startsWith("remove")){
                removeHandler.handle(command);
            }
            else if(command.startsWith("shutdown")){
                System.exit(0);
            }
        }
    }

    private boolean validCommand(String command) {
        if(command == null || command.trim().length()==0){
            return true;
        }
        if(!(command.startsWith("create") || command.startsWith("remove") || command.startsWith("help")))
            return false;

        return true;
    }

    private void printHelp() {
        System.out.println("\nThe regions specified in the below commands should be preexisting." +
                "\nAvailable Commands  : " +
                "\n\t1. create <1GB/1MB> <CustomerRegion>  ----- Loads 1GB/1MB size of values to the given region"+
                "\n\t2. remove <1GB/1MB> <CustomerRegion>  ----- Removes 1GB/1MB worth of entries from the given region"+
                "\n\t3. help                               ----- Prints help");
    }


}
