package com.swiftwombat.aoc.y2023;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import com.swiftwombat.aoc.Day;

/**
 * Day 8: Haunted Wasteland
 * 
 * @see https://adventofcode.com/2023/day/8
 * @author Zachary Cockshutt
 * @since 2023-12-09
 */
public class Day08 extends Day {

    @Override
    public String partOne() throws IOException {
        int count = 0;
        String[] input = this.getInputLines();
        char[] dirs = input[0].toCharArray();
        var nodes = new HashMap<String, Node>();
        for (int i = 2; i < input.length; i++) {
            String[] codes = input[i].replace(")", "").split(" = \\(|, ");
            var node = new Node(codes[1], codes[2]);
            nodes.put(codes[0], node);
        }
        String curr = "AAA";
        while (!curr.equals("ZZZ")) {
            int i = Math.floorMod(count++, dirs.length);
            Node node = nodes.get(curr);
            char dir = dirs[i];
            curr = dir == 'L' ? node.left : node.right;
        }
        return String.valueOf(count);
    }

    @Override
    public String partTwo() throws IOException {
        String[] input = this.getInputLines();
        char[] dirs = input[0].toCharArray();
        var nodes = new HashMap<String, Node>();
        var starts = new ArrayList<String>();
        for (int i = 2; i < input.length; i++) {
            String[] codes = input[i].replace(")", "").split(" = \\(|, ");
            var node = new Node(codes[1], codes[2]);
            nodes.put(codes[0], node);
            if (codes[0].charAt(2) == 'A') { starts.add(codes[0]); }
        }
        var counts = new int[starts.size()];
        for (int i = 0; i < counts.length; i++) {
            var curr = starts.get(i);
            while (curr.charAt(2) != 'Z') {
                int j = Math.floorMod(counts[i]++, dirs.length);
                Node node = nodes.get(curr);
                char dir = dirs[j];
                curr = dir == 'L' ? node.left : node.right;
            }
        }
        return String.valueOf(lcm(counts));
    }

    public static long lcm(int[] arr) {
        long lcm = 1;
        int divisor = 2;
        while (true) {
            int count = 0;
            boolean divisible = false;
            for (int i = 0; i < arr.length; i++) {
                if (arr[i] == 0) { return 0; }
                if (arr[i] < 0) { arr[i] = arr[i] * (-1); }
                if (arr[i] == 1) { count++; }
                if (arr[i] % divisor == 0) {
                    divisible = true;
                    arr[i] = arr[i] / divisor;
                }
            }
            if (divisible) {
                lcm *= divisor;
            } else {
                divisor++;
            }
            if (count == arr.length) { return lcm; }
        }
    }

    private record Node(String left, String right) {}
}