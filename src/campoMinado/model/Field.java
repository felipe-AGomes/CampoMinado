package campoMinado.model;

import java.util.ArrayList;
import java.util.List;

import campoMinado.model.enums.FieldEvent;
import campoMinado.model.interfaces.FieldListener;

public class Field {
	private int qntBombClose = 0;
	private boolean opened = false;
	private boolean bomb = false;
	private boolean marked = false;
	private List<Field> fieldsClose = new ArrayList<Field>();
	private List<FieldListener> listeners = new ArrayList<FieldListener>();

	public final Position POSITION;

	public Field(Position position, boolean hasBomb) {
		this.POSITION = position;
		this.bomb = hasBomb;
	}

	public int getQntBombClose() {
		return this.qntBombClose;
	}

	@Override
	public String toString() {
		return String.format("PositionX => %d, PositionY => %d, isBomb => %b, isOpened => %b", this.POSITION.X,
				this.POSITION.Y, this.bomb, this.opened);
	}

	public boolean isOpened() {
		return this.opened;
	}

	public boolean isBomb() {
		return this.bomb;
	}

	public boolean isMarked() {
		return this.marked;
	}

	public List<Field> getFieldsClose() {
		return this.fieldsClose;
	}

	public void setQtdBombClose() {
		this.qntBombClose = (int) this.fieldsClose.stream().filter(f -> f.isBomb()).count();
	}

	public void setFieldsClose(List<Field> fields) {
		this.fieldsClose.addAll(fields);
		this.setQtdBombClose();
	}

	public void openChainField() {
		if (this.isOpened() || this.isMarked() || this.isBomb()) {
			return;
		}

		this.opened = true;

		for (Field f : this.getFieldsClose()) {
			if (f.getQntBombClose() == 0 && !f.isBomb()) {
				f.openChainField();
			}

			if (f.getQntBombClose() > 0) {
				f.openUniqueField();
			}
		}
	}

	public void openBomb() {
		this.opened = true;
	};

	public void noNotifyOpen() {
		this.opened = true;
	}

	public void openField() {
		if (this.isOpened() || this.isMarked()) {
			return;
		}

		if (this.isBomb()) {
			this.notifyListeners(FieldEvent.EXPLOSION);
			return;
		}

		if (this.getQntBombClose() == 0) {
			this.openChainField();
		} else {
			this.openUniqueField();
		}

		this.notifyListeners(FieldEvent.OPEN);
	}

	public void toggleMarked() {
		if (this.isOpened()) {
			return;
		}

		this.marked = !this.marked;
		this.notifyListeners(FieldEvent.MARK);
	}

	public void addEventListener(FieldListener board) {
		this.listeners.add(board);
	}

	public void notifyListeners(FieldEvent event) {
		for (FieldListener listener : listeners) {
			switch (event) {
			case EXPLOSION:
				listener.onExplosionFieldEvent(this);
				break;
			case MARK:
				listener.onMarkFieldEvent(this);
				break;
			default:
				listener.onOpenFieldEvent(this);
			}
		}
	}

	private void openUniqueField() {
		if (this.isOpened() || this.isMarked()) {
			return;
		}

		this.opened = true;
		this.notifyListeners(FieldEvent.OPEN);
	}
}
