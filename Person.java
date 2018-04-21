package test;

import java.util.Date;

public class Person {
	String name, lastName,tele1,tele2,tele3,image;
	
	Date time;
	
	public Person(String name, String lastName, String tele, Date time) {
		this.name=name;
		this.lastName=lastName;
		this.tele1=tele;
		this.time=time;
	}
	
	public Person(String name, String lastName) {
		this.name=name;
		this.lastName=lastName;
		
	}
	public Person(String name) {
		this.name=name;
	}
	
	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}


	
	public String getName() {
		return this.name;
	}
	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(String time) {
		//convert from epoch time to a java date object
		if(time!=null)
			this.time=new Date(Long.parseLong(time) * 1000);
	}

	public String getTele() {
		return tele1;
	}

	public void setTele(String tele) {
		//if contacts has more than one phone number
		if(tele1!=null) {
			if(tele2!=null)
				this.tele3 = tele;
			else this.tele2=tele;
		}
		else tele1=tele;
	}
	
	public String toString() {
		String s="Name:"+this.name;
		s+="\nLast Name:"+this.lastName;
		s+="\nTele:";
		if(tele1==null)
			s+="No Phone Number";
		else {
			s+=this.tele1;
			if(tele2!=null) {
				s+="\nTele 2:"+this.tele2;
				if(tele3!=null)
					s+="\nTele 3:"+this.tele3;
							}
			}
		s+="\nTime:"+this.time;
		s+="\nContact has a picture:";
		if(image!=null)
			s+="Yes";
		else s+="No";
		s+="\n";
		return s;
	}
}
