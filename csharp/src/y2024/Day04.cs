namespace aoc.y2024;

/**
 * Day 4: Ceres Search
 *
 * @see https://adventofcode.com/2024/day/4
 * @author Zachary Cockshutt
 * @since  2024-12-04
 */
public class Day04 : Day
{
    public override string PartOne()
    {
        long count = 0L;
        string match = "XMAS";
        var matrix = GetInputLines();
        int n = matrix.Length;
        int len = match.Length;
        for (int x = 0; x < n; x++)
            for (int y = 0; y < n; y++)
                count += new string[] {
                    GetWord(matrix, x, y, 1, 0, len),
                    GetWord(matrix, x, y, 0, 1, len),
                    GetWord(matrix, x, y, 1,-1, len),
                    GetWord(matrix, x, y,-1,-1, len)
                }.Count(x => IsMatch(x, match));
        return count.ToString();
    }

    public override string PartTwo()
    {
        long count = 0L;
        string match = "MAS";
        var matrix = GetInputLines();
        int len = match.Length;
        int buf = len - 1;
        int n = matrix.Length - buf;
        for (int x = 0; x < n; x++)
            for (int y = 0; y < n; y++)
                count += new string[] {
                    GetWord(matrix, x, y, 1, 1, len),
                    GetWord(matrix, x, y + buf, 1, -1, len),
                }.All(x => IsMatch(x, match)) ? 1 : 0;
        return count.ToString();
    }

    private static string GetWord(
        string[] matrix, int x, int y, int dx, int dy, int len)
    {
        string str = "";
        int n = matrix.Length;
        if (y + dy * (len - 1) >= n || y + dy * (len - 1) < 0) { return ""; }
        for (int i = 0; i < len; i++)
        {
            if (x >= n || x < 0) { return ""; }
            str += matrix[x][y];
            x += dx;
            y += dy;
        }
        return str;
    }

    public static bool IsMatch(string str, string match)
    {
        char[] rev = str.ToCharArray();
        Array.Reverse(rev);
        return match.Equals(str)
            || match.Equals(new string(rev));
    }
}
