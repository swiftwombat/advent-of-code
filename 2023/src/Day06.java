import static java.lang.Math.sqrt;

import java.io.IOException;

/**
 * @author Zachary Cockshutt
 * @since  2023-12-06
 */
public class Day06 extends Day
{
    @Override
    public String partOne() throws IOException
    {
        int product = 1;
        var races = parseRaces();
        for (var race : races)
        {
            var t = race.time();
            var d = race.record();
            var x1 = Math.abs(Math.floor((-t + sqrt(t*t-4*-1*-d))/(2*-1)));
            var x2 = Math.abs(Math.ceil((-t - sqrt(t*t-4*-1*-d))/(2*-1)));
            var count = (x2-x1-1);
            product *= count;
        }
        return String.valueOf(product);
    }

    @Override
    public String partTwo() throws IOException
    {
        var races = parseRaces();
        String tstr = "", dstr = "";
        for (var race : races) 
        { 
            tstr += race.time(); 
            dstr += race.record();
        }
        var t = Long.parseLong(tstr);
        var d = Long.parseLong(dstr);
        var x1 = Math.abs(Math.floor((-t + sqrt(t*t-4*-1*-d))/(2*-1)));
        var x2 = Math.abs(Math.ceil((-t - sqrt(t*t-4*-1*-d))/(2*-1)));
        var count = (long)(x2-x1-1);
        return String.valueOf(count);
    }

    private Race[] parseRaces() throws IOException
    {
        var input = input();
        var nums = new String[2][];
        for (int i = 0; i < 2; i++) { nums[i] = input[i].split(": +")[1].split(" +"); }
        var races = new Race[nums[0].length];
        for (int i = 0; i < races.length; i++)  
        { 
            var time = Integer.parseInt(nums[0][i]);
            var record = Integer.parseInt(nums[1][i]);
            races[i] = new Race(time, record); 
        }
        return races;
    }

    private record Race(int time, int record) {}
}