package tps.tp4;

import java.awt.FlowLayout;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

public class NomeJogador extends JFrame {

	private static final long serialVersionUID = 1L;

	private FlowLayout layout = null;

	public static Sokoban jogo;

	private JLabel label1 = null;

	private JButton buttonOK = null;

	private JButton buttonSAIR = null;

	private JTextField text = null;

	/**
	 * Este método cria toda a frame e coloca-a visível
	 * 
	 */
	public void init() {

		// uma forma de apanhar keys em termos globais a uma aplicação
		KeyboardFocusManager.getCurrentKeyboardFocusManager()
				.addKeyEventDispatcher(new KeyEventDispatcher() {

					public boolean dispatchKeyEvent(KeyEvent e) {
						// System.out.println(e);
						if (e.getID() == KeyEvent.KEY_PRESSED) {
							Enter(e);
						}
						return false;
					}

					private void Enter(KeyEvent e) {
						if (e.getKeyCode() == KeyEvent.VK_ENTER) {
							System.out.print("key pressed - ENTER");
							Scanner sc = new Scanner(text.getText());
							if (sc.hasNext()) {
								String texto1 = sc.nextLine();
								jogo = new Sokoban();
								Sokoban.NomeJogador = texto1;
								jogo.inicializar();
								jogo.criarMenuInicial();
								KeyboardFocusManager
										.getCurrentKeyboardFocusManager()
										.removeKeyEventDispatcher(this);
							}
							sc.close();
							// Fecha a janela
							dispose();
						} else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
							System.out.print("key pressed - ESCAPE");
							// Fecha a janela
							dispose();
						}
					}

				});

		setTitle("Inserir nome do Jogador");

		// on close button hide and dispose frame
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

		layout = new FlowLayout();
		getContentPane().setLayout(layout);

		label1 = new JLabel("Insira o seu Nome :");
		label1.setHorizontalAlignment(SwingConstants.CENTER);
		getContentPane().add(label1);

		text = new JTextField("Nome", 30);
		getContentPane().add(text);

		// button1
		buttonOK = new JButton("Ok (Enter)");
		buttonOK.setActionCommand("ok");
		getContentPane().add(buttonOK);

		// button2
		buttonSAIR = new JButton("Sair (Esc)");
		buttonSAIR.setActionCommand("Sair");
		getContentPane().add(buttonSAIR);

		// adjust size to minimum as needed
		pack();
		// set location
		setLocationRelativeTo(null); // to center a frame

		// disable resize ---------------
		setResizable(false);

		// set dynamic behavior
		// Listener do botão
		// actionCommand
		ActionListener al = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Scanner sc = new Scanner(text.getText());
				if (sc.hasNext()) {
					String texto1 = sc.nextLine();
					if (((JButton) e.getSource()).getActionCommand().equals(
							"ok")) {
						jogo = new Sokoban();
						Sokoban.NomeJogador = texto1;
						jogo.inicializar();
						jogo.criarMenuInicial();
					}
				}
				sc.close();
				dispose();
				if (((JButton) e.getSource()).getActionCommand().equals("Sair")) {
					dispose();
				}
			}
		};
		buttonOK.addActionListener(al);
		buttonSAIR.addActionListener(al);

		setVisible(true);
	}

}
