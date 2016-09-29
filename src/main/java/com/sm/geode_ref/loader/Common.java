package com.sm.geode_ref.loader;

import java.util.Queue;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by smanvi on 8/9/16.
 */
public class Common {

    public static final int KB = 1204;
    public static final int MB = KB * 1204;
    public static final int GB = MB * 1204;

    public static final AtomicInteger CURRENT_MAX_KEY = new AtomicInteger();

    public static final int QUEUE_SIZE=5;
    public static final BlockingQueue<CommandHolder> commandQueue = new LinkedBlockingQueue<CommandHolder>(QUEUE_SIZE);

    public static void printHelp() {
        System.out.println("\nThe regions specified in the below commands should be preexisting." +
                "\nAvailable Commands  : " +
                "\n\t1. create <1024MB> <CustomerRegion>  ----- Loads 1024MB size of values to the given region" +
                "\n\t2. remove <1024MB> <CustomerRegion>  ----- Removes 1024MB worth of entries from the given region" +
                "\n\t3. help                               ----- Prints help" +
                "\n\t4. quit                               ----- Quit the program");
    }

    public static long getRandomLong(){
        Random random = new Random();
        return random.nextLong();
    }
}
