package campoMinado.model.interfaces;

import campoMinado.model.Field;

public interface BoardListener {
	void onLoseBoardEvent(Field field);

	void onMarkBoardEvent(Field field);

	void onOpenBoardEvent(Field field);

	void onWinBoardEvent(Field field);
}
