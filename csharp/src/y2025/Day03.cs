namespace aoc.y2025;

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
            int prevIndex = -1;
            long joltage = 0L;
            List<Battery> sorted = SortAndIndex(batteries);
            for (int i = batteryCount; i > 0; i--)
            {
                int maxIndex = batteries.Length - i + 1;
                var battery = sorted.First(b => b.Index > prevIndex && b.Index < maxIndex);
                prevIndex = battery.Index;
                joltage = joltage * 10 + (battery.Joltage - '0');
            }
            sum += joltage;
        });
        return sum.ToString();
    }

    private static List<Battery> SortAndIndex(string batteries)
    {
        return [.. batteries
            .Select((j, i) => new Battery(j, i))
            .OrderByDescending(b => b.Joltage)];
    }

    private record Battery(char Joltage, int Index);
}
