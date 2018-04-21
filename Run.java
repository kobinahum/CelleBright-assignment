package test;

public class Run {

	public static void main(String[] args) {
		
		//change to yours file path
		String filePath="enter file location here";
		//choose a path to save the images
		String saveImagePath="enter path to save the images";
		Decoder decoder=new Decoder(filePath,saveImagePath,new PhoneBook());
		decoder.decodePhoneBook();
		decoder.getPhoneBook().printPhoneBook();
		
		
	
		
	
		
		
	}
	

}
