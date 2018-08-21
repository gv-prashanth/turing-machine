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

	public char getSymbolAt(int position) {
		return symbols[position];
	}

	public void setSymbolAt(int position, char setcharacter) {
		symbols[position] = setcharacter;
	}

	public int getTapeSize() {
		return symbols.length;
	}
}
