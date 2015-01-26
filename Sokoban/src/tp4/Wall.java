package tps.tp4;

import javax.swing.Icon;

public class Wall extends ElementoBase {

	Wall(int posicao) {
		super(posicao);
		super.setDock(false);
	}

	public boolean move(Direction direction) {
		return false;
	}
	
	public Icon getIcon() {
		return Sokoban.imageIconWall;
	}
}
