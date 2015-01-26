package tps.tp4;

import javax.swing.Icon;

public class Box extends ElementoBase {

	Box(int posicao, boolean dock) {
		super(posicao);
		setDock(dock);
	}

	public void setDock(boolean dock) {
		if (this.getDock() == false && dock == true) {
			super.setDock(dock);
			Sokoban.somBoxDock.rewind();
			Sokoban.somBoxDock.play();
			Sokoban.nivelAtual.setnCaixotesPosFinal(Sokoban.nivelAtual
					.getnCaixotesPosFinal() + 1);
		} else if (this.getDock() == true && dock == false) {
			super.setDock(dock);
			Sokoban.somBoxUndock.rewind();
			Sokoban.somBoxUndock.play();
			Sokoban.nivelAtual.setnCaixotesPosFinal(Sokoban.nivelAtual
					.getnCaixotesPosFinal() - 1);
		}
	}

	public Icon getIcon() {
		return getDock() ? Sokoban.imageIconBoxDock : Sokoban.imageIconBox;
	}
}
