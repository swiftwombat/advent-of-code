namespace aoc;

/**
 * @author Zachary Cockshutt
 * @since  2024-12-01
 */
class Program
{
    static void Main(string[] args)
    {
        try
        {
            if (args.Length < 2) { Error("Invalid number of args."); }
            Run(args[0], int.Parse(args[1]));
        }
        catch (Exception e) { Error(e.ToString()); }
    }

    private static void Run(String year, int dayNumber)
    {
        var name = string.Format("aoc.y{0}.Day{1}", year, dayNumber.ToString("D2"));
        var day = Type.GetType(name);
        if (day == null) { Error("Day not found."); }
        else { (Activator.CreateInstance(day) as Day)?.Run(); }
    }

    private static void Error(string msg)
    {
        Console.WriteLine("Error: {0}", msg);
        Environment.Exit(1);
    }
}
