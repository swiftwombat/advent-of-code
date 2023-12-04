import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Zachary Cockshutt
 * @since  2023-12-03
 */
public class Day04 extends Day
{
    @Override
    public String partOne() throws IOException
    {
        var sum = new AtomicInteger(0);
        this.input((s) -> 
        {
            var score = 0;
            var game = s.split(":")[1].split("\\|");
            var winners = game[0].trim().split(" +");
            var numbers = game[1].trim().split(" +");
            for (var num : numbers) 
                if (isWinner(winners, num)) { score += score==0 ? 1 : score; }
            sum.set(sum.get() + score);
        });
        return sum.toString();
    }

    @Override
    public String partTwo() throws IOException
    {
        var sum = new AtomicInteger(0);
        return sum.toString();
    }

    private boolean isWinner(String[] arr, String str)
    {
        for (var s : arr) { if (s.equals(str)) { return true; } }
        return false;
    }
}