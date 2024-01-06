package campoMinado.controller;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import campoMinado.model.Board;
import campoMinado.model.Field;
import campoMinado.model.Position;
import campoMinado.model.enums.Difficulty;
import campoMinado.model.interfaces.BoardListener;
import campoMinado.view.GameUI;

public class GameController implements BoardListener {
	private boolean gameOver = false;
	private boolean gameWin = false;
	private Board board;
	private GameUI gameUi;

	private Difficulty choseDifficulty() {
		String userResponse;
		Matcher matcher;
		Pattern pattern = Pattern.compile("[1-3]");

		do {
			userResponse = this.gameUi.requestDifficulty();
			matcher = pattern.matcher(userResponse);
		} while (!matcher.find());

		switch (userResponse) {
		case "1":
			return Difficulty.EASY;
		case "2":
			return Difficulty.MODERATE;
		default:
			return Difficulty.HARD;
		}
	}

	public void start() {
		this.gameUi = new GameUI();
		Difficulty difficulty = this.choseDifficulty();
		this.board = new Board(difficulty);
		this.gameUi.setBoard(board);
		this.gameUi.drawBoard();
		
		this.board.addEventListener(this);

		this.gameLoop();
	}

	public void gameLoop() {
		while (!this.gameOver && !this.gameWin) {
			Position position = this.requestPosition();

			if (this.markOrOpenField() == 1) {
				this.board.openField(position);
			} else {
				this.board.toggleField(position);
			}
			
		}
	}

	private int markOrOpenField() {
		String markOrOpen;
		Matcher matcher;
		Pattern pattern = Pattern.compile("[1-2]");

		do {
			markOrOpen = this.gameUi.requestIsMarkOrOpenField();
			matcher = pattern.matcher(markOrOpen);
		} while (!matcher.find());

		return Integer.parseInt(markOrOpen);
	}

	private Position requestPosition() {
		String position;
		Matcher matcher;
		Pattern pattern = Pattern.compile("[A-Z]{2}");

		do {
			position = this.gameUi.requestPoistion();
			matcher = pattern.matcher(position);
		} while (!matcher.find());

		Character firstCharacter = position.charAt(0);
		int firstPosition = this.convertCharacterToPosition(firstCharacter);
		Character secondCharacter = position.charAt(1);
		int secondPosition = this.convertCharacterToPosition(secondCharacter);

		return new Position(firstPosition, secondPosition);
	}

	private int convertCharacterToPosition(Character character) {
		int aAsci = Character.getNumericValue('A');
		int characterAsc = Character.getNumericValue(character);

		return characterAsc - aAsci;
	}

	@Override
	public void onLoseBoardEvent(Field field) {
		this.gameOver = true;
		this.gameUi.gameOver(field);
	}

	@Override
	public void onMarkBoardEvent(Field field) {
		this.gameUi.markField(field);
	}

	@Override
	public void onOpenBoardEvent(Field field) {
		this.gameUi.openField(field);
	}

	@Override
	public void onWinBoardEvent(Field field) {
		this.gameWin = true;
		this.gameUi.gameWin(field);
	}
}