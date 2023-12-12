import java.io.IOException;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author Zachary Cockshutt
 * @since  2023-12-09
 */
public class Day09 extends Day
{
    @Override
    public String partOne() throws IOException
    {
        var sum = new AtomicLong(0L);
        this.input(s -> {
            var data = s.split(" ");
            var sqnc = new long[data.length];
            for (int i = 0; i < data.length; i++) { sqnc[i] = Long.parseLong(data[i]); }
            var future = extrapolate(sqnc);
            sum.set(sum.get()+future);
        });
        return sum.toString();
    }

    @Override
    public String partTwo() throws IOException
    {
        var sum = new AtomicLong(0L);
        this.input(s -> {
            var data = s.split(" ");
            var sqnc = new long[data.length];
            int j = data.length-1;
            for (int i = 0; i < data.length; i++) { sqnc[i] = Long.parseLong(data[j--]); }
            var future = extrapolate(sqnc);
            sum.set(sum.get()+future);
        });
        return sum.toString();
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