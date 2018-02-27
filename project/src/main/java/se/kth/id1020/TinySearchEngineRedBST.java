package se.kth.id1020;

import java.util.ArrayList;
import java.util.List;

import se.kth.id1020.util.Attributes;
import se.kth.id1020.util.Document;
import se.kth.id1020.util.Word;

public class TinySearchEngineRedBST implements TinySearchEngineBase{
	private Node root;

    private static final boolean RED   = true;
    private static final boolean BLACK = false;

	
	private class Node{
		private Word word;
		private Attributes[] attributes;
		public Node left,right;
		private boolean color;
		private int size;
		public Node(Word word, Attributes attr, boolean color, int size) {
			this.attributes = new Attributes[1];
			this.attributes[0] = attr;
			this.word = word;
			this.color = color;
			this.size = size;
		}

		public void insertAttributes(Attributes attr) {
			if(this.attributes[this.attributes.length-1]!=null) {
				Attributes[] temp = this.attributes;
				this.attributes = new Attributes[this.attributes.length*2];
				for(int i = 0; i < temp.length;i++)this.attributes[i]=temp[i];
			}
			for(int i = 0; i<this.attributes.length;i++)if(this.attributes[i]==null)this.attributes[i]=attr;
		}
		public Document getDocument(int i) {
			return this.attributes[i].document;
		}
		public int size() {
			return this.size;
		}
		public void setSize() {
			this.size=1;
			if(this.left!=null)this.size=this.size+this.left.size;
			if(this.right!=null)this.size=this.size+this.right.size;
		}
		public void setColor(boolean color) {
			this.color = color;
		}
		public boolean getColor() {
			return this.color;
		}
	}
	
	public TinySearchEngineRedBST(){root = null;}
	
    private int size() {
        return root.size();
    }
	private boolean isRed(Node x) {
	        if (x == null) return false;
	        return x.color == RED;
	}
	private int compare(String a, String b) {
			return a.compareTo(b);
	}
    // make a left-leaning link lean to the right
    private Node rotateRight(Node h) {
        assert (h != null) && isRed(h.left);
        Node x = h.left;
        h.left = x.right;
        x.right = h;
        x.color = x.right.color;
        x.right.color = RED;
        x.size = h.size;
        h.setSize();
        return x;
    }
    // make a right-leaning link lean to the left
    private Node rotateLeft(Node h) {
        assert (h != null) && isRed(h.right);
        Node x = h.right;
        h.right = x.left;
        x.left = h;
        x.setColor(x.left.getColor());
        x.left.setColor(RED);
        x.size = h.size();
        h.setSize();
        return x;
    }

    // flip the colors of a node and its two children
    private void flipColors(Node h) {
        // h must have opposite color of its two children
        // assert (h != null) && (h.left != null) && (h.right != null);
        // assert (!isRed(h) &&  isRed(h.left) &&  isRed(h.right))
        //    || (isRed(h)  && !isRed(h.left) && !isRed(h.right));
        h.color = !h.color;
        h.left.color = !h.left.color;
        h.right.color = !h.right.color;
    }
	@Override
	public void insert(Word word, Attributes attr) {
        if (word == null) throw new IllegalArgumentException("first argument to put() is null");
        root = insert(root, word, attr);
        root.setColor(BLACK);
	}
	
	private Node insert(Node h, Word word, Attributes attr) {
		if (h == null) return new Node(word, attr, RED, 1);

        int cmp = compare(word.word, h.word.word);
        if      (cmp < 0) h.left  = insert(h.left,  word, attr); 
        else if (cmp > 0) h.right = insert(h.right, word, attr); 
        else              
        h.insertAttributes(attr);

        // fix-up any right-leaning links
        if (isRed(h.right) && !isRed(h.left))      h = rotateLeft(h);
        if (isRed(h.left)  &&  isRed(h.left.left)) h = rotateRight(h);
        if (isRed(h.left)  &&  isRed(h.right))     flipColors(h);
        h.setSize();

        return h;
	}

    
	@Override
	public List<Document> search(String query) {
		List<Document> list = new ArrayList<>();
		list = search(root,query, list);
		return null;
	}
    private List<Document> search(Node x, String word, List<Document> list) {
        while (x != null) {
            int cmp = compare(word, x.word.word);
            if      (cmp < 0) x = x.left;
            else if (cmp > 0) x = x.right;
            else{
            	for (int i = 0; i<x.attributes.length; i++)list.add(x.attributes[i].document);
            	return list;
            }
        }
        return null;
    }
}
