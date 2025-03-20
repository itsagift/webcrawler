import java.util.ArrayList;

public class TreeNode {
    private ArrayList<TreeNode> children;
    private String url;
    private int value;

    public TreeNode(String url, int value) {
        children = new ArrayList<TreeNode>();
        this.url = url;
        this.value = value;
    }

    public void addChild(TreeNode child) {
        children.add(child);
    }

    public void print() {
        System.out.printf("URL: %s, value: %d\n", url, value);
        if (!children.isEmpty()) {
            System.out.println("Children:");
            for (TreeNode child : children) {
                child.print();
            }
        }
    }
}