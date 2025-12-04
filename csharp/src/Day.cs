namespace aoc;

/**
 * @author Zachary Cockshutt
 * @since  2024-12-01
 */
public abstract class Day
{
    private string fileName = "";

    public abstract string PartOne();

    public abstract string PartTwo();

    public void Run(string year)
    {
        var day = GetType().Name.ToLower();
        fileName = string.Format("dat/y{0}/{1}.txt", year, day);
        var rs = new string[] { Run(PartOne), Run(PartTwo) };
        for (int i = 0; i < 2; i++) { Console.WriteLine("P{0}: {1}", i + 1, rs[i]); }
    }

    public static string Run(Func<string> func)
    {
        var rs = "";
        long t = DateTime.Now.Ticks;
        try { rs = func(); }
        catch (Exception e) { Console.WriteLine(e.ToString()); }
        float td = (float)(DateTime.Now.Ticks - t) / TimeSpan.TicksPerMillisecond;
        rs = string.Format("{0,-16}[{1,0:F2} ms]", rs, td);
        return rs;
    }

    protected string[] GetInputLines()
    {
        var lines = File.ReadAllLines(fileName);
        return lines;
    }

    protected void ForEachInputLine(Action<string> callback)
    {
        string? line = null;
        using var reader = new StreamReader(fileName);
        while ((line = reader.ReadLine()) != null) { callback(line); }
    }
}

public static class InputExtensions
{
    public static char[][] ToCharMatrix(this string[] input)
    {
        int i = 0;
        var matrix = new char[input.Length][];
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