package se.kth.id1020;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

import se.kth.id1020.util.Attributes;
import se.kth.id1020.util.Document;
import se.kth.id1020.util.Sentence;
import se.kth.id1020.util.Word;

public class TinySearch implements TinySearchEngineBase {
	static HashMap<String, Node> hm = new HashMap<String, Node>();
	int progress = 0;
	public class Node{
		public Node(Word wrd) {
			this.att = new LinkedList<Attributes>();
			this.wrd = wrd;
			this.cnt = new LinkedList<Integer>();
		}
		public LinkedList<Integer> cnt = new LinkedList<Integer>();
		public Word wrd;
		public LinkedList<Attributes> att = new LinkedList<Attributes>();
	}

	public class rN{
		public rN(List<Document> doc, int index) {
			this.doc = doc;
			this.index = index;
		}
		List<Document> doc;
		int index;
	}
	
	public void preInserts() {
		// TODO Auto-generated method stub
		
	}

	public void insert(Sentence sentence, Attributes attr) {
		Attributes a = attr;
		Node x = null;
		for(Word w : sentence.getWords()) {
			if(hm.containsKey(w.word)) {
				x = hm.get(w.word);
				//System.out.println(x);
				if(x.att.peekLast().document.equals(attr.document))x.cnt.set(x.cnt.size()-1, x.cnt.getLast()+1);
				else {
					x.att.add(a);
					x.cnt.add(1);
				}
			}
			else {
				x = new Node(w);
				x.att.add(a);
				x.cnt.add(1);
				hm.put(w.word, x);
			}
			a = new Attributes(a.document, a.occurrence+1);
		}
		
	}

	public void postInserts() {
		// TODO Auto-generated method stub
		
	}

	public String infix(String query) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Document> search(String query) {
		//System.out.println(progress++);
		System.out.println(query);
		byte[] b = query.getBytes();
		List<Document> rl = new ArrayList<Document>();
		boolean s = false;
		if((b[0]>='a'&&b[0]<='z')||(b[0]>='A'&&b[0]<='Z')) {
			String ss = new String();
			for(int i = 0; i < b.length; i++)if(i == query.length()-1||b[i+1] == ' ') {
				ss = query.substring(0,i+1);
				System.out.println(ss);
				if(i != query.length()-1) {
					for(int j = i+1; j < query.length(); j++) {
						if(b[j] == ' ' || j == query.length()-1) {
							String sss = query.substring(i+1, j+1);
							System.out.println(query);
							query = query.substring(i+1);
							if(sss.equals("orderby"))s = true;
						}
					}				
				}
				break;
			}
			if(!hm.containsKey(ss))return null;
			Node n = hm.get(ss);
			BubbleSort bs = new BubbleSort();
			if(s == true)bs.bubblesort(n, query.substring(query.indexOf(" ")));
			for(Attributes a : n.att)rl.add(a.document);
			System.out.println(rl);
		}
		else {
			byte[] a = query.getBytes();
			if(!(a[a.length-1] == ' '))query = query + " ";
			rl = search(query, 0).doc;
		}
		return rl;
	}
	
	public rN search(String query, int index){
		System.out.println(query.substring(index));
		System.out.println(index);
		byte[] b = query.getBytes();
		List<Document> rl = new ArrayList<Document>();
		boolean s = false;
		if((b[0]>='a'&&b[0]<='z')||(b[0]>='A'&&b[0]<='Z')) {
			String ss = new String();
			for(int i = 0; i < b.length; i++)if(i == query.length()-1||b[i+1] == ' ') {
				ss = query.substring(0,i+1);
				System.out.println(ss);
				if(i != query.length()-1) {
					for(int j = i+1; j < query.length(); j++) {
						if(b[j] == ' ' || j == query.length()-1) {
							String sss = query.substring(i+1, j+1);
							System.out.println(query);
							query = query.substring(i+1);
							if(sss.equals("orderby"))s = true;
						}
					}				
				}
				break;
			}
			if(!hm.containsKey(ss))return null;
			Node n = hm.get(ss);
			BubbleSort bs = new BubbleSort();
			if(s == true)bs.bubblesort(n, query.substring(query.indexOf(" ")));
			for(Attributes a : n.att)rl.add(a.document);
			System.out.println(rl);
		}
		else {
			rN n1 = new rN(null, index);
			rN n2 = new rN(null, index);

			if(b[index] == '+') {
				n1 = search(query, index+2);
				index = n1.index;
				n2 = search(query, index);
				index = n2.index;
				System.out.println(n1.doc);
				System.out.println(n2.doc);
				for(Document d : n1.doc)if(n2.doc.contains(d))rl.add(d);
				System.out.println(rl);
				System.out.println("Intersection");	
			}
			else if(b[index] == '|') {
				n1 = search(query, index+2);
				index = n1.index;
				n2 = search(query, index);
				index = n2.index;
				for(Document d : n1.doc)if(!rl.contains(d))rl.add(d);
				for(Document d : n2.doc)if(!rl.contains(d))rl.add(d);
				System.out.println("Union");			
			}
			else if(b[index] == '-') {
				n1 = search(query, index+2);
				index = n1.index;
				n2 = search(query, index);
				index = n2.index;
				for(Document d : n1.doc)if(!n2.doc.contains(d))rl.add(d);
				System.out.println("Difference");
			}
		}
		return new rN(rl, index);
	}
}
