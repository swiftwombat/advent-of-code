

using System.Security.Cryptography.X509Certificates;

/**
 * @author Zachary Cockshutt
 * @since  2024-12-07
 */
public class Day08 : Day
{
    public override string PartOne()
    {
        var map = this.Input().ToCharMatrix();
        var positions = FindPositions(map);
        var antinodes = new HashSet<(int, int)>();
        foreach (var set in positions)
            foreach (var p1 in set.Value)
                foreach (var p2 in set.Value)
                {
                    if (p1 == p2) { continue; }
                    var p3 = (2*p1.x-p2.x, 2*p1.y-p2.y);
                    if (IsBounded(p3, map.Length)) { antinodes.Add(p3); }
                }
        return antinodes.Count.ToString();
    }

    public override string PartTwo()
    {
        var map = this.Input().ToCharMatrix();
        var positions = FindPositions(map);
        var antinodes = new HashSet<(int, int)>();
        foreach (var set in positions)
            for (int i = 0; i < set.Value.Count; i++)
                for (int j = 0; j < set.Value.Count; j++)
                {
                    var p1 = set.Value[i];
                    var p2 = set.Value[j];
                    if (p1 == p2) { continue; }
                    do
                    {
                        antinodes.Add(p2);
                        var p3 = (2*p1.x-p2.x, 2*p1.y-p2.y);
                        p2 = p1;
                        p1 = p3;
                    }
                    while (IsBounded(p2, map.Length));
                }
        return antinodes.Count.ToString();
    }

    private static Dictionary<char, List<(int x, int y)>> FindPositions(char[][] map)
    {
        int n = map.Length;
        var positions = new Dictionary<char, List<(int x, int y)>>();
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
            {
                char id = map[i][j];
                if (positions.TryAdd(id, [(i,j)])) { continue; }
                if (id != '.') { positions[id].Add((i,j)); }
            }
        return positions;
    }

    private static bool IsBounded(
        (int x, int y) pos, int n)
    {
        return pos.x >= 0 && pos.x < n
            && pos.y >= 0 && pos.y < n;
    }
}
