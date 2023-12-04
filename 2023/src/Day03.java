import java.io.IOException;
import java.util.ArrayList;

/**
 * @author Zachary Cockshutt
 * @since  2023-12-03
 */
public class Day03 extends Day
{
    @Override
    public String partOne() throws IOException
    {
        int sum = 0;
        var matrix = getCharMatrix();
        for (int i = 0; i < matrix.length; i++)
            for (int j = 0; j < matrix[0].length; j++)
                if (isSymbol(matrix[i][j])) { sum += getAdjSum(matrix, i, j); }
        return String.valueOf(sum);
    }

    @Override
    public String partTwo() throws IOException
    {
        int sum = 0;
        var matrix = getCharMatrix();
        for (int i = 0; i < matrix.length; i++)
            for (int j = 0; j < matrix[0].length; j++)
                if (isSymbol(matrix[i][j])) { sum += getGearRatio(matrix, i, j); }
        return String.valueOf(sum);
    }

    private char[][] getCharMatrix() throws IOException
    {
        var matrix = new ArrayList<char[]>();
        this.input((s) -> matrix.add(s.toCharArray()));
        return matrix.toArray(new char[matrix.size()][]);
    }

    private int getAdjSum(char[][] matrix, int i, int j)
    {
        int sum = 0, n = matrix.length-1, m = matrix[0].length-1;
        for (int dx = (i > 0 ? -1 : 0); dx <= (i < n ? 1 : 0); ++dx)
            for (int dy = (j > 0 ? -1 : 0); dy <= (j < m ? 1 : 0); ++dy)
                if (isDigit(matrix[i+dx][j+dy])) { sum += sprawlDigit(matrix[i+dx], j+dy); }
        return sum;
    }
    
    private int getGearRatio(char[][] matrix, int i, int j)
    {
        int ratio = 1, count = 0, n = matrix.length-1, m = matrix[0].length-1;
        for (int dx = (i > 0 ? -1 : 0); dx <= (i < n ? 1 : 0); ++dx)
            for (int dy = (j > 0 ? -1 : 0); dy <= (j < m ? 1 : 0); ++dy)
                if (isDigit(matrix[i+dx][j+dy])) 
                { 
                    ratio *= sprawlDigit(matrix[i+dx], j+dy);
                    count++;
                }
        return count == 2 ? ratio : 0;
    }

    private int sprawlDigit(char[] row, int y)
    {
        var i = y;
        var buff = row[i] + "";
        while (--i >= 0 && isDigit(row[i])) { buff = row[i]+buff; }
        while (++y < row.length && isDigit(row[y])) { buff += row[y]; row[y] = '.'; }
        return Integer.parseInt(buff);
    }

    private boolean isDigit(char ch)
    {
        return ch >= '0'
            && ch <= '9';
    }

    private boolean isSymbol(char ch)
    {
        return !isDigit(ch)
             && ch != '.';
    }
}