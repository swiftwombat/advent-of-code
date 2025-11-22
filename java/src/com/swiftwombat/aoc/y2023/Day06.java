package com.swiftwombat.aoc.y2023;

import static java.lang.Long.parseLong;
import static java.lang.Math.abs;
import static java.lang.Math.ceil;
import static java.lang.Math.floor;
import static java.lang.Math.sqrt;

import java.io.IOException;

import com.swiftwombat.aoc.Day;

/**
 * Day 6: Wait For It
 * 
 * @see https://adventofcode.com/2023/day/6
 * @author Zachary Cockshutt
 * @since 2023-12-06
 */
public class Day06 extends Day {

    @Override
    public String partOne() throws IOException {
        int product = 1;
        Race[] races = parseRaces();
        for (var race : races) {
            product *= race.permutations();
        }
        return String.valueOf(product);
    }

    @Override
    public String partTwo() throws IOException {
        Race race = parseRace();
        long count = race.permutations();
        return String.valueOf(count);
    }

    private Race[] parseRaces() throws IOException {
        String[] input = getInputLines();
        var nums = new String[2][];
        for (int i = 0; i < 2; i++) {
            nums[i] = input[i].split(": +")[1].split(" +");
        }
        var races = new Race[nums[0].length];
        for (int i = 0; i < races.length; i++) {
            races[i] = new Race(nums[0][i], nums[1][i]);
        }
        return races;
    }

    private Race parseRace() throws IOException {
        String[] input = getInputLines();
        for (int i = 0; i < input.length; i++) {
            input[i] = input[i].replaceAll("\\D+", "");
        }
        return new Race(input[0], input[1]);
    }

    private record Race(long time, long record) {

        private Race(String t, String r) {
            this(parseLong(t), parseLong(r));
        }

        private long permutations() {
            long b = -time, c = -record;
            long det = b * b - 4 * -1 * c;
            double x1 = abs(floor((b + sqrt(det)) / (2 * -1)));
            double x2 = abs(ceil((b - sqrt(det)) / (2 * -1)));
            return (long) (x2 - x1 - 1);
        }
    }
}