namespace aoc;

/**
 * @author Zachary Cockshutt
 * @since  2024-12-01
 */
public abstract class Day
{
    private readonly string fileName;

    public Day()
    {
        var day = GetType().Name.ToLower();
        fileName = string.Format("dat/{0}.txt", day);
    }

    public abstract string PartOne();

    public abstract string PartTwo();

    public void Run()
    {
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
}