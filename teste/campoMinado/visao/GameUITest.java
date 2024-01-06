package campoMinado.visao;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import campoMinado.model.Board;
import campoMinado.model.enums.Difficulty;
import campoMinado.view.GameUI;

class GameUITest {
	private GameUI sut;
	
	@BeforeEach
	void makeSut() {
		this.sut = new GameUI(new Board(Difficulty.EASY));
	}
	
	@Test
	void requestDifficulty() {
		this.sut.requestDifficulty();
	}
	
	@Test
	void drawBoard() {
		this.sut.drawBoard();
	}
	
	@Test
	void requestPosition() {
		this.sut.requestPoistion();
	}
	
	@Test
	void requestIsMarkOrOpen() {
		this.sut.requestIsMarkOrOpenField();
	}
}
