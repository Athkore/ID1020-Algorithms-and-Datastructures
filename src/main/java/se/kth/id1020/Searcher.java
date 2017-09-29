package se.kth.id1020;

import java.nio.file.Files;
import java.nio.file.Paths;

public class Searcher {

	public static void main(String[] args) throws Exception{
		TinySearchEngineBase searchEngine = new TinySearchEnginelist();
		if(Files.exists(Paths.get("search", "A"))) {
			System.out.println("Index already exists, dou you want to remake the index? Y/N");
			int a = System.in.read();
			if(a=='N'||a=='n')Driver.testTheEngine(searchEngine);
		}
		Driver.run(searchEngine);
	}
}
