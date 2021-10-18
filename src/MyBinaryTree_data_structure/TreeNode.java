package MyBinaryTree_data_structure;

public class TreeNode<T extends Comparable<T>> {
	
	//Attributes
	private T value;
	
	//Relations
	private TreeNode<T>right;
	private TreeNode<T>left;
	
	//Constructor
	public TreeNode(T value) {
		this.value = value;
		right = null;
		left = null;
	}

	//Getters and setters
	public void setValue(T value) {
		this.value = value;
	}

	public T getValue() {
		return value;
	}

	public void setRight(TreeNode<T> right) {
		this.right = right;
	}

	public TreeNode<T> getRight() {
		return right;
	}

	public void setLeft(TreeNode<T> left) {
		this.left = left;
	}

	public TreeNode<T> getLeft() {
		return left;
	}	
	
	public String toString() {
		return "Padre: "+value+" --> hijo izquierdo: "+left.getValue()+" --> hijo derecho: "+right.getValue();
	}

}
