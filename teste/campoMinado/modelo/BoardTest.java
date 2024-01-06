package campoMinado.modelo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import campoMinado.model.Board;
import campoMinado.model.Position;
import campoMinado.model.enums.Difficulty;

class BoardTest {
	private int board_width = 30;
	private int board_height = 30;
	private Board sut;
	
	@BeforeEach
	void makeSut() {
		this.sut = new Board(Difficulty.HARD);
	}
	
	@Test
	void contructor() {
		assertEquals(this.sut.DIFFICULTY, Difficulty.HARD);
		assertEquals(this.sut.BOARD_WIDTH, this.board_width);
		assertEquals(this.sut.BOARD_HEIGHT, this.board_height);
		assertEquals(this.sut.getFields().size(), this.board_width * this.board_height);
		assertEquals(this.sut.getFields().get(0).getFieldsClose().size(), 3);
		assertEquals(this.sut.getFields().get(1).getFieldsClose().size(), 5);
	}
}
