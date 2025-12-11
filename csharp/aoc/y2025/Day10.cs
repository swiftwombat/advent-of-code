namespace csharp.aoc.y2025;

/**
 * Day 10: Factory
 *
 * @see https://adventofcode.com/2025/day/10
 * @author Zachary Cockshutt
 * @since  2025-12-10
 */
public class Day10 : Day
{
    public override string PartOne()
    {
        int presses = 0;
        ForEachInputLine(line =>
        {
            uint goal = ParseLights(line);
            uint[] wirings = ParseWirings(line);
            PriorityQueue<uint, int> combos = new();
            combos.Enqueue(goal, 0);
            while (combos.TryDequeue(out uint state, out int count))
            {
                if (state == 0U) { presses += count; break; }
                foreach (var wiring in wirings)
                    combos.Enqueue(state ^ wiring, count + 1);
            }
        });
        return presses.ToString();
    }

    public override string PartTwo()
    {
        return "";
    }

    private static uint ParseLights(string line)
    {
        uint bitmask = 0U;
        string lights = line[1..line.IndexOf(']')];
        for (int i = 0; i < lights.Length; i++)
            if (lights[i] == '#') { bitmask |= 1U << i; }
        return bitmask;
    }

    private static uint[] ParseWirings(string line)
    {
        List<uint> bitmasks = [];
        int start = line.IndexOf(']') + 1;
        string[] wirings = [.. line[start..].Split(')').SkipLast(1)];
        foreach (var wiring in wirings)
            bitmasks.Add(ParseWiring(wiring));
        return [.. bitmasks];
    }

    private static uint ParseWiring(string wiring)
    {
        uint bitmask = 0U;
        int[] wires = [.. wiring[2..].Split(',').Select(int.Parse)];
        foreach (var wire in wires)
            bitmask |= 1U << wire;
        return bitmask;
    }
}
