namespace aoc.y2024;

/**
 * Day 1: Historian Hysteria
 *
 * @see https://adventofcode.com/2024/day/1
 * @author Zachary Cockshutt
 * @since  2024-12-01
 */
public class Day01 : Day
{
    public override string PartOne()
    {
        List<int> left = [];
        List<int> right = [];
        ForEachInputLine(line =>
        {
            var (l, r) = ParseIds(line);
            left.Add(l);
            right.Add(r);
        });
        left.Sort();
        right.Sort();
        long distance = left.Zip(right, (l, r) => Math.Abs(l - r)).Sum();
        return distance.ToString();
    }

    public override string PartTwo()
    {
        List<int> left = [];
        Dictionary<int, int> right = [];
        ForEachInputLine(line =>
        {
            var (l, r) = ParseIds(line);
            left.Add(l);
            int occurances = right.GetValueOrDefault(r) + 1;
            if (!right.TryAdd(r, occurances)) { right[r] = occurances; }
        });
        return left.Sum(l => l * right.GetValueOrDefault(l)).ToString();
    }

    private static (int left, int right) ParseIds(string line)
    {
        var ids = line.Split("  ").Select(int.Parse).ToArray();
        return (ids[0], ids[1]);
    }
}
