
using System.Diagnostics;

/**
 * @author Zachary Cockshutt
 * @since  2024-12-01
 */
class Program
{
    private static readonly Dictionary<int, Day> days = new() {
        { 1, new Day01() }
    };

    static void Main(string[] args)
    {
        try
        {
            if (args.Length < 1) { Error("Invalid number of args."); }
            Run(int.Parse(args[0]));
        }
        catch (Exception e) { Error(e.ToString()); }
    }

    private static void Run(int dayNumber)
    {
        var day = days[dayNumber];
        if (day == null) { Error("Day not found."); }
        else             { day.Run(); }
    }

    private static void Error(string msg)
    {
        Console.WriteLine("Error: {0}", msg);
        Environment.Exit(1);
    }
}
