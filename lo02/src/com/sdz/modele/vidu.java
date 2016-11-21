package com.sdz.modele;

import java.util.Iterator;
import java.util.LinkedList;

public class vidu {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("ashfbalskfb \n alksjbfalkjsfb");
		
		LinkedList<Integer> arr =new LinkedList<Integer>();
		arr.add(1);
		arr.add(2);
		arr.add(3);
		int compte=0;
		Iterator<Integer> it=arr.iterator();
		while( it.hasNext()){
			compte++;
			System.out.println(compte);
			Integer a=(Integer)it.next();
			System.out.println(a);
		}
		System.out.println(compte);
	}

}
