package com.sdz.vue;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class FenetreConfig extends JFrame {
	private JPanel contentPane;
	public static JTextField txtNom;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private final ButtonGroup buttonGroup_1 = new ButtonGroup();
	public static JComboBox cbBox;
	private static String type;
	private static String niveau;

	public static String getTypeJeu() {
		return type;
	}

	public static String getNiveau() {
		return niveau;
	}

	public JComboBox getcbBox() {
		return cbBox;
	}

	public FenetreConfig() {
		setIconImage(Toolkit.getDefaultToolkit().getImage("image/mt.PNG"));
		this.setTitle("Configuration");
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setBounds(100, 100, 419, 289);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(26, 13, 355, 185);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblNiveau = new JLabel("Niveau");
		lblNiveau.setBounds(10, 145, 95, 31);
		panel.add(lblNiveau);
		lblNiveau.setFont(new Font("Arial Black", Font.PLAIN, 14));
		
		JLabel lblNewLabel = new JLabel("Nom");
		lblNewLabel.setBounds(10, 11, 95, 31);
		panel.add(lblNewLabel);
		lblNewLabel.setFont(new Font("Arial Black", Font.PLAIN, 14));
		
		txtNom = new JTextField();
		txtNom.setName("");
		txtNom.setBounds(123, 16, 198, 24);
		panel.add(txtNom);
		txtNom.setColumns(10);
		
		JRadioButton rdDebutant = new JRadioButton("Debutant");
		buttonGroup_1.add(rdDebutant);
		rdDebutant.setSelected(true);
		rdDebutant.setBounds(119, 146, 84, 31);
		panel.add(rdDebutant);
		
		JRadioButton rdMedium = new JRadioButton("Expert");
		buttonGroup_1.add(rdMedium);
		rdMedium.setBounds(229, 146, 74, 31);
		panel.add(rdMedium);
		
		JLabel lblNewLabel_2 = new JLabel("Nombre AI");
		lblNewLabel_2.setFont(new Font("Arial Black", Font.PLAIN, 14));
		lblNewLabel_2.setBounds(10, 101, 95, 31);
		panel.add(lblNewLabel_2);
		
		cbBox = new JComboBox();
		cbBox.setModel(new DefaultComboBoxModel(new String[] {"1", "2", "3", "4", "5"}));
		cbBox.setBounds(123, 106, 41, 23);
		panel.add(cbBox);
		
		JLabel lblAge = new JLabel("Age");
		lblAge.setFont(new Font("Arial Black", Font.PLAIN, 14));
		lblAge.setBounds(10, 55, 95, 31);
		panel.add(lblAge);
		
		JComboBox comboBox = new JComboBox();
		String[] ages=new String[93];
		for (int i = 0; i < 93; i++) {
			ages[i] = Integer.toString(i + 8);
		}
		comboBox.setModel(new DefaultComboBoxModel(ages));
		comboBox.setBounds(123, 60, 41, 22);
		panel.add(comboBox);
		
		JButton btnOK = new JButton("OK");
		btnOK.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
				if(rdRapide.isSelected()){
					type=rdRapide.getText();
				} else {
					type=rdAvance.getText();
				}
				if(rdDebutant.isSelected()){
					niveau=rdDebutant.getText();
				} else {
					niveau=rdMedium.getText();
				}
				FramePrincipal frame = new FramePrincipal();
				frame.setVisible(true);
				Jeu jeu = new Jeu(txtNom.getText(),cbBox.getSelectedIndex()+2,type,niveau,frame);    	
				jeu.getTypeJeu().lancer(jeu);
			}
		});
		btnOK.setFont(new Font("Arial", Font.PLAIN, 14));
		btnOK.setBounds(84, 211, 99, 27);
		contentPane.add(btnOK);
		
		JButton btnReset = new JButton("RESET");
		btnReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				rdRapide.setSelected(true);
				rdDebutant.setSelected(true);
				txtNom.setText("");
				txtNom.requestFocus();
				cbBox.setSelectedIndex(0);
			}
		});
		btnReset.setFont(new Font("Arial", Font.PLAIN, 14));
		btnReset.setBounds(221, 211, 99, 27);
		contentPane.add(btnReset);
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FenetreConfig frame1 = new FenetreConfig();
					frame1.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
