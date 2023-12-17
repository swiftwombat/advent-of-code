import java.util.HashMap;
import java.util.Map;

/**
 * @author Zachary Cockshutt
 * @since  2023-12-03
 */
public class Main
{
    private static final Map<Integer, Day> days = new HashMap<>()
    {{
        put(1, new Day01()); put(2, new Day02()); put(3, new Day03()); put(4, new Day04()); put(5, new Day05());
        put(6, new Day06()); put(7, new Day07()); put(8, new Day08()); put(9, new Day09()); put(10,new Day10());
        put(11,new Day11()); put(12,new Day12()); put(13,new Day13()); put(14,new Day14()); put(15,new Day15());
        put(16,new Day16());
    }};

    public static void main(String[] args)
    {
        try
        {
            if (args.length < 1) { err("Invalid number of args."); }
            run(Integer.parseInt(args[0]));
        }
        catch (Exception e) { err(e.toString()); }
    }

    private static void run(int dayNumber)
    {
        var day = days.get(dayNumber);
        if (day == null) { err("Day not found."); }
        day.run();
    }

    private static void err(String msg)
    {
        System.err.printf("Error: %s\n",msg);
        System.exit(1);
    }
}