namespace csharp.aoc.y2025;

/**
 * Day 7: Laboratories
 *
 * @see https://adventofcode.com/2025/day/7
 * @author Zachary Cockshutt
 * @since  2025-12-07
 */
public class Day07 : Day
{
    public override string PartOne()
    {
        long splits = 0L;
        string[] input = GetInputLines();
        HashSet<int> propogations = [input[0].IndexOf('S')];
        foreach (var line in input.Skip(2))
        {
            if (line.Distinct().Count() == 1) { continue; }
            for (int i = 0; i < line.Length; i++)
            {
                if (line[i] == '.') { continue; }
                if (propogations.Remove(i))
                {
                    splits++;
                    propogations.Add(i + 1);
                    propogations.Add(i - 1);
                }
            }
        }
        return splits.ToString();
    }

    public override string PartTwo()
    {
        string[] input = GetInputLines();
        int propogration = input[0].IndexOf('S');
        var cache = new Dictionary<(int, int), long>();
        return CountTimelines(input, propogration, 1, cache).ToString();
    }

    private static long CountTimelines(
        string[] input,
        int propogration,
        int index,
        Dictionary<(int, int), long> cache)
    {
        if (index == input.Length) { return 1L; }

        var key = (index, propogration);
        if (cache.TryGetValue(key, out long cached)) { return cached; }

        string line = input[index];
        long timelines = 0L;
        if (line[propogration] == '^')
        {
            timelines += CountTimelines(input, propogration + 1, index + 1, cache);
            timelines += CountTimelines(input, propogration - 1, index + 1, cache);
        }
        else
        {
            timelines += CountTimelines(input, propogration, index + 1, cache);
        }

        cache[key] = timelines;
        return timelines;
    }
}
