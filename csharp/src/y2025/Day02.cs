namespace aoc.y2025;

/**
 * Day 2: Gift Shop
 *
 * @see https://adventofcode.com/2025/day/2
 * @author Zachary Cockshutt
 * @since  2025-12-02
 */
public class Day02 : Day
{
    public override string PartOne()
    {
        long sum = 0;
        (long a, long b)[] ranges = ParseRanges(GetInputLines()[0]);
        foreach (var (a, b) in ranges)
        {
            for (long i = a; i <= b; i++)
            {
                string id = i.ToString();
                if (id.Length % 2 != 0) { continue; }
                if (IsInvalidId(id, 2)) { sum += i; }
            }
        }
        return sum.ToString();
    }

    public override string PartTwo()
    {
        long sum = 0;
        (long a, long b)[] ranges = ParseRanges(GetInputLines()[0]);
        foreach (var (a, b) in ranges)
        {
            for (long i = a; i <= b; i++)
            {
                string id = i.ToString();
                if (IsInvalidId(id, id.Length)) { sum += i; }
            }
        }
        return sum.ToString();
    }

    private static (long a, long b)[] ParseRanges(string input)
    {
        string[] ranges = input.Split(',');
        return [.. ranges.Select(ParseRange)];
    }

    private static (long a, long b) ParseRange(string range)
    {
        long[] bounds = [.. range.Split('-').Select(long.Parse)];
        return (bounds[0], bounds[1]);
    }

    private static bool IsInvalidId(string id, int maxChunks)
    {
        for (int n = 2; n <= maxChunks; n++)
        {
            if (id.Length % n != 0) { continue; }
            if (Chunkify(id, n).Distinct().Count() == 1) { return true; }
        }
        return false;
    }

    static string[] Chunkify(string str, int chunkCount)
    {
        int chunkSize = str.Length / chunkCount;
        return [.. Enumerable.Range(0, chunkCount)
            .Select(i => str.Substring(i * chunkSize, chunkSize))];
    }
}
