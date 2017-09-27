package se.kth.id1020;

import java.util.ArrayList;
import java.util.List;
import se.kth.id1020.TinySearchEngineBase;
import se.kth.id1020.util.Attributes;
import se.kth.id1020.util.Document;
import se.kth.id1020.util.Word;

public class TinySearchEngine implements TinySearchEngineBase {
	private Node root;
		
	private class Node{
		private Word word;
		private Attributes[] attributes;
		private Node[] next;
		public Node() {
			this.next = new Node[28];
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
	}
	@Override
	public void insert(Word word, Attributes attr) {
		Node r = root;
		for(int i = 0; i < word.word.length(); i++) 
		{
			char c = word.word.charAt(i);
			int index;
			if(c == '\'') index = 26;
			else if(c== '-') index = 27;
			else index = c-'a';
			if(c == 0)break;
			if(r.next[index]==null)
			{
				Node t = new Node();
				r.next[index] = t;
				r = t;
			}
			else
			{
				r = r.next[index];
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
			char c = query.charAt(i);
			int index = c-'a';
			
			if(r.next[index]==null) 
			{
				return null;
			}
			else r = r.next[index];
		}
		for(int i = 0; i < r.attributes.length;i++)a.add(r.getDocument(i));
		return a;
	}

}
