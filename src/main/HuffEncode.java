package main;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import io.OutputStreamBitSink;

public class HuffEncode {

	public static void main(String[] args) throws IOException {
		String input_file_name = "/Users/aleccritser/Downloads/comptest.txt";
		String output_file_name = "/Users/aleccritser/Downloads/entropytest.txt";

		FileInputStream fis = new FileInputStream(input_file_name);

		int[] symbol_counts = new int[256];
		for (int i = 0; i < 256; i++) {
			symbol_counts[i] = 0;
		}
		int num_symbols = 0;

		// Read in each symbol (i.e. byte) of input file and
		// update appropriate count value in symbol_counts
		// Should end up with total number of symbols
		// (i.e., length of file) as num_symbols
		int stop = fis.read();
		while (stop != -1) {
			int ascii = (int) stop;
			symbol_counts[ascii]++;
			num_symbols++;
			stop = fis.read();
		}

		System.out.println(num_symbols);
		int perc = 0;
		for (int i = 0; i < 256; i++) {
			perc += symbol_counts[i];
			System.out.println("symbol count for " + i + ": " + symbol_counts[i] + " sum count: " + perc);
		}
		System.out.println("Sum Count: " + perc);
		String yeet = Integer.toBinaryString(perc);
		System.out.println(yeet);

		// Close input file
		fis.close();

		// Create array of symbol values

		int[] symbols = new int[256];
		for (int i = 0; i < 256; i++) {
			symbols[i] = i;
		}

		// Create encoder using symbols and their associated counts from file.

		HuffmanEncoder encoder = new HuffmanEncoder(symbols, symbol_counts);

		// Open output stream.
		FileOutputStream fos = new FileOutputStream(output_file_name);
		OutputStreamBitSink bit_sink = new OutputStreamBitSink(fos);

		// Write out code lengths for each symbol as 8 bit value to output file.
		for (int i = 0; i < 256; i++) {
			int len = (encoder.getCode(i).length());
			String output = Integer.toBinaryString(len);
			if (output.length() < 8) {
				while (output.length() != 8) {
					output = "0" + output;

				}
			}
			System.out.println(len);
			char[] writeArr = output.toCharArray();
			bit_sink.write(output);
		}

		// Write out total number of symbols as 32 bit value.
		int totalCount = num_symbols;

		String numSymStr = Integer.toBinaryString(totalCount);

		if (numSymStr.length() < 32) {
			while (numSymStr.length() != 32) {
				numSymStr = "0" + numSymStr;

			}
		}
		System.out.println(numSymStr);
		bit_sink.write(numSymStr);

		// Reopen input file.
		fis = new FileInputStream(input_file_name);

		int stopper = fis.read();
		int y = 0;
		while (y < num_symbols) {
			String iter = encoder.getCode(stopper);
			bit_sink.write(iter);

			stopper = fis.read();
			y++;
			// System.out.println(encoder.getCode(stopper));

		}

		// Go through input file, read each symbol (i.e. byte),
		// look up code using encoder.getCode() and write code
		// out to output file.

		// Pad output to next word.
		bit_sink.padToWord();

		// Close files.
		fis.close();
		fos.close();
	}
}
