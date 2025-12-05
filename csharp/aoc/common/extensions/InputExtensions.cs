namespace csharp.aoc.common.extensions;

public static partial class InputExtensions
{
    public static char[][] ToCharMatrix(this IEnumerable<string> input)
    {
        int i = 0;
        var matrix = new char[input.Count()][];
        foreach (var line in input) { matrix[i++] = line.ToCharArray(); }
        return matrix;
    }

    public static IEnumerable<char> NeighboursOf(
        this char[][] matrix, int i, int j, int r = 1)
    {
        int m = matrix.Length;
        int n = matrix[0].Length;
        for (int x = Math.Max(i - r, 0); x <= Math.Min(i + r, m - 1); x++)
            for (int y = Math.Max(j - r, 0); y <= Math.Min(j + r, n - 1); y++)
            {
                if (x == i && y == j) { continue; }
                yield return matrix[x][y];
            }
    }

    public static void Print(this char[][] matrix)
    {
        var sb = new StringBuilder();
        foreach (var row in matrix) { sb.Append(row).Append('\n'); }
        Console.WriteLine(sb.ToString());
    }
}