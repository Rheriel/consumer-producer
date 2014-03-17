package org.tictactoe.game.test;

import org.junit.Assert;
import org.junit.Test;
import org.tictactoe.game.NotValidMoveException;
import org.tictactoe.game.TicTacToeGame;
import org.tictactoe.game.TicTacToeGameStatus;

public class TicTacToeGameTest {

	@Test(expected=NotValidMoveException.class)
	public void testMoveAlreadyDone() throws NotValidMoveException {
		
		final int tableSize = 3;
		
		final TicTacToeGame ticTacToeGame = new TicTacToeGame(tableSize);
		
		ticTacToeGame.move(0, 1, "X");
		
		ticTacToeGame.move(0, 1, "O");
		
	}
	
	@Test(expected=NotValidMoveException.class)
	public void testInvalidMark() throws NotValidMoveException {
		
		final int tableSize = 3;

		final TicTacToeGame ticTacToeGame = new TicTacToeGame(tableSize);

		ticTacToeGame.move(0, 1, "L");

	}
	
	@Test
	public void testLinealMoveWinner() throws NotValidMoveException {
		
		final int tableSize = 3;
		
		/*
		 *  |X|X|X|
		 *  | |O| |
	     *  |O| | |
		 */
		
		final TicTacToeGame ticTacToeGame = new TicTacToeGame(tableSize);
		
		TicTacToeGameStatus gameStatus = ticTacToeGame.move(0,0, "X");
		
		Assert.assertEquals(TicTacToeGameStatus.CONTINUE_NEXT_MOVE, gameStatus);
		
		gameStatus = ticTacToeGame.move(2,0,"O");

		Assert.assertEquals(TicTacToeGameStatus.CONTINUE_NEXT_MOVE, gameStatus);
		
		gameStatus = ticTacToeGame.move(0,1,"X");
		
		Assert.assertEquals(TicTacToeGameStatus.CONTINUE_NEXT_MOVE, gameStatus);
		
		gameStatus = ticTacToeGame.move(1,1,"O");

		Assert.assertEquals(TicTacToeGameStatus.CONTINUE_NEXT_MOVE, gameStatus);
		
		gameStatus = ticTacToeGame.move(0,2,"X");
		
		Assert.assertEquals(TicTacToeGameStatus.WINNER, gameStatus);
		
		gameStatus = ticTacToeGame.move(2,1,"O");
		
		Assert.assertEquals(TicTacToeGameStatus.GAME_ALREADY_FINISHED, gameStatus);
		
		/*
		 *  |O| |X|
		 *  |O| | |
	     *  |O| |X|
		 */
		
		ticTacToeGame.resetGame(tableSize);
		
		gameStatus = ticTacToeGame.move(0,0, "O");

		Assert.assertEquals(TicTacToeGameStatus.CONTINUE_NEXT_MOVE, gameStatus);

		gameStatus = ticTacToeGame.move(0,2, "X");

		Assert.assertEquals(TicTacToeGameStatus.CONTINUE_NEXT_MOVE, gameStatus);
		
		gameStatus = ticTacToeGame.move(1,0,"O");

		Assert.assertEquals(TicTacToeGameStatus.CONTINUE_NEXT_MOVE, gameStatus);

		gameStatus = ticTacToeGame.move(2,2, "X");

		Assert.assertEquals(TicTacToeGameStatus.CONTINUE_NEXT_MOVE, gameStatus);
		
		gameStatus = ticTacToeGame.move(2,0,"O");

		Assert.assertEquals(TicTacToeGameStatus.WINNER, gameStatus);
		
		
		/*
		 *  | |O| |
		 *  | |O| |
	     *  |X|X|X|
		 */
		
		ticTacToeGame.resetGame(tableSize);
		
		gameStatus = ticTacToeGame.move(2,0, "X");

		Assert.assertEquals(TicTacToeGameStatus.CONTINUE_NEXT_MOVE, gameStatus);

		gameStatus = ticTacToeGame.move(0,1, "O");

		Assert.assertEquals(TicTacToeGameStatus.CONTINUE_NEXT_MOVE, gameStatus);
		
		gameStatus = ticTacToeGame.move(2,1,"X");

		Assert.assertEquals(TicTacToeGameStatus.CONTINUE_NEXT_MOVE, gameStatus);
		
		gameStatus = ticTacToeGame.move(1,1, "O");

		Assert.assertEquals(TicTacToeGameStatus.CONTINUE_NEXT_MOVE, gameStatus);

		gameStatus = ticTacToeGame.move(2,2, "X");

		Assert.assertEquals(TicTacToeGameStatus.WINNER, gameStatus);
		
		/*
		 *  | | |X|
		 *  |O|O|O|
	     *  |X| |X|
		 */
		
		ticTacToeGame.resetGame(tableSize);
		
		gameStatus = ticTacToeGame.move(0,2,"X");

		Assert.assertEquals(TicTacToeGameStatus.CONTINUE_NEXT_MOVE, gameStatus);
		
		gameStatus = ticTacToeGame.move(1,2,"O");

		Assert.assertEquals(TicTacToeGameStatus.CONTINUE_NEXT_MOVE, gameStatus);
		
		gameStatus = ticTacToeGame.move(2,0,"X");

		Assert.assertEquals(TicTacToeGameStatus.CONTINUE_NEXT_MOVE, gameStatus);
		
		gameStatus = ticTacToeGame.move(1,0,"O");

		Assert.assertEquals(TicTacToeGameStatus.CONTINUE_NEXT_MOVE, gameStatus);

		gameStatus = ticTacToeGame.move(2,2, "X");

		Assert.assertEquals(TicTacToeGameStatus.CONTINUE_NEXT_MOVE, gameStatus);
		
		gameStatus = ticTacToeGame.move(1,1,"O");

		Assert.assertEquals(TicTacToeGameStatus.WINNER, gameStatus);
		
	}

