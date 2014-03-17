package org.tictactoe.game;

public class NotValidMoveException extends Exception {

	/**
	 * Serial id for this.
	 */
	private static final long serialVersionUID = 7382978300812884634L;

	public NotValidMoveException(final String message) {
		super(message);
	}
	
}
