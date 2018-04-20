package test;

import java.util.HashMap;
import java.util.Map;

public class PhoneBook {
	private HashMap<String, Person> contactsMap;
	
	public PhoneBook() {
		this.contactsMap=new HashMap<String, Person>();
	}
	
	public HashMap<String, Person> getContactsMap() {
		return contactsMap;
	}

	public void setContactsMap(HashMap<String, Person> contactsMap) {
		this.contactsMap = contactsMap;
	}

	
	public void createContact(String key, Person person) {
		if(key!=null)
			contactsMap.put(key, person);
	}
	
	public void addLastName(String key, String lastName) {
		if(this.contactsMap.get(key)!=null)
			this.contactsMap.get(key).setLastName(lastName);
		else contactsMap.put(key, new Person("No First Name",lastName));
	}
	
	public void phoneNumAdder(String key, String phoneNumber) {
		if(this.contactsMap.get(key)!=null)
			this.contactsMap.get(key).setTele(phoneNumber);
	}
	public void timeAdder(String key,String epochTime) {
		if(contactsMap.get(key)!=null)
			  contactsMap.get(key).setTime(epochTime);
		
	}
	public void imageAdder(String key, String base64Image) {
		if(contactsMap.get(key)!=null)
			  contactsMap.get(key).setImage(base64Image);
		
	}
	
	public void printPhoneBook() {
		for (Map.Entry<String, Person> entry : contactsMap.entrySet()) 
			System.out.println(entry.getValue().toString());
	}
}
