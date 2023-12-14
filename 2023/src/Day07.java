import static java.lang.Integer.parseInt;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;

/**
 * @author Zachary Cockshutt
 * @since  2023-12-08
 */
public class Day07 extends Day
{
    private final static String RANKS = "AKQJT98765432", RANKS_JOKER = "AKQT98765432J";

    @Override
    public String partOne() throws IOException
    {
        var hands = parseHands(false);
        int winnings = getTotalWinnings(hands, RANKS);
        return String.valueOf(winnings);
    }

    @Override
    public String partTwo() throws IOException
    {
        var hands = parseHands(true);
        int winnings = getTotalWinnings(hands, RANKS_JOKER);
        return String.valueOf(winnings);
    }

    private Hand[] parseHands(boolean isPartTwo) throws IOException
    {
        var hands = new ArrayList<Hand>();
        this.input((s) ->
        {
            var input = s.split(" ");
            var cards = isPartTwo ? input[0].replace('J', getBestCard(input[0])) : input[0];
            var type = parseHandType(cards);
            hands.add(new Hand(input[0].toCharArray(), parseInt(input[1]), type));
        });
        return hands.toArray(Hand[]::new);
    }

    private int parseHandType(String cards)
    {
        var max = 0;
        var map = new HashMap<Character, Integer>();
        for (var c : cards.toCharArray()) 
        { 
            var i = map.containsKey(c) ? map.get(c)+1 : 1;
            if (i > max) { max = i; };
            map.put(c, i); 
        }
        return map.size() - max;
    }

    private char getBestCard(String cards)
    {
        int max = 0;
        char maxChar = '\0';
        var ranks = RANKS_JOKER.substring(0, RANKS_JOKER.length()-1).toCharArray();
        for(var r : ranks) 
        {
            int diff = cards.length() - cards.replace(""+r, "").length();
            if(diff <= max) { continue; }
            maxChar = r; max = diff;
        }
        return maxChar;
    }

    private int getTotalWinnings(Hand[] hands, String ranks)
    {
        int sum = 0;
        var list = new ArrayList<>(Arrays.asList(hands));
        list.sort(new HandComparator(ranks));
        for (int i=0; i<list.size(); i++) { sum += list.get(i).bid * (i+1); }
        return sum;
    }

    private record Hand(char[] cards, int bid, int type) {}

    private record HandComparator(String ranks) implements Comparator<Hand>
    {
        @Override
        public int compare(Hand a, Hand b)
        {
            if (a.type < b.type) { return 1; }
            if (a.type > b.type) { return -1; }
            for (int i = 0; i < 5; i++) 
            {
                var c = ranks.indexOf(a.cards[i]);
                var o = ranks.indexOf(b.cards[i]);
                if (c == o) { continue; }
                return c < o ? 1 : -1;
            }
            return 0;
        }
    }
}