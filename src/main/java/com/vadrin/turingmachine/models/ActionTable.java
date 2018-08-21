package com.vadrin.turingmachine.models;

public class ActionTable {

	private ActionRow[] actionRow;

	public ActionTable(ActionRow[] actionRow) {
		super();
		this.actionRow = actionRow;
	}

	public int getNumberOfRows() {
		return actionRow.length;
	}

	public ActionRow getActionRow(int rownumber) {
		return actionRow[rownumber];
	}
}
