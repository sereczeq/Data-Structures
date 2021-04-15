package RedBlackTree;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.Queue;

interface IWithName
{
	
	String getName();
	
}

interface IList<E> extends Iterable<E>
{
	
	boolean add(E e); // add element to the list on proper position
	
	void add(int index, E element) throws NoSuchElementException; // not implemented
	
	void clear(); // delete all elements
	
	boolean contains(E element); // is list containing an element (equals())
	
	E get(int index) throws NoSuchElementException; // get element from position
	
	E set(int index, E element) throws NoSuchElementException; // not implemented
	
	int indexOf(E element); // where is element (equals())
	
	boolean isEmpty();
	
	Iterator<E> iterator();
	
	ListIterator<E> listIterator() throws UnsupportedOperationException; // for ListIterator
	
	E remove(int index) throws NoSuchElementException; // remove element from position index
	
	boolean remove(E e); // remove element
	
	int size();
	
}

public class RBBST<T>
{
	
	// public static void main(String[] arts)
	// {
	//
	// RBBST bst = new RBBST();
	// bst.insert(10);
	// bst.insert(4);
	// bst.insert(20);
	// bst.insert(30);
	// bst.insert(40);
	// bst.insert(15);
	// bst.insert(13);
	// bst.insert(6);
	// bst.insert(8);
	// bst.insert(12);
	// bst.printNaturalForm();
	// System.out.println(bst.toStringInOrder());
	// System.out.println(bst.toStringPostOrder());
	// System.out.println(bst.toStringPreOrder());
	//
	// }
	
	private class Node
	{
		
		T value;
		Node left = null, right = null, parent = null;
		boolean red = true;
		
		public Node(T v)
		{
			
			value = v;
			
		}
		
		
		public Node(T value, Node left, Node right, Node parent)
		{
			
			super();
			this.value = value;
			this.left = left;
			this.right = right;
			this.parent = parent;
			
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
	
	private Node root;
	
	public RBBST()
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
	private Node findNode(Node start, T toFind)
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
	
	
	private boolean add(T elem)
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
	
	
	public void insert(T value)
	{
		
		insert(new Node(value));
		
	}
	
	
	public void insert(Node node)
	{
		
		add(node.value);
		node = findNode(root, node.value);
		while (node != root && node.parent.red)
		{
			if (node.parent == node.parent.parent.left)
			{
				Node other = node.parent.parent.right;
				if (other != null && other.red)
				{
					node.parent.red = false;
					other.red = false;
					node.parent.parent.red = true;
					node = node.parent.parent;
				}
				else if (node == node.parent.right)
				{
					node = node.parent;
					rotateLeft(node);
				}
				else
				{
					node.parent.red = false;
					node.parent.parent.red = true;
					rotateRight(node.parent.parent);
				}
			}
			else
			{
				Node other = node.parent.parent.left;
				if (other != null && other.red)
				{
					node.parent.red = false;
					other.red = false;
					node.parent.parent.red = true;
					node = node.parent.parent;
				}
				else if (node == node.parent.left)
				{
					node = node.parent;
					rotateRight(node);
				}
				else
				{
					node.parent.red = false;
					node.parent.parent.red = true;
					rotateLeft(node.parent.parent);
				}
			}
		}
		root.red = false;
		
	}
	
	
	public void rotateLeft(Node node)
	{
		
		Node other = node.right;
		node.right = other.left;
		if (other.left != null) other.left.parent = node;
		other.parent = node.parent;
		if (node.parent == null) root = other;
		else if (node == node.parent.left) node.parent.left = other;
		else node.parent.right = other;
		other.left = node;
		node.parent = other;
		
	}
	
	
	public void rotateRight(Node node)
	{
		
		Node other = node.left;
		node.left = other.right;
		if (other.right != null) other.right.parent = node;
		other.parent = node.parent;
		if (node.parent == null) root = other;
		else if (node == node.parent.right) node.parent.right = other;
		else node.parent.left = other;
		other.right = node;
		node.parent = other;
		
	}
	
	
	public void printNaturalForm()
	{
		
		int width = 32;
		printLevels(root, width);
		
	}
	
	
	private void printLevels(Node node, int width)
	{
		
		if (node == null)
		{
			return;
		}
		
		Queue<Node> queue = new LinkedList<>();
		queue.add(node);
		
		while (true)
		{
			int levelNodeCount = queue.size();
			
			if (levelNodeCount == 0)
			{
				break;
			}
			
			StringBuilder levelAsString = new StringBuilder();
			while (levelNodeCount > 0)
			{ // until we have read this entire level
				Node currentNode = queue.poll();
				
				String valToString = currentNode == null ? "X " : currentNode.value + " ";
				// String valStr = StringUtils.center(valToString, width);
				levelAsString.append(valToString);
				
				if (currentNode != null)
				{
					queue.add(currentNode.left);
					queue.add(currentNode.right);
				}
				
				levelNodeCount--;
			}
			width /= 2;
			System.out.printf("%s%n", levelAsString.toString());
		}
		
	}
	
}
