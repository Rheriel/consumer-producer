package org.tictactoe.game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Tic-tac-toe table game object representation.
 * 
 * @author Leo Gutierrez.
 *
 */
public class TicTacToeGame {

	/**
	 * Reference to an empty box within tic-tac-toe table.
	 */
	private static final String EMPTY_BOX = " ";

	/**
	 * Reference to the tic-tac-toe table.
	 */
	private String [][] ticTacToeTable;
	
	/**
	 * Reference to the marks supported to use within tic-tac-toe table.
	 */
	private List<String> marksSupported = 
			new ArrayList<String>(Arrays.asList("X","O"));
	
	/**
	 * Reference to the previous mark used for next move validation.
	 */
	private String previousMarkUsed;
	
	/**
	 * Reference to the total count of move to verify for draws.
	 */
	private int moveCount;
	
	/**
	 * Reference to know if the game is already finished.
	 */
	private boolean gameFinished;
	
	/**
	 * The constructor.
	 * 
	 * @param tableSize Table size for the game.
	 * @throws IllegalArgumentException If the table size is invalid, 3 or less.
	 */
	public TicTacToeGame(final int tableSize) throws IllegalArgumentException {
		
		initTicTacToeTable(tableSize);
		
	}

	/**
	 * Resets the tic-tac-toe table for a new game.
	 * 
	 * @param tableSize Table size for the new game.
	 */
	public void resetGame(final int tableSize) {

		initTicTacToeTable(tableSize);

		previousMarkUsed = null;
		
		moveCount = 0;
		
		gameFinished = false;
		
	}
	
	/**
	 * Initializes the tic-tac-toe-table with the given table size.
	 * 
	 * @param tableSize Table size.
	 * @throws IllegalArgumentException If the table size is invalid, 2 or less.
	 */
	private void initTicTacToeTable(final int tableSize) throws IllegalArgumentException {
		
		if (tableSize < 3) {
			throw new IllegalArgumentException(
					"Invalid table size, should be 3 or above");
		}
		
		ticTacToeTable = new String[tableSize][tableSize];	
		
		for (String [] tableLine: ticTacToeTable) {
			
			Arrays.fill(tableLine, EMPTY_BOX);
			
		}
		
	}
	
	/**
	 * Player's move on the tic-tac game table.
	 * 
	 * @param xAxisMove X coordinate for the move.
	 * @param yAxisMove Y coordinate for the move.
	 * @param mark Mark to place in the move.
	 * @return Either TicTacToeGameStatus.GAME_ALREADY_FINISHED, TicTacToeGameStatus.DRAW, 
	 *         TicTacToeGameStatus.CONTINUE_NEXT_MOVE or TicTacToeGameStatus.WINNER. 
	 * @throws NotValidMoveException If the move is out of table range, the 
	 *                               move is already taken or the mark used 
	 *                               is not the expected.
	 */
	public TicTacToeGameStatus move(final int xAxisMove, final int yAxisMove, 
			final String mark) throws NotValidMoveException {
		
		validateIfInvalidMove(xAxisMove, yAxisMove, mark);
		
		if (gameFinished) {
			
			return TicTacToeGameStatus.GAME_ALREADY_FINISHED;
			
		} else {
			
			placeMoveInTable(xAxisMove, yAxisMove, mark);

			final TicTacToeGameStatus gameStatus = 
					updateGameStatus(xAxisMove, yAxisMove, mark);

			return gameStatus;
			
		}
		
	}

	/**
	 * Updates the game status for the next move.
	 * 
	 * @param xAxisMove X coordinate of the move.
	 * @param yAxisMove Y coordinate of the move.
	 * @param mark Mark used for the move.
	 * @return Either TicTacToeGameStatus.DRAW, 
	 *         TicTacToeGameStatus.CONTINUE_NEXT_MOVE or TicTacToeGameStatus.WINNER.
	 */
	private TicTacToeGameStatus updateGameStatus(final int xAxisMove,
			final int yAxisMove, final String mark) {
		
		final boolean moveOfTheWin = 
				verifyIfWinner(xAxisMove, yAxisMove, mark);

		if (moveOfTheWin) {
			
			gameFinished = true;
			
			return TicTacToeGameStatus.WINNER;
			
		} else if (isADraw()) {

			gameFinished = true;

			return TicTacToeGameStatus.DRAW;

		}

		return TicTacToeGameStatus.CONTINUE_NEXT_MOVE;
		
	}

