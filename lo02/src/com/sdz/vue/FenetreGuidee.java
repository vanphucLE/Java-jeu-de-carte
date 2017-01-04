package com.sdz.vue;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Insets;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.sdz.modele.Joueur;

public class FenetreGuidee extends JFrame implements Observer {

	private JPanel contentPane;
	private Joueur j;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FenetreGuidee frame = new FenetreGuidee();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public FenetreGuidee(Joueur j) {
		this.j=j;
		this.setTitle("Les cartes Guidée");
		this.setResizable(false);
		this.setLocation(200, 50);
		this.setBounds(50, 50, 1839, 916);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		contentPane.setBackground(Color.GREEN);
		this.dessinerCarteGuidee();
		setContentPane(contentPane);

	}
	public FenetreGuidee() {
		this.j=j;
		this.setTitle("Les cartes Guidée");
		this.setResizable(false);
		this.setLocation(200, 50);
		this.setBounds(50, 50, 1839, 916);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		contentPane.setBackground(Color.GREEN);
		this.dessinerCarteGuidee();
		setContentPane(contentPane);

	}

	public void dessinerCarteGuidee() {
		int indice=-1;
		for (int k = 0; k <= 3; k++) {
			for (int j = 0; j <= 3; j++) {
				indice++;
				
				
				JButton button = new JButton();
				button.setBounds(460 * j + 10, 215 * k + 10, 150, 210);

				try {
					BufferedImage image = ImageIO.read(new File("cartes/1.PNG"));
					ImageIcon icon = new ImageIcon(image.getScaledInstance(150, 210, image.SCALE_SMOOTH));
					button.setIcon(icon);
					button.setMargin(new Insets(0, 0, 0, 0));
					this.contentPane.add(button);
				} catch (IOException ex) {
					Logger.getLogger(PanelJP.class.getName()).log(Level.SEVERE, null, ex);
				}
				for (int i = 0; i <= 2; i++) {
					button = new JButton();
					button.setBounds(460 * j + 162 + 60 * i, 215 * k + 10, 150, 210);

					try {
						BufferedImage image = ImageIO.read(new File("cartes/1.PNG"));
						ImageIcon icon = new ImageIcon(image.getScaledInstance(150, 210, image.SCALE_SMOOTH));
						button.setIcon(icon);
						button.setMargin(new Insets(0, 0, 0, 0));
						this.contentPane.add(button);
					} catch (IOException ex) {
						Logger.getLogger(PanelJP.class.getName()).log(Level.SEVERE, null, ex);
					}
				}
			}
		}
	}
	@Override
	public void update(Observable o, Object arg) {
		
	}

}
