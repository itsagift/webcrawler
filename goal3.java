import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashMap;

/**
 * Class to fetch URLs from www.hunter.cuny.edu, deduplicate URLs, and build a tree.
 *
 * @author Tess Avitabile
 */
public class goal3 {
    /**
     * Tests fetching URLs from www.hunter.cuny.edu, deduplicating URLs, and building a tree.
     * 
     * @param args arguments are not used.
     */
    public static void main(String[] args) throws IOException {
        String url = "http://www.hunter.cuny.edu/";

        Document doc = Jsoup.connect(url).get();
        Elements links = doc.select("a[href]");

        HashMap<String, Integer> map = new HashMap<String, Integer>();
        int value = 1;
        for (Element link : links) {
            String linkStr = link.attr("abs:href");
            if (linkStr.startsWith("https://")) {
                linkStr = linkStr.substring("https://".length());
            }
            if (linkStr.startsWith("http://")) {
                linkStr = linkStr.substring("http://".length());
            }
            if (linkStr.startsWith("www.")) {
                linkStr = linkStr.substring("www.".length());
            }
            if (!linkStr.startsWith("hunter.cuny.edu")) {
                continue;
            }
            linkStr = linkStr.substring("hunter.cuny.edu".length());
            if (!map.containsKey(linkStr)) {
                map.put(linkStr, value++);
            }
        }

        System.out.println("Unique links in hash map:");
        for (String link : map.keySet()) {
            System.out.println(link);
        }
        System.out.println();

        TreeNode root = new TreeNode(url, 0);
        for (HashMap.Entry<String, Integer> entry : map.entrySet()) {
            root.addChild(new TreeNode(entry.getKey(), entry.getValue()));
        }
        Tree tree = new Tree(root);
        System.out.println("Printing tree");
        tree.print();
    }
}