namespace csharp.aoc.y2025;

/**
 * Day 9: Movie Theater
 *
 * @see https://adventofcode.com/2025/day/9
 * @author Zachary Cockshutt
 * @since  2025-12-09
 */
public class Day09 : Day
{
    public override string PartOne()
    {
        Tile[] tiles = [.. GetInputLines().Select(ParseTile)];
        var areas = new SortedSet<long>(Comparer<long>.Create((a, b) => b.CompareTo(a)));
        for (int i = 0; i < tiles.Length; ++i)
            for (int j = i + 1; j < tiles.Length; ++j)
                areas.Add(CalculateArea(tiles[i], tiles[j]));
        return areas.First().ToString();
    }

    public override string PartTwo()
    {
        Tile[] poly = [.. GetInputLines().Select(ParseTile)];
        var areas = new SortedSet<long>(Comparer<long>.Create((a, b) => b.CompareTo(a)));
        var cache = new Dictionary<Tile, bool>();
        for (int i = 0; i < poly.Length; ++i)
            for (int j = i + 1; j < poly.Length; ++j)
            {
                Tile a = poly[i];
                Tile b = poly[j];
                if (!IsTileInPolygon(poly, new Tile(a.X, b.Y), cache) ||
                    !IsTileInPolygon(poly, new Tile(b.X, a.Y), cache)) { continue; }
                if (IsRectangleInPolygon(poly, a, b, cache))
                    areas.Add(CalculateArea(a, b));
            }
        return areas.First().ToString();
    }

    private static Tile ParseTile(string tile)
    {
        long[] coords = [.. tile.Split(',').Select(long.Parse)];
        return new(coords[0], coords[1]);
    }

    private static long CalculateArea(Tile a, Tile b)
    {
        long width = Math.Abs(b.X - a.X) + 1;
        long height = Math.Abs(b.Y - a.Y) + 1;
        return width * height;
    }

    private static bool IsRectangleInPolygon(
        Tile[] poly, Tile a, Tile b, Dictionary<Tile, bool> cache)
    {
        foreach (var tile in GetPerimeterTiles(a, b))
            if (!IsTileInPolygon(poly, tile, cache)) { return false; }
        return true;
    }

    private static bool IsTileInPolygon(
        Tile[] poly, Tile tile, Dictionary<Tile, bool> cache)
    {
        if (cache.TryGetValue(tile, out bool cached)) { return cached; }
        int n = poly.Length;
        bool inside = false;
        for (int i = 0, j = n - 1; i < n; j = i++)
        {
            Tile vi = poly[i];
            Tile vj = poly[j];
            if ((vi.X == vj.X && tile.X == vi.X && tile.Y >= Math.Min(vi.Y, vj.Y) && tile.Y <= Math.Max(vi.Y, vj.Y)) ||
                (vi.Y == vj.Y && tile.Y == vi.Y && tile.X >= Math.Min(vi.X, vj.X) && tile.X <= Math.Max(vi.X, vj.X)))
                return cache[tile] = true;

            if (((vi.Y > tile.Y) != (vj.Y > tile.Y)) &&
                (tile.X < (vj.X - vi.X) * (tile.Y - vi.Y) / (vj.Y - vi.Y + 0.0) + vi.X))
                inside = !inside;
        }
        cache[tile] = inside;
        return inside;
    }

    private static IEnumerable<Tile> GetPerimeterTiles(Tile c1, Tile c2)
    {
        long minX = Math.Min(c1.X, c2.X);
        long maxX = Math.Max(c1.X, c2.X);
        long minY = Math.Min(c1.Y, c2.Y);
        long maxY = Math.Max(c1.Y, c2.Y);
        for (long x = minX; x <= maxX; x++)
        {
            yield return new Tile(x, minY);
            if (minY != maxY) { yield return new Tile(x, maxY); }
        }
        for (long y = minY + 1; y < maxY; y++)
        {
            yield return new Tile(minX, y);
            if (minX != maxX) { yield return new Tile(maxX, y); }
        }
    }

    private record Tile(long X, long Y);
}
