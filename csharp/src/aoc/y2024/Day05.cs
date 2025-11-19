namespace aoc.y2024;

/**
 * @author Zachary Cockshutt
 * @since  2024-12-05
 */
public class Day05 : Day
{
    public override string PartOne()
    {
        string[] input = this.Input();
        string[][] pages = ParsePageLists(input);
        Comparer<string> ruleset = ParseRuleset(input);
        return pages
            .Where(x => Enumerable.SequenceEqual(x, x.OrderBy(x => x, ruleset)))
            .Sum(x => int.Parse(x[x.Length / 2]))
            .ToString();
    }

    public override string PartTwo()
    {
        string[] input = this.Input();
        string[][] pages = ParsePageLists(input);
        Comparer<string> ruleset = ParseRuleset(input);
        return pages
            .Where(x => !Enumerable.SequenceEqual(x, x.OrderBy(x => x, ruleset)))
            .Select(x => x.OrderBy(y => y, ruleset).ToArray())
            .Sum(x => int.Parse(x[x.Length / 2]))
            .ToString();
    }

    private static Comparer<string> ParseRuleset(string[] input)
    {
        var rules = input.TakeWhile(x => !x.Equals(""));
        return Comparer<string>
            .Create((x, y) => rules.Contains(x + "|" + y) ? -1 : 1);
    }

    private static string[][] ParsePageLists(string[] input)
    {
        var pages = input.SkipWhile(x => !x.Equals("")).Skip(1);
        return pages.Select(x => x.Split(",")).ToArray();
    }
}
