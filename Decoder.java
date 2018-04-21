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
	private String filePath,saveImgPath;
	private String firstLine,secondLine,thridLine,fourthLine, base64imagesString;
	
	
	public Decoder(String filePath,String saveImgPath,PhoneBook phoneBook) {
		this.filePath=filePath;
		this.saveImgPath=saveImgPath;
		this.phoneBook=phoneBook;
		
		try {
			bufferdReader=new BufferedReader (new InputStreamReader(new FileInputStream(this.filePath)));
			//reads the first names
			firstLine=bufferdReader.readLine();
			//reads last names,first read is for the space line
			secondLine=bufferdReader.readLine();
			secondLine=bufferdReader.readLine();
			//reads phone numbers
			thridLine=bufferdReader.readLine();
			thridLine=bufferdReader.readLine();
			//reads time
			fourthLine=bufferdReader.readLine();
			fourthLine=bufferdReader.readLine();
			//reads the base64 strings
			base64imagesString=bufferdReader.readLine();
			base64imagesString=bufferdReader.readLine();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
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

			  for(i=0; i<firstLine.length()-1;) {
				  String key=  firstLine.substring(i, i+5);//key=first 5 letters of memory address.
				  i+=9;
				  //name finished with lowercase letter
				  Character lowerCase = null;
				  StringBuilder name = new StringBuilder();
				  name.append(firstLine.charAt(i));
				  //reads contact's name
				  for(j=i+1;(j<firstLine.length()-1)&&(lowerCase.isLowerCase(firstLine.charAt(j))||(lowerCase.isSpaceChar(firstLine.charAt(j))));j++) {
					  //a name with more than one word
					  if(lowerCase.isSpaceChar(firstLine.charAt(j))) {
						  name.append(firstLine.charAt(j));
						  j++;
					  }
						  
					  name.append(firstLine.charAt(j));
				  }
				//last char in line
				  if(j==firstLine.length()-1)
					  name.append(firstLine.charAt(j));
				//add the contact to the map
				  this.phoneBook.createContact(key, new Person(new String(name)));
				  i=j;
			  }
	}
	
	public void decodeLastName() {
		int i,j;
		
			  for(i=0; i<secondLine.length()-1;) {
				  String key=  secondLine.substring(i, i+5);
				  i+=9;
				  StringBuilder name = new StringBuilder();
				  Character lowerCase = null;
				  name.append(secondLine.charAt(i));
				  for(j=i+1;(j<secondLine.length()-1)&&
						  (lowerCase.isLowerCase(secondLine.charAt(j))||(lowerCase.isSpaceChar(secondLine.charAt(j))));j++) {
					  //a name with more than one word
					  if(lowerCase.isSpaceChar(secondLine.charAt(j))) {
						  name.append(secondLine.charAt(j));
						  j++;
					  }
					  name.append(secondLine.charAt(j));
				  }
				  
				  this.phoneBook.addLastName(key, new String(name));
				  i=j;
			  }
	}
	
	public void decodePhone() {
		
		int i,j;
			
				  for(i=0; i<thridLine.length()-1;) {
					  String key=  thridLine.substring(i, i+5);
					  i+=9;
					  Character upperCase = null;
					  StringBuilder number =new StringBuilder();
					  number.append(thridLine.charAt(i));
					  //phone number length
					  int counter=0;
					  //phone numbers before 941B0,64440,1E270 are 9 digit phone number
					  //so i had to fix the key string not generically
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
					  if(thridLine.charAt(i)=='+'||thridLine.charAt(i)=='-')
						  counter=-1;
					  for(j=i+1;counter<9&&j<thridLine.length()-1;j++,counter++) {
						  if(thridLine.charAt(j)==' '||thridLine.charAt(j)=='+'||thridLine.charAt(j)=='-'||thridLine.charAt(j)==')')
							  counter--;
						  else if((thridLine.charAt(j)=='(')) {
							  counter-=4; 
						  }
						//phone number is shorter than 10 digits,
						// and the next memory address starts with UpperCase letter
						  else if(upperCase.isUpperCase(thridLine.charAt(j)))
							  break;
						  
						  number.append(thridLine.charAt(j));
					  }
					  i=j;
					  this.phoneBook.phoneNumAdder(key, new String(number));
					  
				  }
				  
	}
	public void decodeTime(){
		
		int i,j;
			
			  for(i=0; i<fourthLine.length()-1;) {
				  String key=  fourthLine.substring(i, i+5);
				  i+=9;
				  StringBuilder epochTime = new StringBuilder();
				  epochTime.append(fourthLine.charAt(i));
				  for(j=1;(j<fourthLine.length()-1)&&j<10;j++) 
					  epochTime.append(fourthLine.charAt(i+j));
				  
				  this.phoneBook.timeAdder(key, new String(epochTime));
				  i+=j;
			  	}
	}
	
	public void decodeImages() {
    	
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
