package tps.tp4;

import javax.swing.Icon;

public class Floor extends ElementoBase {

	Floor(int posicao, boolean dock) {
		super(posicao);
		super.setDock(dock);
	}

	public boolean move(Direction direction) {
		return true;
	}

	public Icon getIcon() {
		return getDock() ? Sokoban.imageIconDock : Sokoban.imageIconFloor;
	}
}
