package com.swiftwombat.aoc.y2023;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.swiftwombat.aoc.Day;

/**
 * Day 14: Parabolic Reflector Dish
 * 
 * @see https://adventofcode.com/2023/day/14
 * @author Zachary Cockshutt
 * @since 2023-12-14
 */
public class Day14 extends Day {

    private static final Direction N = Direction.N, S = Direction.S,
            E = Direction.E, W = Direction.W;

    @Override
    public String partOne() throws IOException {
        Platform platform = this.parsePlatform();
        platform.tilt(N);
        int load = platform.getLoad();
        return String.valueOf(load);
    }

    @Override
    public String partTwo() throws IOException {
        Platform platform = this.parsePlatform();
        for (int i = 0; i < 1000; i++) {
            platform.tilt(N);
            platform.tilt(W);
            platform.tilt(S);
            platform.tilt(E);
        }
        int load = platform.getLoad();
        return String.valueOf(load);
    }

    private Platform parsePlatform() throws IOException {
        var matrix = new ArrayList<char[]>();
        this.forEachInputLine(line -> matrix.add(line.toCharArray()));
        return new Platform(matrix.toArray(new char[matrix.size()][]));
    }

    private record Platform(char[][] matrix) {

        private void tilt(Direction dir) {
            var rows = IntStream.range(0, matrix.length).boxed().collect(Collectors.toCollection(ArrayList::new));
            var cols = IntStream.range(0, matrix[0].length).boxed().collect(Collectors.toCollection(ArrayList::new));
            if (dir == S) { Collections.reverse(rows); }
            if (dir == E) { Collections.reverse(cols); }
            for (int i : rows)
                for (int j : cols)
                    if (matrix[i][j] == 'O') {
                        int y = i + dir.y, x = j + dir.x;
                        while (y >= 0 && y < matrix.length
                                && x >= 0 && x < matrix[0].length
                                && matrix[y][x] == '.') {
                            matrix[y - dir.y][x - dir.x] = '.';
                            matrix[y][x] = 'O';
                            x += dir.x;
                            y += dir.y;
                        }
                    }
        }

        private int getLoad() {
            int load = 0;
            for (int i = 0; i < matrix.length; i++)
                for (int j = 0; j < matrix[0].length; j++)
                    if (matrix[i][j] == 'O') { load += matrix.length - i; }
            return load;
        }
    }

    private static enum Direction {

        N(0, -1), S(0, 1), E(1, 0), W(-1, 0);

        private final int x, y;

        Direction(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}