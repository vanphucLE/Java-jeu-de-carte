package com.sdz.modele;

import java.util.Iterator;
import java.util.LinkedList;

public class vidu {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("ashfbalskfb \n alksjbfalkjsfb");
		
		LinkedList<Integer> arr =new LinkedList<Integer>();
		arr.add(11);
		arr.add(22);
		arr.add(33);
		int compte=0;
		Iterator<Integer> it=arr.iterator();
		while( it.hasNext()){
			compte++;
			Integer a=(Integer)it.next();
			if (a==22){
				arr.remove(compte);
				System.out.println(compte);
				break;
			}
		}
		
		int num = (int) Math.ceil(3 * Math.random());
		System.out.println(num);
		
		System.out.print("néant");
		
	}

}
