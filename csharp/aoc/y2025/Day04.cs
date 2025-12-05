namespace csharp.aoc.y2025;

/**
 * Day 4: Printing Department
 *
 * @see https://adventofcode.com/2025/day/4
 * @author Zachary Cockshutt
 * @since  2025-12-04
 */
public class Day04 : Day
{
    public override string PartOne()
    {
        long sum = 0L;
        char[][] grid = GetInputLines().ToCharMatrix();
        sum += GetAccessibleCells(grid).Count;
        return sum.ToString();
    }

    public override string PartTwo()
    {
        long sum = 0L;
        char[][] grid = GetInputLines().ToCharMatrix();
        List<(int i, int j)> accessible;
        do
        {
            accessible = GetAccessibleCells(grid);
            sum += accessible.Count;
            foreach (var (i, j) in accessible) { grid[i][j] = 'x'; }
        }
        while (accessible.Count > 0);
        return sum.ToString();
    }

    private static List<(int i, int j)> GetAccessibleCells(char[][] grid)
    {
        List<(int, int)> cells = [];
        for (int i = 0; i < grid[0].Length; i++)
            for (int j = 0; j < grid[1].Length; j++)
            {
                if (grid[i][j] != '@') { continue; }
                var neighbours = grid.NeighboursOf(i, j);
                if (neighbours.Count(cell => cell == '@') < 4) { cells.Add((i, j)); }
            }
        return cells;
    }
}