	@Test
	public void testDiagonalMoveWinner() throws NotValidMoveException {

		final int tableSize = 3;

		/*
		 *  |O|X| |
		 *  |X|O| |
		 *  | | |O|
		 */

		final TicTacToeGame ticTacToeGame = new TicTacToeGame(tableSize);

		TicTacToeGameStatus gameStatus = ticTacToeGame.move(0,0, "O");

		Assert.assertEquals(TicTacToeGameStatus.CONTINUE_NEXT_MOVE, gameStatus);

		gameStatus = ticTacToeGame.move(1,0, "X");

		Assert.assertEquals(TicTacToeGameStatus.CONTINUE_NEXT_MOVE, gameStatus);
		
		gameStatus = ticTacToeGame.move(2,2, "O");

		Assert.assertEquals(TicTacToeGameStatus.CONTINUE_NEXT_MOVE, gameStatus);
		
		gameStatus = ticTacToeGame.move(0,1, "X");

		Assert.assertEquals(TicTacToeGameStatus.CONTINUE_NEXT_MOVE, gameStatus);
		
		gameStatus = ticTacToeGame.move(1,1, "O");
		
		Assert.assertEquals(TicTacToeGameStatus.WINNER, gameStatus);
		
		/*
		 * |O|X| |
		 * | |O| |
		 * | |X|O|
		 */
		ticTacToeGame.resetGame(tableSize);
		
		gameStatus = ticTacToeGame.move(1,1, "O");

		Assert.assertEquals(TicTacToeGameStatus.CONTINUE_NEXT_MOVE, gameStatus);

		gameStatus = ticTacToeGame.move(0,1, "X");

		Assert.assertEquals(TicTacToeGameStatus.CONTINUE_NEXT_MOVE, gameStatus);
		
		gameStatus = ticTacToeGame.move(0,0, "O");

		Assert.assertEquals(TicTacToeGameStatus.CONTINUE_NEXT_MOVE, gameStatus);
		
		gameStatus = ticTacToeGame.move(2,1, "X");

		Assert.assertEquals(TicTacToeGameStatus.CONTINUE_NEXT_MOVE, gameStatus);
		
		gameStatus = ticTacToeGame.move(2,2, "O");
		
		Assert.assertEquals(TicTacToeGameStatus.WINNER, gameStatus);
		
		/*
		 * |O| |X|
		 * |O|X| |
		 * |X| | |
		 */
		ticTacToeGame.resetGame(tableSize);
		
		gameStatus = ticTacToeGame.move(2,0, "X");

		Assert.assertEquals(TicTacToeGameStatus.CONTINUE_NEXT_MOVE, gameStatus);

		gameStatus = ticTacToeGame.move(0,0, "O");

		Assert.assertEquals(TicTacToeGameStatus.CONTINUE_NEXT_MOVE, gameStatus);
		
		gameStatus = ticTacToeGame.move(0,2, "X");

		Assert.assertEquals(TicTacToeGameStatus.CONTINUE_NEXT_MOVE, gameStatus);
		
		gameStatus = ticTacToeGame.move(1,0, "O");

		Assert.assertEquals(TicTacToeGameStatus.CONTINUE_NEXT_MOVE, gameStatus);
		
		gameStatus = ticTacToeGame.move(1,1, "X");
		
		Assert.assertEquals(TicTacToeGameStatus.WINNER, gameStatus);
		
		/*
		 * | |O|X|
		 * | |X| |
		 * |X|O| |
		 */
		ticTacToeGame.resetGame(tableSize);
		
		gameStatus = ticTacToeGame.move(0,2, "X");

		Assert.assertEquals(TicTacToeGameStatus.CONTINUE_NEXT_MOVE, gameStatus);

		gameStatus = ticTacToeGame.move(2,1, "O");

		Assert.assertEquals(TicTacToeGameStatus.CONTINUE_NEXT_MOVE, gameStatus);
		
		gameStatus = ticTacToeGame.move(1,1, "X");

		Assert.assertEquals(TicTacToeGameStatus.CONTINUE_NEXT_MOVE, gameStatus);
		
		gameStatus = ticTacToeGame.move(0,1, "O");

		Assert.assertEquals(TicTacToeGameStatus.CONTINUE_NEXT_MOVE, gameStatus);
		
		gameStatus = ticTacToeGame.move(2,0, "X");
		
		Assert.assertEquals(TicTacToeGameStatus.WINNER, gameStatus);
		
		/*
		 * |X| |O|
		 * |X|O|X|
		 * |O| |O|
		 */
		ticTacToeGame.resetGame(tableSize);
		
		gameStatus = ticTacToeGame.move(2,0, "O");

		Assert.assertEquals(TicTacToeGameStatus.CONTINUE_NEXT_MOVE, gameStatus);

		gameStatus = ticTacToeGame.move(1,0, "X");

		Assert.assertEquals(TicTacToeGameStatus.CONTINUE_NEXT_MOVE, gameStatus);
		
		gameStatus = ticTacToeGame.move(1,1, "O");

		Assert.assertEquals(TicTacToeGameStatus.CONTINUE_NEXT_MOVE, gameStatus);
		
		gameStatus = ticTacToeGame.move(0,0, "X");

		Assert.assertEquals(TicTacToeGameStatus.CONTINUE_NEXT_MOVE, gameStatus);
		
		gameStatus = ticTacToeGame.move(2,2, "O");
		
		Assert.assertEquals(TicTacToeGameStatus.CONTINUE_NEXT_MOVE, gameStatus);
		
		gameStatus = ticTacToeGame.move(1,2, "X");

		Assert.assertEquals(TicTacToeGameStatus.CONTINUE_NEXT_MOVE, gameStatus);
		
		gameStatus = ticTacToeGame.move(0,2, "O");
		
		Assert.assertEquals(TicTacToeGameStatus.WINNER, gameStatus);
		
	}
	
