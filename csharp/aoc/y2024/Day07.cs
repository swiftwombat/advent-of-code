namespace csharp.aoc.y2024;

/**
 * Day 7: Bridge Repair
 *
 * @see https://adventofcode.com/2024/day/7
 * @author Zachary Cockshutt
 * @since  2024-12-07
 */
public class Day07 : Day
{
    public override string PartOne()
    {
        long sum = 0L;
        ForEachInputLine(line =>
        {
            (long lhs, long[] nums) = Parse(line);
            if (Check(lhs, nums[0], nums[1..])) { sum += lhs; }
        });
        return sum.ToString();
    }

    public override string PartTwo()
    {
        long sum = 0L;
        ForEachInputLine(line =>
        {
            (long lhs, long[] nums) = Parse(line);
            if (Check(lhs, nums[0], nums[1..], true)) { sum += lhs; }
        });
        return sum.ToString();
    }

    private static (long lhs, long[] nums) Parse(string line)
    {
        var arr = line.Split(": ");
        long lhs = long.Parse(arr[0]);
        long[] nums = [.. arr[1].Split(" ").Select(long.Parse)];
        return (lhs, nums);
    }

    private static bool Check(
        long lhs, long rhs, long[] nums, bool isPart2 = false)
    {
        return nums switch
        {
            _ when rhs > lhs => false,
            [] => rhs == lhs,
            _ => Check(lhs, rhs * nums[0], nums[1..], isPart2)
               || Check(lhs, rhs + nums[0], nums[1..], isPart2)
               || (isPart2 && Check(lhs, Concat(rhs, nums[0]), nums[1..], isPart2))
        };
    }

    private static long Concat(long x, long y)
        => long.Parse($"{x}{y}");
}
