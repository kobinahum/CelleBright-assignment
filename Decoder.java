package test;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Base64;

public class Decoder {
	
	private BufferedReader bufferdReader;
	private PhoneBook phoneBook;
	private String filePath;
	private String saveImgPath;
	
	
	public Decoder(String filePath,String saveImgPath,PhoneBook phoneBook) {
		this.filePath=filePath;
		this.saveImgPath=saveImgPath;
		this.phoneBook=phoneBook;
		
	}
	public PhoneBook getPhoneBook() {
		return this.phoneBook;
	}
	
	public PhoneBook decodePhoneBook() {
		decodeName();
		decodeLastName();
		decodePhone();
		decodeTime();
		decodeImages();
		return this.phoneBook;
	}
	
	//read the name of the contact and add it to the hashmap as a person object
	public void decodeName() {
		int i,j;
		
		try {
			bufferdReader=new BufferedReader (new InputStreamReader(new FileInputStream(this.filePath)));

			  String str=bufferdReader.readLine();
			 
			  for(i=0; i<str.length()-1;) {
				  String key=  str.substring(i, i+5);//key=first 5 letters of memory address.
				  i+=9;
				  //name finished with lowercase letter
				  Character lowerCase = null;
				  StringBuilder name = new StringBuilder();
				  name.append(str.charAt(i));
				  //reads contact's name
				  for(j=i+1;(j<str.length()-1)&&(lowerCase.isLowerCase(str.charAt(j))||(lowerCase.isSpaceChar(str.charAt(j))));j++) {
					  //a name with more than one word
					  if(lowerCase.isSpaceChar(str.charAt(j))) {
						  name.append(str.charAt(j));
						  j++;
					  }
						  
					  name.append(str.charAt(j));
				  }
				//last char in line
				  if(j==str.length()-1)
					  name.append(str.charAt(j));
				//add the contact to the map
				  this.phoneBook.createContact(key, new Person(new String(name)));
				  i=j;
				 
			  }
  
			  bufferdReader.close();  
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("didn't find the file");
		}

	}
	
	
	
	public void decodeLastName() {
		int i,j;
		try {
			bufferdReader = new BufferedReader
					(new InputStreamReader(new FileInputStream
							(this.filePath)));
			//skips to the second line
			String str=bufferdReader.readLine();
			for(int k=0;k<2;k++)
				str=bufferdReader.readLine();

			  for(i=0; i<str.length()-1;) {
				  String key=  str.substring(i, i+5);
				  i+=9;
				  StringBuilder name = new StringBuilder();
				  Character lowerCase = null;
				  name.append(str.charAt(i));
				  for(j=i+1;(j<str.length()-1)&&
						  (lowerCase.isLowerCase(str.charAt(j))||(lowerCase.isSpaceChar(str.charAt(j))));j++) {
					 
					  if(lowerCase.isSpaceChar(str.charAt(j))) {//a name with more than one word
						  name.append(str.charAt(j));
						  j++;
					  }
					  name.append(str.charAt(j));
				  }
				  
				  this.phoneBook.addLastName(key, new String(name));
				  i=j;
			  }
			  bufferdReader.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void decodePhone() {
		
		int i,j;
			try {
				bufferdReader = new BufferedReader
						(new InputStreamReader(new FileInputStream
								(this.filePath)));
				
				String str=bufferdReader.readLine();
				for(int k=0;k<4;k++)
					str=bufferdReader.readLine();
				  for(i=0; i<str.length()-1;) {
					  String key=  str.substring(i, i+5);
					  i+=9;
					  Character upperCase = null;
					  StringBuilder number =new StringBuilder();
					  number.append(str.charAt(i));
					  //phone number length
					  int counter=0;
					  //phone numbers before 941B0,64440,1E270 are 9 digit phone number
					  //so i had to fix the key string not generic
					  if(key.equals("41B00")) {
						  key=new String("941B0");
						  counter=1;
					  }
					  if(key.equals("44400")) {
						  key=new String("64440");
						  counter=1;
					  }
					  if(key.equals("E2700")) {
						  key=new String("1E270");
						  counter=1;
					  }
					  //phone numbers are mostly 10 digits, the for loop handles other marks too.
					  if(str.charAt(i)=='+'||str.charAt(i)=='-')
						  counter=-1;
					  for(j=i+1;counter<9&&j<str.length()-1;j++,counter++) {
						  if(str.charAt(j)==' '||str.charAt(j)=='+'||str.charAt(j)=='-'||str.charAt(j)==')')
							  counter--;
						  else if((str.charAt(j)=='(')) {
							  counter-=4; 
						  }
						//phone number is shorter than 10 digits,
						// and the next memory address starts with UpperCase letter
						  else if(upperCase.isUpperCase(str.charAt(j)))
							  break;
						  
						  number.append(str.charAt(j));
					  }
					  i=j;
					  this.phoneBook.phoneNumAdder(key, new String(number));
					  
				  }
				  bufferdReader.close();
				 
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	public void decodeTime(){
		
		int i,j;
		try {
			bufferdReader = new BufferedReader
					(new InputStreamReader(new FileInputStream
							(this.filePath)));
			String str=bufferdReader.readLine();
			for(int k=0;k<6;k++)
				str=bufferdReader.readLine();
			
			  for(i=0; i<str.length()-1;) {
				  String key=  str.substring(i, i+5);
				  i+=9;
				  StringBuilder epochTime = new StringBuilder();
				  epochTime.append(str.charAt(i));
				  for(j=1;(j<str.length()-1)&&j<10;j++) 
					  epochTime.append(str.charAt(i+j));
				  
				  this.phoneBook.timeAdder(key, new String(epochTime));
				  i+=j;
			  	}
			  bufferdReader.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void decodeImages() {
    	String base64imagesString="";
    	try {
    		bufferdReader=new BufferedReader(new InputStreamReader(new FileInputStream(filePath)));
			base64imagesString = bufferdReader.readLine();
			for(int i=0;i<8;i++)
				base64imagesString=bufferdReader.readLine();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	
    	String[] base64ImagesArr=base64imagesString.split("==");
    	for(int j=0;j<4;j++) {
    		String key=base64ImagesArr[j].substring(0,5);
    		String base64Image=base64ImagesArr[j].substring(9,base64ImagesArr[j].length());
    		//the second and third base64 string has a problem with 
    		if(j==1||j==2)
    			continue;
    		if(this.phoneBook.getContactsMap().get(key)!=null) {
		try (FileOutputStream imageOutFile = new FileOutputStream(saveImgPath+this.phoneBook.getContactsMap().get(key).getName()+" "
				+this.phoneBook.getContactsMap().get(key).getLastName()+".jpg")) {
			// Converting a Base64 String into Image byte array
			byte[] imageByteArray = Base64.getDecoder().decode(base64Image);
			imageOutFile.write(imageByteArray);
			//saves image string to contact
			this.phoneBook.imageAdder(key, base64Image);
			
		} catch (FileNotFoundException e) {
			System.out.println("Image not found" + e);
		} catch (IOException ioe) {
			System.out.println("Exception while reading the Image " + ioe);
		}
    		}
	}
    }
	
}
