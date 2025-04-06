import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.LinkedList;
import java.util.Queue;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class goal4 {
  static public List<List<Integer>> levelOrderTraversal(TreeNode root) {
      Queue<TreeNode> queue = new LinkedList<TreeNode>();
      List<List<Integer>> outerList = new LinkedList<List<Integer>>();
      
      if(root == null) return outerList;
      
      queue.add(root);
      while(!queue.isEmpty()){
          int levelNum = queue.size();
          List<Integer> innerList = new LinkedList<Integer>();
          for(int i=0; i<levelNum; i++) {
            TreeNode current = queue.poll();
            innerList.add(current.value); 
            for (TreeNode child : current.getChildren()) {
                queue.add(child);
            }
          }
          outerList.add(innerList);
      }
      return outerList;
  }

  public static void buildTree(TreeNode node, int depth, int maxDepth){
    if (depth >= maxDepth) {
        return;
    }
        try {
            Document doc = Jsoup.connect(node.url).get();
        Elements links = doc.select("a[href]");

        HashMap<String, Integer> map = new HashMap<String, Integer>();
        int value = 2;
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
                TreeNode child = new TreeNode(linkStr, value++);
        node.addChild(child);
        buildTree(child, depth + 1, maxDepth);
            }
        }
       
    }
    catch (Exception e) {
        System.err.println("Error fetching links for URL: " + node.url);
        e.printStackTrace();
    }}
    public static void main(String[] args) {
        String url = "http://www.hunter.cuny.edu/";
        try {
            TreeNode root = new TreeNode(url, 0);
            buildTree(root, 0, 3);

            System.out.println("Tree structure:");
            root.print();

            List<List<Integer>> traversalResult = levelOrderTraversal(root);
            System.out.println("Level Order Traversal Result:");
            for (List<Integer> level : traversalResult) {
                System.out.println(level);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }}