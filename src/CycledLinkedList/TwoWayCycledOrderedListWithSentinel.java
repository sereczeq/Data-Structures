package CycledLinkedList;

import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 * All for loops with isEmpty() in front of them are just me being lazy
 * TODO: change that to something normal or make a single method
 */

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

public class TwoWayCycledOrderedListWithSentinel<E> implements IList<E>
{
	
	private class Element
	{
		
		public Element(E e)
		{
			
			object = e;
			
		}
		
		
		public Element(E e, Element next, Element prev)
		{
			
			object = e;
			this.next = next;
			this.prev = prev;
			
		}
		
		
		// add element e after this
		public void addAfter(Element elem)
		{
			
			Element temp = next;
			next = elem;
			elem.prev = this;
			temp.prev = elem;
			elem.next = temp;
			
		}
		
		
		// assert it is NOT a sentinel
		public E remove()
		{
			
			if (object == null) throw new NoSuchElementException("can't remove sentinel / element with null object");
			if (prev != null) prev.next = next;
			if (next != null) next.prev = prev;
			return object;
			
		}
		
		E object;
		Element next = null;
		Element prev = null;
		
	}
	
	Element sentinel;
	int size;
	
	private class InnerIterator implements Iterator<E>
	{
		
		Element curr;
		
		public InnerIterator()
		{
			
			curr = sentinel;
			
		}
		
		
		@Override
		public boolean hasNext()
		{
			
			return !isEmpty();
			
		}
		
		
		@Override
		public E next()
		{
			
			if (hasNext())
			{
				curr = curr.next;
				if (curr == sentinel) curr = curr.next;
				return curr.object;
			}
			throw new NoSuchElementException();
			
		}
		
	}
	
	private class InnerListIterator implements ListIterator<E>
	{
		
		Element curr;
		
		public InnerListIterator()
		{
			
			curr = sentinel;
			
		}
		
		
		@Override
		public boolean hasNext()
		{
			
			return !isEmpty();
			
		}
		
		
		@Override
		public E next()
		{
			
			if (hasNext())
			{
				curr = curr.next;
				if (curr == sentinel) curr = curr.next;
				return curr.object;
			}
			throw new NoSuchElementException();
			
		}
		
		
		@Override
		public void add(E arg0)
		{
			
			throw new UnsupportedOperationException();
			
		}
		
		
		@Override
		public boolean hasPrevious()
		{
			
			return !isEmpty();
			
		}
		
		
		@Override
		public int nextIndex()
		{
			
			throw new UnsupportedOperationException();
			
		}
		
		
		@Override
		public E previous()
		{
			
			if (hasPrevious())
			{
				curr = curr.prev;
				if (curr == sentinel) curr = curr.prev;
				return curr.object;
			}
			throw new NoSuchElementException();
			
		}
		
		
		@Override
		public int previousIndex()
		{
			
			throw new UnsupportedOperationException();
			
		}
		
		
		@Override
		public void remove()
		{
			
			throw new UnsupportedOperationException();
			
		}
		
		
		@Override
		public void set(E arg0)
		{
			
			throw new UnsupportedOperationException();
			
		}
		
	}
	
	public TwoWayCycledOrderedListWithSentinel()
	{
		
		sentinel = new Element(null);
		sentinel.prev = sentinel.next = sentinel;
		
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean add(E e)
	{
		
		if (isEmpty())
		{
			sentinel.next = sentinel.prev = new Element(e, sentinel, sentinel);
			return true;
		}
		else if (e instanceof Comparable)
		{
			
			Element elem = sentinel.next;
			for (int x = 0; x < size(); x++)
			{
				// if e is smaller than elem put e before elem
				if (((Comparable<E>) elem.object).compareTo(e) > 0)
				{
					elem.prev.addAfter(new Element(e));
					return true;
				}
				elem = elem.next;
			}
			
		}
		// default case
		sentinel.prev.addAfter(new Element(e));
		return true;
		
	}
	
	
	private Element getElement(int index)
	{
		
		Element elem = sentinel.next;
		// I'm proud of this one, whole method done in one loop :')
		for (int x = 0; elem != sentinel && x <= index; x++, elem = elem.next) if (x == index) return elem;
		throw new NoSuchElementException();
		
	}
	
	
	@SuppressWarnings("unused")
	private Element getElement(E obj)
	{
		
		return getElement(indexOf(obj));
		
	}
	
	
	@Override
	public void add(int index, E element)
	{
		
		throw new UnsupportedOperationException();
		
	}
	
	
	@Override
	public void clear()
	{
		
		sentinel.next = sentinel.prev = sentinel;
		
	}
	
	
	@Override
	public boolean contains(E element)
	{
		
		for (Element elem = sentinel.next; elem != sentinel; elem = elem.next)
			if (elem.object.equals(element)) return true;
		return false;
		
	}
	
	
	@Override
	public E get(int index)
	{
		
		return getElement(index).object;
		
	}
	
	
	@Override
	public E set(int index, E element)
	{
		
		throw new UnsupportedOperationException();
		
	}
	
	
	@Override
	public int indexOf(E element)
	{
		
		Iterator<E> it = iterator();
		for (int x = 0; x < size(); x++) if (it.next().equals(element)) return x;
		return -1;
		
	}
	
	
	@Override
	public boolean isEmpty()
	{
		
		return sentinel.next == sentinel;
		
	}
	
	
	@Override
	public Iterator<E> iterator()
	{
		
		return new InnerIterator();
		
	}
	
	
	@Override
	public ListIterator<E> listIterator()
	{
		
		return new InnerListIterator();
		
	}
	
	
	@Override
	public E remove(int index)
	{
		
		Element elem = sentinel.next;
		for (int x = 0; x <= index && elem != sentinel; x++, elem = elem.next) if (x == index) return elem.remove();
		throw new NoSuchElementException();
		
	}
	
	
	@Override
	public boolean remove(E e)
	{
		
		try
		{
			remove(indexOf(e));
			return true;
		}
		catch (NoSuchElementException ex)
		{
			return false;
		}
		
	}
	
	
	@Override
	public int size()
	{
		
		int x = 0;
		for (Element elem = sentinel.next; elem != sentinel; x++, elem = elem.next);
		return x;
		
	}
	
	
	@Override
	public boolean equals(Object other)
	{
		
		// I assumed everything (Link + weight) will have to be equal (not like with
		// Link, where only ref matters)
		return toString().contentEquals(other.toString());
		
	}
	
	
	public void add(TwoWayCycledOrderedListWithSentinel<E> other)
	{
		/*
		 * Program now takes advantage of the fact that both lists are sorted. It
		 * searches for element of THIS list that is bigger than element of OTHER list
		 * and puts it in front of it. If THIS list finishes before OTHER list does,
		 * elements of other list are all added
		 */
		
		if (equals(other) || other.isEmpty()) return;
		if (isEmpty())
		{
			sentinel = other.sentinel;
			return;
		}
		Element otherElem = other.sentinel.next;
		for (Element elem = sentinel.next; otherElem != other.sentinel; elem = elem.next)
		{
			if (((Comparable<E>) elem.object).compareTo(otherElem.object) > 0 || elem.next == sentinel)
			{
				Element temp = otherElem.next;
				elem.prev.addAfter(otherElem);
				otherElem = temp;
				elem = elem.prev;
			}
		}
		other.clear();
		
	}
	
	
	public void removeAll(E e)
	{
		
		/*
		 * Program now goes through all elements until it finds first match, then
		 * removes elements taking advantage of the fact that they are next to each
		 * other. Once there is no more elements to remove program breaks the loop
		 */
		boolean removed = false;
		for (Element elem = sentinel.next; elem != sentinel; elem = elem.next) if (elem.object.equals(e))
		{
			elem.remove();
			removed = true;
		}
		else if (removed) return;
		
	}
	
	
	@Override
	public String toString()
	{
		
		String string = "";
		Iterator<E> it = iterator();
		for (int x = 0; x < size(); x++) string += "\n" + it.next();
		return string;
		
	}
	
}

class Link implements Comparable<Link>
{
	
	public String ref;
	public int weight;
	
	public Link(String ref)
	{
		
		this.ref = ref;
		weight = 1;
		
	}
	
	
	public Link(String ref, int weight)
	{
		
		this.ref = ref;
		this.weight = weight > 0 ? weight : 1;
		
	}
	
	
	@Override
	public boolean equals(Object obj)
	{
		
		return obj instanceof Link && ref.contentEquals(((Link) obj).ref);
		
	}
	
	
	@Override
	public String toString()
	{
		
		return ref + "(" + weight + ")";
		
	}
	
	
	@Override
	public int compareTo(Link other)
	{
		
		return ref.compareTo(other.ref);
		
	}
	
}

class Document
{
	
	public String name;
	public TwoWayCycledOrderedListWithSentinel<Link> link;
	final static Pattern regex = Pattern.compile("(link=)(?<ref>[a-z][\\w_]*)(\\((?<number>[0-9-]*)\\))?$");
	final static Pattern docName = Pattern.compile("^[a-z].*$");
	
	public Document(String name, Scanner scan)
	{
		
		this.name = name.toLowerCase();
		link = new TwoWayCycledOrderedListWithSentinel<Link>();
		load(scan);
		
	}
	
	
	public void load(Scanner scan)
	{
		
		while (scan.hasNext())
		{
			String line = scan.next().toLowerCase();
			if (line.contains("eod")) break;
			if (createLink(line) != null) link.add(createLink(line));
		}
		
	}
	
	
	static boolean isCorrectId(String id)
	{
		
		return docName.matcher(id.toLowerCase()).find();
		
	}
	
	
	// accepted only small letters, capitalic letter, digits nad '_' (but not on the
	// begin)
	// and eventually weight in parenthesis
	static Link createLink(String line)
	{
		
		Matcher matcher = regex.matcher(line);
		if (matcher.find())
		{
			if (matcher.group("number") != null)
			{
				if (Integer.parseInt(matcher.group("number")) < 0) return null;
				return new Link(matcher.group("ref"), Integer.parseInt(matcher.group("number")));
			}
			return new Link(matcher.group("ref"));
		}
		return null;
		
	}
	
	
	@Override
	public String toString()
	{
		
		String retStr = "Document: " + name;
		Iterator<Link> it = link.iterator();
		for (int x = 0; x < link.size(); x++)
		{
			if (x == 0 || x == 10) retStr += "\n";
			retStr += it.next();
			if (x != link.size() - 1 && x != 9) retStr += " ";
		}
		return retStr;
		
	}
	
	
	public String toStringReverse()
	{
		
		String retStr = "Document: " + name;
		
		ListIterator<Link> it = link.listIterator();
		for (int x = 0; x < link.size(); x++)
		{
			if (x == 0 || x == 10) retStr += "\n";
			retStr += it.previous();
			if (x != link.size() - 1 && x != 9) retStr += " ";
		}
		return retStr;
		
	}
	
}
