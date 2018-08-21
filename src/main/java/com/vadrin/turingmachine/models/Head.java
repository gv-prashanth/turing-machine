package com.vadrin.turingmachine.models;

import com.vadrin.turingmachine.commons.InsufficientTapeException;

public class Head {

	private int position;

	public Head() {
		super();
		this.position = 0;
	}

	public char readTape(Tape tape) {
		return tape.getSymbolAt(position);
	}

	public void moveHead(Tape tape, char direction) throws InsufficientTapeException {
		if (direction == 'L') {
			position--;
		}
		if (direction == 'R') {
			position++;
		}
		if (position >= (tape.getTapeSize())) {
			throw new InsufficientTapeException();
		}
	}

	public void writeToTape(Tape tape, char chartowrite) {
		tape.setSymbolAt(position, chartowrite);
	}

	public int getPosition() {
		return position;
	}

}
