import java.io.IOException;
import java.util.ArrayList;

/**
 * @author Zachary Cockshutt
 * @since  2023-12-04
 */
public class Day03 extends Day
{
    @Override
    public String partOne() throws IOException
    {
        var sum = sumSymbolAdj(false);
        return String.valueOf(sum);
    }

    @Override
    public String partTwo() throws IOException
    {
        var sum = sumSymbolAdj(true);
        return String.valueOf(sum);
    }

    private int sumSymbolAdj(boolean isPartTwo) throws IOException
    {
        int sum = 0;
        var matrix = getCharMatrix();
        for (int i = 0; i < matrix.length; i++)
            for (int j = 0; j < matrix[0].length; j++)
                if (isSymbol(matrix[i][j])) { sum += getAdj(matrix, i, j, isPartTwo); }
        return sum;
    }

    private char[][] getCharMatrix() throws IOException
    {
        var matrix = new ArrayList<char[]>();
        this.input((s) -> matrix.add(s.toCharArray()));
        return matrix.toArray(new char[matrix.size()][]);
    }

    private int getAdj(char[][] matrix, int i, int j, boolean isPartTwo)
    {
        int sum = 0, ratio = 1, count = 0, n = matrix.length-1, m = matrix[0].length-1;
        for (int dx = (i > 0 ? -1 : 0); dx <= (i < n ? 1 : 0); ++dx)
            for (int dy = (j > 0 ? -1 : 0); dy <= (j < m ? 1 : 0); ++dy)
                if (isDigit(matrix[i+dx][j+dy])) 
                { 
                    var digit = sprawlDigit(matrix[i+dx], j+dy);
                    sum += digit; ratio *= digit; count++;
                }
        return isPartTwo ? (count == 2 ? ratio : 0) : sum;
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