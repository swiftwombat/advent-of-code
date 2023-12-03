package com.swiftwombat.adventofcode;

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
            System.out.printf("%s\n", getDayName());
            var rs = new String[] { partOne(), partTwo() };
            for (int i = 0; i < rs.length; i++) { System.out.printf("P%d: %s\n", i+1, rs[i]); }
        }
        catch (IOException e) { e.printStackTrace(); }
    }
    
    protected interface StreamCallback 
    {
        void call(String line);
    }

    protected void streamInput(StreamCallback callback) throws IOException 
    {
        final var fn = String.format("dat/%s.txt", this.getDayName().toLowerCase());
        final var br = new BufferedReader(new FileReader(fn));
        while (br.ready()) { callback.call(br.readLine()); }
        br.close();
    }

    protected String getDayName()
    {
        var name = this.getClass().getSimpleName();
        return name;
    }
}