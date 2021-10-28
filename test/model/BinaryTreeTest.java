package model;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import MyBinaryTree_data_structure.BinaryTree;
import MyBinaryTree_data_structure.TreeNode;

public class BinaryTreeTest {

	private BinaryTree<Integer> tree;

	public void sc1() {
		tree = new BinaryTree<>();
	}

	public void sc2() {
		tree = new BinaryTree<>();
		tree.createNode(10);
		tree.createNode(20);
	}

	public void sc3() {
		tree = new BinaryTree<>();
		tree.createNode(10);
		tree.createNode(20);
		tree.createNode(30);
	}

	@Test	
	public void createNodeTest1() { // Verifies the correct creation of a tree node when the tree is empty.
		sc1();
		boolean emptyInitState = tree.isEmpty();
		tree.createNode(10);
		Assert.assertNotEquals(emptyInitState, tree.isEmpty());
		Assert.assertNotNull(tree.getRoot());
	}

	@Test
	public void createNodeTest2() { // Verifies the correct creation of a tree node with an element greater than all the existing elements.
		sc2();
		TreeNode<Integer> initialLeaf = tree.getTreeNode(tree.getRoot(), 20); // Initial leaf is 20.
		TreeNode<Integer> initialLeafRightChild = initialLeaf.getRight(); // Initial leaf right child == null.
		tree.createNode(30);
		Assert.assertNotEquals(initialLeafRightChild, initialLeaf.getRight()); // Initial Child -> null != New Child -> 30.
		Assert.assertTrue(tree.isLeaf(tree.getTreeNode(tree.getRoot(), 30))); // 30 is now a leaf.
	}

	@Test
	public void isEmptyTest1() {
		sc1();
		Assert.assertTrue(tree.isEmpty());
	}

	@Test
	public void isEmptyTest2() {
		sc2();
		Assert.assertFalse(tree.isEmpty());
	}

	@Test
	public void isLeafTest() {
		sc2();
		Assert.assertTrue(tree.isLeaf(tree.getTreeNode(tree.getRoot(), 20)));
		Assert.assertFalse(tree.isLeaf(tree.getTreeNode(tree.getRoot(), 10)));
	}

	@Test
	public void getTreeNodeTest() {
		sc2();
		Assert.assertNotNull(tree.getTreeNode(tree.getRoot(), 10)); // Exists. Returns node.
		Assert.assertNull(tree.getTreeNode(tree.getRoot(), 11)); // Doesn't exists. Returns null.
	}

	@Test
	public void deleteNodeTest1() { // Deletes leaf.
		sc3();
		TreeNode<Integer> leafParent = tree.getTreeNode(tree.getRoot(), 20);
		boolean initialIsLeaf = tree.isLeaf(leafParent); // Is not leaf -> False.
		tree.deleteNode(tree.getRoot(), 30);
		Assert.assertNotEquals(initialIsLeaf, tree.isLeaf(leafParent));
	}

	@Test
	public void deleteNodeTest2() { // Deletes root.
		sc3();
		TreeNode<Integer> initialRoot = tree.getRoot();
		int initialRootRightChild = initialRoot.getRight().getValue();
		tree.deleteNode(tree.getRoot(), 10); // Deletes root.
		int newRoot = tree.getRoot().getValue();
		Assert.assertEquals(initialRootRightChild,newRoot);
	}

}
