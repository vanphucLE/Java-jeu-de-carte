package com.sdz.vue;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Scanner;

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

import com.sdz.controler.Controler;
import com.sdz.modele.Debutant;
import com.sdz.modele.Expert;
import com.sdz.modele.Joueur;
import com.sdz.modele.JoueurPhysique;
import com.sdz.modele.JoueurVirtuel;
import com.sdz.modele.Partie;

/*
 * Class FenetreConfig est utilisé pour créer un fenetre de configuration avant de jouer un jeu
 * @author LE Van Phuc
 * 
 */
public class FenetreConfig extends JFrame {
	private JPanel contentPane;
	public static JTextField txtNom;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private final ButtonGroup buttonGroup_1 = new ButtonGroup();
	public static JComboBox cbBox;
	private static String type;
	private static String niveau;
	private Partie partie;
	private Fenetre fenetrePrincipal;

	public static String getTypeJeu() {
		return type;
	}

	public static String getNiveau() {
		return niveau;
	}

	public JComboBox getcbBox() {
		return cbBox;
	}

	public FenetreConfig(Fenetre fenetrePrincipal) {

		this.fenetrePrincipal = fenetrePrincipal;

		setIconImage(Toolkit.getDefaultToolkit().getImage("image/mt.PNG"));
		this.setTitle("Configuration");
		setResizable(false);
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

		JRadioButton rdDebutant = new JRadioButton("Débutant");
		buttonGroup_1.add(rdDebutant);
		rdDebutant.setSelected(true);
		rdDebutant.setBounds(119, 146, 84, 31);
		panel.add(rdDebutant);

		JRadioButton rdExpert = new JRadioButton("Expert");
		buttonGroup_1.add(rdExpert);
		rdExpert.setBounds(229, 146, 74, 31);
		panel.add(rdExpert);

		JLabel lblNom = new JLabel("Nom");
		lblNom.setBounds(10, 11, 95, 31);
		panel.add(lblNom);
		lblNom.setFont(new Font("Arial Black", Font.PLAIN, 14));

		txtNom = new JTextField();
		txtNom.setName("");
		txtNom.setBounds(123, 16, 198, 24);
		panel.add(txtNom);
		txtNom.setColumns(10);

		JLabel lblNewLabel_2 = new JLabel("Nombre AI");
		lblNewLabel_2.setFont(new Font("Arial Black", Font.PLAIN, 14));
		lblNewLabel_2.setBounds(10, 101, 95, 31);
		panel.add(lblNewLabel_2);

		cbBox = new JComboBox();
		cbBox.setModel(new DefaultComboBoxModel(new String[] { "1", "2", "3", "4", "5" }));
		cbBox.setBounds(123, 106, 41, 23);
		panel.add(cbBox);

		JLabel lblAge = new JLabel("Age");
		lblAge.setFont(new Font("Arial Black", Font.PLAIN, 14));
		lblAge.setBounds(10, 55, 95, 31);
		panel.add(lblAge);

		JComboBox comboBox = new JComboBox();
		String[] ages = new String[93];
		for (int i = 0; i < 93; i++) {
			ages[i] = Integer.toString(i + 8);
		}
		comboBox.setModel(new DefaultComboBoxModel(ages));
		comboBox.setBounds(123, 60, 41, 22);
		panel.add(comboBox);

		JButton btnOK = new JButton("OK");
		btnOK.setFont(new Font("Arial", Font.PLAIN, 14));
		btnOK.setBounds(84, 211, 99, 27);
		btnOK.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String nomJP = txtNom.getText();
				int ageJP = Integer.parseInt(comboBox.getSelectedItem().toString());
				int nbJoueurs = Integer.parseInt(cbBox.getSelectedItem().toString());
				if (rdDebutant.isSelected()) {
					niveau = rdDebutant.getText();
				} else {
					niveau = rdExpert.getText();
				}
				setVisible(false);
				creerPartie(nomJP, ageJP, nbJoueurs, niveau);
			}
		});
		contentPane.add(btnOK);

		JButton btnReset = new JButton("RESET");
		btnReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
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

	public void creerPartie(String nomJP, int ageJP, int nbJoueurs, String niveau) {
		ArrayList<Joueur> listeJoueurs = new ArrayList<Joueur>();
		listeJoueurs.add(new JoueurPhysique(nomJP, ageJP));
		ArrayList<Joueur> listeJoueursVirtuel = new ArrayList<Joueur>();
		if (niveau.equals("Débutant")) {
			listeJoueursVirtuel.add(new JoueurVirtuel(2, "ALBERT", 21, new Debutant()));
			listeJoueursVirtuel.add(new JoueurVirtuel(3, "ANZAR", 22, new Debutant()));
			listeJoueursVirtuel.add(new JoueurVirtuel(4, "DIOUF", 21, new Debutant()));
			listeJoueursVirtuel.add(new JoueurVirtuel(5, "GALANTE", 23, new Debutant()));
			listeJoueursVirtuel.add(new JoueurVirtuel(6, "GENIN", 21, new Debutant()));
		} else {
			listeJoueursVirtuel.add(new JoueurVirtuel(2, "ALBERT", 21, new Expert()));
			listeJoueursVirtuel.add(new JoueurVirtuel(3, "ANZAR", 22, new Expert()));
			listeJoueursVirtuel.add(new JoueurVirtuel(4, "DIOUF", 21, new Expert()));
			listeJoueursVirtuel.add(new JoueurVirtuel(5, "GALANTE", 23, new Expert()));
			listeJoueursVirtuel.add(new JoueurVirtuel(6, "GENIN", 21, new Expert()));
		}
		for (int i = 0; i < nbJoueurs; i++) {
			listeJoueurs.add(listeJoueursVirtuel.get(i));
		}
		// Créer nouvel jeu de carte
		this.partie = new Partie(listeJoueurs, niveau);
		this.fenetrePrincipal.setCtrl(this.partie);
		this.fenetrePrincipal.setPanelJeu();

		// Commencer le partie
		this.fenetrePrincipal.commencerPartie();
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FenetreConfig frame1 = new FenetreConfig(new Fenetre());
					frame1.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
