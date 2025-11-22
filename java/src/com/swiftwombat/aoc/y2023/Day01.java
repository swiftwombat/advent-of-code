package com.swiftwombat.aoc.y2023;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Pattern;

import com.swiftwombat.aoc.Day;

/**
 * Day 1: Trebuchet?!
 * 
 * @see https://adventofcode.com/2023/day/1
 * @author Zachary Cockshutt
 * @since 2023-12-03
 */
public class Day01 extends Day {

    // @formatter:off
    private static final Map<String, String> digitWords = new HashMap<>()
    {{
        put("one",  "1"); put("two",  "2"); put("three","3"); 
        put("four", "4"); put("five", "5"); put("six",  "6"); 
        put("seven","7"); put("eight","8"); put("nine", "9");
    }};
    // @formatter:on

    @Override
    public String partOne() throws IOException {
        var sum = new AtomicInteger(0);
        this.forEachInputLine(line -> sum.set(sum.get() + this.parseInt(line)));
        return sum.toString();
    }

    @Override
    public String partTwo() throws IOException {
        var sum = new AtomicInteger(0);
        Pattern p = Pattern.compile("(?=(one|t(wo|hree)|f(our|ive)|s(ix|even)|eight|nine))");
        this.forEachInputLine(line -> {
            var matcher = p.matcher(line);
            line = matcher.replaceAll(m -> digitWords.get(m.group(1)));
            sum.set(sum.get() + this.parseInt(line));
        });
        return sum.toString();
    }

    private int parseInt(String s) {
        s = s.replaceAll("\\D", "");
        s = "" + s.charAt(0) + s.charAt(s.length() - 1);
        return Integer.parseInt(s);
    }
}