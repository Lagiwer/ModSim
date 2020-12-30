package de.lingen.hs.modsim;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class TicTacToe
{
	private static final int MAXX = 3;		
	private static final int MAXY = 3;
	private static final int DEC_POS_ENC = 10;		// Constraint MAX(MAXX,MAXY) must be < DEC_POC_ENC in Magnitude in decimal system!

	private int[][] board = null;							// X = 1, O = -1, empty = 0
	private int activePlayer = 1;
	private int moveDepth = 0;
	private int bestMove = 0;

	public TicTacToe()
	{
		board = new int[MAXX][MAXY];
		for (int x = 0; x < board.length; x++)
			for (int y = 0; y < board[x].length; y++)
				board[x][y] = 0; 
	}

	private int countOccupiedFields(int[][] board)
	{
		int occupiedFields = 0;

		for (int x = 0; x < board.length; x++)
			for (int y = 0; y < board[x].length; y++)
				if (board[x][y] != 0)
					occupiedFields++;

		return occupiedFields;
	}

	private int[] computeNonOccupiedFields(int[][] board)
	{
		int[] openPositions = new int[MAXX * MAXY - countOccupiedFields(board)];
		int moves = 0;

		for (int x = 0; x < board.length; x++)
			for (int y = 0; y < board[x].length; y++)
				if (board[x][y] == 0)
					openPositions[moves++] = x * DEC_POS_ENC + y;

		return openPositions;
	}

	private boolean move(int x, int y, int stone)
	{
		// valid move?
		if ((x >= 0 && x < MAXX)
				&& (y >= 0 && y < MAXY)
				&& board[x][y] == 0)
		{
			board[x][y] = stone;
			activePlayer = -activePlayer;
			moveDepth++;
			return true;
		}
		return false;
	}

	private void writeBoardCoordinates()
	{
		for (int x = 0; x < MAXX; x++)
			System.out.print((x + 1) + "*");
		System.out.println("*");

	}

	private void showBoard()
	{
		System.out.print("\n**");
		writeBoardCoordinates();

		for (int y = 0; y < MAXY; y++)
		{
			System.out.print((y+1) + " ");	// print line number

			for (int x = 0; x < MAXX; x++)
			{
				int bp = board[x][y];		// boardposition

				if (bp == -1)
					System.out.print("O");
				else if (bp == 1)
					System.out.print("X");
				else
					System.out.print("-");

				System.out.print(" ");
			}

			System.out.println((y+1) + " ");	// print line number
		}

		System.out.print("**");
		writeBoardCoordinates();
		System.out.println("\n");
	}

	/**
	 * Evaluates a squared(!) ...
	 * @param (given) board
	 * @return 1 = X wins, -1 = O wins, 0 = draw
	 */
	private int evaluateBoard(int[][] board)
	{
		for (int i = 0; i < MAXX; i++)
		{
			// vertical
			int sumY = board[i][0] + board[i][1] + board[i][2];
			// horizontal
			int sumX = board[0][i] + board[1][i] + board[2][i];

			if (sumY == MAXX || sumX == MAXX)
				return 1;
			if (sumY == -MAXX || sumX == -MAXX)
				return -1;
		}

		// diagonal
		int sumD1 = board[0][0] + board[1][1] + board[2][2];
		int sumD2 = board[0][2] + board[1][1] + board[2][0];

		if (sumD1 == MAXX || sumD2 == MAXX)
			return 1;
		if (sumD1 == -MAXX || sumD2 == -MAXX)
			return -1;

		return 0;
	}

	private boolean checkIfGameEnded()
	{
		int boardEvaluation = evaluateBoard(board);
		if (boardEvaluation != 0 
				|| countOccupiedFields(board) == MAXX * MAXY)
		{
			System.out.println("End!");
			switch (boardEvaluation)
			{
				case 0 : System.out.println("It's a tie.");
				break;
				case 1 : System.out.println("Player X wins.");
				break;
				case -1 : System.out.println("Player O wins.");
				break;
			}
			showBoard();
			return true;
		}
		return false;
	}

	/**
	 * moves (virtually) a stone of a given player filling the next possible
	 * (empty) position and calls itself recursively for the next competitor move. 
	 * @param board
	 * @param depth
	 * @param player
	 * @return
	 */
	
	private int minMax(int[][] board, int depth, int player)
	{
		int boardEvaluationOwnMove = evaluateBoard(board);
		if (boardEvaluationOwnMove != 0 								// one player has won?
				|| countOccupiedFields(board) == MAXX * MAXY)	// no more possible moves?
			return boardEvaluationOwnMove;
		
		int[] openPositions = computeNonOccupiedFields(board);
		int bestMoveEvaluationSoFar = -player * 2;
		
		for (int i = 0; i < openPositions.length; i++)
		{
			board[openPositions[i] / DEC_POS_ENC][openPositions[i] % DEC_POS_ENC] = player;	// MOVE
			int boardEvaluationNextMoves = minMax(board, depth + 1, -player);
			
			// store best move so far
			if (player == -1 && boardEvaluationNextMoves < bestMoveEvaluationSoFar
				|| player == 1 && boardEvaluationNextMoves > bestMoveEvaluationSoFar)
			{
				bestMoveEvaluationSoFar = boardEvaluationNextMoves;
				
				if (depth == moveDepth)
					bestMove = openPositions[i];
			}
			
			board[openPositions[i] / DEC_POS_ENC][openPositions[i] % DEC_POS_ENC] = 0;	// UNDO MOVE
		}
		
		return bestMoveEvaluationSoFar;
	}
	
	
	public static void main(String[] args)
	{
		TicTacToe tictactoe = new TicTacToe();
		// testBoard(tictactoe);
		BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
		int x = 0, y = 0;
		
		do 
		{
			tictactoe.showBoard();
			System.out.println("Your move, first the x position then the y position (press enter to confirm) : ");
			
			try 
			{
				x = Integer.parseInt(console.readLine()) - 1;
				y = Integer.parseInt(console.readLine()) -1;
			}
			catch (Exception e)
			{
				System.out.println("Input Error!");
				continue;
			}
			
			if (!tictactoe.move(x, y, tictactoe.activePlayer))
			{
				System.out.println("Invalid Move!");
				continue;
			}
			
			tictactoe.minMax(tictactoe.board, tictactoe.moveDepth, tictactoe.activePlayer);
			tictactoe.move(tictactoe.bestMove / DEC_POS_ENC, tictactoe.bestMove % DEC_POS_ENC, tictactoe.activePlayer);
			
			System.out.println("The computer has made it's move:");
			
		} while (!tictactoe.checkIfGameEnded());
	}

	private static void testBoard(TicTacToe tictactoe)
	{
		tictactoe.move(0, 2, -1);
		tictactoe.move(1, 1, 1);
		tictactoe.move(2, 0, -1);
		tictactoe.move(0, 0, -1);

		tictactoe.showBoard();
		// System.out.println(tictactoe.evaluateBoard(tictactoe.board));
		System.out.println(tictactoe.countOccupiedFields(tictactoe.board));
	}

}
