import java.util.HashMap;
import java.util.List;
import java.util.LinkedList;
import java.util.Queue;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class goal4 {
    public static final String urlPrefix = "http://hunter.cuny.edu";

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