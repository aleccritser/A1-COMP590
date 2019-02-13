package main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.OutputStreamBitSink;

public class HuffmanEncoder {

	private Map<Integer, String> _code_map;

	public HuffmanEncoder(int[] symbols, int[] symbol_counts) {
		assert symbols.length == symbol_counts.length;

		// Given symbols and their associated counts, create initial
		// Huffman tree. Use that tree to get code lengths associated
		// with each symbol. Create canonical tree using code lengths.
		// Use canonical tree to form codes as strings of 0 and 1
		// characters that are inserted into _code_map.

		// Start with an empty list of nodes

		List<HuffmanNode> node_list = new ArrayList<HuffmanNode>();

		// Create a leaf node for each symbol, encapsulating the
		// frequency count information into each leaf.

		for (int i = 0; i < 256; i++) {
			LeafHuffmanNode newLeaf = new LeafHuffmanNode();
			newLeaf.count = symbol_counts[i];
			newLeaf.symbol = i;
			newLeaf.isItLeaf = true;
			node_list.add(newLeaf);
		}

		// Sort the leaf nodes
		Collections.sort(node_list, new SortByFreq());
		Double entropyCalc = 0.0;
		for (HuffmanNode h : node_list) {
			System.out.println("symbol: " + h.symbol() + " freq: " + h.count());
			entropyCalc += (((double) h.count()) / ((double) 574992.00));
		}

		// While you still have more than one node in your list...
		while (node_list.size() > 1) {
			// Remove the two nodes associated with the smallest counts
			HuffmanNode smallest = node_list.get(0);
			HuffmanNode smaller = node_list.get(1);
			node_list.remove(0);
			node_list.remove(0);

			// Create a new internal node with those two nodes as children.

			InternalHuffmanNode intNode = new InternalHuffmanNode();
			intNode.lc = smallest;
			intNode.rc = smaller;

			intNode.count = (smallest.count() + smaller.count());

			// Add the new internal node back into the list

			node_list.add(intNode);

			// Resort

			Collections.sort(node_list, new SortByFreq());
		}

		// Create a temporary empty mapping between symbol values and their code
		// strings
		Map<Integer, String> cmap = new HashMap<Integer, String>();

		// Start at root and walk down to each leaf, forming code string along
		// the
		// way (0 means left, 1 means right). Insert mapping between symbol
		// value and
		// code string into cmap when each leaf is reached.
		InternalHuffmanNode mvRoot = (InternalHuffmanNode) node_list.get(0);
		String bitPath = "";
		mvRoot.cwLength = 0;
		recurTrav(mvRoot, bitPath, cmap);

		// Create empty list of SymbolWithCodeLength objects
		List<SymbolWithCodeLength> sym_with_length = new ArrayList<SymbolWithCodeLength>();

		// For each symbol value, find code string in cmap and create new
		// SymbolWithCodeLength
		// object as appropriate (i.e., using the length of the code string you
		// found in cmap).

		for (int i = 0; i < 256; i++) {
			String path = cmap.get(i);
			SymbolWithCodeLength len = new SymbolWithCodeLength(i, path.length());
			sym_with_length.add(len);
		}

		// Sort sym_with_lenght

		Collections.sort(sym_with_length, new SortByCL());
		SymbolWithCodeLength[] lenArr = new SymbolWithCodeLength[256];
		int count = 0;

		for (SymbolWithCodeLength s : sym_with_length) {
			System.out.println("symbol: " + s.val + " CL: " + s.CL);
			lenArr[count] = s;
			count++;
		}

		_code_map = new HashMap<Integer, String>();

		int hc = 0;
		for (int i = 0; i < 256; i++) {
			System.out.println("iter: " + i + " huffCode: " + hc);
			if (i != 255) {
				lenArr[i].huffCode = hc;
				String binCheck = Integer.toBinaryString(hc);
				if (binCheck.length() < lenArr[i].CL) {
					while (binCheck.length() != lenArr[i].CL) {
						binCheck = "0" + binCheck;
					}
				}
				System.out.println(binCheck);
				_code_map.put(lenArr[i].val, binCheck);
				hc = (hc + 1) << ((lenArr[i + 1].CL) - (lenArr[i].CL));
			} else if (i == 255) {

				lenArr[i].huffCode = hc;
				String binCheck = Integer.toBinaryString(hc);
				System.out.println(binCheck);
				_code_map.put(lenArr[i].val, binCheck);
			}
		}

		// Now construct the canonical tree as you did in HuffmanDecodeTree
		// constructor

		HuffmanDecodeTree huff_tree = new HuffmanDecodeTree(lenArr);

		InternalHuffmanNode canonical_root = huff_tree.getRoot();

		System.out.println("entropy " + entropyCalc);

		// If all went well, tree should be full.
		assert canonical_root.isFull();

		// Create code map that encoder will use for encoding

		// Walk down canonical tree forming code strings as you did before and
		// insert into map.

	}

	public String getCode(int symbol) {
		return _code_map.get(symbol);
	}

	public void encode(int symbol, OutputStreamBitSink bit_sink) throws IOException {
		bit_sink.write(_code_map.get(symbol));
	}

	public void recurTrav(HuffmanNode trav, String bitPath, Map<Integer, String> cmap) {
		Double entropyCalc = 0.0;
		if (trav.isLeaf() == false) {
			InternalHuffmanNode tracer = (InternalHuffmanNode) trav;
			recurTrav(tracer.lc, bitPath + "0", cmap);
			recurTrav(tracer.rc, bitPath + "1", cmap);
		} else if (trav.isLeaf() == true) {
			cmap.put(trav.symbol(), bitPath);
			System.out.println("symbol: " + trav.symbol() + " path: " + bitPath + " pathlength: "
					+ cmap.get(trav.symbol()).length());
			entropyCalc += (((((double) trav.count()) / ((double) 574992.00)) * cmap.get(trav.symbol()).length()));
		}
		System.out.println("ec: " + entropyCalc);
	}

}
