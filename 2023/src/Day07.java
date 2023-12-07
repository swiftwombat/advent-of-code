import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Zachary Cockshutt
 * @since  2023-12-06
 */
public class Day07 extends Day
{
    @Override
    public String partOne() throws IOException
    {
        var sum = new AtomicInteger(0);
        var hands = new ArrayList<Hand>();
        this.input((s) -> 
        {
            var hand = parseHand(s);
            System.out.println();
        });
        return sum.toString();
    }

    @Override
    public String partTwo() throws IOException
    {
        var sum = new AtomicInteger(0);
        this.input((s) -> 
        {
            
        });
        return sum.toString();
    }

    private Hand parseHand(String s)
    {
        var hand = s.split(" ");
        var cards = hand[0].toCharArray();
        var bid = Integer.parseInt(hand[1]);
        var type = parseHandType(cards);
        return new Hand(cards, bid, type);
    }

    private int parseHandType(char[] cards)
    {
        var s = new HashSet<Character>();
        for(int i = 0; i < cards.length; i++) { s.add(cards[i]); }
        return s.size();
    }

    private record Hand(char[] cards, int bid, int type) {}
}