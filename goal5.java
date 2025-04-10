import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Class to fetch URLs from www.hunter.cuny.edu and output a dot graph.
 *
 * @author Chase Reynolds, Tess Avitabile
 */
public class goal5 {

    /**
     * The prefix for all relative URLs.
     */
    public static final String urlPrefix = "http://hunter.cuny.edu";

    /**
     * Prints a dot graph of the tree.
     * 
     * @param root the root of the tree
     */
    static public void printDotLevelOrder(TreeNode root) {
        Queue<TreeNode> queue = new LinkedList<TreeNode>();
        queue.add(root);
        while (!queue.isEmpty()) {
            TreeNode current = queue.poll();
            System.out.printf("node%d [label=\"%s\"]\n", current.value,
                    current.relativeUrl == null ? current.url : current.relativeUrl);
            for (TreeNode child : current.getChildren()) {
                System.out.printf("node%d -> node%d\n", current.value, child.value);
                queue.add(child);
            }
        }
    }

    /**
     * Builds a URL tree.
     * 
     * @param node the node to start with
     * @param depth the current depth of the tree
     * @param maxDepth the maximum depth of the tree
     * @param maxChildren the maximum number of children for each node
     * @param map a map containing all relative links, in order to check for duplicates
     * @param value the next value to use for a node
     * @return the next value to use for a node
     */
    public static int buildTree(TreeNode node, int depth, int maxDepth, int maxChildren,
            HashMap<String, Integer> map, int value) {
        if (depth >= maxDepth) {
            return value;
        }
        try {
            Document doc = Jsoup.connect(node.url).get();
            Elements links = doc.select("a[href]");

            for (Element link : links) {
                if (node.getChildren().size() == maxChildren) {
                    return value;
                }
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
                if (linkStr.endsWith(".pdf")) {
                    continue;
                }
                if (linkStr.equals("acert")) {
                    continue;
                }
                if (linkStr.contains("#")) {
                    continue;
                }
                linkStr = linkStr.substring("hunter.cuny.edu".length());
                if (linkStr.equals("/")) {
                    continue;
                }
                if (!map.containsKey(linkStr)) {
                    map.put(linkStr, 0);
                    TreeNode child = new TreeNode(link.attr("abs:href"), linkStr, value++);
                    node.addChild(child);
                    value = buildTree(child, depth + 1, maxDepth, maxChildren, map, value);
                }
            }
        } catch (Exception e) {
            System.err.println("Error fetching links for URL: " + node.url);
            e.printStackTrace();
        }
        return value;
    }

    /**
     * Tests fetching URLs from www.hunter.cuny.edu and outputting a dot graph.
     * 
     * @param args arguments are not used.
     */
    public static void main(String[] args) {
        String url = "http://www.hunter.cuny.edu/";
        TreeNode root = new TreeNode(url, 0);
        HashMap<String, Integer> map = new HashMap<String, Integer>();
        buildTree(root, 0, 3, 3, map, 1);
        System.out.println("digraph {\nnode [shape=circle]");
        printDotLevelOrder(root);
        System.out.println("}");
    }
}