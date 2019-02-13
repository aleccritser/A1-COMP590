package main;

import java.util.Comparator;

public class SortByCL implements Comparator<SymbolWithCodeLength> {

	@Override
	public int compare(SymbolWithCodeLength o1, SymbolWithCodeLength o2) {
		// TODO Auto-generated method stub
		return o1.codeLength() - o2.codeLength();
	}

}
