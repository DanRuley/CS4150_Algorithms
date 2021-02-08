import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Scanner;

/**
 * Represents a BinarySearchTree: For any node n, all values smaller than n will
 * be in its left subtree and all values larger than n will be in the right
 * subtree.
 */
public class BinaryTree {

	/**
	 * String representing the shape of the tree
	 */
	private StringBuilder shape;

	/**
	 * Root of the tree
	 */
	private Node root;

	/**
	 * Create an empty BST
	 */
	public BinaryTree() {
		shape = new StringBuilder();
	}

	/**
	 * Create a BST from the input int[]
	 */
	public BinaryTree(int[] vals) {
		shape = new StringBuilder();
		addAll(vals);
	}

	/**
	 * Add all of the values in nums to the tree using the recursiveAdd fn.
	 */
	public void addAll(int[] nums) {
		for (int i : nums) {
			if (root == null)
				root = new Node(i);
			recursiveAdd(i, root);
		}
	}

	/**
	 * Add a value to the Tree
	 */
	public void add(int num) {
		if (root == null)
			root = new Node(num);
		else
			recursiveAdd(num, root);
	}

	/**
	 * Performs an in order traversal of the tree to create a string representation
	 * of all of the indices in the tree that have values. Using 0 based indexing,
	 * e.g. left child is (parentindex * 2 + 1) and right child is (parentindex * 2
	 * + 2)
	 */
	private void inOrder(int index, Node n) {
		if (n == null)
			return;
		else
			shape.append(index);
		inOrder(index * 2 + 1, n.left);
		inOrder(index * 2 + 2, n.right);
	}

	/**
	 * Recursively add a value to the right position in the tree
	 */
	private void recursiveAdd(int i, Node parent) {
		if (i < parent.val && parent.left == null)
			parent.left = new Node(i);
		else if (i > parent.val && parent.right == null)
			parent.right = new Node(i);
		else if (i < parent.val && parent.left != null)
			recursiveAdd(i, parent.left);
		else if (i > parent.val && parent.right != null)
			recursiveAdd(i, parent.right);
	}

	/**
	 * Get a string representation of the tree.
	 */
	public String getString() {
		inOrder(0, root);
		return shape.toString();
	}

	/**
	 * Simple node class: node consist of a value and a pointer to their left/right
	 * children
	 */
	private class Node {
		private Node left;
		private Node right;
		private int val;

		private Node(int n) {
			val = n;
		}
	}

	/**
	 * Main entry point for the application
	 */
	public static void main(String[] args) throws IOException {
		HashSet<String> shapes = new HashSet<>();

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		// read the parameters n and k
		String[] params = br.readLine().split(" ");
		int n = Integer.parseInt(params[0]);
		int k = Integer.parseInt(params[1]);

		// n lines each containing k ints in [1,10^6]
		for (int i = 0; i < n; i++) {
			// split line of numbers into string[] and create an empty BST
			String[] nums = br.readLine().split(" ");
			BinaryTree b = new BinaryTree();

			// parse numbers and add them to tree one by one
			for (int j = 0; j < k; j++) {
				b.add(Integer.parseInt(nums[j]));
			}

			// Hash the string representation of the tree
			shapes.add(b.getString());
		}

		// The size of the set should be the number of unique tree hashes (shapes)
		System.out.println(shapes.size());

		br.close();
	}
}
