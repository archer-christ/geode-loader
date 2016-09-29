package com.sm.geode_ref.loader;

/**
 * Created by smanvi on 8/24/16.
 */
public class CommandHolder {

    private String command;

    public CommandHolder(String command) {
        this.command = command;
    }

    public String getCommand() {
        return command;
    }

    @Override
    public String toString() {
        return " Command = " + command ;
    }
}
