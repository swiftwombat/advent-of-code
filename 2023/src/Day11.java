import static java.lang.Math.abs;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @author Zachary Cockshutt
 * @since  2023-12-13
 */
public class Day11 extends Day
{
    @Override
    public String partOne() throws IOException
    {
        long sum = 0;
        var matrix = getCharMatrix();
        var rows = getExpandedRows(matrix);
        var cols = getExpandedCols(matrix);
        var galaxies = getGalaxies(matrix, rows, cols, 1);
        for (var g : galaxies) { sum += g.getDistanceSum(galaxies); }
        return String.valueOf(sum/2);
    }

    @Override
    public String partTwo() throws IOException
    {
        long sum = 0;
        var matrix = getCharMatrix();
        var rows = getExpandedRows(matrix);
        var cols = getExpandedCols(matrix);
        var galaxies = getGalaxies(matrix, rows, cols, 999999);
        for (var g : galaxies) { sum += g.getDistanceSum(galaxies); }
        return String.valueOf(sum/2);
    }

    private char[][] getCharMatrix() throws IOException
    {
        var matrix = new ArrayList<char[]>();
        this.input((s) -> matrix.add(s.toCharArray()));
        return matrix.toArray(new char[matrix.size()][]);
    }
    
    private int[] getExpandedRows(char[][] matrix)
    {
        var rows = new ArrayList<Integer>();
        for (int i = 0; i < matrix.length; i++)
            if (isEmptyRow(matrix, i)) { rows.add(i); }
        return rows.stream().mapToInt(x->x).toArray();
    }
    
    private boolean isEmptyRow(char[][] matrix, int i)
    {
        for (int j=0; j<matrix[i].length; j++)
            if (matrix[i][j] == '#') { return false; }
        return true;
    }

    private int[] getExpandedCols(char[][] matrix)
    {
        var cols = new ArrayList<Integer>();
        for (int j = 0; j < matrix[0].length; j++)
            if (isEmptyCol(matrix, j)) { cols.add(j); }
        return cols.stream().mapToInt(x->x).toArray();
    }

    private boolean isEmptyCol(char[][] matrix, int j)
    {
        for (int i=0; i<matrix.length; i++)
            if (matrix[i][j] == '#') { return false; }
        return true;
    }

    private Galaxy[] getGalaxies(char[][] matrix, int[] rows, int[] cols, int rate)
    {
        var galaxies = new ArrayList<Galaxy>();
        for (int i=0; i<matrix.length; i++)
            for (int j=0; j<matrix[i].length; j++)
                if (matrix[i][j] == '#') { galaxies.add(getGalaxy(i,j,rows,cols,rate)); }
        return galaxies.toArray(new Galaxy[galaxies.size()]);
    }

    private Galaxy getGalaxy(int i, int j, int[] rows, int[] cols, int rate)
    {
        i = i + getOffset(rows,i,rate);
        j = j + getOffset(cols,j,rate);
        return new Galaxy(j,i);
    }

    private int getOffset(int[] expandeds, int i, int rate)
    {
        var offset = 0;
        for (var e : expandeds) 
            if (e<i) { offset+=rate; }
        return offset;
    }

    private record Galaxy(int x, int y) 
    {
        private long getDistanceSum(Galaxy[] galaxies)
        {
            var sum = 0L;
            for (var g : galaxies)
            {
                if (this.equals(g)) { continue; }
                sum += abs(g.x-x) + abs(g.y-y);
            } return sum;
        }

        @Override
        public boolean equals(Object o)
        {
            if (!(o instanceof Galaxy)) { return false; }
            var g = (Galaxy)o;
            return x == g.x && y == g.y;
        }
    }
}