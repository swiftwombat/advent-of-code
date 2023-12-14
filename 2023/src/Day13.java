import static java.lang.Math.min;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @author Zachary Cockshutt
 * @since  2023-12-14
 */
public class Day13 extends Day
{
    @Override
    public String partOne() throws IOException
    {
        int sum = 0;
        var tensor = this.parseCharTensor();
        for (var matrix : tensor) { sum += getSummary(matrix); }
        return String.valueOf(sum);
    }

    @Override
    public String partTwo() throws IOException
    {
        int sum = 0;
        var tensor = this.parseCharTensor();
        for (var matrix : tensor) { sum += getSummary(matrix); }
        return String.valueOf(sum);
    }

    private char[][][] parseCharTensor() throws IOException
    {
        var tensor = new ArrayList<char[][]>();
        var matrix = new ArrayList<char[]>();
        this.input((s) -> 
        {
            if (!s.isEmpty()) { matrix.add(s.toCharArray()); return; }
            tensor.add(matrix.toArray(new char[matrix.size()][])); 
            matrix.removeAll(matrix);
        });
        tensor.add(matrix.toArray(new char[matrix.size()][]));
        return tensor.toArray(new char[tensor.size()][][]);
    }

    private int getSummary(char[][] matrix)
    {
        int row = 0, col = 0;
        row = getReflection(matrix);
        if (row == 0)
        {
            matrix = rotateMatrix(matrix);
            col = getReflection(matrix);
        }
        return col + row * 100;
    }

    private int getReflection(char[][] matrix)
    {
        for (int i=1; i<matrix.length; i++)
        {
            //var isReflected = true;
            var mismatches = 0;
            var n = i + min(i, matrix.length-i);
            for (int j=i-1, k=i; j>=0 && k<n; j--, k++)
            {
                //isReflected = Arrays.equals(matrix[j], matrix[k]);
                mismatches += mismatches(matrix[j], matrix[k]);
                if (mismatches > 1) { break; }
                //if (!isReflected) { break; }
            }
            if (mismatches == 1) { return i; }
            //if (isReflected) { return i; }
        } 
        return 0;
    }

    private char[][] rotateMatrix(char[][] matrix) {
        final int M = matrix.length;
        final int N = matrix[0].length;
        var mat = new char[N][M];
        for (int i = 0; i < M; i++)
            for (int j = 0; j < N; j++)
                mat[j][M-1-i] = matrix[i][j];
        return mat;
    }

    private int mismatches(char[] a, char[] b)
    {
        var count = 0;
        for (int i=0; i<a.length;i++) { if (a[i] != b[i]) { count++; } }
        return count;
    }
}