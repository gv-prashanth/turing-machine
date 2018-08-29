package com.vadrin.turingmachine.models;

public class ActionTable {

	private ActionRow[] actionRows;

	public ActionTable(ActionRow[] actionRows) {
		super();
		this.actionRows = actionRows;
	}

	public int size() {
		return actionRows.length;
	}

	public ActionRow fetch(int rownumber) {
		return actionRows[rownumber];
	}

	public ActionRow[] getActionRows() {
		return actionRows;
	}

}
