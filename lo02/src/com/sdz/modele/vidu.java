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

	public void equal3(Integer a) {
		System.out.println(a);
		a = 3;
		System.out.println(a);
	}

	public static void main(String[] args) {
		
		int nbCa = (int) (Math.ceil(Math.random() * (0 - 1)));
		System.out.println(nbCa);
	}
}
