package com.swiftwombat.aoc;

import static java.lang.String.format;

/**
 * @author Zachary Cockshutt
 * @since 2023-12-03
 */
public class Main {

    public static void main(String[] args) {
        try {
            if (args.length < 2) { err("Invalid number of args."); }
            run(args[0], Integer.parseInt(args[1]));
        } catch (Exception e) {
            err(e.toString());
        }
    }

    private static void run(String year, int dayNumber) {
        try {
            var name = format("com.swiftwombat.aoc.y%s.Day%02d", year, dayNumber);
            var day = (Day) Class.forName(name).getDeclaredConstructor().newInstance();
            day.run(year);
        } catch (Exception e) {
            err("Day not found.");
        }
    }

    private static void err(String msg) {
        System.err.printf("Error: %s\n", msg);
        System.exit(1);
    }
}