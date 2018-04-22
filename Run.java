package test;

import java.util.Scanner;

public class Run {

	public static void main(String[] args) {
		
		System.out.println("Enter your txt file location(please include .txt)\n");
		Scanner in = new Scanner(System.in);
		String filePath=in.nextLine();
		System.out.println("Enter your path to save images for(please exlude file name)\n");
		String saveImagePath=in.nextLine();
		
		Decoder decoder=new Decoder(filePath,saveImagePath,new PhoneBook());
		decoder.decodePhoneBook();
		decoder.getPhoneBook().printPhoneBook();
		in.close();
		
		
	
		
	
		
		
	}
	

}
