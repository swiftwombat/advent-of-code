import static java.lang.Math.max;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Zachary Cockshutt
 * @since  2023-12-04
 */
public class Day02 extends Day
{
    private static final int MAX_RED = 12, MAX_GREEN = 13, MAX_BLUE = 14;
    private static final Pattern[] rgbRgx = { Pattern.compile("(\\d+) red"), Pattern.compile("(\\d+) green"), Pattern.compile("(\\d+) blue") };

    @Override
    public String partOne() throws IOException
    {
        var sum = new AtomicInteger(0);
        this.input((s) ->
        {
            var sets = s.split(";");
            var possible = true;
            for (int i = 0; i < sets.length && possible; i++) { possible = parseSet(sets[i]).isPossible(); }
            var id = possible ? Integer.parseInt(s.split(":")[0].split(" ")[1]) : 0;
            sum.set(sum.get() + id);
        });
        return sum.toString();
    }

    @Override
    public String partTwo() throws IOException
    {
        var sum = new AtomicInteger(0);
        this.input((s) -> 
        {
            int r = 0, g = 0, b = 0;
            for (var str : s.split(";"))
            {
                var set = parseSet(str);
                r = max(r, set.red);
                g = max(g, set.green);
                b = max(b, set.blue);
            }
            var power = r * b * g;
            sum.set(sum.get() + power);
        });
        return sum.toString();
    }

    private Set parseSet(String s)
    {
        var rgb = new int[3];
        for (int i=0; i<3; i++) { rgb[i] = this.getColourCount(rgbRgx[i], s); }
        return new Set(rgb[0], rgb[1], rgb[2]);
    }
    
    private int getColourCount(Pattern p, String str)
    {
        final Matcher matcher = p.matcher( str );
        return matcher.find() ? Integer.parseInt(matcher.group(1).split(" ")[0]) : 0;
    }

    private record Set(int red, int green, int blue) 
    {
        private boolean isPossible()
        {
            return red   <= MAX_RED 
                && green <= MAX_GREEN 
                && blue  <= MAX_BLUE;
        }
    }
}