import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Zachary Cockshutt
 * @since  2023-12-09
 */
public class Day09 extends Day
{
    @Override
    public String partOne() throws IOException
    {
        return getFutureSum(false);
    }

    @Override
    public String partTwo() throws IOException
    {
        return getFutureSum(true);
    }

    private String getFutureSum(boolean isPartTwo) throws IOException
    {
        var sum = new AtomicLong(0L);
        this.input(s -> 
        {
            var sqnc = parseSequence(s, isPartTwo);
            sum.set(sum.get() + extrapolate(sqnc));
        });
        return sum.toString();
    }

    private long[] parseSequence(String s, boolean isPartTwo)
    {
        var sqnc = Stream.of(s.split(" "))
            .map(Long::parseLong)
            .collect(Collectors.toCollection(ArrayList::new));
        if (isPartTwo) { Collections.reverse(sqnc); }
        return sqnc.stream().mapToLong(x->x).toArray();
    }

    private long extrapolate(long[] sqnc)
    {
        long future = 0;
        while (!isZeroed(sqnc))
        {
            future += sqnc[sqnc.length-1];
            var next = new long[sqnc.length-1];
            for (int i=0; i<next.length; i++) { next[i] = sqnc[i+1]-sqnc[i]; }
            sqnc = next;
        }
        return future;
    }

    private boolean isZeroed(long[] sqnc)
    {
        for (int i = 0; i < sqnc.length; i++)
            if (sqnc[i] != 0) { return false; }
        return true;
    }
}