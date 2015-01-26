package tps.tp4;

import javax.swing.Icon;

public class Outside extends ElementoBase {

	Outside(int posicao) {
		super(posicao);
		super.setDock(false);
	}

	public boolean move(Direction direction) {
		return false;
	}

	public Icon getIcon() {
		return Sokoban.imageIconFloor;
	}
}
