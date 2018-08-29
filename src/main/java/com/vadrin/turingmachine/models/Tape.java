package com.vadrin.turingmachine.models;

public class Tape {

	private char[] symbols;

	public Tape(int size) {
		super();
		char[] temp = new char[size];
		for (int i = 0; i < temp.length; i++) {
			temp[i] = ' ';
		}
		this.symbols = temp;
	}

	public char symbolAt(int position) {
		return symbols[position];
	}

	public void writeSymbol(int position, char setcharacter) {
		symbols[position] = setcharacter;
	}

	public int size() {
		return symbols.length;
	}

	public String getSymbols() {
		String toReturn = "";
		for (int i = 0; i < size(); i++) {
			if (symbols[i] == ' ') {
				toReturn = toReturn + " ";
			} else {
				toReturn = toReturn + symbolAt(i);
			}
		}
		return toReturn;
	}

}
