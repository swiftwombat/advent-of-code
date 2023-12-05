import static java.lang.Math.pow;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
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
            var game = s.split("(: +)|( \\| +)");
            var sets = new String[2][];
            for (var i = 1; i < game.length; i++) { sets[i-1] = game[i].split(" +"); }
            var count = this.getWinCount(sets);
            var score = (int)(count > 1 ? pow(2, count-1) : count);
            sum.set(sum.get() + score);
        });
        return sum.toString();
    }

    @Override
    public String partTwo() throws IOException
    {
        var sum = new AtomicInteger(0);
        var games = new HashMap<Integer, Integer>();
        this.input((s) -> 
        {
            var game = s.split("(: +)|( \\| +)");
            var id = Integer.parseInt(game[0].split(" +")[1]);
            var c = games.get(id);
            games.put(id, c == null ? 1 : c+1);
            var sets = new String[2][];
            for (var i = 1; i < game.length; i++) { sets[i-1] = game[i].split(" +"); }
            var count = this.getWinCount(sets);
            for (int i = 0; i < games.get(id); i++)
                for (int j = id+1; j <= id+count; j++) 
                { 
                    var curr = games.get(j);
                    games.put(j, curr == null ? 1 : curr+1); 
                }
            sum.set(sum.get() + games.get(id));
        });
        return sum.toString();
    }

    private int getWinCount(String[][] sets)
    {
        var set = new HashSet<String>(Arrays.asList(sets[0]));
        set.retainAll(Arrays.asList(sets[1]));
        return set.size();
    }
}