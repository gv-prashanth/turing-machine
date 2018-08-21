package com.vadrin.turingmachine.models;

public class ActionRow {

	private int initialState;
	private char initialSymbol;
	private int finalState;
	private char finalSymbol;
	private char moveTo;

	public int getInitialState() {
		return initialState;
	}

	public char getInitialSymbol() {
		return initialSymbol;
	}

	public int getFinalState() {
		return finalState;
	}

	public char getFinalSymbol() {
		return finalSymbol;
	}

	public char getMoveTo() {
		return moveTo;
	}

	public ActionRow(int initialState, char initialSymbol, int finalState, char finalSymbol, char moveTo) {
		super();
		this.finalState = finalState;
		this.finalSymbol = finalSymbol;
		this.initialState = initialState;
		this.initialSymbol = initialSymbol;
		this.moveTo = moveTo;
	}

}
