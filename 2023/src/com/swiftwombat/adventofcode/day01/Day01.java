package com.swiftwombat.adventofcode.day01;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Pattern;

import com.swiftwombat.adventofcode.Day;

/**
 * @author Zachary Cockshutt
 * @since  2023-12-03
 */
public class Day01 extends Day
{
    private final static Map<String, String> digitWords = new HashMap<>()
    {{
        put("one", "1");  put("two", "2");  put("three","3"); 
        put("four", "4"); put("five", "5"); put("six", "6"); 
        put("seven","7"); put("eight","8"); put("nine", "9");
    }};

    @Override
    public String partOne() throws IOException
    {
        var sum = new AtomicInteger(0);
        this.streamInput((s) -> {
            var rs = sum.get() + this.reduce(s);
            sum.set(rs);
        });
        return sum.toString();
    }

    @Override
    public String partTwo() throws IOException
    {
        var sum = new AtomicInteger(0);
        var p = Pattern.compile("(?=(one|t(wo|hree)|f(our|ive)|s(ix|even)|eight|nine))");
        this.streamInput((s) -> {
            var matcher = p.matcher(s);
            s = matcher.replaceAll(m -> digitWords.get(m.group(1)));
            var rs = sum.get() + this.reduce(s);
            sum.set(rs);
        });
        return sum.toString();
    }

    private int reduce(String s)
    {
        s = s.replaceAll("\\D", "");
        s = "" + s.charAt(0) + s.charAt(s.length()-1);
        return Integer.parseInt(s);
    }
}