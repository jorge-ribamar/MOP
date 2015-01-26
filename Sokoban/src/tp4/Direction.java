package tps.tp4;

public enum Direction {

	North(-Sokoban.nivelAtual.getnColunas()), East(+1), South(
			Sokoban.nivelAtual.getnColunas()), West(-1);
	private int deslocamento; // estado de cada valor do enumerado

	private Direction(int deslocamento) {
		this.deslocamento = deslocamento;
	}

	public int getDeslocamento() {
		return deslocamento;
	}

	public static void actualizarDeslocamentos() {
		setDeslocamento(Direction.North, -Sokoban.nivelAtual.getnColunas());
		setDeslocamento(Direction.South, Sokoban.nivelAtual.getnColunas());

	}

	public static void setDeslocamento(Direction direcao, int deslocamento) {
		direcao.deslocamento = deslocamento;
	}

	public Direction oposta() {
		if (this == Direction.North) {
			return Direction.South;
		}
		if (this == Direction.South) {
			return Direction.North;
		}
		if (this == Direction.East) {
			return Direction.West;
		}
		if (this == Direction.West) {
			return Direction.East;
		}
		return null;
	}

}
