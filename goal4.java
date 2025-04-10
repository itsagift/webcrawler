import java.util.HashMap;
import java.util.List;
import java.util.LinkedList;
import java.util.Queue;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Class to fetch URLs from www.hunter.cuny.edu, build a 3-layer tree, and traverse
 * in level order.
 *
 * @author Chase Reynolds, Tess Avitabile
 */
public class goal4 {

    /**
     * The prefix for all relative URLs.
     */
    public static final String urlPrefix = "http://hunter.cuny.edu";
    
    /**
     * Performs level order traversal on a tree.
     * 
     * @param root the root of the tree
     * @return a list of lists of URLs, where each list of URLs corresponds to a level
     */
    static public List<List<String>> levelOrderTraversal(TreeNode root) {
        Queue<TreeNode> queue = new LinkedList<TreeNode>();
        List<List<String>> outerList = new LinkedList<List<String>>();

        if (root == null)
            return outerList;

        queue.add(root);
        while (!queue.isEmpty()) {
            int levelNum = queue.size();
            List<String> innerList = new LinkedList<String>();
            for (int i = 0; i < levelNum; i++) {
                TreeNode current = queue.poll();
                innerList.add(current.relativeUrl == null ? current.url : current.relativeUrl);
                for (TreeNode child : current.getChildren()) {
                    queue.add(child);
                }
            }
            outerList.add(innerList);
        }
        return outerList;
    }
    
    /**
     * Builds a URL tree.
     * 
     * @param node the node to start with
     * @param depth the current depth of the tree
     * @param maxDepth the maximum depth of the tree
     * @param maxChildren the maximum number of children for each node
     * @param map a map containing all relative links, in order to check for duplicates
     */
    public static void buildTree(TreeNode node, int depth, int maxDepth, int maxChildren, HashMap<String, Integer> map) {
        if (depth >= maxDepth) {
            return;
        }
        try {
            Document doc = Jsoup.connect(node.url).get();
            Elements links = doc.select("a[href]");

            int value = 2;
            for (Element link : links) {
                if (node.getChildren().size() == maxChildren) {
                    return;
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
                linkStr = linkStr.substring("hunter.cuny.edu".length());
                if (!map.containsKey(linkStr)) {
                    map.put(linkStr, value++);
                    TreeNode child = new TreeNode(link.attr("abs:href"), linkStr, value++);
                    node.addChild(child);
                    buildTree(child, depth + 1, maxDepth, maxChildren, map);
                }
            }

        } catch (Exception e) {
            System.err.println("Error fetching links for URL: " + node.url);
            e.printStackTrace();
        }
    }

    /**
     * Tests fetching URLs from www.hunter.cuny.edu, deduplicating URLs, building a 
     * 3-layer tree, and traversing in level order.
     * 
     * @param args arguments are not used.
     */
    public static void main(String[] args) {
        String url = "http://www.hunter.cuny.edu/";
        try {
            TreeNode root = new TreeNode(url, 0);
            HashMap<String, Integer> map = new HashMap<String, Integer>();
            buildTree(root, 0, 3, 5, map);

            List<List<String>> traversalResult = levelOrderTraversal(root);
            System.out.println("Level Order Traversal Result:");
            for (int i = 0; i < traversalResult.size(); i++) {
                System.out.printf("\nLevel %d:\n", i);
                for (String relativeUrl : traversalResult.get(i)) {
                    System.out.println(relativeUrl);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}