
using System.Reflection.Emit;

/**
 * @author Zachary Cockshutt
 * @since  2024-12-01
 */
public class Day01 : Day
{
    public override string PartOne()
    {
        List<int> left = [];
        List<int> right = [];
        Input(s =>
        {
            var (l, r) = ParseIds(s);
            left.Add(l);
            right.Add(r);
        });
        left.Sort();
        right.Sort();
        long sum = left.Zip(right, (l,r) => Math.Abs(l-r)).Sum();
        return sum.ToString();
    }

    public override string PartTwo()
    {

        List<int> left = [];
        Dictionary<int,int> right = [];
        Input(s =>
        {
            var (l, r) = ParseIds(s);
            left.Add(l);
            int count = right.GetValueOrDefault(r) + 1;
            if (!right.TryAdd(r, count)) { right[r] = count; };
        });
        return left.Sum(l => l * right.GetValueOrDefault(l)).ToString();
    }

    private static (int left, int right) ParseIds(string line)
    {
        var ids = line.Split("  ").Select(int.Parse).ToArray();
        return (ids[0], ids[1]);
    }
}