	/**
	 * Places the move in the table.
	 * 
	 * @param xAxisMove X coordinate for the move.
	 * @param yAxisMove Y coordinate for the move.
	 * @param mark Mark used for the move.
	 */
	private void placeMoveInTable(int xAxisMove, int yAxisMove, String mark) {
		
		ticTacToeTable[xAxisMove][yAxisMove] = mark;
		
		previousMarkUsed = mark;
		
		moveCount++;
		
	}

	/**
	 * Verifies if the game is a draw (all boxes already used).
	 * 
	 * @return True if it is a draw; False, otherwise.
	 */
	private boolean isADraw() {
		
		return moveCount == 
				(ticTacToeTable.length*ticTacToeTable.length);
		
	}
	
	/**
	 * Verifies if the move recent placed is the winner one.
	 * 
	 * @param xAxisMove X coordinate for the move.
	 * @param yAxisMove Y coordinate for the move.
	 * @param mark Mark used for the move.
	 * @return True if it is the winner; False otherwise.
	 */
	private boolean verifyIfWinner(int xAxisMove, int yAxisMove, String mark) {
		
		boolean winner = false;
		
		if (verifyWinnerLinear(xAxisMove, yAxisMove, mark)) {
			
			winner = true;
			
		} else if (verifyWinnerDiagonal(xAxisMove, yAxisMove, mark)) {
			
			winner = true;
			
		}
		
		return winner;
		
	}
	
	/**
	 * Verifies if the given move is the winner verifying the linear combinations.
	 * 
	 * @param xAxisMove X coordinate for the move.
	 * @param yAxisMove Y coordinate for the move.
	 * @param mark Mark used for the move.
	 * @return True if it is the winner; False otherwise.
	 */
	private boolean verifyWinnerLinear(final int xAxisMove, 
			final int yAxisMove, final String mark) {
		
		boolean winnerLineal = false;
		
		final int initialYCoordinate = 0;
		final int initialXCoordinate = 0;
		
		if (verifyWinnerXLinear(xAxisMove, initialYCoordinate, mark)) {

			winnerLineal = true;

		} else if (verifyWinnerYLinear(initialXCoordinate, yAxisMove, mark)) {

			winnerLineal = true;
		}
		
		return winnerLineal;
	}

	/**
	 * Verifies if the given move is the winner verifying the diagonal combinations.
	 * 
	 * @param xAxisMove X coordinate for the move.
	 * @param yAxisMove Y coordinate for the move.
	 * @param mark Mark used for the move.
	 * @return True if it is the winner; False otherwise.
	 */
	private boolean verifyWinnerDiagonal(int xAxisMove, int yAxisMove, String mark) {
		
		boolean winnerDiagonal = false;
		
		final int initialXCoordinate = 0;
		final int initialYCoordinate = 0;
		final int latestXCoordinate = ticTacToeTable.length - 1;
		
		if (isDiagonalMove(xAxisMove, yAxisMove)) {
			
			if (verifyWinnerCrossToLeft(initialXCoordinate, initialYCoordinate, mark)) {
				winnerDiagonal = true;
			} else if (verifyWinnerCrossToRight(latestXCoordinate, initialYCoordinate, mark)) {
				winnerDiagonal = true;
			}
			
		}
		
		return winnerDiagonal;
		
	}
	
