package campoMinado.model;

import campoMinado.controller.GameController;
import campoMinado.view.GameUI;

public class MineField {

	public static void main(String[] args) {
		GameController game = new GameController(new GameUI(), new Board());

		game.start();
	}

}
