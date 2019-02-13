package main;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.InputStreamBitSource;
import io.InsufficientBitsLeftException;

public class HuffDecode {

	public static void main(String[] args) throws InsufficientBitsLeftException, IOException {
		String input_file_name = "/Users/aleccritser/Downloads/recomp.txt";
		String output_file_name = "/Users/aleccritser/Downloads/comptest.txt";

		FileInputStream fis = new FileInputStream(input_file_name);

		InputStreamBitSource bit_source = new InputStreamBitSource(fis);

		List<SymbolWithCodeLength> symbols_with_length = new ArrayList<SymbolWithCodeLength>();

		// Read in huffman code lengths from input file and associate them with
		// each symbol as a
		// SymbolWithCodeLength object and add to the list symbols_with_length.

		for (int i = 0; i < 256; i++) {
			SymbolWithCodeLength agc = new SymbolWithCodeLength(i, bit_source.next(8));
			System.out.println(agc.CL);
			symbols_with_length.add(agc);
		}

		// Then sort they symbols.

		Collections.sort(symbols_with_length, new SortByCL());

		SymbolWithCodeLength[] huff = new SymbolWithCodeLength[256];
		int count = 0;
		for (SymbolWithCodeLength s : symbols_with_length) {
			huff[count] = s;
			System.out.println(s.value() + ", " + s.codeLength());
			count++;
		}

		int hc = 000;
		for (int i = 0; i < 256; i++) {
			huff[i].huffCode = hc;
			if (i != 255) {
				hc = (hc + 1) << ((huff[i + 1].CL) - (huff[i].CL));
				String binCheck = Integer.toBinaryString(huff[i].huffCode);
				String z = "0";
				if (binCheck.length() < huff[i].CL) {
					for (int j = 0; j <= (huff[i].CL - binCheck.length()); j++) {
						binCheck = z + binCheck;
					}
				}
				huff[i].huffCodeArr = binCheck.toCharArray();

				System.out.print(binCheck);

				System.out.println();
			} else {
				hc = (hc + 1);
				String binCheck = Integer.toBinaryString(huff[i].huffCode);
				String z = "0";
				if (binCheck.length() < huff[i].CL) {
					for (int j = 0; j <= (huff[i].CL - binCheck.length()); j++) {
						binCheck = z + binCheck;
					}
				}
				huff[i].huffCodeArr = binCheck.toCharArray();

				System.out.print(binCheck);

				System.out.println();
			}
		}

		// Now construct the canonical huffman tree

		HuffmanDecodeTree huff_tree = new HuffmanDecodeTree(huff);
		int i = 0;
		while (i < 256) {
			huff_tree.insert(huff[i]);
			i++;
		}

		int num_symbols = bit_source.next(32);

		System.out.println(num_symbols);

		// Read in the next 32 bits from the input file the number of symbols

		try {

			// Open up output file.

			FileOutputStream fos = new FileOutputStream(output_file_name);

			for (int t = 0; t < num_symbols; t++) {
				// Decode next symbol using huff_tree and write out to file.
				int cr = huff_tree.decode(bit_source);
				System.out.print((char) cr);
				fos.write(cr);
			}

			// Flush output and close files.

			fos.flush();
			fos.close();
			fis.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
