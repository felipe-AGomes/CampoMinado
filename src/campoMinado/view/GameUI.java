package campoMinado.view;

import java.util.Scanner;

import campoMinado.model.Board;
import campoMinado.model.Field;

public class GameUI {
	private Board BOARD;

	public GameUI() {
	}

	public void setBoard(Board board) {
		this.BOARD = board;
	}

	private String scannerRequest() {
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		String response = scanner.nextLine();
		scanner = null;

		return response.toUpperCase();
	}

	public String requestDifficulty() {
		System.out.print("Escolha a dificuldade: 1 - Fácil / 2 - Médio / 3 - Difícil ");

		return this.scannerRequest();
	}

	public String requestPoistion() {
		System.out.print("\nEscolha a posição: EX (\"1A\", \"3B\") ");

		return this.scannerRequest();
	}

	public String requestIsMarkOrOpenField() {
		System.out.print("\nAbrir campo our criar marcação? 1 - Abrir Campo / 2 - Criar Marcação ");

		return this.scannerRequest();
	}

	public void drawBoard() {
		StringBuilder table = new StringBuilder("\n");
		int currentLine = 0;

		for (int i = 0; i < this.BOARD.getFields().size(); i++) {
			Field field = this.BOARD.getFields().get(i);

			if (currentLine != field.POSITION.Y) {
				table.append("|\n");
				currentLine = field.POSITION.Y;
			}

			// Definir os indíces das colunas
			if (i == 0) {
				table.append(" ");
				for (int j = 0; j < this.BOARD.getWidth(); j++) {
					int a = (int) 'A';
					char charIndex = (char) (a + j);

					table.append(" " + charIndex);
				}
				table.append("\n");
			}

			// Definir os indíces das linhas
			if (field.POSITION.X == 0) {
				int a = (int) 'A';
				char charIndex = (char) (a + field.POSITION.Y);

				table.append(charIndex);
			}

			if (!field.isMarked() && !field.isOpened()) {
				table.append("|@");
			} else if (field.isMarked() && !field.isOpened()) {
				table.append("|!");
			} else if (field.isOpened() && !field.isBomb()) {
				table.append("|" + (field.getQntBombClose() == 0 ? " " : field.getQntBombClose()));
			} else if (field.isOpened() && field.isBomb()) {
				table.append("|X");
			}

		}

		table.append("|\n");

		System.out.println(table);
	}

	public void gameWin(Field field) {
		this.drawBoard();
		System.out.println("Parabéns!!! Você ganou!!!");
	}

	public void gameOver(Field field) {
		this.drawBoard();
		System.out.println("Sinto muito, mas você perdeu");
	}

	public void markField(Field field) {
		// FIXME Auto-generated method stub
		this.drawBoard();
	}

	public void openField(Field field) {
		// FIXME Auto-generated method stub
		this.drawBoard();
	}

}