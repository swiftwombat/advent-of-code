package com.swiftwombat.aoc.y2023;

import java.io.IOException;
import java.util.ArrayList;
import com.swiftwombat.aoc.Day;

/**
 * Day 16: The Floor Will Be Lava
 * 
 * @see https://adventofcode.com/2023/day/16
 * @author Zachary Cockshutt
 * @since 2023-12-14
 */
public class Day16 extends Day {

    private static final Direction N = Direction.N, S = Direction.S,
            E = Direction.E, W = Direction.W;

    @Override
    public String partOne() throws IOException {
        var beam = new Beam(0, 0, Direction.E);
        var grid = this.parseGrid();
        int energised = grid.trace(beam);
        System.out.println(grid.toString());
        return String.valueOf(energised);
    }

    @Override
    public String partTwo() throws IOException {
        var sum = 0;
        return String.valueOf(sum);
    }

    private Grid parseGrid() throws IOException {
        var matrix = new ArrayList<Tile[]>();
        this.input((s) -> {
            var chars = s.toCharArray();
            var row = new Tile[s.length()];
            for (int i = 0; i < chars.length; i++) {
                row[i] = new Tile(chars[i], 0);
            }
            matrix.add(row);
        });
        return new Grid(matrix.toArray(new Tile[matrix.size()][]));
    }

    private record Grid(Tile[][] tiles) {

        private int trace(Beam beam) {
            int sum = 0;
            int x = beam.x, y = beam.y;
            while (x >= 0 && x < tiles[0].length
                    && y >= 0 && y < tiles.length
                    && tiles[y][x].type == '.') {
                var tile = tiles[y][x];
                if (tiles[y][x].count > 1) { return sum; }
                if (tile.count == 0) { sum++; }
                tiles[y][x] = new Tile(tile.type, tile.count + 1);
                x += beam.dir.x;
                y += beam.dir.y;
            }
            if (x >= 0 && x < tiles[0].length
                    && y >= 0 && y < tiles.length) {
                if (tiles[y][x].count == 0) { sum++; }
                tiles[y][x] = new Tile(tiles[y][x].type, tiles[y][x].count + 1);
                var beams = getReflections(x, y, tiles[y][x].type, beam);
                for (var b : beams) {
                    sum += this.trace(b);
                }
            }
            return sum;
        }

        private ArrayList<Beam> getReflections(int x, int y, char type, Beam beam) {
            var reflections = new ArrayList<Beam>();
            if (type == '-' && beam.dir.x == 0) {
                reflections.add(new Beam(x + E.x, y + E.y, E));
                reflections.add(new Beam(x + W.x, y + W.y, W));
                return reflections;
            }
            if (type == '|' && beam.dir.y == 0) {
                reflections.add(new Beam(x + S.x, y + S.y, S));
                reflections.add(new Beam(x + N.x, y + N.y, N));
                return reflections;
            }
            var d = beam.dir;
            if (type == '\\') {
                d = Direction.reflect(d);
            } else if (type == '/') { d = Direction.rotate(d); }
            x += d.x;
            y += d.y;
            reflections.add(new Beam(x, y, d));
            return reflections;
        }

        @Override
        public String toString() {
            var str = "";
            for (int i = 0; i < tiles.length; i++) {
                for (int j = 0; j < tiles[0].length; j++) {
                    if (tiles[i][j].count > 0) {
                        str += '#';
                        continue;
                    }
                    str += '.';
                }
                str += '\n';
            }
            return str;
        }
    }

    private record Tile(char type, int count) {}

    private record Beam(int x, int y, Direction dir) {}

    private static enum Direction {

        N(0, -1), S(0, 1), E(1, 0), W(-1, 0);

        private final int x, y;

        Direction(int x, int y) {
            this.x = x;
            this.y = y;
        }

        private static Direction rotate(Direction dir) {
            int x = dir.y * -1;
            int y = dir.x * -1;
            return get(x, y);
        }

        private static Direction reflect(Direction dir) {
            int x = dir.y;
            int y = dir.x;
            return get(x, y);
        }

        private static Direction get(int x, int y) {
            for (var d : Direction.values())
                if (d.x == x && d.y == y) { return d; }
            return null;
        }
    }
}