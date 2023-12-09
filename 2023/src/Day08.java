import java.io.IOException;
import java.util.HashMap;

/**
 * @author Zachary Cockshutt
 * @since  2023-12-09
 */
public class Day08 extends Day
{
    @Override
    public String partOne() throws IOException
    {
        int count = 0;
        var input = this.input();
        var dirs = input[0].toCharArray();
        var nodes = new HashMap<String, Node>();
        for (int i = 2; i < input.length; i++)
        {
            var codes = input[i].replace(")","").split(" = \\(|, ");
            var node = new Node(codes[1], codes[2]);
            nodes.put(codes[0], node);
        }
        var curr = "AAA";
        while (!curr.equals("ZZZ"))
        {
            int i = Math.floorMod(count++, dirs.length);
            var node = nodes.get(curr);
            var dir = dirs[i];
            curr = dir == 'L' ? node.left : node.right;
        }
        return String.valueOf(count);
    }

    // 11653, 21409, 19241, 12737, 14363, 15989
    @Override
    public String partTwo() throws IOException
    {
        int count = 0;
        var input = this.input();
        var dirs = input[0].toCharArray();
        var nodes = new HashMap<String, Node>();
        for (int i = 2; i < input.length; i++)
        {
            var codes = input[i].replace(")","").split(" = \\(|, ");
            var node = new Node(codes[1], codes[2]);
            nodes.put(codes[0], node);
        }
        var curr = "PSA";
        while (curr.charAt(2) != 'Z')
        {
            int i = Math.floorMod(count++, dirs.length);
            var node = nodes.get(curr);
            var dir = dirs[i];
            curr = dir == 'L' ? node.left : node.right;
        }
        return String.valueOf(count);
    }

    private record Node(String left, String right) {}
}