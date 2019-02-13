package main;

import java.util.Comparator;

public class SortByFreq implements Comparator<HuffmanNode> {

	public int compare(HuffmanNode o1, HuffmanNode o2) {
		// TODO Auto-generated method stub
		return o1.count() - o2.count();
	}

}
