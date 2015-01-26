package tps.tp4;

import javax.swing.Icon;

public abstract class ElementoBase {

	private int posicao;

	private boolean dock;

	ElementoBase(int posicao) {
		this.posicao = posicao;
	}

	public boolean getDock() {
		return dock;
	}

	public void setDock(boolean dock) {
		this.dock = dock;
	}

	public int getPosicao() {
		return posicao;
	}

	public void setPosicao(int posicao) {
		this.posicao = posicao;
	}

	public void mudarPosicoes(Direction direction) {
		boolean dock = Sokoban.nivelAtual.getArmazem()
				.get(posicao - 1 + direction.getDeslocamento()).getElemento()
				.getDock();
		this.setDock(dock);
		Sokoban.nivelAtual.getArmazem()
				.get(posicao - 1 + direction.getDeslocamento()).set(this);
		Sokoban.nivelAtual.getArmazem().get(posicao - 1).removeElement();
		this.setPosicao(posicao += direction.getDeslocamento());
	}

	public boolean move(Direction direction) {
		if (Sokoban.nivelAtual.getArmazem()
				.get(posicao - 1 + direction.getDeslocamento())
				.canRecieveElement()) {
			this.mudarPosicoes(direction);
			return true;
		}
		return false;
	}
	
	public abstract Icon getIcon();

}