	@Test
	public void testDrawGame() throws NotValidMoveException {
	
		final int tableSize = 3;
		
		/*
		 * |O-|X-|O-|
		 * |X-|X-|O-|
		 * |O-|O-|X-|
		 */
		
		final TicTacToeGame ticTacToeGame = new TicTacToeGame(tableSize);

		TicTacToeGameStatus gameStatus = ticTacToeGame.move(0,0, "O");

		Assert.assertEquals(TicTacToeGameStatus.CONTINUE_NEXT_MOVE, gameStatus);

		gameStatus = ticTacToeGame.move(0,1, "X");

		Assert.assertEquals(TicTacToeGameStatus.CONTINUE_NEXT_MOVE, gameStatus);
		
		gameStatus = ticTacToeGame.move(0,2, "O");

		Assert.assertEquals(TicTacToeGameStatus.CONTINUE_NEXT_MOVE, gameStatus);
		
		gameStatus = ticTacToeGame.move(1,1, "X");

		Assert.assertEquals(TicTacToeGameStatus.CONTINUE_NEXT_MOVE, gameStatus);
		
		gameStatus = ticTacToeGame.move(1,2, "O");

		Assert.assertEquals(TicTacToeGameStatus.CONTINUE_NEXT_MOVE, gameStatus);
		
		gameStatus = ticTacToeGame.move(2,2, "X");

		Assert.assertEquals(TicTacToeGameStatus.CONTINUE_NEXT_MOVE, gameStatus);
		
		gameStatus = ticTacToeGame.move(2,1, "O");

		Assert.assertEquals(TicTacToeGameStatus.CONTINUE_NEXT_MOVE, gameStatus);
		
		gameStatus = ticTacToeGame.move(1,0, "X");

		Assert.assertEquals(TicTacToeGameStatus.CONTINUE_NEXT_MOVE, gameStatus);
		
		gameStatus = ticTacToeGame.move(2,0, "O");

		Assert.assertEquals(TicTacToeGameStatus.DRAW, gameStatus);
		
	}
}