	/**
	 * Verifies if the given move is the winner verifying the diagonal right 
	 * to left combination.
	 * 
	 * @param xAxisMove X coordinate for the move.
	 * @param yAxisMove Y coordinate for the move.
	 * @param mark Mark used for the move.
	 * @return True if it is the winner; False otherwise.
	 */
	private boolean verifyWinnerCrossToRight(int xAxisMove, int yAxisMove, 
			final String mark) {
		
		if (EMPTY_BOX.equals(ticTacToeTable[xAxisMove][yAxisMove])) {

			return false;

		} else if (ticTacToeTable[xAxisMove][yAxisMove].equalsIgnoreCase(mark)){

			final int nextXLinealPosition = --xAxisMove;
			final int nextYLinealPosition = ++yAxisMove;
			
			if ((nextXLinealPosition < 0)
					|| (nextYLinealPosition >= ticTacToeTable.length)) {
				return true;
			} else {
				return verifyWinnerCrossToRight(nextXLinealPosition, 
						nextYLinealPosition, mark);
			}

		} else {

			return false;

		} 
		
	}
	
	/**
	 * Verifies if the given move is the winner verifying the diagonal left 
	 * to right combination.
	 * 
	 * @param xAxisMove X coordinate for the move.
	 * @param yAxisMove Y coordinate for the move.
	 * @param mark Mark used for the move.
	 * @return True if it is the winner; False otherwise.
	 */
	private boolean verifyWinnerCrossToLeft(int xAxisMove, int yAxisMove, 
			final String mark) {
		
		if (EMPTY_BOX.equals(ticTacToeTable[xAxisMove][yAxisMove])) {

			return false;

		} else if (ticTacToeTable[xAxisMove][yAxisMove].equalsIgnoreCase(mark)){

			int nextYLinealPosition = ++yAxisMove;
			int nextXLinealPosition = ++xAxisMove;
			
			if ((nextYLinealPosition >= ticTacToeTable.length)
					|| (nextXLinealPosition >= ticTacToeTable.length)) {
				return true;
			} else {
				return verifyWinnerCrossToLeft(nextXLinealPosition, 
						nextYLinealPosition, mark);
			}

		} else {

			return false;

		}
		
	}
	
	/**
	 * Verifies if the given move can have diagonal combinations.
	 * 
	 * @param xAxisMove X coordinate for the move.
	 * @param yAxisMove Y coordinate for the move.
	 * @return True if the move can have diagonal combinations; False otherwise.
	 */
	private boolean isDiagonalMove(int xAxisMove, int yAxisMove) {
		
		boolean equalAxis = false;
		
		final int axisDifference = xAxisMove - yAxisMove;
		
		final int equalsAxis = 0;

		final int positiveDiagonalMove = ticTacToeTable.length - 1;
		final int negativeDiagonalMove = 1 - ticTacToeTable.length;
		
		if ((axisDifference == equalsAxis) 
				|| (axisDifference == positiveDiagonalMove) 
				|| (axisDifference == negativeDiagonalMove)) {
			
			equalAxis = true;
			
		}
		
		return equalAxis;
		
	}
	
	/**
	 * Verifies if the given move is the winner verifying the linear 
	 * combinations for X coordinate.
	 * 
	 * @param xAxisMove X coordinate for the move.
	 * @param yAxisMove Y coordinate for the move.
	 * @param mark Mark used for the move.
	 * @return True if it is the winner; False otherwise.
	 */
	private boolean verifyWinnerXLinear(final int xAxisMove, int yAxisMove, 
			final String mark) {
		
		if (EMPTY_BOX.equals(ticTacToeTable[xAxisMove][yAxisMove])) {
			
			return false;
			
		} else if (ticTacToeTable[xAxisMove][yAxisMove].equalsIgnoreCase(mark)){
			
			int nextYLinealPosition = ++yAxisMove;
			
			if (nextYLinealPosition >= ticTacToeTable.length) {
				return true;
			} else {
				return verifyWinnerXLinear(xAxisMove, nextYLinealPosition, mark);
			}
			
		} else {
			
			return false;
			
		}
		
	}
	
