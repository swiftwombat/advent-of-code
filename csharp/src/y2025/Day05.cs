namespace aoc.y2025;

/**
 * Day 5: Cafeteria
 *
 * @see https://adventofcode.com/2025/day/5
 * @author Zachary Cockshutt
 * @since  2025-12-05
 */
public class Day05 : Day
{
    public override string PartOne()
    {
        long sum = 0L;
        string[] input = GetInputLines();
        List<(long min, long max)> ranges = [.. input
            .TakeWhile(l => l.Length > 0)
            .Select(l =>
            {
                string[] bounds = l.Split("-");
                return (long.Parse(bounds[0]), long.Parse(bounds[1]));
            })];
        long[] ids = [.. input.SkipWhile(l => l.Length > 0).Skip(1).Select(long.Parse)];
        foreach (var id in ids)
            if (ranges.Any(range => id >= range.min && id <= range.max)) { sum++; }
        return sum.ToString();
    }

    public override string PartTwo()
    {
        long sum = 0L;
        string[] ranges = [.. GetInputLines().TakeWhile(l => l.Length > 0)];
        List<Boundary> boundaries = [.. ExpandRanges(ranges)
            .OrderBy(r => r.Value)
            .ThenBy(r => r.IsEnd)];

        int count = 0, start = 0, end = 0;
        for (int i = 0; i < boundaries.Count; i++)
        {
            count += boundaries[i].IsEnd ? -1 : 1;
            if (count == 0)
            {
                end = i;
                sum += boundaries[end].Value - boundaries[start].Value + 1;
                start = i + 1;
            }
        }
        return sum.ToString();
    }

    private static IEnumerable<Boundary> ExpandRanges(string[] ranges)
    {
        foreach (var range in ranges)
        {
            var bounds = range.Split('-');
            yield return new Boundary(long.Parse(bounds[0]), false);
            yield return new Boundary(long.Parse(bounds[1]), true);
        }
    }

    private record Boundary(long Value, bool IsEnd);
}
