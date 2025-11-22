package com.swiftwombat.aoc.y2023;

import java.io.IOException;
import java.util.LinkedHashMap;

import com.swiftwombat.aoc.Day;

/**
 * Day 15: Lens Library
 * 
 * @see https://adventofcode.com/2023/day/15
 * @author Zachary Cockshutt
 * @since 2023-12-14
 */
public class Day15 extends Day {

    @Override
    public String partOne() throws IOException {
        int sum = 0;
        String input = this.getInputLines()[0];
        for (String s : input.split(",")) {
            sum += getHash(s);
        }
        return String.valueOf(sum);
    }

    @Override
    public String partTwo() throws IOException {
        int sum = 0;
        String input = this.getInputLines()[0];
        var boxes = new LinkedHashMap<Integer, LinkedHashMap<String, Integer>>();
        for (String s : input.split(",")) {
            String[] lens = s.split("-|=");
            int box = getHash(lens[0]);
            if (!boxes.containsKey(box)) { boxes.put(box, new LinkedHashMap<>()); }
            if (s.charAt(s.length() - 1) == '-') {
                boxes.get(box).remove(lens[0]);
                continue;
            }
            boxes.get(box).put(lens[0], Integer.parseInt(lens[1]));
        }
        for (int box : boxes.keySet()) {
            var slot = 1;
            for (int lens : boxes.get(box).values())
                sum += (1 + box) * slot++ * lens;
        }
        return String.valueOf(sum);
    }

    private int getHash(String s) {
        int hash = 0;
        char[] arr = s.toCharArray();
        for (char ch : arr) {
            hash = ((hash + ch) * 17) % 256;
        }
        return hash;
    }
}