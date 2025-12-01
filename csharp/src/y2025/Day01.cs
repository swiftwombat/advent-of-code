namespace aoc.y2025;

/**
 * Day 1: Secret Entrance
 *
 * @see https://adventofcode.com/2025/day/1
 * @author Zachary Cockshutt
 * @since  2025-12-01
 */
public class Day01 : Day
{
    public override string PartOne()
    {
        int password = 0;
        int dial = 50;
        ForEachInputLine(line =>
        {
            int delta = ParseDelta(line);
            dial = Mod(dial + delta, 100);
            if (dial == 0) { password++; }
        });
        return password.ToString();
    }

    public override string PartTwo()
    {
        int password = 0;
        int dial = 50;
        ForEachInputLine(line =>
        {
            int delta = ParseDelta(line);
            int sign = Math.Sign(delta);
            int normalisedDial = Mod(100 + dial * sign, 100);
            password += (normalisedDial + delta * sign) / 100;
            dial = Mod(dial + delta, 100);
        });
        return password.ToString();
    }

    private static int ParseDelta(string line)
    {
        char dir = line[0];
        int delta = int.Parse(line[1..]);
        return dir == 'L' ? -delta : delta;
    }

    private static int Mod(int x, int m)
    {
        int r = x % m;
        return r < 0 ? r + m : r;
    }
}
