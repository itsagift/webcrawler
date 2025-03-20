public class Tree {
    private TreeNode root;

    public Tree(TreeNode root) {
        this.root = root;
    }

    public void print() {
        root.print();
    }
}