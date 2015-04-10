package com.java.test;

import java.util.*;

public class SampelExceptionHandling {
	static List<String> items = new ArrayList();
	static Iterator<String> itr;
	public static void main(String[] args) {
		items.add("A");items.add("B");items.add("C");items.add("D");items.add("E");
		itr = items.iterator();
		while(itr.hasNext()){
		try{
			
			
				printFun(itr.next());
			
			
		}catch(Exception e){
			
			System.out.println("continuing...");
			//itr.remove();
			//continue;
			/*try {
				printFun(itr.next());
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}*/
		}
		
		}

	}

	
	public static void printFun(String str) throws Exception{
		if(str.equals("B")){
			//itr.remove();
			throw new Exception();
			
		}else
			System.out.println(str);
		
	}
}
