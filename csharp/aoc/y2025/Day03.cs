namespace csharp.aoc.y2025;

/**
 * Day 3: Lobby
 *
 * @see https://adventofcode.com/2025/day/3
 * @author Zachary Cockshutt
 * @since  2025-12-03
 */
public class Day03 : Day
{
    public override string PartOne() { return SumTotalJoltage(2); }

    public override string PartTwo() { return SumTotalJoltage(12); }

    public string SumTotalJoltage(int batteryCount)
    {
        long sum = 0L;
        ForEachInputLine(batteries =>
        {
            long totalJoltage = 0L;
            for (int i = batteryCount - 1; i >= 0; i--)
            {
                char joltage = batteries[..^i].Max();
                batteries = batteries[(batteries.IndexOf(joltage) + 1)..];
                totalJoltage = totalJoltage * 10 + (joltage - '0');
            }
            sum += totalJoltage;
        });
        return sum.ToString();
    }
}