	/**
	 * Verifies if the given move is the winner verifying the linear 
	 * combinations for Y coordinate.
	 * 
	 * @param xAxisMove X coordinate for the move.
	 * @param yAxisMove Y coordinate for the move.
	 * @param mark Mark used for the move.
	 * @return True if it is the winner; False otherwise.
	 */
	public boolean verifyWinnerYLinear(int xAxisMove, final int yAxisMove, 
			final String mark) {
		
		if (EMPTY_BOX.equals(ticTacToeTable[xAxisMove][yAxisMove])) {
			
			return false;
			
		} else if (ticTacToeTable[xAxisMove][yAxisMove].equalsIgnoreCase(mark)) {
		
			int nextXLinealPosition = ++xAxisMove;
			
			if (nextXLinealPosition >= ticTacToeTable.length) {
				return true;
			} else {
				return verifyWinnerYLinear(nextXLinealPosition, yAxisMove, mark);
			}
			
		} else {
			return false;
		}
		
	}
	
	/**
	 * Verifies if the given move is valid or not. If it is not valid, it 
	 * throws NotValidMoveException.
	 * 
	 * @param xAxisMove X coordinate for the move.
	 * @param yAxisMove Y coordinate for the move.
	 * @param mark Mark used for the move.
	 * @throws NotValidMoveException If the move is out of range, if already 
	 *                               done or the mark is not the expected.
	 */
	private void validateIfInvalidMove(int x, int y, String mark)
			throws NotValidMoveException {
		
		verifyIfOutOfRange(x, y);
		
		verifyIfMoveAlreadyDone(x, y);
		
		verifyIfCorrectMark(mark);
		
	}

	/**
	 * Verifies if the given mark in the move is the expected one.
	 * 
	 * @param mark Mark to validate.
	 * @throws NotValidMoveException If it is not a mark expected.
	 */
	private void verifyIfCorrectMark(String mark) throws NotValidMoveException {
		
		if (!marksSupported.contains(mark)) {
			
			throw new NotValidMoveException("Not valid mark '" + mark + "'");
			
		}
		
		if (previousMarkUsed != null) {
			
			if (previousMarkUsed.equalsIgnoreCase(mark)) {
				
				String nextMarkExpected = null;
				
				final int nextMarkExpectedIndex = marksSupported.indexOf(mark) + 1;
				
				if (nextMarkExpectedIndex == marksSupported.size()) {
					
					final int initialPosition = 0;
					nextMarkExpected = marksSupported.get(initialPosition);
					
				} else {
					
					nextMarkExpected = marksSupported.get(nextMarkExpectedIndex);
					
				}
				
				throw new NotValidMoveException("Invalid mark, '" 
						+ nextMarkExpected + "' was expected instead");
				
			}
			
		}
		
	}

	/**
	 * Verifies if the given move is already taken.
	 * 
	 * @param xAxisMove X coordinate of the move.
	 * @param yAxisMove Y coordinate of the move.
	 * @throws NotValidMoveException If the move is already taken.
	 */
	private void verifyIfMoveAlreadyDone(int xAxisMove, int yAxisMove)
			throws NotValidMoveException {
		
		if (!EMPTY_BOX.equals(ticTacToeTable[xAxisMove][yAxisMove])) {
			
			throw new NotValidMoveException("Position already taken");
			
		}
		
	}

	/**
	 * Verifies if the given move is out of range of the table.
	 * 
	 * @param xAxisMove xAxisMove X coordinate of the move.
	 * @param yAxisMove Y coordinate of the move.
	 * @throws NotValidMoveException If the move is out of range.
	 */
	private void verifyIfOutOfRange(int xAxisMove, int yAxisMove) throws NotValidMoveException {
		 
		final int ticTacToeTableLowestRange = 0;
		final int ticTacToeTableMaxRange = ticTacToeTable.length;
		
		if (((xAxisMove < ticTacToeTableLowestRange) || (xAxisMove >= ticTacToeTableMaxRange)) || 
				((yAxisMove < ticTacToeTableLowestRange) || (yAxisMove >= ticTacToeTableMaxRange))) {
			
			throw new NotValidMoveException("Move out of range");
			
		}
		
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		
		final StringBuilder string = new StringBuilder();
		
		for (String [] lineal: ticTacToeTable) {
			
			string.append(Arrays.toString(lineal));
			string.append("\n");
			
		}
		
		return string.toString();
		
	}
	
}
