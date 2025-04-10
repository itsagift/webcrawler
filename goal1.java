import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * Class to fetch URLs from www.hunter.cuny.edu.
 *
 * @author Chase Reynolds, Tess Avitabile
 */
public class goal1 {
    /**
     * Tests fetching URLs from www.hunter.cuny.edu.
     * 
     * @param args arguments are not used.
     */
    public static void main(String[] args) throws IOException {
        String url = "http://www.hunter.cuny.edu/";
        print("Fetching %s...", url);

        Document doc = Jsoup.connect(url).get();
        Elements links = doc.select("a[href]");

        print("\nLinks: (%d)", links.size());
        for (Element link : links) {
            print(" * a: <%s>  (%s)", link.attr("abs:href"), trim(link.text(), 35));
        }
    }

    /**
     * Formats and prints a string.
     * 
     * @param msg the format string
     * @param args the actual arguments to replace with in the format string
     */
    private static void print(String msg, Object... args) {
        System.out.println(String.format(msg, args));
    }

    /**
     * Trims a string to a particular width.
     * 
     * @param s the string
     * @param width the width to trim to
     * @return the trimmed string
     */
    private static String trim(String s, int width) {
        if (s.length() > width)
            return s.substring(0, width - 1) + ".";
        else
            return s;
    }
}