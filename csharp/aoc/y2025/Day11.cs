namespace csharp.aoc.y2025;

/**
 * Day 11: Reactor
 *
 * @see https://adventofcode.com/2025/day/11
 * @author Zachary Cockshutt
 * @since  2025-12-11
 */
public class Day11 : Day
{
    public override string PartOne()
    {
        Server[] servers = ParseServers(GetInputLines());
        Server root = servers.First(s => s.Id.Equals("you"));
        var cache = new Dictionary<(string id, bool dac, bool fft), long>();
        return CountOutputPaths(root, servers, false, false, false, cache).ToString();
    }

    public override string PartTwo()
    {
        Server[] servers = ParseServers(GetInputLines());
        Server root = servers.First(s => s.Id.Equals("svr"));
        var cache = new Dictionary<(string id, bool dac, bool fft), long>();
        return CountOutputPaths(root, servers, true, false, false, cache).ToString();
    }

    private static long CountOutputPaths(
        Server root, Server[] servers, bool isPartTwo, bool dac, bool fft, Dictionary<(string, bool, bool), long> cache)
    {
        dac |= root.Id.Equals("dac");
        fft |= root.Id.Equals("fft");
        var key = (root.Id, dac, fft);
        if (cache.TryGetValue(key, out long cached)) { return cached; }

        if (root.Outputs[0].Equals("out"))
        {
            long rs = !isPartTwo || (dac && fft) ? 1L : 0L;
            cache[key] = rs;
            return rs;
        }
        long paths = 0L;
        foreach (var output in root.Outputs)
        {
            var next = servers.First(s => s.Id.Equals(output));
            paths += CountOutputPaths(next, servers, isPartTwo, dac, fft, cache);
        }
        cache[key] = paths;
        return paths;
    }

    private static Server[] ParseServers(string[] input)
    {
        List<Server> servers = [];
        foreach (var line in input)
        {
            string[] ids = line.Split();
            servers.Add(new(ids[0][..^1], ids[1..]));
        }
        return [.. servers];
    }

    private record Server(string Id, string[] Outputs);
}
