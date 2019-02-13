package main;

public class InternalHuffmanNode implements HuffmanNode {
	HuffmanNode lc = null;
	HuffmanNode rc = null;
	Boolean isFull;
	Boolean hasLeft = false;
	Boolean hasRight = false;
	int count;
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
		return false;
	}

	@Override
	public int symbol() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int height() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isFull() {
		// TODO Auto-generated method stub
		if (this.rc != null && this.lc != null) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean insertSymbol(int length, int symbol) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public HuffmanNode left() {
		// TODO Auto-generated method stub
		return this.lc;
	}

	@Override
	public HuffmanNode right() {
		// TODO Auto-generated method stub
		return this.rc;
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
