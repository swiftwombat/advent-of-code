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
        put(1, new Day01()); put(2, new Day02()); put(3, new Day03()); put (4, new Day04());
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