namespace csharp.aoc.y2025;

/**
 * Day 6: Trash Compactor
 *
 * @see https://adventofcode.com/2025/day/6
 * @author Zachary Cockshutt
 * @since  2025-12-06
 */
public class Day06 : Day
{
    public override string PartOne()
    {
        string[] sheet = GetInputLines();
        var operaters = sheet[^1].SplitByWhitespace().Select(op => op[0]).ToArray();
        var operands = sheet[..^1].Select(row => row.SplitByWhitespace().Select(long.Parse).ToArray()).ToArray();
        return operaters.Select((op, col) => operands.Select(row => row[col]).Aggregate((a, b) => OperateOn(a, b, op))).Sum().ToString();
    }

    public override string PartTwo()
    {
        long sum = 0L;
        char[][] sheet = GetInputLines().ToMatrix();
        var operators = sheet[^1];
        var operands = new List<long>();
        for (int col = sheet.Max(row => row.Length) - 1; col >= 0; col--)
        {
            char[] numbers = sheet[..^1].GetColumn(col);
            long operand = long.Parse(string.Join("", numbers).Trim());
            operands.Add(operand);
            if (col >= operators.Length || operators[col] == ' ') { continue; }
            sum += operands.Aggregate((a, b) => OperateOn(a, b, operators[col]));
            operands = [];
            col--;
        }
        return sum.ToString();
    }

    private static long OperateOn(long a, long b, char op)
        => op == '*' ? (a * b) : (a + b);
}
