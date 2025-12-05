namespace csharp.aoc.y2025;

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
        var (ranges, ids) = ParseInput(GetInputLines());
        return ids.Count(id => ranges.Any(r => r.Contains(id))).ToString();
    }

    public override string PartTwo()
    {
        var (ranges, _) = ParseInput(GetInputLines());
        return ranges.Sum(r => r.Length).ToString();
    }

    private static (Range<long>[], long[]) ParseInput(string[] input)
    {
        int i = Array.IndexOf(input, "");
        var ranges = input.Take(i).Select(ParseRange).ToArray();
        var ids = input.Skip(i + 1).Select(long.Parse).ToArray();
        return (MergeRanges(ranges), ids);
    }

    private static Range<long> ParseRange(string range)
    {
        long[] bounds = [.. range.Split("-").Select(long.Parse)];
        return new(bounds[0], bounds[1]);
    }

    private static Range<long>[] MergeRanges(Range<long>[] ranges)
    {
        var ordered = ranges.OrderBy(r => r.Start);
        var merged = new List<Range<long>>(ordered.Count()) { ordered.First() };
        foreach (var range in ordered.Skip(1))
        {
            var current = merged[^1];
            if (current.Intersects(range)) { merged[^1] = current.Union(range); }
            else { merged.Add(range); }
        }
        return [.. merged];
    }
}
