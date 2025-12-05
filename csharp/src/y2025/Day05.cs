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
        (long[][] ranges, long[] ids) = SplitInput(GetInputLines());
        foreach (var id in ids)
            if (ranges.Any(r => id >= r[0] && id <= r[1])) { sum++; }
        return sum.ToString();
    }

    public override string PartTwo()
    {
        long sum = 0L;
        (long[][] ranges, _) = SplitInput(GetInputLines());
        (long value, bool isEnd)[] boundaries = [.. ranges
            .SelectMany(r => new (long, bool)[] { (r[0], false), (r[1], true) })
            .OrderBy(b => b.Item1)
            .ThenBy(b => b.Item2)];

        int count = 0, start = 0, end = 0;
        for (int i = 0; i < boundaries.Length; i++)
        {
            count += boundaries[i].isEnd ? -1 : 1;
            if (count == 0)
            {
                end = i;
                sum += boundaries[end].value - boundaries[start].value + 1;
                start = i + 1;
            }
        }
        return sum.ToString();
    }

    private static (long[][], long[]) SplitInput(string[] input)
    {
        int i = Array.IndexOf(input, "");
        long[][] ranges = [.. input.Take(i).Select(l => l.ParseLongs().ToArray())];
        long[] ids = [.. input.Skip(i).ParseLongs()];
        return (ranges, ids);
    }
}
