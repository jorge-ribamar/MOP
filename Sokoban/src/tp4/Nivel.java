package tps.tp4;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Nivel {

	private String Nivel;
	private String descricaoNivel;
	public Worker trabalhador;

	private int nColunas;
	private int nCaixotesPosFinal;
	private int nCaixotes;
	private ArrayList<WarehousePlace> armazem;
	private int nPassos;
	public int HighScore = 0;
	public String Recordista;

	Nivel(String Nivel) throws FileNotFoundException {
		this.Nivel = Nivel;
		nPassos = 0;
		armazem = new ArrayList<WarehousePlace>();
		getHighScore(Nivel);
		System.out.println("Nivel " + Nivel + "criado com sucesso");
	}

	public final void getHighScore(String Nivel) throws FileNotFoundException {
		String FileURL = "src/tps/tp4/files/nivel" + Nivel + ".txt";
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(FileURL));
			String line;
			while ((line = br.readLine()) != null) {
				String[] linha = line.split(" ");
				if (linha[0].equals("H")) {
					String[] ArrayNome = Arrays.copyOfRange(linha, 2,
							linha.length);
					Recordista = Arrays.toString(ArrayNome).replace("[", "")
							.replace("]", "").replace(",", "");
					HighScore = Integer.valueOf(linha[1]);
				}
			}
		} catch (FileNotFoundException e) {
			throw ((FileNotFoundException) e);
		} catch (NumberFormatException | IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)
					br.close();
			} catch (IOException e) {
				System.err.println(e);
			}
		}

	}

	public String getNivel() {
		return Nivel;
	}

	public void setArmazem(ArrayList<WarehousePlace> armazem) {
		this.armazem = armazem;
	}

	public ArrayList<WarehousePlace> getArmazem() {
		return armazem;
	}

	public int getnColunas() {
		return this.nColunas;
	}

	public int getnCaixotes() {
		return this.nCaixotes;
	}

	public boolean NivelCompleto() {
		if (nCaixotes == nCaixotesPosFinal) {
			return true;
		}
		return false;
	}

	public int getnCaixotesPosFinal() {
		return nCaixotesPosFinal;
	}

	public void setnCaixotesPosFinal(int nCaixotesPosFinal) {
		this.nCaixotesPosFinal = nCaixotesPosFinal;
	}

	public int getnPassos() {
		return nPassos;
	}

	public void setnPassos(int nPassos) {
		this.nPassos = nPassos;
	}

	public void ContrutorArmazem() {
		armazem.clear();
		for (SurpresaFantasma a : Surpresa.Fantasmas) {
			a.timer.cancel();
		}
		nPassos = 0;
		nCaixotesPosFinal = 0;
		int posicao = 1;
		String inputFileUrl = "src/tps/tp4/files/nivel" + Nivel + ".txt";
		Scanner fileScan = null;
		try {
			fileScan = new Scanner(new File(inputFileUrl));
			System.out.println("Abrindo o nível" + Nivel);
			this.descricaoNivel = fileScan.nextLine();
			while (fileScan.hasNextLine()) {
				String Linha = fileScan.nextLine();
				String[] linha = Linha.split(" ");
				if (linha[0].equalsIgnoreCase("o")
						|| linha[0].equalsIgnoreCase("w")) {
					this.nColunas = linha.length;
				}
				for (String valor : linha) {
					if (valor.equalsIgnoreCase("o")) {
						armazem.add(new WarehousePlace(new Outside(posicao)));
						posicao++;
					} else if (valor.equalsIgnoreCase("w")) {
						armazem.add(new WarehousePlace(new Wall(posicao)));
						posicao++;
					} else if (valor.equalsIgnoreCase("f")) {
						armazem.add(new WarehousePlace(
								new Floor(posicao, false)));
						posicao++;
					} else if (valor.equalsIgnoreCase("d")) {
						armazem.add(new WarehousePlace(new Floor(posicao, true)));
						posicao++;
					} else if (valor.equalsIgnoreCase("s")) {
						System.out.println("achou um s");
						for (int i = 1; i < linha.length; i++) {
							this.trabalhador = new Worker(
									Integer.valueOf(linha[i]), false);
							if (armazem.get(Integer.valueOf(linha[i]) - 1)
									.getElemento().getDock() == false) {
								armazem.get(Integer.valueOf(linha[i]) - 1).set(
										trabalhador);
							} else if (armazem
									.get(Integer.valueOf(linha[i]) - 1)
									.getElemento().getDock() == true) {
								trabalhador.setDock(true);
								armazem.get(Integer.valueOf(linha[i]) - 1).set(
										trabalhador);
							}
						}
					} else if (valor.equalsIgnoreCase("b")) {
						System.out.println("achou um b");
						nCaixotes = linha.length - 1;
						for (int i = 1; i < linha.length; i++) {
							if (armazem.get(Integer.valueOf(linha[i]) - 1)
									.canRecieveElement()) {
								if (armazem.get(Integer.valueOf(linha[i]) - 1)
										.getElemento().getDock() == false) {
									armazem.get(Integer.valueOf(linha[i]) - 1)
											.set(new Box(Integer
													.valueOf(linha[i]), false));
								} else if (armazem
										.get(Integer.valueOf(linha[i]) - 1)
										.getElemento().getDock() == true) {
									armazem.get(Integer.valueOf(linha[i]) - 1)
											.set(new Box(Integer
													.valueOf(linha[i]), true));
								}
							}
						}

					}
				}
			}
			Direction.actualizarDeslocamentos();
			System.out.println("Nível aberto com sucesso");
		} catch (FileNotFoundException e) {
			System.err.println(e);
		} finally {
			if (fileScan != null) {
				fileScan.close();
			}
		}
		colocarPosSurpresa();
	}

	private void colocarPosSurpresa() {
		boolean PosEncontrada = false;
		while (!PosEncontrada) {
			int Pos = ((int) (Math.random() * armazem.size()));
			if (armazem.get(Pos).getElemento() instanceof Floor
					&& ((Floor) armazem.get(Pos).getElemento()).getDock() == false) {
				ElementoBase elementoOriginal = armazem.get(Pos).getElemento();
				armazem.get(Pos)
						.setElementoBase(
								new FloorSurpresa(
										elementoOriginal.getPosicao(), false));
				PosEncontrada = true;
			}
		}
		System.out.println("Casa surpresa colocada com sucesso.");
	}

}
