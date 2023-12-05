import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.Callable;

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
        var rs = new String[] { run(()->partOne()), run(()->partTwo()) };
        for (int i = 0; i < 2; i++) { System.out.printf("P%d: %s\n", i+1, rs[i]); }
    }

    private String run(Callable<String> func)
    {
        var rs = "";
        long t = System.nanoTime();
        try                 { rs = func.call(); }
        catch (Exception e) { e.printStackTrace(); }
        rs = String.format("%-11s[%05.2f ms]", rs, (float)(System.nanoTime()-t)/1000000);
        return rs;
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