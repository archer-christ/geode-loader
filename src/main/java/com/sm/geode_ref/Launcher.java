package com.sm.geode_ref;


import com.sm.geode_ref.loader.*;
import org.apache.log4j.Logger;

import java.util.Scanner;

/**
 * Created by smanvi on 8/9/16.
 */
public class Launcher {

    static Logger logger = Logger.getLogger(Launcher.class.getSimpleName());

    public static void main(String args[]) {

        Launcher launcher = new Launcher();
        launcher.start();
    }

    private void start() {
        Common.printHelp();

        CommandHandler commandHandler = new CommandHandler();
        commandHandler.start();

        Scanner scanInput = new Scanner(System.in);

        while (true) {
            System.out.println("\nPlease enter a command to execute $>");
            String command = scanInput.nextLine();
            if (command.startsWith("quit")) {
                System.exit(0);
            }

            try {
                if (Common.commandQueue.size() == Common.QUEUE_SIZE) {
                    System.out.println("Too many commands being processed currently. Your command will be executed in a while. ");
                }
                Common.commandQueue.put(new CommandHolder(command));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
