package main;

public class LeafHuffmanNode implements HuffmanNode {
	int symbol;
	int count;
	boolean isItLeaf = true;
	String binPath;
	boolean isRC;
	int cwLength;

	@Override
	public int count() {
		// TODO Auto-generated method stub
		return count;
	}

	@Override
	public boolean isLeaf() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public int symbol() {
		// TODO Auto-generated method stub
		return symbol;
	}

	@Override
	public int height() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isFull() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean insertSymbol(int length, int symbol) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public HuffmanNode left() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HuffmanNode right() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String path() {
		// TODO Auto-generated method stub
		return binPath;
	}

	@Override
	public Boolean isRC() {
		// TODO Auto-generated method stub
		return isRC;
	}

	@Override
	public int cwLen() {
		// TODO Auto-generated method stub
		return cwLength;
	}

}
