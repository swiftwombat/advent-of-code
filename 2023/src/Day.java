import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * @author Zachary Cockshutt
 * @since  2023-12-03
 */
public abstract class Day
{
    public abstract String partOne() throws IOException;

    public abstract String partTwo() throws IOException;

    public void run()
    {
        try
        {
            var rs = new String[] { partOne(), partTwo() };
            for (int i = 0; i < 2; i++) { System.out.printf("P%d: %s\n", i+1, rs[i]); }
        }
        catch (IOException e) { e.printStackTrace(); }
    }
    
    protected interface StreamCallback 
    {
        void call(String line);
    }

    protected void input(StreamCallback callback) throws IOException 
    {
        final var day = this.getClass().getSimpleName();
        final var fn = String.format("dat/%s.txt", day);
        final var br = new BufferedReader(new FileReader(fn));
        while (br.ready()) { callback.call(br.readLine()); }
        br.close();
    }
}