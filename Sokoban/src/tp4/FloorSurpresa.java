package tps.tp4;

import javax.swing.Icon;

public class FloorSurpresa extends Floor {

	FloorSurpresa(int posicao, boolean dock) {
		super(posicao, dock);
	}

	public boolean move(Direction direction) {
		int aux = ((int) (Math.random() * 2));
		if (aux == 0) {
			new SurpresaBonus();
		} else if (aux == 1) {
			new SurpresaFantasma();
		}
		return true;
	}
	
	public Icon getIcon() {
		return  Sokoban.imageIconSurpresa;
	}
}
