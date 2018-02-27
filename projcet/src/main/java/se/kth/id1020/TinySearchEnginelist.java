package se.kth.id1020;

import java.util.ArrayList;
import java.util.List;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import se.kth.id1020.util.Attributes;
import se.kth.id1020.util.Document;
import se.kth.id1020.util.Word;

public class TinySearchEnginelist implements TinySearchEngineBase {
	//private List<Node>[] listarray;
	private List<Node> list = new ArrayList<>();
	private class Node{
		private Word word;
		private Attributes attr;
		public Node(Word word, Attributes attr) {
			this.word = word;
			this.attr = attr;
		}
	}
	@Override
	public void insert(Word word, Attributes attr) {
		char[] index = new char[1];
		if(word.word.charAt(0)>='a'&&word.word.charAt(0)<='z')index[0] = (char)(word.word.charAt(0));
		else if(word.word.charAt(0)>='A'&&word.word.charAt(0)<='Z')index[0] = (char)(word.word.charAt(0));
		else index[0] = '1';
		try {
			byte[] get = word.word.getBytes();
			byte[] ins = new byte[get.length+1];
			for(int i = 0; i < get.length; i++)ins[i] = get[i];
			ins[get.length] = '\n';
			Files.write(Paths.get("search", new String(index)), ins, StandardOpenOption.valueOf("CREATE"), StandardOpenOption.valueOf("APPEND"));
			get = Integer.toString(attr.occurrence).getBytes();
			ins = new byte[get.length+1];
			for(int i = 0; i < get.length; i++)ins[i] = get[i];
			ins[get.length] = '\n';
			Files.write(Paths.get("search", new String(index) + ".occurence"), ins, StandardOpenOption.valueOf("CREATE"), StandardOpenOption.valueOf("APPEND"));
			get = attr.document.name.getBytes();
			ins = new byte[get.length+1];
			for(int i = 0; i < get.length; i++)ins[i] = get[i];
			ins[get.length] = '\n';
			Files.write(Paths.get("search", new String(index) + ".Documentname"), ins, StandardOpenOption.valueOf("CREATE"), StandardOpenOption.valueOf("APPEND"));
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}

	@Override
	public List<Document> search(String query) {
		List<Document> returnlist = new ArrayList<>();
		char[] index = new char[1];
		if(query.charAt(0)>='a'&&query.charAt(0)<='z')index[0] = (char)(query.charAt(0));
		else if(query.charAt(0)>='A'&&query.charAt(0)<='Z')index[0] = (char)(query.charAt(0));
		else index[0] = '1';
		try{
			int i;
			List<String> list = Files.readAllLines(Paths.get("search", new String(index)), Charset.defaultCharset());
			List<String> dlist = Files.readAllLines(Paths.get("search", new String(index) + ".Documentname"), Charset.defaultCharset());
			for(i = 0; i<list.size();i++)if(query.equals(list.get(i))) {
			returnlist.add(new Document(dlist.get(i)));
			}
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		return returnlist;
	}

}
