import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class RobotGUI {

	private JFrame frame;
	private JTextField txtNomeRobot;
	private JTextField txtDistance;
	private JTextField txtAngle;
	private JTextField txtRadius;
	private JTextField txtOffsetLeft;
	private JTextField txtOffsetRight;
	private JTextArea txtrLogging;
	private JCheckBox chckbxCheckLogging;
	private JCheckBox chckbxVaguear;
	private JCheckBox chckbxEvitar;
	private JCheckBox chckbxGestor;
	private JButton btnConectar;
	private JLabel lblConectado;

	// Variaveis Globais
	private String name;
	private int offSetLeft, offSetRight, angle, distance, radius;
	private boolean wandering; // vaguear
	private boolean avoid; // evitar
	private Gestor manager; // gestor
	
	public static final byte ID = 2;
	
	private Comunicar inbox;
	private Comunicar gestorBox = new Comunicar("gestor");

	// private RobotLegoEV3 robot;
	private myRobot robot;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RobotGUI window = new RobotGUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Iniciar variaveis
	 */

	public void init() {
		this.inbox = new Comunicar("gui");
		this.name = ""; // default - EV3
		this.offSetLeft = 0;
		this.offSetRight = 0;
		this.angle = 90;
		this.distance = 20;
		this.radius = 10;

		this.txtNomeRobot.setText(name);
		this.txtOffsetLeft.setText(String.valueOf(offSetLeft));
		this.txtOffsetRight.setText(String.valueOf(offSetRight));
		this.txtRadius.setText(String.valueOf(radius));
		this.txtAngle.setText(String.valueOf(angle));
		this.txtDistance.setText(String.valueOf(distance));
		
	}

	/**
	 * Fun��o que regista o texto no logger caso este esteja activo
	 * 
	 * @param text
	 */
	public void logger(String text) {
		if (txtrLogging.isEnabled()) {
			txtrLogging.append(text + "\n");
		}
	}

	public void clearLog() {
		if (!chckbxCheckLogging.isEnabled()) {
			txtrLogging.setEnabled(true);
			txtrLogging.setText("");
			txtrLogging.setEnabled(false);
		} else {
			txtrLogging.setText("");
		}
	}



	/**
	 * Fun��o para fazer liga��o ao robot EV3
	 * 
	 * @param name
	 */
	public boolean connectRobot(String name) {
		if(name.equals("") || name == null || name.length()==0) {
			return false;
		}
		//this.robot = new RobotLegoEV3();
		this.robot = new myRobot();
		boolean control = true;
		while (control)
			if (robot.OpenEV3(name)) {
				btnConectar.setText("Desligar");
				lblConectado.setBackground(Color.GREEN);
				control = false;
			} else {
				btnConectar.setText("Ligar");
				lblConectado.setBackground(Color.RED);
			}
		return true;
	}

	public void disconnectRobot() {
		robot.CloseEV3();
		btnConectar.setText("Ligar");
		lblConectado.setBackground(Color.RED);
	}

	/**
	 * Create the application.
	 */
	public RobotGUI() {
		initialize();
		init();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("..:FSO-TP1:..");
		frame.setResizable(false);
		frame.getContentPane().setBackground(Color.BLACK);
		frame.setBounds(100, 100, 584, 600);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setLocationRelativeTo(null);

		JPanel panelConeccao = new JPanel();
		panelConeccao.setBorder(
				new TitledBorder(null, "CONEC\u00C7\u00C3O", TitledBorder.LEFT, TitledBorder.TOP, null, Color.WHITE));
		panelConeccao.setBackground(Color.BLACK);
		panelConeccao.setBounds(10, 10, 548, 79);
		frame.getContentPane().add(panelConeccao);
		panelConeccao.setLayout(null);

		JLabel lblNomeRobot = new JLabel("Nome");
		lblNomeRobot.setHorizontalAlignment(SwingConstants.LEFT);
		lblNomeRobot.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNomeRobot.setForeground(Color.WHITE);
		lblNomeRobot.setBounds(10, 24, 47, 40);
		panelConeccao.add(lblNomeRobot);

		txtNomeRobot = new JTextField();
		txtNomeRobot.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent event) {
				if (event.getKeyChar() == KeyEvent.VK_ENTER) {
					name = txtNomeRobot.getText();
				}
			}
		});
		txtNomeRobot.setHorizontalAlignment(SwingConstants.CENTER);
		txtNomeRobot.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txtNomeRobot.setBounds(67, 34, 226, 21);
		panelConeccao.add(txtNomeRobot);
		txtNomeRobot.setColumns(10);

		btnConectar = new JButton("Conectar");
		btnConectar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (btnConectar.getText().equals("Conectar")) {
					connectRobot(name);
				} else {
					disconnectRobot();
				}
			}
		});
		btnConectar.setFont(new Font("Tahoma", Font.PLAIN, 10));
		btnConectar.setBounds(420, 34, 112, 19);
		panelConeccao.add(btnConectar);

		lblConectado = new JLabel("");
		lblConectado.setBackground(Color.RED);
		lblConectado.setOpaque(true);
		lblConectado.setForeground(Color.RED);
		lblConectado.setBounds(420, 55, 112, 3);
		panelConeccao.add(lblConectado);

		JPanel panelRobot = new JPanel();
		panelRobot.setBorder(new TitledBorder(
				new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "ROBOT",
				TitledBorder.LEFT, TitledBorder.TOP, null, new Color(255, 255, 255)));
		panelRobot.setBackground(Color.BLACK);
		panelRobot.setBounds(10, 99, 548, 214);
		frame.getContentPane().add(panelRobot);
		panelRobot.setLayout(null);

		JLabel lblDistancia = new JLabel("Dist\u00E2ncia");
		lblDistancia.setHorizontalAlignment(SwingConstants.LEFT);
		lblDistancia.setForeground(Color.WHITE);
		lblDistancia.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblDistancia.setBounds(10, 33, 60, 50);
		panelRobot.add(lblDistancia);

		JLabel lblAngulo = new JLabel("\u00C2ngulo");
		lblAngulo.setHorizontalAlignment(SwingConstants.LEFT);
		lblAngulo.setForeground(Color.WHITE);
		lblAngulo.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblAngulo.setBounds(10, 93, 60, 50);
		panelRobot.add(lblAngulo);

		JLabel lblRaio = new JLabel("Raio");
		lblRaio.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblRaio.setForeground(Color.WHITE);
		lblRaio.setBounds(10, 153, 60, 50);
		panelRobot.add(lblRaio);

		txtDistance = new JTextField();
		txtDistance.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent event) {
				if (event.getKeyChar() == KeyEvent.VK_ENTER) {
					if(Integer.parseInt(txtDistance.getText()) >= 0 && Integer.parseInt(txtDistance.getText()) <= 127) {
						txtDistance.setBackground(Color.white);
					}else {
						txtDistance.setBackground(Color.red);
					}
					distance = Integer.parseInt(txtDistance.getText());
				}
			}
		});
		txtDistance.setHorizontalAlignment(SwingConstants.CENTER);
		txtDistance.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txtDistance.setBounds(80, 49, 76, 19);
		panelRobot.add(txtDistance);
		txtDistance.setColumns(10);

		txtAngle = new JTextField();
		txtAngle.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent event) {
				if (event.getKeyChar() == KeyEvent.VK_ENTER) {
					angle = Integer.parseInt(txtAngle.getText());
				}
			}
		});
		txtAngle.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txtAngle.setHorizontalAlignment(SwingConstants.CENTER);
		txtAngle.setBounds(80, 111, 76, 19);
		panelRobot.add(txtAngle);
		txtAngle.setColumns(10);

		txtRadius = new JTextField();
		txtRadius.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent event) {
				if (event.getKeyChar() == KeyEvent.VK_ENTER) {
					radius = Integer.parseInt(txtRadius.getText());
				}
			}
		});
		txtRadius.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txtRadius.setHorizontalAlignment(SwingConstants.CENTER);
		txtRadius.setBounds(80, 171, 76, 19);
		panelRobot.add(txtRadius);
		txtRadius.setColumns(10);

		JButton btnAvancar = new JButton("Avan\u00E7ar");
		btnAvancar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				gestorBox.enviarMsg(new byte[] {ID,Comunicar.AVANCAR, Byte.parseByte(txtDistance.getText())});
			}
		});
		btnAvancar.setFont(new Font("Tahoma", Font.PLAIN, 10));
		btnAvancar.setBounds(272, 44, 76, 32);
		panelRobot.add(btnAvancar);

		JButton btnParar = new JButton("Parar");
		btnParar.setFont(new Font("Tahoma", Font.PLAIN, 10));
		btnParar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
			}
		});
		btnParar.setBounds(272, 104, 76, 32);
		panelRobot.add(btnParar);

		JButton btnRecuar = new JButton("Recuar");
		btnRecuar.setFont(new Font("Tahoma", Font.PLAIN, 10));
		btnRecuar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
			}
		});
		btnRecuar.setBounds(272, 164, 76, 32);
		panelRobot.add(btnRecuar);

		JButton btnEsquerda = new JButton("Esquerda");
		btnEsquerda.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
			}
		});
		btnEsquerda.setFont(new Font("Tahoma", Font.PLAIN, 10));
		btnEsquerda.setBounds(173, 104, 76, 32);
		panelRobot.add(btnEsquerda);

		JButton btnDireita = new JButton("Direita");
		btnDireita.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
			}
		});
		btnDireita.setFont(new Font("Tahoma", Font.PLAIN, 10));
		btnDireita.setBounds(371, 104, 76, 32);
		panelRobot.add(btnDireita);

		JLabel lblOffsetEsq = new JLabel("Offset Esquerdo");
		lblOffsetEsq.setVerticalAlignment(SwingConstants.TOP);
		lblOffsetEsq.setForeground(Color.WHITE);
		lblOffsetEsq.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblOffsetEsq.setHorizontalAlignment(SwingConstants.CENTER);
		lblOffsetEsq.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		lblOffsetEsq.setBounds(155, 20, 113, 19);
		panelRobot.add(lblOffsetEsq);

		txtOffsetLeft = new JTextField();
		txtOffsetLeft.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent event) {
				if (event.getKeyChar() == KeyEvent.VK_ENTER) {
					offSetLeft = Integer.parseInt(txtOffsetLeft.getText());
				}
			}
		});
		txtOffsetLeft.setHorizontalAlignment(SwingConstants.CENTER);
		txtOffsetLeft.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txtOffsetLeft.setBounds(173, 49, 76, 19);
		panelRobot.add(txtOffsetLeft);
		txtOffsetLeft.setColumns(10);

		txtOffsetRight = new JTextField();
		txtOffsetRight.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent event) {
				if (event.getKeyChar() == KeyEvent.VK_ENTER) {
					offSetRight = Integer.parseInt(txtOffsetRight.getText());
				}
			}
		});
		txtOffsetRight.setHorizontalAlignment(SwingConstants.CENTER);
		txtOffsetRight.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txtOffsetRight.setBounds(371, 49, 76, 19);
		panelRobot.add(txtOffsetRight);
		txtOffsetRight.setColumns(10);

		JLabel lblOffsetDrt = new JLabel("Offset Direito");
		lblOffsetDrt.setForeground(Color.WHITE);
		lblOffsetDrt.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblOffsetDrt.setHorizontalAlignment(SwingConstants.CENTER);
		lblOffsetDrt.setVerticalAlignment(SwingConstants.TOP);
		lblOffsetDrt.setBounds(359, 20, 95, 19);
		panelRobot.add(lblOffsetDrt);

		chckbxVaguear = new JCheckBox("Vaguear");
		chckbxVaguear.setEnabled(false);
		chckbxVaguear.setFont(new Font("Tahoma", Font.PLAIN, 15));
		chckbxVaguear.setForeground(Color.WHITE);
		chckbxVaguear.setBackground(Color.BLACK);
		chckbxVaguear.setBounds(461, 48, 81, 21);
		panelRobot.add(chckbxVaguear);

		chckbxEvitar = new JCheckBox("Evitar");
		chckbxEvitar.setEnabled(false);
		chckbxEvitar.setForeground(Color.WHITE);
		chckbxEvitar.setFont(new Font("Tahoma", Font.PLAIN, 15));
		chckbxEvitar.setBackground(Color.BLACK);
		chckbxEvitar.setBounds(461, 108, 81, 21);
		panelRobot.add(chckbxEvitar);

		chckbxGestor = new JCheckBox("Gestor");
		chckbxGestor.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(chckbxGestor.isSelected()){
					chckbxEvitar.setEnabled(true);
					chckbxVaguear.setEnabled(true);
					manager = new Gestor();
				}else {
					chckbxEvitar.setEnabled(false);
					chckbxVaguear.setEnabled(false);
					manager = null;
				}

			}
		});
		chckbxGestor.setForeground(Color.WHITE);
		chckbxGestor.setBackground(Color.BLACK);
		chckbxGestor.setFont(new Font("Tahoma", Font.PLAIN, 15));
		chckbxGestor.setBounds(461, 168, 81, 21);
		panelRobot.add(chckbxGestor);

		JPanel panelLogging = new JPanel();
		panelLogging
				.setBorder(new TitledBorder(null, "LOGGING", TitledBorder.LEFT, TitledBorder.TOP, null, Color.WHITE));
		panelLogging.setBackground(Color.BLACK);
		panelLogging.setBounds(10, 323, 548, 228);
		frame.getContentPane().add(panelLogging);
		panelLogging.setLayout(null);

		chckbxCheckLogging = new JCheckBox("Ativar Logger");
		chckbxCheckLogging.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (chckbxCheckLogging.isSelected()) {
					txtrLogging.setEnabled(true);
					chckbxCheckLogging.setText("Desactivar Logger");
				} else {
					txtrLogging.setEnabled(false);
					chckbxCheckLogging.setText("Ativar Logger");
				}
			}
		});
		chckbxCheckLogging.setSelected(true);
		chckbxCheckLogging.setFont(new Font("Tahoma", Font.PLAIN, 15));
		chckbxCheckLogging.setForeground(Color.WHITE);
		chckbxCheckLogging.setBackground(Color.BLACK);
		chckbxCheckLogging.setBounds(6, 17, 157, 27);
		panelLogging.add(chckbxCheckLogging);

		JButton btnClearLogging = new JButton("Limpar Logger");
		btnClearLogging.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				clearLog();
			}
		});
		btnClearLogging.setFont(new Font("Tahoma", Font.PLAIN, 10));
		btnClearLogging.setBounds(428, 22, 110, 21);
		panelLogging.add(btnClearLogging);

		JScrollPane spLogging = new JScrollPane();
		spLogging.setFont(new Font("Tahoma", Font.PLAIN, 15));
		spLogging.setBackground(Color.BLACK);
		spLogging.setBounds(10, 50, 528, 168);
		panelLogging.add(spLogging);

		txtrLogging = new JTextArea();
		txtrLogging.setEditable(false);
		txtrLogging.setLineWrap(true);
		spLogging.setViewportView(txtrLogging);
	}
}
