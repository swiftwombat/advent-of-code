package com.swiftwombat.aoc.y2023;

import static java.lang.Math.abs;

import java.io.IOException;
import java.util.ArrayList;

import com.swiftwombat.aoc.Day;

/**
 * Day 11: Cosmic Expansion
 * 
 * @see https://adventofcode.com/2023/day/11
 * @author Zachary Cockshutt
 * @since 2023-12-13
 */
public class Day11 extends Day {

    @Override
    public String partOne() throws IOException {
        return String.valueOf(sumGalacticDistances(1));
    }

    @Override
    public String partTwo() throws IOException {
        return String.valueOf(sumGalacticDistances(999999));
    }

    private long sumGalacticDistances(int expansionRate) throws IOException {
        long sum = 0;
        char[][] matrix = getCharMatrix();
        int[] rows = getExpandedRows(matrix);
        int[] cols = getExpandedCols(matrix);
        Galaxy[] galaxies = getGalaxies(matrix, rows, cols, expansionRate);
        for (Galaxy galaxy : galaxies) {
            sum += galaxy.getDistanceSum(galaxies);
        }
        return sum / 2;
    }

    private char[][] getCharMatrix() throws IOException {
        var matrix = new ArrayList<char[]>();
        this.forEachInputLine(line -> matrix.add(line.toCharArray()));
        return matrix.toArray(new char[matrix.size()][]);
    }

    private int[] getExpandedRows(char[][] matrix) {
        var rows = new ArrayList<Integer>();
        for (int i = 0; i < matrix.length; i++)
            if (isEmptyRow(matrix, i)) { rows.add(i); }
        return rows.stream().mapToInt(x -> x).toArray();
    }

    private boolean isEmptyRow(char[][] matrix, int i) {
        for (int j = 0; j < matrix[i].length; j++)
            if (matrix[i][j] == '#') { return false; }
        return true;
    }

    private int[] getExpandedCols(char[][] matrix) {
        var cols = new ArrayList<Integer>();
        for (int j = 0; j < matrix[0].length; j++)
            if (isEmptyCol(matrix, j)) { cols.add(j); }
        return cols.stream().mapToInt(x -> x).toArray();
    }

    private boolean isEmptyCol(char[][] matrix, int j) {
        for (int i = 0; i < matrix.length; i++)
            if (matrix[i][j] == '#') { return false; }
        return true;
    }

    private Galaxy[] getGalaxies(
            char[][] matrix, int[] rows, int[] cols, int expansionRate) {
        var galaxies = new ArrayList<Galaxy>();
        for (int i = 0; i < matrix.length; i++)
            for (int j = 0; j < matrix[i].length; j++)
                if (matrix[i][j] == '#') {
                    Galaxy galaxy = getGalaxy(i, j, rows, cols, expansionRate);
                    galaxies.add(galaxy);
                }
        return galaxies.toArray(new Galaxy[galaxies.size()]);
    }

    private Galaxy getGalaxy(
            int i, int j, int[] rows, int[] cols, int expansionRate) {
        i = i + getOffset(rows, i, expansionRate);
        j = j + getOffset(cols, j, expansionRate);
        return new Galaxy(j, i);
    }

    private int getOffset(int[] expandeds, int i, int expansionRate) {
        var offset = 0;
        for (var e : expandeds)
            if (e < i) { offset += expansionRate; }
        return offset;
    }

    private record Galaxy(int x, int y) {

        private long getDistanceSum(Galaxy[] galaxies) {
            long sum = 0L;
            for (Galaxy galaxy : galaxies) {
                if (this.equals(galaxy)) { continue; }
                sum += abs(galaxy.x - x) + abs(galaxy.y - y);
            }
            return sum;
        }

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof Galaxy)) { return false; }
            Galaxy galaxy = (Galaxy) o;
            return x == galaxy.x && y == galaxy.y;
        }
    }
}