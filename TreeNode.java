import java.util.ArrayList;
import java.util.List;

public class TreeNode {
    private ArrayList<TreeNode> children;
    public String url;
    public int value;
    public TreeNode leftChild;
    public TreeNode rightChild;

    public TreeNode(String url, int value) {
        this.children = new ArrayList<TreeNode>();
        this.url = url;
        this.value = value;
    }

    public void addChild(TreeNode child) {
        children.add(child);
    }

    public List<TreeNode> getChildren(){
        return children;
    }

    public String getUrl(){
        return this.url;
    }

    public void print() {
        System.out.printf("URL: %s, value: %d\n", url, value);
        if (!children.isEmpty()) {
            System.out.println("Children:");
            for (TreeNode child : this.children) {
                child.print();
            }
        }
    }
}