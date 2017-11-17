package Vehicles;

public class Vehicle {
	private String Type;
	private int Velocity;
	
	public Vehicle() {
		Type = "Normal";
		Velocity = 50; 
	}
	public Vehicle(String type,int vel) {
		Type = type;
		Velocity = vel ; 
	}
	
	public void SetType(String type) {
		Type = type;
	}
	public String GetType() {
		return Type;
	}
	
	public void SetVelocity(int Vel) {
		Velocity = Vel;
	}
	public double GetVelocity() {
		return Velocity;
	}
	
	
	
}
