/**
 * @author Zachary Cockshutt
 * @since  2024-12-02
 */
public class Day02 : Day
{
    public override string PartOne()
    {
        long safeCount = 0L;
        this.Input(s =>
        {
            int[] levels = s.Split(" ").Select(int.Parse).ToArray();
            if (IsSafe(levels)) { safeCount++; }
        });
        return safeCount.ToString();
    }

    public override string PartTwo()
    {
        long safeCount = 0L;
        this.Input(s =>
        {
            int[] levels = s.Split(" ").Select(int.Parse).ToArray();
            if (IsSafe(levels)) { safeCount++; return; }
            for (int i = 0; i < levels.Length; i++)
            {
                int[] copy = levels.Where((v, j) => j != i).ToArray();
                if (IsSafe(copy)) { safeCount++; return; }
            }
        });
        return safeCount.ToString();
    }

    private static bool IsSafe(int[] levels)
    {
        for (int i = 1; i < levels.Length; i++)
        {
            int diff = Math.Abs(levels[i] - levels[i-1]);
            if (diff < 1 || diff > 3) { return false; }
        }
        return IsOrdered(levels);
    }

    private static bool IsOrdered(int[] arr)
    {
        return arr.SequenceEqual(arr.OrderDescending())
            || arr.SequenceEqual(arr.Order());
    }
}
