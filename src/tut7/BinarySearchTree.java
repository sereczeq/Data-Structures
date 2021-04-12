package tut7;

import java.util.NoSuchElementException;

public class BinarySearchTree<T>
{
	
	// public static void main(String[] arts)
	// {
	//
	// BinarySearchTree bst = new BinarySearchTree();
	// System.out.println(bst.add(7));
	// System.out.println(bst.add(7));
	// bst.add(2);
	// System.out.println(bst.add(7));
	// bst.remove(1);
	// System.out.println(bst.toStringInOrder());
	// System.out.println(bst.toStringPostOrder());
	// System.out.println(bst.toStringPreOrder());
	//
	// }
	
	protected class Node
	{
		
		T value;
		Node left = null, right = null, parent = null;
		boolean red = true;
		
		public Node(T v)
		{
			
			value = v;
			
		}
		
		
		@Override
		public String toString()
		{
			
			String string = "";
			string += value.toString();
			// string += "value = " + value;
			// string += ", parent=" + (parent != null ? parent.value : "null");
			// string += ", right=" + (right != null ? right.value : "null");
			// string += ", left=" + (left != null ? left.value : "null");
			return string;
			
		}
		
	}
	
	protected Node root;
	
	public BinarySearchTree()
	{
		
		root = null;
		
	}
	
	
	public T getElement(T toFind)
	{
		
		return getElement(root, toFind);
		
	}
	
	
	@SuppressWarnings("unchecked")
	private T getElement(Node start, T toFind)
	{
		
		Comparable<T> toFindComparable = (Comparable<T>) toFind;
		if (start == null) return null;
		
		if (toFindComparable.compareTo(toFind) == 0) return start.value;
		// searched value is smaller - look in the left subtree
		if (toFindComparable.compareTo(start.value) < 0) return getElement(start.left, toFind);
		// searched value is larger - look in the right subtree
		return getElement(start.right, toFind);
		
	}
	
	
	@SuppressWarnings("unchecked")
	protected Node findNode(Node start, T toFind)
	{
		
		Comparable<T> toFindComparable = (Comparable<T>) toFind;
		if (start == null) return null;
		
		if (toFindComparable.compareTo(start.value) == 0) return start;
		// searched value is smaller - look in the left subtree
		if (toFindComparable.compareTo(start.value) < 0) return findNode(start.left, toFind);
		// searched value is larger - look in the right subtree
		return findNode(start.right, toFind);
		
	}
	
	
	private T getMinimum(Node startNode)
	{
		
		if (root == null) throw new NoSuchElementException();
		
		return getMinimumNode(startNode).value;
		
	}
	
	
	private Node getMinimumNode(Node startNode)
	{
		
		Node currentNode = startNode;
		for (; currentNode.left != null; currentNode = currentNode.left);
		return currentNode;
		
	}
	
	
	private T getMaximum(Node startNode)
	{
		
		if (root == null) throw new NoSuchElementException();
		
		return getMaximumNode(startNode).value;
		
	}
	
	
	private Node getMaximumNode(Node startNode)
	{
		
		Node currentNode = startNode;
		for (; currentNode.right != null; currentNode = currentNode.right);
		return currentNode;
		
	}
	
	
	public T successor(T elem)
	{
		
		Node successor = findNode(root, elem);
		
		if (successor.right != null) return getMinimum(successor.right);
		
		Node currNode = successor.parent;
		while (currNode != null && successor == currNode.right)
		{
			successor = currNode;
			currNode = currNode.parent;
		}
		
		return currNode.value;
		
	}
	
	
	public String toStringInOrder()
	{
		
		return removeComma(inOrder(root));
		
	}
	
	
	private String inOrder(Node node)
	{
		
		String string = "";
		if (node == null) return string;
		string += inOrder(node.left);
		string += node.toString() + ", ";
		string += inOrder(node.right);
		return string;
		
	}
	
	
	public String toStringPreOrder()
	{
		
		return removeComma(preOrder(root));
		
	}
	
	
	private String preOrder(Node node)
	{
		
		String string = "";
		if (node == null) return string;
		string += node.toString() + ", ";
		string += preOrder(node.left);
		string += preOrder(node.right);
		return string;
		
	}
	
	
	public String toStringPostOrder()
	{
		
		return removeComma(postOrder(root));
		
	}
	
	
	private String postOrder(Node node)
	{
		
		String string = "";
		if (node == null) return string;
		string += postOrder(node.left);
		string += postOrder(node.right);
		string += node.toString() + ", ";
		return string;
		
	}
	
	
	private String removeComma(String string)
	{
		
		if (string.length() < 2) return string;
		return string.substring(0, string.length() - 2);
		
	}
	
	
	public boolean add(T elem)
	{
		
		return add(root, elem);
		
	}
	
	
	@SuppressWarnings("unchecked")
	private boolean add(Node startNode, T elem)
	{
		
		Node currNode = null;
		Node root = startNode;
		Node newNode = new Node(elem);
		
		while (root != null)
		{
			currNode = root;
			if (((Comparable<T>) newNode.value).compareTo(root.value) < 0) root = root.left;
			else if (((Comparable<T>) newNode.value).compareTo(root.value) > 0) root = root.right;
			else return false;
		}
		
		newNode.parent = currNode;
		if (currNode == null) this.root = newNode;
		else if (((Comparable) newNode.value).compareTo(currNode.value) < 0) currNode.left = newNode;
		else if (((Comparable) newNode.value).compareTo(currNode.value) > 0) currNode.right = newNode;
		else return false;
		return true;
		
	}
	
	
	public T remove(T value)
	{
		
		Node toDelete = findNode(root, value);
		if (toDelete == null) return null;
		T temp = toDelete.value;
		if (size() == 1) clear();
		
		// case 1 - no children
		else if (toDelete.left == null && toDelete.right == null)
		{
			if (toDelete.parent.left == toDelete) toDelete.parent.left = null;
			else toDelete.parent.right = null;
		}
		// case 2 - has one child
		else if (toDelete.left != null && toDelete.right == null)
		{
			if (toDelete == root) root = toDelete.left;
			else if (toDelete.parent.left == toDelete) toDelete.parent.left = toDelete.left;
			else toDelete.parent.right = toDelete.left;
		}
		else if (toDelete.right != null && toDelete.left == null)
		{
			if (toDelete == root) root = toDelete.right;
			else if (toDelete.parent.left == toDelete) toDelete.parent.left = toDelete.right;
			else toDelete.parent.right = toDelete.right;
		}
		// case 3 - has two children
		else
		{
			Node succesor = findNode(root, successor(toDelete.value));
			remove(succesor.value);
			toDelete.value = succesor.value;
		}
		return temp;
		
	}
	
	
	public void clear()
	{
		
		root = null;
		
	}
	
	
	public int size()
	{
		
		return size(root);
		
	}
	
	
	private int size(Node start)
	{
		
		if (start == null) return 0;
		int x = 1;
		x += size(start.left);
		x += size(start.right);
		return x;
		
	}
	
}
