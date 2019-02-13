package main;

import java.io.IOException;

import io.InputStreamBitSource;
import io.InsufficientBitsLeftException;

public class HuffmanDecodeTree {

	private InternalHuffmanNode _root;

	public HuffmanDecodeTree(SymbolWithCodeLength[] huff) {

		// Create empty internal root node.

		_root = new InternalHuffmanNode();

		int prevL = 1;

		// Insert each symbol from list into tree
		int i = 0;

		// If all went well, your tree should be full

		assert _root.isFull();
	}

	public boolean insert(SymbolWithCodeLength swcl) {
		InternalHuffmanNode trav = _root;
		int count = swcl.huffCodeArr.length;
		for (int i = 0; i < (count - 1); i++) {
			if (swcl.huffCodeArr[i] == '0') {
				System.out.print("0");
				if (trav.lc == null) {
					InternalHuffmanNode intNode = new InternalHuffmanNode();
					trav.lc = intNode;
					trav = (InternalHuffmanNode) trav.lc;
				} else {
					trav = (InternalHuffmanNode) trav.lc;
				}
			} else if (swcl.huffCodeArr[i] == '1') {
				System.out.print("1");
				if (trav.rc == null) {
					InternalHuffmanNode intNode = new InternalHuffmanNode();
					trav.rc = intNode;
					trav = (InternalHuffmanNode) trav.rc;
				} else {
					trav = (InternalHuffmanNode) trav.rc;
				}
			}
		}
		if (swcl.huffCodeArr[count - 1] == '0') {
			System.out.print("0");
			LeafHuffmanNode newLeaf = new LeafHuffmanNode();
			newLeaf.symbol = swcl.val;
			newLeaf.isItLeaf = true;
			trav.lc = newLeaf;
			System.out.println("successL" + swcl.val);
		} else if (swcl.huffCodeArr[count - 1] == '1') {
			System.out.print("1");
			LeafHuffmanNode newLeaf = new LeafHuffmanNode();
			newLeaf.symbol = swcl.val;
			trav.rc = newLeaf;
			System.out.println("successR" + newLeaf.symbol);
		}

		return false;

	}

	public int decode(InputStreamBitSource bit_source) throws IOException, InsufficientBitsLeftException {

		// Start at the root
		InternalHuffmanNode n = _root;

		while (n.rc.isLeaf() == false || n.lc.isLeaf() == false) {
			// Get next bit and walk either left or right,
			// updating n to be as appropriate
			int dir = bit_source.next(1);

			if (dir == 0) {
				if (n.lc.isLeaf() == false) {
					n = (InternalHuffmanNode) n.lc;
				} else {
					break;
				}
			} else if (dir == 1) {
				if (n.rc.isLeaf() == false) {
					n = (InternalHuffmanNode) n.rc;
				} else {
					break;
				}
			}
		}
		int nextNum = bit_source.next(1);
		if (nextNum == 1) {
			return n.rc.symbol();
		} else if (nextNum == 0) {
			return n.lc.symbol();
		}
		// Return symbol at leaf

		return n.symbol();
	}

	public InternalHuffmanNode getRoot() {
		return _root;
	}

}
