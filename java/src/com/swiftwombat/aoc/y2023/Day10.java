package com.swiftwombat.aoc.y2023;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.swiftwombat.aoc.Day;

/**
 * Day 10: Pipe Maze
 * 
 * @see https://adventofcode.com/2023/day/10
 * @author Zachary Cockshutt
 * @since 2023-12-11
 */
public class Day10 extends Day {

    private static final Direction N = Direction.N, S = Direction.S,
            E = Direction.E, W = Direction.W;

    private static Map<Character, Pipe> pipes = new HashMap<>() {

        {
            put('|', new Pipe(N, S));
            put('-', new Pipe(E, W));
            put('L', new Pipe(N, E));
            put('F', new Pipe(S, E));
            put('J', new Pipe(N, W));
            put('7', new Pipe(S, W));
        }
    };

    @Override
    public String partOne() throws IOException {
        var path = new ArrayList<Tile>();
        var matrix = getTileMatrix();
        var curr = getStartingTile(matrix);
        var dir = N;
        while (true) {
            curr = dir.travel(curr, matrix);
            path.add(curr);
            if (curr.isStart()) { break; }
            dir = pipes.get(curr.ch).getExitDirection(dir);
        }
        return String.valueOf(path.size() / 2);
    }

    @Override
    public String partTwo() throws IOException {
        var path = new ArrayList<Tile>();
        var matrix = getTileMatrix();
        var curr = getStartingTile(matrix);
        var dir = N;
        while (true) {
            curr = dir.travel(curr, matrix);
            if (curr.ch != '|' && curr.ch != '-') { path.add(curr); }
            if (curr.isStart()) { break; }
            dir = pipes.get(curr.ch).getExitDirection(dir);
        }
        var count = 0;
        var tiles = new ArrayList<Tile>();
        for (int i = 0; i < matrix.length; i++)
            for (int j = 0; j < matrix[0].length; j++) {
                var tile = matrix[i][j];
                if (!tile.isVisited && !tile.isStart()) { tiles.add(tile); }
            }
        for (var t : tiles) {
            if (t.isInside(path)) { count++; }
        }
        return String.valueOf(count);
    }

    private Tile[][] getTileMatrix() throws IOException {
        var input = this.input();
        var matrix = new Tile[input.length][input[0].length()];
        for (int i = 0; i < input.length; i++) {
            var row = input[i].toCharArray();
            for (int j = 0; j < row.length; j++) {
                matrix[i][j] = new Tile(row[j], j, i, false);
            }
        }
        return matrix;
    }

    private Tile getStartingTile(Tile[][] matrix) {
        for (int i = 0; i < matrix.length; i++)
            for (int j = 0; j < matrix[i].length; j++)
                if (matrix[i][j].isStart()) { return matrix[i][j]; }
        return null;
    }

    private record Tile(char ch, int x, int y, boolean isVisited) {

        private boolean isInside(List<Tile> polygon) {
            boolean c = false;
            int i, j;
            for (i = 0, j = polygon.size() - 1; i < polygon.size(); j = i++) {
                var a = polygon.get(i);
                var b = polygon.get(j);
                if (((a.y > y) != (b.y > y)) &&
                        (x < (b.x - a.x) * (y - a.y) / (b.y - a.y) + a.x)) { c = !c; }
            }
            return c;
        }

        private boolean isStart() {
            return ch == 'S';
        }

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof Tile)) { return false; }
            var p = (Tile) o;
            return x == p.x && y == p.y;
        }
    }

    private record Pipe(Direction a, Direction b) {

        private Direction getExitDirection(Direction entry) {
            var x = entry.x * -1;
            var y = entry.y * -1;
            return a.x == x && a.y == y ? b : a;
        }
    }

    private static enum Direction {

        N(0, -1), S(0, 1), E(1, 0), W(-1, 0);

        private final int x, y;

        Direction(int x, int y) {
            this.x = x;
            this.y = y;
        }

        private Tile travel(Tile curr, Tile[][] matrix) {
            var i = curr.y + this.y;
            var j = curr.x + this.x;
            return matrix[i][j] = new Tile(matrix[i][j].ch, j, i, true);
        }
    }
}