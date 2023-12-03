import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Zachary Cockshutt
 * @since  2023-12-04
 */
public class Day02 extends Day
{
    
    private final static Map<String, Integer> maxCubeCounts = new HashMap<>()
    {{
        put("red",  12);
        put("green",13);
        put("blue", 14);
    }};

    @Override
    public String partOne() throws IOException
    {
        var sum = new AtomicInteger(0);
        this.streamInput((s) ->
        {
            var arr = s.split("[ :;,]");
            var isPossible = true;
            for (int i = 3; i < arr.length-1; i+=3)
            {
                var count = Integer.parseInt(arr[i]);
                isPossible = count <= maxCubeCounts.get(arr[i+1]);
                if (!isPossible) { break; }
            }
            if (isPossible) { sum.set(sum.get() + Integer.parseInt(arr[1])); }
        });
        return sum.toString();
    }

    @Override
    public String partTwo() throws IOException
    {
        var sum = new AtomicInteger(0);
        this.streamInput((s) -> 
        {
            var cubeCounts = new HashMap<String, Integer>() {{ put("red", 0); put("green", 0); put("blue", 0); }};
            var arr = s.split("[ :;,]");
            for (int i = 3; i < arr.length-1; i+=3)
            {
                var count = Integer.parseInt(arr[i]);
                var colour = arr[i+1];
                if (cubeCounts.get(colour) < count) { cubeCounts.put(colour, count); }
            }
            var power = cubeCounts.get("red") * cubeCounts.get("green") * cubeCounts.get("blue");
            sum.set(sum.get() + power);
        });
        return sum.toString();
    }
}