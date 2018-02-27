package se.kth.id1020;

import java.util.Collections;
import java.util.LinkedList;

import se.kth.id1020.TinySearch.Node;
import se.kth.id1020.util.Attributes;

public class BubbleSort {
	TinySearch x = new TinySearch();
    public BubbleSort() {}
	Attributes current;
	LinkedList<Attributes> list;
	LinkedList<Integer> cntlist;
	int progress = 0;
	int cnt;
    
    public Node bubblesort(Node root, String sort)
	{
    	String sort2 = "";
    	if(sort.contains(" ")) {
    		byte[] bb = new byte[4];
    		bb[0] = 'd';
    		bb[1] = 'e';
    		bb[2] = 's';
    		bb[3] = 'c';
    		sort2 = sort.substring(sort.indexOf(" ")+1);
    		if(sort2.getBytes()==bb)sort2 = "desc";
    		System.out.println(sort2);
    		sort = sort.substring(0, sort.indexOf(" "));
    		System.out.println(sort);
    	}
    	else sort2 = "asc";
		cntlist = root.cnt;
		list = root.att;
    	current = list.peek();
        Boolean swapvar = true;
        System.out.println(sort);
	    while(swapvar == true)
		{
			swapvar = false;
			current = list.get(0);
			for(int i = 1; i < list.size(); i++)
			{
				if(sort.equals("count")) {
					if(cnt > cntlist.get(i))
					{
						swapcnt(i);
						swapvar = true;
					}
					cnt = cntlist.get(i);
				}
				if(sort.equals("relevance")) {
					if(current.occurrence > list.get(i).occurrence)
					{
						swap(i);	
						swapvar = true;
					}	
				}
				if(sort.equals("popularity")) 
				{	
					if(current.document.popularity > list.get(i).document.popularity)
					{
						swap(i);
						swapvar = true;
					}
				}
				current = list.get(i);
				System.out.println(progress++);
			}
		}			
	    if(sort2.equals("desc")) {
	    	Collections.reverse(list);
	    }
	    root.att = list;
	    return root;
	}
	
	public void swap(int i){
		Attributes x = current;
		current = list.get(i);
		list.set(i, x);
	}
	public void swapcnt(int i){
		int t = cnt;
		cnt = cntlist.get(i);
		cntlist.set(i, t);
		swap(i);
	}
}
