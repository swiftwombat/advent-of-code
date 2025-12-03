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
    public override string PartOne() { return SumInvalidIds(false); }

    public override string PartTwo() { return SumInvalidIds(true); }

    private string SumInvalidIds(bool isPartTwo)
    {
        long sum = 0L;
        string input = GetInputLines()[0];
        string[] ids = [.. input.Split(',').SelectMany(ParseRange)];
        foreach (string id in ids)
        {
            int maxChunks = isPartTwo ? id.Length : 2;
            if (IsInvalidId(id, maxChunks)) { sum += long.Parse(id); }
        }
        return sum.ToString();
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

    static string[] Chunkify(string id, int chunkCount)
    {
        int chunkSize = id.Length / chunkCount;
        return [.. Enumerable.Range(0, chunkCount)
            .Select(i => id.Substring(i * chunkSize, chunkSize))];
    }

    private static IEnumerable<string> ParseRange(string range)
    {
        long[] bounds = [.. range.Split('-').Select(long.Parse)];
        for (long i = bounds[0]; i <= bounds[1]; i++) { yield return i.ToString(); }
    }
}
