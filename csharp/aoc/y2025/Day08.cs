using System.Numerics;

namespace csharp.aoc.y2025;

/**
 * Day 8: Playground
 *
 * @see https://adventofcode.com/2025/day/8
 * @author Zachary Cockshutt
 * @since  2025-12-08
 */
public class Day08 : Day
{
    public override string PartOne() => SolveJunctionGraph(false).ToString();

    public override string PartTwo() => SolveJunctionGraph(true).ToString();

    private long SolveJunctionGraph(bool isPartTwo)
    {
        int count = 0;
        var junctions = ParseVectors(GetInputLines());
        var circuits = new List<HashSet<Vector3>>();
        var distances = new PriorityQueue<(Vector3, Vector3), float>();
        for (int i = 0; i < junctions.Length; ++i)
        {
            for (int j = i + 1; j < junctions.Length; ++j)
            {
                var a = junctions[i];
                var b = junctions[j];
                distances.Enqueue((a, b), Vector3.Distance(a, b));
            }
        }
        while (distances.TryDequeue(out (Vector3 a, Vector3 b) pair, out float _))
        {
            if (count++ == 1000 && !isPartTwo) { break; }
            var existing = circuits.Where(c => c.Contains(pair.a) || c.Contains(pair.b));
            switch (existing.Count())
            {
                case 0: circuits.Add([pair.a, pair.b]); break;
                case 1: ExpandCircuit(existing.First(), pair); break;
                default: MergeCircuits(existing.First(), existing.Last(), circuits); break;
            }
            if (circuits.Count == 1 && circuits.First().Count == junctions.Length)
                return (long)pair.a.X * (long)pair.b.X;
        }
        return circuits
            .OrderByDescending(c => c.Count).Take(3)
            .Aggregate(1L, (answer, c) => answer *= c.Count);
    }

    private static Vector3[] ParseVectors(string[] input)
        => [.. input.Select(ParseVector)];

    private static Vector3 ParseVector(string line)
        => new(new ReadOnlySpan<float>([.. line.Split(',').Select(float.Parse)]));

    private static void ExpandCircuit(
        HashSet<Vector3> circuit, (Vector3 a, Vector3 b) pair)
    {
        circuit.Add(pair.a);
        circuit.Add(pair.b);
    }

    private static void MergeCircuits(
        HashSet<Vector3> c1, HashSet<Vector3> c2, List<HashSet<Vector3>> circuits)
    {
        c1.UnionWith(c2);
        circuits.Remove(c2);
    }
}
