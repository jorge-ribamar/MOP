package tps.tp4;

import javax.swing.Icon;

public class Fantasma extends ElementoBase {

	Fantasma(int posicao) {
		super(posicao);
	}

	public boolean move(Direction direction) {
		NomeJogador.jogo.setGameOver(true);
		return true;
	}

	public Icon getIcon() {
		return Sokoban.imageIconFantasma;
	}

}
