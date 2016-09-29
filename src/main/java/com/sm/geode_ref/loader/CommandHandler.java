package com.sm.geode_ref.loader;

import org.apache.log4j.Logger;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by smanvi on 8/23/16.
 */
public class CommandHandler {

    static Logger logger = Logger.getLogger(CommandHolder.class.getSimpleName());

    ExecutorService handlerService = Executors.newSingleThreadExecutor();
    public void start() {

        handlerService.submit(new Runnable() {
            public void run() {
                CreateHandler createHandler = new CreateHandler();
                RemoveHandler removeHandler = new RemoveHandler();

                CommandHolder commandHolder = null;
                String command="";

                while (true) {
                    try {
                        commandHolder = Common.commandQueue.take();
                        command = commandHolder.getCommand();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    if (!validCommand(commandHolder.getCommand())) {
                        logger.error("Invalid Command : " + commandHolder.getCommand());
                        Common.printHelp();
                        continue;
                    }
                    if (command.startsWith("create")) {
                        createHandler.handle(commandHolder);
                    } else if (command.startsWith("remove")) {
                        removeHandler.handle(command);
                    }
                }
            }
        });

    }


    private boolean validCommand(String command) {
        if (command == null || command.trim().length() == 0) {
            return true;
        }
        if (!(command.startsWith("create") || command.startsWith("remove") || command.startsWith("help")))
            return false;

        return true;
    }

}
