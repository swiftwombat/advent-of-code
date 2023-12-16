import java.io.IOException;
import java.util.LinkedHashMap;

/**
 * @author Zachary Cockshutt
 * @since  2023-12-14
 */
public class Day15 extends Day
{
    @Override
    public String partOne() throws IOException
    {
        int sum = 0;
        var input = this.input()[0];
        for (var s : input.split(",")) { sum += getHash(s); }
        return String.valueOf(sum);
    }

    @Override
    public String partTwo() throws IOException
    {
        var sum = 0;
        var input = this.input()[0];
        var boxes = new LinkedHashMap<Integer, LinkedHashMap<String, Integer>>();
        for (var s : input.split(","))
        {
            var lens = s.split("-|=");
            int box = getHash(lens[0]);
            if (!boxes.containsKey(box)) { boxes.put(box, new LinkedHashMap<>()); }
            if (s.charAt(s.length()-1) == '-') 
            { 
                boxes.get(box).remove(lens[0]);
                continue; 
            }
            boxes.get(box).put(lens[0], Integer.parseInt(lens[1]));
        }
        for (var box : boxes.keySet())
        {
            var slot = 1;
            for (var lens : boxes.get(box).values())
                sum += (1+box) * slot++ * lens;
        }
        return String.valueOf(sum);
    }

    private int getHash(String s)
    {
        int hash = 0;
        var arr = s.toCharArray();
        for (var ch : arr) {  hash = ((hash+ch) * 17) % 256; }
        return hash;
    }
}