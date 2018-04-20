package test;

public class Run {

	public static void main(String[] args) {
		//change to yours file path
		String filePath="C:/Users/KobKob/Desktop/New folder/ex_v5.txt";
		//choose a path to save the images
		String saveImagePath="c:/Games/";
		Decoder decoder=new Decoder(filePath,saveImagePath,new PhoneBook());
		decoder.decodePhoneBook();
		decoder.getPhoneBook().printPhoneBook();
		
	
		
	
		
		
	}
	

}
