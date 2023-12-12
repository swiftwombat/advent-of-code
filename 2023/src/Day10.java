import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Zachary Cockshutt
 * @since  2023-12-09
 */
public class Day10 extends Day
{
    private static final Direction N = Direction.NORTH, S = Direction.SOUTH,
                                   E  = Direction.EAST, W = Direction.WEST;

    private static Map<Character, Pipe> pipes = new HashMap<>() 
    {{
        put('|', new Pipe(N,S)); put('-', new Pipe(E,W));
        put('L', new Pipe(N,E)); put('F', new Pipe(S,E));
        put('J', new Pipe(N,W)); put('7', new Pipe(S,W));
    }};

    @Override
    public String partOne() throws IOException
    {
        var sum = new AtomicInteger(0);
        var matrix = getCharMatrix();
        var start = getStartingPoint(matrix);
        return sum.toString();
    }

    @Override
    public String partTwo() throws IOException
    {
        var sum = new AtomicInteger(0);
        this.input(s -> {
            
        });
        return sum.toString();
    }

    private char[][] getCharMatrix() throws IOException
    {
        var matrix = new ArrayList<char[]>();
        this.input((s) -> matrix.add(s.toCharArray()));
        return matrix.toArray(new char[matrix.size()][]);
    }

    private Point getStartingPoint(char[][] matrix)
    {
        for (int i = 0; i < matrix.length; i++)
            for (int j = 0; j < matrix[i].length; j++)
                if (matrix[i][j] == 'S') { return new Point(i, j); }
        return null;
    }

    private record Point(int x, int y) 
    {
        @Override
        public boolean equals(Object o)
        {
            if (!(o instanceof Point)) { return false; }
            var p = (Point)o;
            return x == p.x && y == p.y;
        }
    }

    private record Pipe(Direction a, Direction b) {}

    private static enum Direction
    {
        NORTH(0,-1),
        SOUTH(0, 1),
        EAST (1, 0),
        WEST (-1,0);
        private final int x, y;
        Direction(int x, int y) { this.x = x; this.y = y; }
    }
}