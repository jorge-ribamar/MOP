package tps.tp4;

public class SurpresaBonus extends Surpresa {

	SurpresaBonus() {
		if (Sokoban.nivelAtual.getnPassos() >= 20)
			Sokoban.nivelAtual.setnPassos(Sokoban.nivelAtual.getnPassos() - 20);
		else
			Sokoban.nivelAtual.setnPassos(-1);
	}
}
