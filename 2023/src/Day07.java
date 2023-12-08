import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

/**
 * @author Zachary Cockshutt
 * @since  2023-12-06
 */
public class Day07 extends Day
{
    private final static String RANKS = "AKQJT98765432";

    @Override
    public String partOne() throws IOException
    {
        int sum = 0;
        var hands = new ArrayList<Hand>();
        this.input((s) -> hands.add(parseHand(s)));
        hands.sort(new HandComparator());
        for (int i = 0; i < hands.size(); i++) { sum += hands.get(i).bid * (i+1); }
        return String.valueOf(sum);
    }

    @Override
    public String partTwo() throws IOException
    {
        int sum = 0;
        return String.valueOf(sum);
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
        var max = 0;
        var map = new HashMap<Character, Integer>();
        for (var c : cards) 
        { 
            var i = map.containsKey(c) ? map.get(c)+1 : 1;
            if (i > max) { max = i; };
            map.put(c, i); 
        }
        return map.size() - max;
    }

    private record Hand(char[] cards, int bid, int type) {}

    private record HandComparator() implements Comparator<Hand>
    {
        @Override
        public int compare(Hand a, Hand b)
        {
            if (a.type < b.type) { return 1; }
            if (a.type > b.type) { return -1; }
            for (int i = 0; i < 5; i++) 
            {
                var c = RANKS.indexOf(a.cards[i]);
                var o = RANKS.indexOf(b.cards[i]);
                if (c == o) { continue; }
                return c < o ? 1 : -1;
            }
            return 0;
        }
    }
}