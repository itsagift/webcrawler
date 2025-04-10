import java.util.ArrayList;
import java.util.List;

/**
 * Class to represent a node of an n-ary tree.
 *
 * @author Chase Reynolds, Tess Avitabile
 */
public class TreeNode {
    private ArrayList<TreeNode> children;

    /**
     * The absolute URL of the node.
     */
    public String url;

    /**
     * The relative URL of the node, if it exists.
     */
    public String relativeUrl;

    /**
     * The number of the node.
     */
    public int value;

    /**
     * Constructs a node with a relative URL.
     * 
     * @param url the absolute URL
     * @param relativeURL the relative URL
     * @param value the number
     */
    public TreeNode(String url, String relativeURL, int value) {
        this.children = new ArrayList<TreeNode>();
        this.url = url;
        this.relativeUrl = relativeURL;
        this.value = value;
    }

    /**
     * Constructs a node without a relative URL.
     * 
     * @param url the absolute URL
     * @param value the number
     */
    public TreeNode(String url, int value) {
        this.children = new ArrayList<TreeNode>();
        this.url = url;
        this.value = value;
    }

    /**
     * Adds a child to a node.
     * 
     * @param child the child to add
     */
    public void addChild(TreeNode child) {
        children.add(child);
    }

    /**
     * Gets the children of the node.
     * 
     * @return a list of the node's children
     */
    public List<TreeNode> getChildren() {
        return children;
    }

    /**
     * Gets the absolute URL of a node.
     * 
     * @return the absolute URL
     */
    public String getUrl() {
        return this.url;
    }

    /**
     * Recursively prints a node and its descendants.
     */
    public void print() {
        System.out.printf("URL: %s, value: %d\n", relativeUrl == null ? url : relativeUrl, value);
        if (!children.isEmpty()) {
            System.out.println("Children:");
            for (TreeNode child : this.children) {
                child.print();
            }
        }
    }
}