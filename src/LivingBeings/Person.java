package LivingBeings;

import java.awt.Image;

public class Person {
	private String Name;
	private int Velocity;
	protected Image PlayerImage;

	
	Person(){
		Name     = "Normal";
		Velocity = 5;
	}
	Person(String name,int vel){
		Name     = name;
		Velocity = vel;
	}
	
	public void SetName(String Name) {
		this.Name = Name;
	}
	public String GetName() {
		return Name;
	}
	
	public void SetVelocity(int Velocity) {
		this.Velocity = Velocity;
	}
	public int GetVelocity() {
		return Velocity;
	}
	

	
}
