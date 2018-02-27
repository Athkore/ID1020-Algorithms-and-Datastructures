package se.kth.id1020;


public class Run {

	public static void main(String[] args) throws Exception {
		TinySearchEngineBase searchEngine = new TinySearch();
		Driver.run(searchEngine);
	}

}
