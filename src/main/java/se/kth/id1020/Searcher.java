package se.kth.id1020;

public class Searcher {

	public static void main(String[] args) throws Exception{
		TinySearchEngineBase searchEngine = new TinySearchEngineRedBST();
		Driver.run(searchEngine);
	}
}
