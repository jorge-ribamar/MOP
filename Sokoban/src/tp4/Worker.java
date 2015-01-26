package tps.tp4;

import javax.swing.Icon;

public class Worker extends ElementoBase {

	Worker(int posicao, boolean dock) {
		super(posicao);
		super.setDock(dock);
	}

	public boolean move(Direction direction) {
		System.out.println("A tentar mover o trabalhador, n caixotes="
				+ Sokoban.nivelAtual.getnCaixotes() + " "
				+ Sokoban.nivelAtual.getnCaixotesPosFinal());
		boolean MovimentoVizinho = Sokoban.nivelAtual.getArmazem()
				.get(super.getPosicao() - 1 + direction.getDeslocamento())
				.getElemento().move(direction);
		if (MovimentoVizinho) {
			if (this.NextIsBox(direction)) {
				Sokoban.somPassosBox.rewind();
				Sokoban.somPassosBox.play();
			} else {
				Sokoban.somPassos.rewind();
				Sokoban.somPassos.play();
			}
			super.move(direction);
			Sokoban.nivelAtual.setnPassos(Sokoban.nivelAtual.getnPassos() + 1);
			System.out.println(Sokoban.nivelAtual.getnPassos() + " Passos");
			return true;
		}
		return false;
	}

	public boolean moverComCaixa(Direction direction) {
		if (this.NextIsBox(direction) & this.move(direction)) {
			return true;
		} else {
			return false;
		}
	}

	public boolean NextIsBox(Direction direction) {
		ElementoBase nextElement = Sokoban.nivelAtual.getArmazem()
				.get(super.getPosicao() - 1 + direction.getDeslocamento())
				.getElemento();
		if (nextElement instanceof Box) {
			return true;
		}
		return false;
	}

	public Icon getIcon() {
		return getDock() ? Sokoban.imageIconWorkDock : Sokoban.imageIconWorker;
	}

}
