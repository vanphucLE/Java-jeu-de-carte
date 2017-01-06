package com.sdz.modele;

import java.util.ArrayList;
import java.util.LinkedList;

import javax.swing.JOptionPane;

import com.sdz.cartes.CarteAction;
import com.sdz.cartes.CarteDivinite;
import com.sdz.cartes.Croyant;
import com.sdz.cartes.GuideSpirituel;

public class vidu {
	public void cong(ArrayList<Integer> abc) {
		abc.remove(2);
	}
	public void equal3(Integer a){
		System.out.println(a);
		a=3;
		System.out.println(a);
	}
	public static void main(String[] args) {
		
		int[] a=new int[5];
		a[0]=2;
		System.out.println(a);
		
		
		int commande = JOptionPane.showConfirmDialog(null, "kafkjanf");
		System.out.println(commande);
		
		for (int i = 1; i <= 37; i++) {
			System.out.println(i);
		}
	}
}
