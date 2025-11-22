namespace aoc.y2024;

/**
 * Day 6: Guard Gallivant
 *
 * @see https://adventofcode.com/2024/day/6
 * @author Zachary Cockshutt
 * @since  2024-12-06
 */
public class Day06 : Day
{
    public override string PartOne()
    {
        char[][] map = GetInputLines().ToCharMatrix();
        (int, int) startPos = FindStartPosition(map);
        int positionCount = Patrol(map, startPos).route.Length;
        return positionCount.ToString();
    }

    public override string PartTwo()
    {
        long loopCount = 0L;
        char[][] map = GetInputLines().ToCharMatrix();
        (int, int) startPos = FindStartPosition(map);
        (var route, _) = Patrol(map, startPos);
        foreach ((int x, int y) pos in route)
        {
            map[pos.x][pos.y] = '#';
            if (Patrol(map, startPos).isLoop) { loopCount++; }
            map[pos.x][pos.y] = '.';
        }
        return loopCount.ToString();
    }

    private static ((int, int)[] route, bool isLoop) Patrol(
        char[][] map, (int x, int y) pos)
    {
        int n = map.Length;
        (int dx, int dy) dir = (-1, 0);
        HashSet<((int, int) pos, (int, int) dir)> visited = [(pos, dir)];
        bool isLoop = false;
        while (!isLoop)
        {
            var _pos = Iterate(pos, dir);
            if (!IsBounded(_pos, n)) { break; }
            if (map[_pos.x][_pos.y] == '#') { dir = Rotate(dir); continue; }
            isLoop = !visited.Add((_pos, dir));
            pos = _pos;
        }
        var route = visited.DistinctBy(x => x.pos).Select(x => x.pos).ToArray();
        return (route, isLoop);
    }

    private static (int x, int y) FindStartPosition(char[][] map)
    {
        int x = 0, y = 0;
        while ((y = Array.IndexOf(map[x++], '^')) == -1) { }
        ;
        return (--x, y);
    }

    private static (int dx, int dy) Rotate((int, int) dir)
    {
        return dir switch
        {
            (0, 1) => (1, 0),
            (1, 0) => (0, -1),
            (0, -1) => (-1, 0),
            (-1, 0) => (0, 1),
            _ => throw new Exception()
        };
    }

    private static (int x, int y) Iterate(
        (int x, int y) pos, (int dx, int dy) dir)
    {
        return (pos.x + dir.dx, pos.y + dir.dy);
    }

    private static bool IsBounded(
        (int x, int y) pos, int n)
    {
        return pos.x >= 0 && pos.x < n
            && pos.y >= 0 && pos.y < n;
    }
}
