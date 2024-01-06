package campoMinado.modelo;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.*;

import campoMinado.model.Field;
import campoMinado.model.Position;
import campoMinado.model.enums.Difficulty;

class FieldTest {
	private Field sut;

	@BeforeEach
	void makeSut() {
		this.sut = new Field(new Position(2, 2), true);
	}

	@Test
	void constructor() {
		assertEquals(this.sut.POSITION, new Position(2, 2));
		assertTrue(this.sut.isBomb());
	}

	@Test
	void fieldsClose() {
		List<Field> fieldsClose = new ArrayList<Field>(
				Arrays.asList(new Field(new Position(1, 1), true), new Field(new Position(1, 2), false)));
		
		this.sut.setFieldsClose(fieldsClose);
		
		assertEquals(this.sut.getFieldsClose(), fieldsClose);
	}
	
	@Test
	void bombClose() {
		List<Field> fieldsClose = new ArrayList<Field>(
				Arrays.asList(new Field(new Position(1, 3), true), new Field(new Position(1, 1), true), new Field(new Position(1, 2), false)));
		this.sut.setFieldsClose(fieldsClose);
		
		this.sut.setQtdBombClose();
		
		assertEquals(this.sut.getQntBombClose(), 2);
	}
	
	@Test
	void marked() {
		this.sut.toggleMarked();
		
		assertTrue(this.sut.isMarked());
		
		this.sut.toggleMarked();
		
		assertFalse(this.sut.isMarked());
		
		this.sut.openChainField();
		
		this.sut.toggleMarked();
		
		assertFalse(this.sut.isMarked());
	}

	@Test
	void opened() {
		this.sut.openChainField();
		
		assertTrue(this.sut.isOpened());
		
		this.sut.openChainField();
		
		assertTrue(this.sut.isOpened());
		
		makeSut();
		this.sut.toggleMarked();
		
		this.sut.openChainField();
		
		assertFalse(this.sut.isOpened());
	}
	
	@Test
	void toStringTest() {
		String result = this.sut.toString();
		
		assertEquals(result, "PositionX => 2, PositionY => 2, isBomb => true, isOpened => false");
	}
}
