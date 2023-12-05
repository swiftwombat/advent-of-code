import static java.lang.Math.pow;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Zachary Cockshutt
 * @since  2023-12-04
 */
public class Day04 extends Day
{
    @Override
    public String partOne() throws IOException
    {
        var sum = new AtomicInteger(0);
        this.input((s) -> 
        {
            var count = this.getWinCount(s);
            var score = (int)(count > 1 ? pow(2, count-1) : count);
            sum.set(sum.get() + score);
        });
        return sum.toString();
    }

    @Override
    public String partTwo() throws IOException
    {
        var games = this.input();
        var counts = new int[games.length];
        var sum = 0;
        for (int i = 0; i < counts.length; i++)
        {
            counts[i]++;
            var wins = this.getWinCount(games[i]);
            for(int j = 1; j <= wins; j++) { counts[i+j] += counts[i]; }
            sum += counts[i];
        }
        return String.valueOf(sum);
    }

    private int getWinCount(String s)
    {
        var game = s.split("(: +)|( \\| +)");
        var sets = new String[2][];
        for (var i = 1; i < game.length; i++) { sets[i-1] = game[i].split(" +"); }
        var set = new HashSet<String>(Arrays.asList(sets[0]));
        set.retainAll(Arrays.asList(sets[1]));
        return set.size();
    }
}