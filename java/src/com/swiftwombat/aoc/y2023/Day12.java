package com.swiftwombat.aoc.y2023;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Stream;

import com.swiftwombat.aoc.Day;

/**
 * Day 12: Hot Springs
 * 
 * @see https://adventofcode.com/2023/day/12
 * @author Zachary Cockshutt
 * @since 2023-12-13
 */
public class Day12 extends Day {

    @Override
    public String partOne() throws IOException {
        var sum = new AtomicLong(0L);
        this.forEachInputLine(line -> {
            State state = parseState(line);
            long count = state.countArrangments();
            sum.set(sum.get() + count);
        });
        return sum.toString();
    }

    @Override
    public String partTwo() throws IOException {
        var sum = new AtomicLong(0L);
        this.forEachInputLine(line -> {
            State state = parseState(line);
            char[] springs = unfoldSprings(state.springs);
            int[] groups = unfoldGroups(state.groups);
            state = new State(springs, groups, 0, state.cache);
            long count = state.countArrangments();
            sum.set(sum.get() + count);
        });
        return sum.toString();
    }

    private State parseState(String s) {
        String[] input = s.split(" ");
        char[] springs = (input[0] + ".").toCharArray();
        int[] groups = Stream.of(input[1].split(",")).mapToInt(Integer::parseInt).toArray();
        return new State(springs, groups, 0, new HashMap<State, Long>());
    }

    private char[] unfoldSprings(char[] springs) {
        String sub = String.valueOf(springs).substring(0, springs.length - 1);
        String str = sub;
        for (int i = 0; i < 4; i++) {
            str += "?" + sub;
        }
        return (str + ".").toCharArray();
    }

    private int[] unfoldGroups(int[] groups) {
        var arr = new int[groups.length * 5];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = groups[i % groups.length];
        }
        return arr;
    }

    private record State(char[] springs, int[] groups, int groupCount, HashMap<State, Long> cache) {

        private long countArrangments() {
            if (cache.containsKey(this)) { return cache.get(this); }
            if (springs.length == 0) { return groupCount == 0 && groups.length == 0 ? 1 : 0; }
            long count = 0L;
            switch (springs[0]) {
                case '.':
                    count = stepWorking();
                    break;
                case '#':
                    count = stepDamaged();
                    break;
                case '?':
                    count = stepUnknown();
                    break;
            }
            cache.put(this, count);
            return count;
        }

        private long stepUnknown() {
            long damaged = stepDamaged();
            long working = stepWorking();
            return damaged + working;
        }

        private long stepWorking() {
            char[] nextSprings = Arrays.copyOfRange(springs, 1, springs.length);
            if (groupCount <= 0) { return new State(nextSprings, groups, 0, cache).countArrangments(); }
            if (groups[0] != groupCount) { return 0L; }
            int[] nextGroups = Arrays.copyOfRange(groups, 1, groups.length);
            return new State(nextSprings, nextGroups, 0, cache).countArrangments();
        }

        private long stepDamaged() {
            if (groups.length == 0 || groupCount >= groups[0]) { return 0L; }
            char[] nextSprings = Arrays.copyOfRange(springs, 1, springs.length);
            return new State(nextSprings, groups, groupCount + 1, cache).countArrangments();
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) { return true; }
            if (obj == null || !(obj instanceof State s)) { return false; }
            if (springs.length != s.springs.length || groups.length != s.groups.length) return false;
            return groupCount == s.groupCount;
        }

        @Override
        public int hashCode() {
            int hash = springs.length;
            hash = 31 * hash + groups.length;
            hash = 31 * hash + groupCount;
            return hash;
        }
    }
}