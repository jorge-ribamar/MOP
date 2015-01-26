package tps.tp4;

import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JLabel;

public class SurpresaFantasma extends Surpresa {

	public Timer timer;
	private int Pos = -1;
	private boolean FantasmaVisivel = true;
	private Fantasma Fantasma;
	private long TempoCadaPasso;

	SurpresaFantasma() {
		Fantasmas.add(this);
		Direction.actualizarDeslocamentos();
		boolean PosEncontrada = false;
		int Nivel = Integer.valueOf(Sokoban.nivelAtual.getNivel());
		TempoCadaPasso = 700 - (25 * (Nivel - 1));
		try {
			while (!PosEncontrada) {
				Pos = ((int) (Math.random() * Sokoban.nivelAtual.getArmazem()
						.size()));
				if (Sokoban.nivelAtual.getArmazem().get(Pos).getElemento() instanceof Floor
						&& ((Floor) Sokoban.nivelAtual.getArmazem().get(Pos)
								.getElemento()).getDock() == false) {
					System.out.println("Posição do fantasma encontrada: " + Pos
							+ ".");
					Fantasma = new Fantasma(Pos);
					Sokoban.nivelAtual.getArmazem().get(Pos).set(Fantasma);
					System.out.println("Fantasma colocado com sucesso.");
					PosEncontrada = true;
				}
			}

			// Actualizar a imagem da posicao
			Sokoban.labelTabuleiro.remove(Pos);
			Sokoban.labelTabuleiro.add(Sokoban.nivelAtual.getArmazem().get(Pos)
					.getImagem(), Pos);
			Sokoban.labelTabuleiro.setVisible(true);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		timer = new Timer();

		final TimerTask piscar = new TimerTask() {
			@Override
			public void run() {
				System.out.println("A piscar");
				if (FantasmaVisivel) {
					Sokoban.labelTabuleiro.remove(Pos);
					JLabel chao = new JLabel(Sokoban.imageIconFloor);
					chao.setVisible(true);
					Sokoban.labelTabuleiro.add(chao, Pos);
					Sokoban.labelTabuleiro.setVisible(true);
					NomeJogador.jogo.revalidate();
					FantasmaVisivel = false;
				} else {
					Sokoban.ActualizarPosicao(Pos);
					Sokoban.labelTabuleiro.setVisible(true);
					NomeJogador.jogo.revalidate();
					FantasmaVisivel = true;
				}
			}
		};

		final TimerTask MoverFantasma = new TimerTask() {
			@Override
			public void run() {
				System.out.println("A Mover Fantasma");
				moverFantasma();

			}

			private void moverFantasma() {

				int posXFant = ((Pos + 1) % Sokoban.nivelAtual.getnColunas());
				int posYFant = (int) Math.floor((Pos + 1)
						/ (Sokoban.nivelAtual.getnColunas())) + 1;
				System.out.println("Fantasma: x=" + posXFant + " , y="
						+ posYFant + ".");

				int PosYWorker = (int) Math
						.floor((Sokoban.nivelAtual.trabalhador.getPosicao())
								/ Sokoban.nivelAtual.getnColunas() + 1);
				int PosXWorker = (int) Math
						.floor(Sokoban.nivelAtual.trabalhador.getPosicao()
								% Sokoban.nivelAtual.getnColunas());

				System.out.println("Trabalhador: x=" + PosXWorker + " , y="
						+ PosYWorker + ".");

				if (Math.abs(PosYWorker - posYFant) > Math.abs(PosXWorker
						- posXFant)) {
					movimentoVertical(posXFant, posYFant, PosXWorker,
							PosYWorker, false);
				} else if (Math.abs(PosXWorker - posXFant) >= Math
						.abs(PosYWorker - posYFant)) {
					movimentoHorizontal(posXFant, posYFant, PosXWorker,
							PosYWorker, false);
				}

			}

			private void movimentoHorizontal(int posXFant, int posYFant,
					int PosXWorker, int PosYWorker, boolean AntesMovVertical) {

				Direction direcao = null;
				if (PosYWorker == posYFant && PosXWorker == posXFant)
					NomeJogador.jogo.setGameOver(true);
				if (PosXWorker > posXFant) {
					direcao = Direction.East;
				} else if (PosXWorker < posXFant) {
					direcao = Direction.West;
				} else {
					movimentoVertical(posXFant, posYFant, PosXWorker,
							PosYWorker, true);
					return;
				}
				boolean movimento = move(direcao);
				if (!movimento && !AntesMovVertical) {
					movimentoVertical(posXFant, posYFant, PosXWorker,
							PosYWorker, true);
					return;
				}
				if (!movimento && AntesMovVertical) {
					colocarEmPosAleatoria();
				}
			}

			private void movimentoVertical(int posXFant, int posYFant,
					int PosXWorker, int PosYWorker, boolean AntesMovHorizontal) {

				Direction direcao = null;
				if (PosYWorker == posYFant && PosXWorker == posXFant) {
					NomeJogador.jogo.setGameOver(true);
					return;
				}
				if (PosYWorker > posYFant) {
					direcao = Direction.South;
				} else if (PosYWorker < posYFant) {
					direcao = Direction.North;
				} else {
					movimentoHorizontal(posXFant, posYFant, PosXWorker,
							PosYWorker, true);
					return;
				}
				boolean movimento = move(direcao);
				if (!movimento && !AntesMovHorizontal) {
					movimentoHorizontal(posXFant, posYFant, PosXWorker,
							PosYWorker, true);
					return;
				}
				if (!movimento && AntesMovHorizontal) {
					colocarEmPosAleatoria();
				}
			}

			private void colocarEmPosAleatoria() {

				// Tirar da Pos Atual
				Sokoban.nivelAtual.getArmazem().get(Pos).setElmOcuNull();
				Sokoban.ActualizarPosicao(Pos);
				Sokoban.labelTabuleiro.setVisible(true);
				NomeJogador.jogo.revalidate();

				// Colocar numa nova Pos
				boolean PosEncontrada = false;
				try {
					while (!PosEncontrada) {
						Pos = ((int) (Math.random() * Sokoban.nivelAtual
								.getArmazem().size()));
						if (Sokoban.nivelAtual.getArmazem().get(Pos)
								.getElemento() instanceof Floor
								&& ((Floor) Sokoban.nivelAtual.getArmazem()
										.get(Pos).getElemento()).getDock() == false) {
							System.out
									.println("Posição do fantasma encontrada: "
											+ Pos + ".");
							Fantasma = new Fantasma(Pos);
							Sokoban.nivelAtual.getArmazem().get(Pos)
									.set(Fantasma);
							System.out
									.println("Fantasma colocado com sucesso.");
							PosEncontrada = true;
						}
					}

					// Actualizar a imagem da posicao
					Sokoban.labelTabuleiro.remove(Pos);
					Sokoban.labelTabuleiro.add(Sokoban.nivelAtual.getArmazem()
							.get(Pos).getImagem(), Pos);
					Sokoban.labelTabuleiro.setVisible(true);

				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
			}
		};

		final TimerTask PararPiscar = new TimerTask() {

			@Override
			public void run() {
				System.out.println("A Parar de Piscar");
				piscar.cancel();
				if (!FantasmaVisivel) {
					Sokoban.ActualizarPosicao(Pos);
					Sokoban.labelTabuleiro.setVisible(true);
					NomeJogador.jogo.revalidate();
					FantasmaVisivel = true;
				}

				timer.scheduleAtFixedRate(MoverFantasma, TempoCadaPasso,
						TempoCadaPasso);
			}

		};

		TimerTask EliminarFantasma = new TimerTask() {
			@Override
			public void run() {
				System.out.println("A Eliminar Fantasma");
				MoverFantasma.cancel();
				Sokoban.nivelAtual.getArmazem().get(Pos).setElmOcuNull();
				Sokoban.ActualizarPosicao(Pos);
				Sokoban.labelTabuleiro.setVisible(true);
				NomeJogador.jogo.revalidate();
				FantasmaVisivel = true;
			}
		};

		timer.scheduleAtFixedRate(piscar, 250, 250);
		timer.schedule(PararPiscar, 1000);
		timer.schedule(EliminarFantasma, 15 * 1000);

	}

	private boolean move(Direction direcao) {
		boolean MovimentoPossivel = Sokoban.nivelAtual.getArmazem()
				.get(Pos + direcao.getDeslocamento()).canRecieveElement()
				|| Sokoban.nivelAtual.getArmazem()
						.get(Pos + direcao.getDeslocamento()).getElemento() instanceof Worker;
		if (MovimentoPossivel) {
			Sokoban.nivelAtual.getArmazem().get(Pos).setElmOcuNull();
			// Actualizar a imagem da posicao
			Sokoban.ActualizarPosicao(Pos);

			Pos += direcao.getDeslocamento();
			Sokoban.nivelAtual.getArmazem().get(Pos).set(Fantasma);
			Sokoban.ActualizarPosicao(Pos);

			NomeJogador.jogo.revalidate();
			Sokoban.labelTabuleiro.repaint();

			Sokoban.labelTabuleiro.setVisible(true);

			System.out.println("Fantasma movido com sucesso.");
			return true;
		}
		return false;
	}

}
