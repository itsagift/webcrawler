/**
 * Class to represent an n-ary tree.
 *
 * @author Tess Avitabile
 */
public class Tree {
    private TreeNode root;

    /**
     * Constructs a tree.
     * 
     * @param root the root of the tree
     */
    public Tree(TreeNode root) {
        this.root = root;
    }

    /** 
     * Prints the tree.
     */
    public void print() {
        root.print();
    }
}