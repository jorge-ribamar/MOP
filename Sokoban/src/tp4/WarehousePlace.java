package tps.tp4;

import javax.swing.JLabel;

public class WarehousePlace extends JLabel {

	private static final long serialVersionUID = 1L;
	private ElementoBase elemento;
	private ElementoBase elementoOcupante;

	int x = 0;
	int y = 0;

	WarehousePlace(ElementoBase elemento) {
		this.elemento = elemento;
		this.elementoOcupante = null;
	}

	public void setElementoBase(ElementoBase elemento) {
		this.elemento = elemento;
		setIcon();
	}

	public ElementoBase getElementoOcupante() {
		return elementoOcupante;
	}

	public JLabel getImagem() {
		this.setIcon();
		return new JLabel(super.getIcon());
	}

	public ElementoBase getElemento() {
		if (elementoOcupante == null) {
			return elemento;
		} else {
			return elementoOcupante;
		}
	}

	public void setIcon() {
		ElementoBase elementoAux = this.getElemento();
		super.setIcon(elementoAux.getIcon());

		// if (elementoAux instanceof FloorSurpresa) {
		// super.setIcon(Sokoban.imageIconSurpresa);
		// } else if (elementoAux instanceof Wall) {
		// super.setIcon(Sokoban.imageIconWall);
		// } else if (elementoAux instanceof Floor) {
		// if (elementoAux.getDock() == true) {
		// super.setIcon(Sokoban.imageIconDock);
		// } else {
		// super.setIcon(Sokoban.imageIconFloor);
		// }
		// } else if (elementoAux instanceof Outside) {
		// super.setIcon(Sokoban.imageIconFloor);
		// } else if (elementoAux instanceof Worker) {
		// if (elementoAux.getDock() == true) {
		// super.setIcon(Sokoban.imageIconWorkDock);
		// } else {
		// super.setIcon(Sokoban.imageIconWorker);
		// }
		// } else if (elementoAux instanceof Box) {
		// if (elementoAux.getDock() == true) {
		// super.setIcon(Sokoban.imageIconBoxDock);
		// } else {
		// super.setIcon(Sokoban.imageIconBox);
		// }
		// } else if (elementoAux instanceof Fantasma) {
		// super.setIcon(Sokoban.imageIconFantasma);
		// }
	}

	public boolean set(ElementoBase elementoNovo) {
		if (this.canRecieveElement()) {
			this.elementoOcupante = elementoNovo;
			return true;
		}
		return false;
	}

	public void setElmOcuNull() {

		this.elementoOcupante = null;
	}

	public boolean move(Direction direcao) {
		return this.getElemento().move(direcao);
	}

	public void removeElement() {
		this.elementoOcupante = null;
	}

	public boolean canRecieveElement() {
		if (this.elemento instanceof Floor && elementoOcupante == null) {
			return true;
		}
		return false;
	}

}
