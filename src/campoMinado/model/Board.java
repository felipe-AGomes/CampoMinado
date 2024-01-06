package campoMinado.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import campoMinado.model.enums.BoardEvent;
import campoMinado.model.enums.Difficulty;
import campoMinado.model.enums.FieldEvent;
import campoMinado.model.interfaces.BoardListener;
import campoMinado.model.interfaces.FieldListener;

public class Board implements FieldListener {
	public final int BOARD_WIDTH;
	public final int BOARD_HEIGHT;
	public final Difficulty DIFFICULTY;

	private int maxBombs;
	private int currentBombs = 0;
	private final List<Field> fields = new ArrayList<Field>();
	private List<BoardListener> listeners = new ArrayList<BoardListener>();

	public Board(Difficulty difficulty) {
		this.DIFFICULTY = difficulty;

		switch (this.DIFFICULTY) {
		case HARD:
			this.maxBombs = 26;
			this.BOARD_WIDTH = 26;
			this.BOARD_HEIGHT = 26;
			break;
		case MODERATE:
			this.maxBombs = 20;
			this.BOARD_WIDTH = 20;
			this.BOARD_HEIGHT = 20;
			break;
		default:
			this.maxBombs = 4;
			this.BOARD_WIDTH = 10;
			this.BOARD_HEIGHT = 10;
			break;
		}

		this.createFields();
		this.setFieldsClose();
	}

	private void createFields() {
		for (int y = 0; y < this.BOARD_WIDTH; y++) {
			for (int x = 0; x < this.BOARD_HEIGHT; x++) {
				fields.add(new Field(new Position(x, y), this.randomizeBomb()));
			}
		}

		for (Field field : this.getFields()) {
			field.addEventListener(this);
		}
	}

	private boolean randomizeBomb() {
		final int MARK_EASY = 10;
		final int MARK_MODERATE = 20;
		final int MARK_HARD = 30;

		Random random = new Random();

		int possibleBomb = random.nextInt(101);

		if (this.currentBombs >= this.maxBombs) {
			return false;
		}

		if (this.DIFFICULTY == Difficulty.HARD && possibleBomb <= MARK_HARD) {
			this.currentBombs++;
			return true;
		} else if (this.DIFFICULTY == Difficulty.MODERATE && possibleBomb <= MARK_MODERATE) {
			this.currentBombs++;
			return true;
		} else if (this.DIFFICULTY == Difficulty.EASY && possibleBomb <= MARK_EASY) {
			this.currentBombs++;
			return true;
		}

		return false;
	}

	private void setFieldsClose() {
		for (Field field : this.fields) {
			List<Field> closeFields = new ArrayList<Field>();

			for (int x = -1; x <= 1; x++) {

				for (int y = -1; y <= 1; y++) {
					int finalX = field.POSITION.X + x;
					int finalY = field.POSITION.Y + y;
					boolean currentField = finalX == field.POSITION.X && finalY == field.POSITION.Y;

					if (currentField || finalX < 0 || finalY < 0)
						continue;

					closeFields.addAll(this.getFields().stream().filter(f -> {
						return f.POSITION.equals(new Position(finalX, finalY));
					}).toList());
				}

			}

			field.setFieldsClose(closeFields);

		}

	}

	public List<Field> getFields() {
		return this.fields;
	}

	public void openField(Position position) {
		for (Field field : this.getFields()) {
			if (field.POSITION.equals(position)) {
				field.openField();				
			}
		}
	}

	public void toggleField(Position position) {
		Field field = this.fields.stream().filter(f -> f.POSITION.equals(position)).findFirst().orElse(null);

		if (field == null) {
			return;
		}

		field.toggleMarked();
	}

	public boolean checkGameWin() {
		boolean winWithMark = true;
		boolean winWithField = true;

		for (Field field : this.getFields()) {
			if (field.isBomb() && !field.isMarked()) {
				winWithMark = false;
			}

			if (!field.isOpened() && !field.isBomb()) {
				winWithField = false;
			}
		}

		return winWithMark || winWithField;
	}

	public void addEventListener(BoardListener gameController) {
		this.listeners.add(gameController);
	}

	public void notifyListeners(BoardEvent event, Field field) {
		for (BoardListener listener : this.listeners) {
			switch (event) {
			case LOSE:
				this.openAllBombs();
				listener.onLoseBoardEvent(field);
				break;
			case MARK:
				listener.onMarkBoardEvent(field);
				break;
			case OPEN:
				listener.onOpenBoardEvent(field);
				break;
			default:
				this.openAllFields();
				listener.onWinBoardEvent(field);
			}
		}
	}

	private void openAllFields() {
		for (Field field : this.getFields()) {
			field.noNotifyOpen();
		}
		
	}

	private void openAllBombs() {
		for (Field field : this.getFields()) {
			if (field.isBomb()) {
				field.openBomb();
			}
		}
	}

	@Override
	public void onExplosionFieldEvent(Field field) {
		this.notifyListeners(BoardEvent.LOSE, field);
	}

	@Override
	public void onMarkFieldEvent(Field field) {
		if (this.checkGameWin()) {
			this.notifyListeners(BoardEvent.WIN, field);
			return;
		}

		this.notifyListeners(BoardEvent.MARK, field);
	}

	@Override
	public void onOpenFieldEvent(Field field) {
		if (this.checkGameWin()) {
			this.notifyListeners(BoardEvent.WIN, field);
			return;
		}

		this.notifyListeners(BoardEvent.OPEN, field);
	}

}
