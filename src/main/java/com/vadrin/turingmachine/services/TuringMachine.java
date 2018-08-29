package com.vadrin.turingmachine.services;

import com.vadrin.turingmachine.commons.InsufficientTapeException;
import com.vadrin.turingmachine.models.ActionRow;
import com.vadrin.turingmachine.models.ActionTable;
import com.vadrin.turingmachine.models.Head;
import com.vadrin.turingmachine.models.Tape;

public class TuringMachine {

	private Tape tape;
	private int state;
	private Head head;
	private ActionTable actionTable;

	public TuringMachine(ActionTable actionTable, Tape tape) {
		super();
		this.actionTable = actionTable;
		this.head = new Head();
		this.state = 0;
		this.tape = tape;
	}

	public void computeSingleStep() throws InsufficientTapeException {
		int currentstate = state;
		char currentsymbol = head.readTape(tape);
		for (int i = 0; i < actionTable.size(); i++) {
			ActionRow currentactionrow = actionTable.fetch(i);
			if ((currentactionrow.getInitialState() == currentstate)
					&& (currentactionrow.getInitialSymbol() == currentsymbol)) {
				state = currentactionrow.getFinalState();
				head.writeToTape(tape, currentactionrow.getFinalSymbol());
				head.moveHead(tape, currentactionrow.getMoveTo());
			}
		}
	}

	public int headIndex() {
		return this.head.getPosition();
	}

	public ActionTable getActionTable() {
		return actionTable;
	}

	public Tape getTape() {
		return tape;
	}

	public int getState() {
		return state;
	}

	public Head getHead() {
		return head;
	}

}
