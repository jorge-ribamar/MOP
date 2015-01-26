package tps.tp4;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

public class Sokoban extends JFrame implements MouseListener,
		MouseMotionListener {

	private static final long serialVersionUID = 8062973277827147723L;
	public static Nivel nivelAtual = null;
	private static Direction lastDirection = null;
	public static String NomeJogador = null;
	public JPanel panel = null;
	private JPanel panel1;
	public static int scale = 2;
	private static boolean GameOver = false;

	public static ImageIcon imageIconWallOriginal = loadImageIcon("files/yoshi-32-wall.png");
	public static ImageIcon imageIconDockOriginal = loadImageIcon("files/yoshi-32-dock.png");
	public static ImageIcon imageIconFloorOriginal = loadImageIcon("files/yoshi-32-floor.png");
	public static ImageIcon imageIconWorkDockOriginal = loadImageIcon("files/yoshi-32-worker-docked.png");
	public static ImageIcon imageIconWorkerOriginal = loadImageIcon("files/yoshi-32-worker.png");
	public static ImageIcon imageIconBoxDockOriginal = loadImageIcon("files/yoshi-32-box-docked.png");
	public static ImageIcon imageIconBoxOriginal = loadImageIcon("files/yoshi-32-box.png");
	public static ImageIcon imageIconSurpresaOriginal = loadImageIcon("files/surpresa.png");
	public static ImageIcon imageIconFantasmaOriginal = loadImageIcon("files/fantasma.png");
	public static ImageIcon fundo = loadImageIcon("files/fundo.png");

	public static ImageIcon imageIconWall;
	public static ImageIcon imageIconDock;
	public static ImageIcon imageIconFloor;
	public static ImageIcon imageIconWorkDock;
	public static ImageIcon imageIconWorker;
	public static ImageIcon imageIconBoxDock;
	public static ImageIcon imageIconBox;
	public static ImageIcon imageIconSurpresa;
	public static ImageIcon imageIconFantasma;

	public static Sound somPassos = new Sound("Passo2.wav");
	public static Sound somPassosBox = new Sound("EmpurrarCaixa2.wav");
	public static Sound somPassarNivel = new Sound("Nivel2.wav");
	public static Sound somBoxDock = new Sound("Fechar2.wav");
	public static Sound somBoxUndock = new Sound("Falhar.wav");
	public static JLabel labelTabuleiro = null;
	public static JLabel label1 = null;
	public static JLabel label3 = null;

	Sokoban() {
		nivelAtual = null;
		addMouseListener(this);
		addMouseMotionListener(this);
		ScaleIcons();
	}

	/** Returns an ImageIcon, or null if the path was invalid. */
	protected static ImageIcon loadImageIcon(String path) {

		// getResource - verifica se o recurso existe, constroi um path com o
		// pathname modificado, para o file system, do package da classe
		// Nivel
		java.net.URL imgURL = Nivel.class.getResource(path);
		if (imgURL != null) {
			ImageIcon ic = new ImageIcon(imgURL);
			System.out.println("Image: " + path + " loaded");
			return ic;
		} else {
			System.err.println("Couldn't find file: " + path);
			return null;
		}
	}

	public static ImageIcon scale(int scale, ImageIcon icon) {

		BufferedImage bi = new BufferedImage(scale * icon.getIconWidth(), scale
				* icon.getIconHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = bi.createGraphics();
		g.scale(scale, scale);
		icon.paintIcon(null, g, 0, 0);
		g.dispose();

		return new ImageIcon(bi);

	}

	private void ScaleIcons() {
		imageIconWall = scale(scale, imageIconWallOriginal);
		imageIconDock = scale(scale, imageIconDockOriginal);
		imageIconFloor = scale(scale, imageIconFloorOriginal);
		imageIconWorkDock = scale(scale, imageIconWorkDockOriginal);
		imageIconWorker = scale(scale, imageIconWorkerOriginal);
		imageIconBoxDock = scale(scale, imageIconBoxDockOriginal);
		imageIconBox = scale(scale, imageIconBoxOriginal);
		imageIconSurpresa = scale(scale, imageIconSurpresaOriginal);
		imageIconFantasma = scale(scale, imageIconFantasmaOriginal);
	}

	private void teclas(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			System.out.println("UP");
			Direction direcao = Direction.North;
			if (Sokoban.nivelAtual.trabalhador.moverComCaixa(direcao)) {
				lastDirection = direcao;
				ActualizarNivel(direcao, true);
			} else {
				lastDirection = null;
				ActualizarNivel(direcao, false);
			}
		} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			System.out.println("DOWN");
			Direction direcao = Direction.South;
			if (Sokoban.nivelAtual.trabalhador.moverComCaixa(direcao)) {
				ActualizarNivel(direcao, true);
				lastDirection = direcao;
			} else {
				ActualizarNivel(direcao, false);
				lastDirection = null;
			}
		} else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			System.out.println("LEFT");
			Direction direcao = Direction.West;
			if (Sokoban.nivelAtual.trabalhador.moverComCaixa(direcao)) {
				ActualizarNivel(direcao, true);
				lastDirection = direcao;
			} else {
				lastDirection = null;
				ActualizarNivel(direcao, false);
			}
		} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			System.out.println("RIGHT");
			Direction direcao = Direction.East;
			if (Sokoban.nivelAtual.trabalhador.moverComCaixa(direcao)) {
				ActualizarNivel(direcao, true);
				lastDirection = direcao;
			} else {
				lastDirection = null;
				ActualizarNivel(direcao, false);
			}
		} else if (e.getKeyCode() == KeyEvent.VK_B) {
			System.out.println("B");
			if (lastDirection != null) {
				Direction direcao = lastDirection;
				Sokoban.nivelAtual.trabalhador.move(lastDirection.oposta());
				Sokoban.nivelAtual
						.getArmazem()
						.get(Sokoban.nivelAtual.trabalhador.getPosicao() - 1
								+ 2 * lastDirection.getDeslocamento())
						.move(lastDirection.oposta());
				lastDirection = null;
				ActualizarNivelBack(direcao.oposta());
			}
		} else if (e.getKeyCode() == KeyEvent.VK_R) {
			System.out.print("R");
			Sokoban.nivelAtual.ContrutorArmazem();
			criarNivelAtual();
			return;
		} else if (e.getKeyCode() == KeyEvent.VK_M) {
			System.out.print("M");
			criarMenuInicial();
			return;
		}
		if (lastDirection == null) {
			label1.getComponent(2).setEnabled(false);
		} else {
			label1.getComponent(2).setEnabled(true);
		}
		if (nivelAtual.NivelCompleto()) {
			passarNivel();
		}
	}

	public final void alterarHighScore(String Nivel) {
		String oldFileURL = "src/tps/tp4/files/nivel" + Nivel + ".txt";
		String tmpFileURL = "src/tps/tp4/files/nivel" + Nivel + "temp.txt";
		boolean existeHighScore = false;
		BufferedReader br = null;
		BufferedWriter bw = null;
		try {
			br = new BufferedReader(new FileReader(oldFileURL));
			bw = new BufferedWriter(new FileWriter(tmpFileURL));
			String line;
			while ((line = br.readLine()) != null) {
				String[] linha = line.split(" ");
				if (linha[0].equals("H")) {
					existeHighScore = true;
					bw.write("H " + Sokoban.nivelAtual.getnPassos() + " "
							+ Sokoban.NomeJogador + "\n");
				} else {
					bw.write(line + "\n");
				}

			}
			if (!existeHighScore) {
				bw.write("H " + Sokoban.nivelAtual.getnPassos() + " "
						+ Sokoban.NomeJogador + "\n");
			}
		} catch (Exception e) {
			return;
		} finally {
			try {
				if (br != null)
					br.close();
			} catch (IOException e) {
				//
			}
			try {
				if (bw != null)
					bw.close();
			} catch (IOException e) {
				//
			}
		}
		// Once everything is complete, delete old file..
		File oldFile = new File(oldFileURL);
		oldFile.delete();

		// And rename tmp file's name to old file name
		File newFile = new File(tmpFileURL);
		newFile.renameTo(oldFile);

	}

	public final void passarNivel() {
		lastDirection = null;
		String NivelAtual = nivelAtual.getNivel();
		System.out.println(NivelAtual);
		if (nivelAtual.HighScore > nivelAtual.getnPassos()
				|| nivelAtual.HighScore == 0) {
			alterarHighScore(NivelAtual);
		}
		int ProximoNivel = Integer.parseInt(NivelAtual.substring(NivelAtual
				.length() - 2)) + 1;
		System.out.println(ProximoNivel);
		somPassarNivel.rewind();
		somPassarNivel.play();
		try {
			if (ProximoNivel < 10) {
				nivelAtual = new Nivel("0" + Integer.toString(ProximoNivel));
			} else {
				nivelAtual = new Nivel(Integer.toString(ProximoNivel));
			}
			nivelAtual.ContrutorArmazem();
			getContentPane().removeAll();
			getContentPane().setLayout(new BorderLayout());
			criarNivelAtual();
			setLocationRelativeTo(null);
		} catch (FileNotFoundException e) {
			terminarJogo();
		}
	}

	public final void criarNivelAtual() {
		getContentPane().removeAll();
		label1 = new JLabel(("Bot�es"));
		label1.setLayout(new GridLayout(1, 0));
		JButton ButMenu = new JButton("Menu (m)");
		JButton ButRestart = new JButton("Restart (r)");
		JButton ButBack = new JButton("Back (b)");
		ButMenu.addActionListener(new ButtonActionListener2());
		ButRestart.addActionListener(new ButtonActionListener2());
		ButBack.addActionListener(new ButtonActionListener2());
		if (lastDirection == null) {
			ButBack.setEnabled(false);
		} else {
			ButBack.setEnabled(true);
		}

		label1.add(ButMenu);
		label1.add(ButRestart);
		label1.add(ButBack);

		JLabel botoesScale = new JLabel(("Bot�es Scale"));
		botoesScale.setLayout(new GridLayout(1, 0));
		JButton ButAumentar = new JButton(" + ");
		ButAumentar.addActionListener(new ButtonActionListener2());
		JButton ButDiminuir = new JButton(" - ");
		ButDiminuir.addActionListener(new ButtonActionListener2());
		botoesScale.add(ButDiminuir);
		botoesScale.add(ButAumentar);
		botoesScale.setVisible(true);
		label1.add(botoesScale);
		label1.setVisible(true);
		label1.setPreferredSize(new Dimension(Sokoban.nivelAtual.getnColunas()
				* 32 * scale, 32));
		if (scale == 1) {
			ButDiminuir.setEnabled(false);
		} else {
			ButDiminuir.setEnabled(true);
			if (scale == 3) {
				ButAumentar.setEnabled(false);
			} else {
				ButAumentar.setEnabled(true);
			}
		}

		labelTabuleiro = new JLabel("Jogo");

		GridLayout tabelaJogo = new GridLayout(nivelAtual.getArmazem().size()
				/ nivelAtual.getnColunas(), nivelAtual.getnColunas());
		labelTabuleiro.setLayout(tabelaJogo);
		for (WarehousePlace celula : nivelAtual.getArmazem()) {
			labelTabuleiro.add(celula.getImagem());
		}
		labelTabuleiro.setOpaque(true);
		labelTabuleiro.setVisible(true);
		labelTabuleiro.setPreferredSize(new Dimension(Sokoban.nivelAtual
				.getnColunas() * 32 * scale, ((Sokoban.nivelAtual.getArmazem()
				.size()) / Sokoban.nivelAtual.getnColunas()) * 32 * scale));

		label3 = new JLabel(("Passos"));
		label3.setLayout(new GridLayout(0, 1));
		label3.setPreferredSize(new Dimension(Sokoban.nivelAtual.getnColunas()
				* 32 * scale, 32 * 2));
		label3.setText("");
		label3.setBackground(new Color(222, 214, 173));
		label3.setOpaque(true);

		JLabel label3_1 = new JLabel();
		label3_1.setText("Passos: " + Sokoban.nivelAtual.getnPassos()
				+ " | High Score: " + nivelAtual.HighScore);
		label3_1.setHorizontalAlignment(SwingConstants.CENTER);

		label3_1.setFont(new Font("Courier", Font.BOLD, 20));
		// // // // colocar uma cor nas letras
		label3_1.setForeground(new Color(95, 95, 95));
		label3_1.setPreferredSize(new Dimension(Sokoban.nivelAtual
				.getnColunas() * 32 * scale, 32));

		label3.add(label3_1);

		JLabel label3_2 = new JLabel();
		if (nivelAtual.Recordista == null) {
			label3_2.setText("Nivel: " + Sokoban.nivelAtual.getNivel()
					+ " | Sem Recorde Registado");
		} else {
			label3_2.setText("Nivel: " + Sokoban.nivelAtual.getNivel()
					+ " | Recordista: " + nivelAtual.Recordista);
		}

		label3_2.setHorizontalAlignment(SwingConstants.CENTER);

		label3_2.setFont(new Font("Courier", Font.BOLD, 20));
		// // // // colocar uma cor nas letras

		label3_2.setForeground(new Color(95, 95, 95));
		label3_2.setPreferredSize(new Dimension(Sokoban.nivelAtual
				.getnColunas() * 32 * scale, 32));

		label3.add(label3_2);

		this.getContentPane().add(label1, BorderLayout.NORTH);
		this.getContentPane().add(labelTabuleiro, BorderLayout.CENTER);
		this.getContentPane().add(label3, BorderLayout.SOUTH);

		pack();
		setVisible(true);
	}

	public final void ActualizarNivel(Direction direcao, boolean ArrastouCaixa) {

		int PosWorker = Sokoban.nivelAtual.trabalhador.getPosicao();
		ActualizarPosicao(PosWorker - 1 - direcao.getDeslocamento());
		ActualizarPosicao(PosWorker - 1);
		if (ArrastouCaixa) {
			ActualizarPosicao(PosWorker - 1 + direcao.getDeslocamento());
		}
		JLabel labelPassos = (JLabel) (label3.getComponent(0));
		labelPassos.setText("Passos: " + Sokoban.nivelAtual.getnPassos()
				+ " | High Score: " + nivelAtual.HighScore);
		setVisible(true);
	}

	public final static void ActualizarPosicao(int Posicao) {
		labelTabuleiro.remove(Posicao);
		labelTabuleiro.add(Sokoban.nivelAtual.getArmazem().get(Posicao)
				.getImagem(), Posicao);
		Sokoban.labelTabuleiro.setVisible(true);
	}

	public final void ActualizarNivelBack(Direction direcao) {
		ActualizarNivel(direcao, true);
		int PosWorker = Sokoban.nivelAtual.trabalhador.getPosicao();
		labelTabuleiro.remove(PosWorker - 1 - 2 * direcao.getDeslocamento());
		labelTabuleiro.add(
				Sokoban.nivelAtual.getArmazem()
						.get(PosWorker - 1 - 2 * direcao.getDeslocamento())
						.getImagem(),
				PosWorker - 1 - 2 * direcao.getDeslocamento());
	}

	public final void criarMenuInicial() {
		nivelAtual = null;
		getContentPane().removeAll();
		System.out.println("Jogador: " + NomeJogador);
		// CenterLayout cl = new CenterLayout();
		this.setLayout(null);

		label1 = new JLabel();
		panel1 = new JPanel();

		GridLayout gl = new GridLayout(0, 2);
		panel1.setLayout(gl);
		panel1.setOpaque(true);

		boolean existeNivel = true;

		while (existeNivel) {
			for (int n = 1; n < 100; n++) {
				String nivel;
				if (n < 10) {
					nivel = "0" + n;
				} else {
					nivel = Integer.toString(n);
				}
				String inputFileUrl = "src/tps/tp4/files/nivel" + nivel
						+ ".txt";
				Scanner fileScan = null;
				try {
					fileScan = new Scanner(new File(inputFileUrl));
					System.out.println("Existe n�vel" + n);
					JButton jb = new JButton("Nivel " + n);
					ActionListener al = new ButtonActionListener();
					// este ActionCommand permite identificar o bot�o
					jb.setActionCommand("Botao" + nivel);
					jb.addActionListener(al);
					jb.setVisible(true);

					panel1.add(jb);
				} catch (FileNotFoundException e) {
					System.out.println(e.getMessage());
					existeNivel = false;
					break;
				} finally {
					if (fileScan != null) {
						fileScan.close();
					}
				}
			}
		}

		// label3 = new JLabel();
		// label3.setIcon(fundo);
		// label3.setLocation(0, 0);
		// label3.setVisible(true);

		this.add(panel1);
		panel1.setLocation(300, 250);
		panel1.setSize(300, 350);
		panel1.setVisible(true);

		// this.add(label3);

		this.setSize(900, 750);
		// setSize(400, 370);

		// centrar
		this.setLocationRelativeTo(null);
		getContentPane().repaint();
		setVisible(true);
	}

	@Override
	public void paint(Graphics g) {
		// TODO Auto-generated method stub
		System.out.println("paint");
		super.paint(g);
		if (nivelAtual == null) {
			g.drawImage(fundo.getImage(), 0, 20, 900, 750, 0, 0, 900, 730, null);
			panel1.repaint();
		}
	}

	/**
	 * Class auxiliar que � um Listener para um JButton
	 */
	class ButtonActionListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			int nivel = Integer.parseInt(((JButton) e.getSource()).getText()
					.substring(6));
			String Nivel = "";
			if (nivel < 10) {
				Nivel = "0" + nivel;
			} else {
				Nivel = Integer.toString(nivel);
			}
			System.out.println("A definir o nivel atual de Sokoban");
			try {
				nivelAtual = new Nivel(Nivel);
			} catch (FileNotFoundException e1) {
				System.out.println(e1.getMessage());
			}
			nivelAtual.ContrutorArmazem();
			getContentPane().removeAll();
			getContentPane().setLayout(new BorderLayout());
			criarNivelAtual();
			setLocationRelativeTo(null);
		}
	};

	/**
	 * Class auxiliar que � um Listener para um JButton
	 */
	class ButtonActionListener2 implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			JButton botao = (JButton) e.getSource();
			String NomeBotão = botao.getText();
			if (NomeBotão.equals("Menu (m)")) {
				criarMenuInicial();
				return;
			} else if (NomeBotão.equals("Restart (r)")) {
				Sokoban.nivelAtual.ContrutorArmazem();
				System.out.println("Nivel reiniciado.");
			} else if (NomeBotão.equals("Back (b)")) {
				if (lastDirection != null) {
					Sokoban.nivelAtual.trabalhador.move(lastDirection.oposta());
					Sokoban.nivelAtual
							.getArmazem()
							.get(Sokoban.nivelAtual.trabalhador.getPosicao()
									- 1 + 2 * lastDirection.getDeslocamento())
							.move(lastDirection.oposta());
					lastDirection = null;
				}
			} else if (NomeBotão.equals(" + ")) {
				scale += 1;
				ScaleIcons();
				System.out.println("Imagens aumentadas.");
				if (scale == 3) {
					botao.setEnabled(false);
				} else {
					botao.setEnabled(true);
				}
			} else if (NomeBotão.equals(" - ")) {
				scale -= 1;
				ScaleIcons();
				System.out.println("Imagens diminuidas.");
				if (scale == 1) {
					botao.setEnabled(false);
				} else {
					botao.setEnabled(true);
				}
			}
			criarNivelAtual();
			if (nivelAtual.NivelCompleto()) {
				passarNivel();
			}
		}

	};

	class ButtonActionListener3 implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			JButton botao = (JButton) e.getSource();
			String NomeBotão = botao.getText();
			if (NomeBotão.equals("Menu (m)")) {
				criarMenuInicial();
				return;
			} else if (NomeBotão.equals("Sair (s)")) {
				System.exit(0);
				return;
			}
		}
	};

	public void inicializar() {
		setTitle("Sokoban");
		setSize(600, 400);
		setLocationRelativeTo(null); // to center a frame
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);

		// uma forma de apanhar keys em termos globais a uma aplica��o
		KeyboardFocusManager.getCurrentKeyboardFocusManager()
				.addKeyEventDispatcher(new KeyEventDispatcher() {
					public boolean dispatchKeyEvent(KeyEvent e) {
						// System.out.println(e);
						if (e.getID() == KeyEvent.KEY_PRESSED) {
							System.out.print("key pressed - ");
							teclas(e);
						}
						return false;
					}
				});
	}

	public void getPosCursor(MouseEvent e) {
		// Arredondamento por defeito (32 � a largura da imagem em pixels)
		int PosX = (int) Math.floor(e.getX() / (32 * scale)) + 1;
		int PosY = (int) Math.floor((e.getY()) / (32 * scale));

		// PosX = ((WarehousePlace)e.getSource()).x;

		int PosGlobal = PosX + (nivelAtual.getnColunas() * (PosY - 1));

		System.out.println("Pos Cursor = (" + PosX + ", " + PosY + ") , Pos = "
				+ PosGlobal);

		movimentoComCursor(PosX, PosY);

		if (nivelAtual.NivelCompleto()) {
			passarNivel();
		}

	}

	private void movimentoComCursor(int posX, int posY) {

		int PosYWorker = (int) Math.floor((nivelAtual.trabalhador.getPosicao())
				/ nivelAtual.getnColunas() + 1);
		int PosXWorker = (int) Math.floor(nivelAtual.trabalhador.getPosicao()
				% nivelAtual.getnColunas());
		if (Math.abs(PosYWorker - posY) > Math.abs(PosXWorker - posX)) {
			movimentoVertical(posX, posY, PosXWorker, PosYWorker, false);
		} else if (Math.abs(PosXWorker - posX) >= Math.abs(PosYWorker - posY)) {
			movimentoHorizontal(posX, posY, PosXWorker, PosYWorker, false);
		}
	}

	private void movimentoVertical(final int posX, final int posY,
			int PosXWorker, int PosYWorker, boolean AntesMovHorizontal) {
		Direction direcao = null;
		if (PosYWorker == posY && PosXWorker == posX)
			return;
		if (PosYWorker > posY) {
			direcao = Direction.North;
		} else if (PosYWorker < posY) {
			direcao = Direction.South;
		} else {
			movimentoHorizontal(posX, posY, PosXWorker, PosYWorker, true);
			return;
		}
		if (nivelAtual.trabalhador.move(direcao)) {
			// criarNivelAtual();
			ActualizarPosicao(Sokoban.nivelAtual.trabalhador.getPosicao() - 1);
			ActualizarPosicao(Sokoban.nivelAtual.trabalhador.getPosicao()
					- direcao.getDeslocamento() - 1);
			if (nivelAtual.trabalhador.NextIsBox(direcao))
				ActualizarPosicao(Sokoban.nivelAtual.trabalhador.getPosicao()
						+ direcao.getDeslocamento() - 1);

			this.setVisible(true);

			if (nivelAtual.NivelCompleto()) {
				passarNivel();
			}
			// try {
			// Thread.sleep(200);
			// } catch (InterruptedException e) {
			// e.printStackTrace();
			// }
			Timer t = new Timer(300, new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					movimentoComCursor(posX, posY);
				}
			});
			t.setRepeats(false);
			t.start();

		} else if (!AntesMovHorizontal) {
			// criarNivelAtual();
			if (nivelAtual.NivelCompleto()) {
				passarNivel();
			}
			movimentoHorizontal(posX, posY, PosXWorker, PosYWorker, true);
		}

	}

	private void movimentoHorizontal(final int posX, final int posY,
			int PosXWorker, int PosYWorker, boolean AntesMovVertical) {

		Direction direcao = null;
		if (PosYWorker == posY && PosXWorker == posX)
			return;
		if (PosXWorker > posX) {
			direcao = Direction.West;
		} else if (PosXWorker < posX) {
			direcao = Direction.East;
		} else {
			movimentoVertical(posX, posY, PosXWorker, PosYWorker, true);
			return;
		}
		if (nivelAtual.trabalhador.move(direcao)) {
			// criarNivelAtual();

			ActualizarPosicao(Sokoban.nivelAtual.trabalhador.getPosicao()
					- direcao.getDeslocamento() - 1);
			ActualizarPosicao(Sokoban.nivelAtual.trabalhador.getPosicao() - 1);
			if (nivelAtual.trabalhador.NextIsBox(direcao))
				ActualizarPosicao(Sokoban.nivelAtual.trabalhador.getPosicao()
						+ direcao.getDeslocamento() - 1);
			this.setVisible(true);

			if (nivelAtual.NivelCompleto()) {
				passarNivel();
			}
			Timer t = new Timer(300, new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					movimentoComCursor(posX, posY);
				}
			});
			t.setRepeats(false);
			t.start();
		} else if (!AntesMovVertical) {
			// criarNivelAtual();
			if (nivelAtual.NivelCompleto()) {
				passarNivel();
			}
			movimentoVertical(posX, posY, PosXWorker, PosYWorker, true);
		}

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		String eventDescription = "Mouse Clicked";
		System.out
				.println((eventDescription + " (" + e.getX() + "," + e.getY()
						+ ")" + " detected on " + e.getComponent().getClass()
						.getName()));
		getPosCursor(e);

	}

	@Override
	public void mousePressed(MouseEvent e) {

	}

	@Override
	public void mouseReleased(MouseEvent e) {

	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}

	@Override
	public void mouseDragged(MouseEvent e) {
		int PosX = (int) Math.floor(e.getX() / (32 * scale)) + 1;
		int PosY = (int) Math.floor((e.getY()) / (32 * scale));

		int PosYWorker = (int) Math.floor((nivelAtual.trabalhador.getPosicao())
				/ nivelAtual.getnColunas() + 1);
		int PosXWorker = (int) Math.floor(nivelAtual.trabalhador.getPosicao()
				% nivelAtual.getnColunas());

		Direction direcao = null;
		if (PosX + 1 == PosXWorker) {
			direcao = Direction.West;
		}
		if (PosX - 1 == PosXWorker) {
			direcao = Direction.East;
		}
		if (PosY + 1 == PosYWorker) {
			direcao = Direction.North;
		}
		if (PosY - 1 == PosYWorker) {
			direcao = Direction.South;
		}
		if (direcao != null) {
			if (nivelAtual.trabalhador.moverComCaixa(direcao)) {
				lastDirection = direcao;
			} else {
				lastDirection = null;
			}
		}
		criarNivelAtual();
		if (nivelAtual.NivelCompleto()) {
			passarNivel();
		}

	}

	@Override
	public void mouseMoved(MouseEvent e) {

	}

	public final void terminarJogo() {
		getContentPane().removeAll();

		label1 = new JLabel("Bot�es");
		label1.setLayout(new GridLayout(1, 0));
		label1.setPreferredSize(new Dimension(500, 75));
		JButton ButMenu = new JButton("Menu (m)");
		JButton ButSair = new JButton("Sair (s)");

		ButMenu.setFont(new Font(ButMenu.getFont().getName(),
				Font.CENTER_BASELINE, 20));
		ButSair.setFont(new Font(ButMenu.getFont().getName(),
				Font.CENTER_BASELINE, 20));

		ButMenu.addActionListener(new ButtonActionListener3());
		ButSair.addActionListener(new ButtonActionListener3());

		label1.add(ButMenu);
		label1.add(ButSair);
		label1.setVisible(true);

		labelTabuleiro = new JLabel("Parab�ns!!! Concluio o jogo!!!!");

		labelTabuleiro.setVerticalAlignment(SwingConstants.CENTER);
		labelTabuleiro.setHorizontalAlignment(SwingConstants.CENTER);
		labelTabuleiro.setBackground(new Color(0, 200, 100));
		labelTabuleiro.setPreferredSize(new Dimension(500, 425));
		labelTabuleiro.setFont(new Font("Courier", Font.BOLD, 25));
		labelTabuleiro.setOpaque(true);
		labelTabuleiro.setVisible(true);

		this.getContentPane().add(label1, BorderLayout.NORTH);
		this.getContentPane().add(labelTabuleiro, BorderLayout.CENTER);

		setSize(500, 400);
		setVisible(true);

	}

	public final void terminarJogoGAMEOVER() {

		for (SurpresaFantasma a : SurpresaFantasma.Fantasmas) {
			a.timer.cancel();
		}

		this.getContentPane().removeAll();

		label1 = new JLabel("Bot�es");
		label1.setPreferredSize(new Dimension(500, 75));
		label1.setLayout(new GridLayout(1, 0));
		JButton ButMenu = new JButton("Menu (m)");
		JButton ButSair = new JButton("Sair (s)");

		ButMenu.setFont(new Font(ButMenu.getFont().getName(),
				Font.CENTER_BASELINE, 20));
		ButSair.setFont(new Font(ButMenu.getFont().getName(),
				Font.CENTER_BASELINE, 20));

		ButMenu.addActionListener(new ButtonActionListener3());
		ButSair.addActionListener(new ButtonActionListener3());

		label1.add(ButMenu);
		label1.add(ButSair);
		label1.setVisible(true);

		labelTabuleiro = new JLabel("Que Pena, PERDEU!!!");
		labelTabuleiro.setPreferredSize(new Dimension(500, 225));
		labelTabuleiro.setFont(new Font("Courier", Font.BOLD, 40));
		labelTabuleiro.setVerticalAlignment(SwingConstants.CENTER);
		labelTabuleiro.setHorizontalAlignment(SwingConstants.CENTER);
		labelTabuleiro.setBackground(new Color(0, 200, 100));
		labelTabuleiro.setOpaque(true);
		labelTabuleiro.setVisible(true);

		label3 = new JLabel("Chegou ao nivel n� "
				+ Sokoban.nivelAtual.getNivel());
		label3.setFont(new Font("Courier", Font.BOLD, 30));
		label3.setVerticalAlignment(SwingConstants.TOP);
		label3.setHorizontalAlignment(SwingConstants.CENTER);
		label3.setPreferredSize(new Dimension(500, 100));
		label3.setBackground(new Color(0, 200, 100));
		label3.setOpaque(true);
		label3.setVisible(true);

		this.getContentPane().add(label1, BorderLayout.NORTH);
		this.getContentPane().add(labelTabuleiro, BorderLayout.CENTER);
		this.getContentPane().add(label3, BorderLayout.SOUTH);

		setSize(500, 400);
		setVisible(true);

	}

	public static boolean getGameOver() {
		return GameOver;
	}

	public void setGameOver(boolean gameOver) {
		if (gameOver == true) {
			System.out.println("GAME OVER!!!");
			terminarJogoGAMEOVER();
		}
		GameOver = gameOver;
	}

	public static void main(String[] args) {

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				NomeJogador pedidoNome = new NomeJogador();
				pedidoNome.init();
			}
		});

	}
}
