import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

/**
 * @author Zachary Cockshutt
 * @since  2023-12-05
 */
public class Day05 extends Day
{
    @Override
    public String partOne() throws IOException
    {
        var sum = new AtomicInteger(0);
        var almanac = this.parseAlmanac();
        return sum.toString();
    }

    @Override
    public String partTwo() throws IOException
    {
        var sum = new AtomicInteger(0);
        var almanac = this.parseAlmanac();
        return sum.toString();
    }

    private Almanac parseAlmanac() throws IOException
    {
        var input = this.input();
        var n = input.length;
        var seeds = Stream.of(input[0].substring(7).split(" +")).mapToLong(Long::parseLong).toArray();
        var categories = new ArrayList<AlmanacMap[]>();
        for (int i = 2; i < n; i++)
        {
            if (isAlpha(input[i].charAt(0))) { continue; }
            var maps = new ArrayList<AlmanacMap>();
            while (i < n && !input[i].isEmpty()) { maps.add(this.parseMap(input[i++])); }
            categories.add(maps.toArray(new AlmanacMap[0]));
        }
        return new Almanac(seeds, categories.toArray(new AlmanacMap[0][]));
    }

    private AlmanacMap parseMap(String s)
    {
        var arr = Stream.of(s.split(" ")).mapToLong(Long::parseLong).toArray();
        return new AlmanacMap(arr[0], arr[1], arr[2]);
    }

    private boolean isAlpha(char ch)
    {
        return ch >= 'a'
            && ch <= 'z';
    }

    private record Almanac(long[] seeds, AlmanacMap[][] categories) {}

    private record AlmanacMap(long dest, long source, long range) {}
}