
using System.Text.RegularExpressions;

/**
 * @author Zachary Cockshutt
 * @since  2024-12-03
 */
public class Day03 : Day
{
    public override string PartOne()
    {
        long total = 0L;
        string memory = string.Join("", this.Input());
        string pattern = @"mul\((?<a>\d+),(?<b>\d+)\)";
        var matches = Regex.Matches(memory, pattern);
        foreach (Match m in matches) { total += Multiply(m); }
        return total.ToString();
    }

    public override string PartTwo()
    {
        long total = 0L;
        bool enabled = true;
        string memory = string.Join("", this.Input());
        string pattern = @"mul\((?<a>\d+),(?<b>\d+)\)|do\(\)|don't\(\)";
        var matches = Regex.Matches(memory, pattern);
        foreach (Match m in matches)
        {
            string val = m.Value;
            if (!val.StartsWith("mul")) { enabled = val.Equals("do()"); }
            else if (enabled)           { total += Multiply(m); }
        }
        return total.ToString();
    }

    private static int Multiply(Match match)
    {
        int a = int.Parse(match.Groups["a"].Value);
        int b = int.Parse(match.Groups["b"].Value);
        return  a * b;
    }
}
