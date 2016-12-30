package com.sdz.vue;

import java.awt.Color;
import java.awt.Insets;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class PanelEspaceCommun extends JPanel {

	/**
	 * Create the panel.
	 */
	public PanelEspaceCommun() {

		this.setLayout(null);
		this.setSize(1480,546);
		Border lineBorder = BorderFactory.createLineBorder(Color.blue);
		this.setBorder(lineBorder);
//		this.setBackground(Color.GRAY);
		
		this.peindreEspace(9);
		
	}
	public void peindreEspace(int nbCartes){
		if(nbCartes<=9){
			for (int i = 0; i < nbCartes; i++) {
				JButton button = new JButton("New button");
				button.setBounds(10+140 * i,0 , 130, 182);
				try {
					BufferedImage image = ImageIO.read(new File("cartes/1.PNG"));
					ImageIcon icon = new ImageIcon(image.getScaledInstance(130,182, image.SCALE_SMOOTH));
					button.setIcon(icon);
					button.setMargin(new Insets(0, 10, 0, 0));
					add(button);
				} catch (IOException ex) {
					Logger.getLogger(PanelJP.class.getName()).log(Level.SEVERE, null, ex);
				}
			}
		}
		if(nbCartes<=9){
			for (int i = 0; i < nbCartes; i++) {
				JButton button = new JButton("New button");
				button.setBounds(10+140 * i,182 , 130, 182);
				try {
					BufferedImage image = ImageIO.read(new File("cartes/1.PNG"));
					ImageIcon icon = new ImageIcon(image.getScaledInstance(130,182, image.SCALE_SMOOTH));
					button.setIcon(icon);
					button.setMargin(new Insets(0, 10, 0, 0));
					add(button);
				} catch (IOException ex) {
					Logger.getLogger(PanelJP.class.getName()).log(Level.SEVERE, null, ex);
				}
			}
		}
		if(nbCartes<=9){
			for (int i = 0; i < nbCartes; i++) {
				JButton button = new JButton("New button");
				button.setBounds(10+140 * i,364 , 130, 182);
				try {
					BufferedImage image = ImageIO.read(new File("cartes/1.PNG"));
					ImageIcon icon = new ImageIcon(image.getScaledInstance(130,182, image.SCALE_SMOOTH));
					button.setIcon(icon);
					button.setMargin(new Insets(0, 10, 0, 0));
					add(button);
				} catch (IOException ex) {
					Logger.getLogger(PanelJP.class.getName()).log(Level.SEVERE, null, ex);
				}
			}
		}
	}
	
	public static void main(String[] args) {
		JFrame j = new JFrame();
		j.setSize(j.getMaximumSize());
		j.getContentPane().add(new PanelEspaceCommun());
		j.setVisible(true);
		j.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
