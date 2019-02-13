package main;

/* SymbolWithCodeLength
 * 
 * Class that encapsulates a symbol value along with the length of the code
 * associated with that symbol. Used to build the canonical huffman tree.
 * Implements Comparable in order to sort first by code length and then by symbol value.
 */

public class SymbolWithCodeLength implements Comparable<SymbolWithCodeLength> {

	// Instance fields should be declared here.
	int CL;
	int val;
	int huffCode;
	char[] huffCodeArr;
	String bitPath;

	// Constructor
	public SymbolWithCodeLength(int value, int code_length) {
		CL = code_length;
		val = value;
	}

	// codeLength() should return the code length associated with this symbol
	public int codeLength() {
		return CL;
		// Needs implementation
	}

	// value() returns the symbol value of the symbol
	public int value() {
		return val;
		// Needs implementation
	}

	// compareTo implements the Comparable interface
	// First compare by code length and then by symbol value.
	public int compareTo(SymbolWithCodeLength other) {
		return 0;
		// Needs implementation
	}
}
