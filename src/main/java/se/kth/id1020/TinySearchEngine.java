package se.kth.id1020;

import java.util.ArrayList;
import java.util.List;
import se.kth.id1020.TinySearchEngineBase;
import se.kth.id1020.util.Attributes;
import se.kth.id1020.util.Document;
import se.kth.id1020.util.Word;

public class TinySearchEngine implements TinySearchEngineBase {
	private Node root = new Node();
		
	private class Node{
		public Word word;
		public Attributes[] attributes;
		public Node[] next;
		public Node() {
			this.next = new Node[100];
			this.attributes = new Attributes[1];
		}
		public void setWord(Word word) {
			this.word = word;
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
		public Node getNext(int i) {
			return this.next[i];
		}
		public void setNext(int i, Node t) {
			this.next[i] = t;
			return;
		}
	}
	@Override
	public void insert(Word word, Attributes attr) {
		Node r = root;
		for(int i = 0; i < word.word.length(); i++) 
		{
			int c = word.word.charAt(i)-28;
			if(r.next[c] == null){
				Node t = new Node();
				r.setNext(c,t);
				r = t;
			}
			else
			{
				r = r.getNext(c);
			}
		}
		r.setWord(word);
		r.insertAttributes(attr);
	}

	@Override
	public List<Document> search(String query) {
		Node r = root;
		List<Document> a = new ArrayList<>();
		for(int i = 0; i < query.length(); i++) 
		{
			int c = query.charAt(i)-28;
			
			if(r.getNext(c)==null) 
			{
				return null;
			}
			else r = r.getNext(c);
		}
		for(int i = 0; i < r.attributes.length;i++)a.add(r.getDocument(i));
		return a;
	}
}
