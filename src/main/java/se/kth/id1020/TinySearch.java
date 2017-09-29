package se.kth.id1020;

import java.util.List;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.file.*;
import se.kth.id1020.util.Attributes;
import se.kth.id1020.util.Document;
import se.kth.id1020.util.Word;

public class TinySearch implements TinySearchEngineBase
{

	@Override
	public void insert(Word word, Attributes attr) {
		try{
			Files.write(Paths.get("search", Integer.toString(System.identityHashCode(word))), word.word.getBytes());
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}

	@Override
	public List<Document> search(String query) {
		
		return null;
	}

}
