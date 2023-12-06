import static java.lang.Math.min;

import java.io.IOException;
import java.util.ArrayList;
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
        var almanac = this.parseAlmanac(false);
        var location = almanac.getMinLocation();
        return String.valueOf(location);
    }

    @Override
    public String partTwo() throws IOException
    {
        var almanac = this.parseAlmanac(true);
        var location = almanac.getMinLocation();
        return String.valueOf(location);
    }

    private Almanac parseAlmanac(boolean isPartTwo) throws IOException
    {
        var input = this.input();
        var n = input.length;
        var seeds = isPartTwo ? parseSeedsAsRanges(input[0]) : parseSeeds(input[0]);
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

    private Seed[] parseSeeds(String s)
    {
        return Stream.of(s.substring(7).split(" +"))
            .map(x->new Seed(Long.parseLong(x), Long.parseLong(x)))
            .toArray(Seed[]::new);
    }
    
    private Seed[] parseSeedsAsRanges(String s)
    {
        var seeds = new ArrayList<Seed>();
        var numbers = Stream.of(s.substring(7).split(" +")).mapToLong(Long::parseLong).toArray();
        for (int i = 0; i < numbers.length; i+=2) { seeds.add(new Seed(numbers[i], (numbers[i]+numbers[i+1]))); }
        return seeds.stream().toArray(Seed[]::new);
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

    private record Almanac(Seed[] seeds, AlmanacMap[][] categories) 
    {
        private long getMinLocation()
        {
            var location = Long.MAX_VALUE;
            for (var seed : seeds)
                for (long i = seed.start(); i <= seed.end(); i++)
                    location = min(location, this.getLocation(i));
            return location;
        }

        private long getLocation(long seed)
        {
            for (var category : this.categories)
                for (var map : category)
                {
                    boolean isMapped = seed >= map.source && seed < map.source + map.range();
                    if (isMapped) { seed = map.dest + (seed - map.source); break; }
                }
            return seed;
        }
    }

    private record AlmanacMap(long dest, long source, long range) {}

    private record Seed(long start, long end) {}
}