package MyBinaryTree_data_structure;

public class BinaryTree<T extends Comparable<T>> {
	
	//Relation
	private TreeNode<T> root;
	
	//Constructor
	public BinaryTree() {
		
	}

	//Getters and setters
	public void setRoot(TreeNode<T> root) {
		this.root = root;
	}
	
	public TreeNode<T> getRoot() {
		return root;
	}
	
	public void createNode(T value) {
		TreeNode<T> newNode = new TreeNode<T>(value);
		
		if (root == null) {
			root = newNode;
		}else {
			insertNode(root,newNode);
		}		
	}
	
	public void insertNode(TreeNode<T>current, TreeNode<T>newNode) {
		if (newNode.getValue().compareTo(current.getValue())<0) {//Si el valor del newNode es menor que el current
			if (current.getLeft() == null) {//Si no hay left
				current.setLeft(newNode);
			}else {
				insertNode(current.getLeft(),newNode);
			}		
			
		}else if (newNode.getValue().compareTo(current.getValue())>0) {//Si el valor del newNode es mayor que el current
			if (current.getRight() == null) {//Si no hay right
				current.setRight(newNode);
			}else {
				insertNode(current.getRight(),newNode);
			}			
		}
	}
	
	//Method to know if the BinaryTree is empty
	public boolean isEmpty() {
		if(root == null) {
			return true;
		}else {
			return false;
		}
	}

	public boolean isLeaf(TreeNode<T>node) {
		if (node.getLeft() == null && node.getRight() == null) {
			return true;
		}else {
			return false;
		}
	}
	
	public TreeNode<T> getTreeNode(TreeNode<T>current, T searchNode){
		TreeNode<T> findNode = null;
		if (searchNode == current.getValue()) {
			findNode = current;			
			
		}else if (searchNode.compareTo(current.getValue())<0) {//Si el valor del newNode es menor que el current
			if (current.getLeft()!=null) {
				return getTreeNode(current.getLeft(),searchNode);
			}			
			
		}else if (searchNode.compareTo(current.getValue())>0) {//Si el valor del newNode es mayor que el current
			if (current.getRight()!=null) {
				return getTreeNode(current.getRight(),searchNode);
			}		
		}
		
		return findNode;		
	}

	public TreeNode<T> deleteNode (TreeNode<T> root, T delete) {		
		if(root == null) {// Caso base
			return root;
		}
		
		if(delete.compareTo(root.getValue())>0){ //Si es mayor que el root			
			root.setRight(deleteNode(root.getRight(), delete)); //Se mueve por la derecha
			
		}else if(delete.compareTo(root.getValue())<0){ //Si es menor que el root
			root.setLeft(deleteNode(root.getLeft(),delete)); //Se mueve por la izquierda
			
		}else{ //Si delete es igual a root
			if(root.getLeft() == null && root.getRight() == null){ //Si root es una hoja
				root = null;
				
			}else if(root.getRight() != null){ //Si tiene un hijo derecho, miramos si es sucesor
				root.setValue(successor(root)); // my worthy successor
				root.setRight(deleteNode(root.getRight(), root.getValue()));
			}else{ //Si tiene un hijo izquierdo, se convierte en precedesor
				root.setValue(predecessor(root));
				root.setLeft(deleteNode(root.getLeft(), root.getValue()));
			}
		}
		return root;
	}	
	
	private T successor(TreeNode<T> root){
        root = root.getRight();
        while(root.getLeft() != null){
            root = root.getLeft();
        }
        return root.getValue();
    }
    
    private T predecessor(TreeNode<T> root){
        root = root.getLeft();
        while(root.getRight() != null){
            root = root.getRight();
        }
        return root.getValue();
    }
    
    public void visit (TreeNode<T> raiz){
        System.out.println(raiz.getValue().toString() + ";");
    }
    
    public void recorrerPreorden(TreeNode <T> nodo){
        if (nodo != null){
        	visit (nodo);
            recorrerPreorden(nodo.getLeft());
            recorrerPreorden(nodo.getRight());
        }
    }
    
    
	 
	
	
	
	
	
}
