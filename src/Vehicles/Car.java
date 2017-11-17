package Vehicles;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class Car extends Vehicle{
	private Image type;
	
	private  int xLocation[] =new int [2];
	private  int yLocation[] =new int [2];
	
	private  int xEnd;
	private  int yEnd;
	
	
	
	public Car(){
		super("Normal",50);
		
	}
	public Car(String Type){
		super(Type,50);
	
	}
		public Image LoadCarImage(){
			if(this.GetType() == "Normal") {
				try {
					type = ImageIO.read(new File("Images\\Car3d.png"));
				} catch (IOException e) {
					e.printStackTrace();
				}			
				return type;
			}
			if(this.GetType() == "Bus") {
				try {
					type = ImageIO.read(new File("C:\\Users\\Alejandro\\Desktop\\ECE373\\AlmostThere\\SunTran.jpg"));
				} catch (IOException e) {
					e.printStackTrace();
				}			
				return type;
			}
			return null;
		}
		
		public void SetVel(int Vel) {	
			super.SetVelocity(Vel);
		}
		public double GetVel() {
			return super.GetVelocity();
		}
	
		

		public int[] getxLocation() {
			return xLocation;
		}
		public void setxLocation(int [] xLocation) {
			this.xLocation = xLocation;
		}
		
		
		public int[] getyLocation() {
			return yLocation;
		}
		public void setyLocation(int [] yLocation) {
			this.yLocation = yLocation;
		}
		public int getxEnd() {
			return xEnd;
		}
		public void setxEnd(int xEnd) {
			this.xEnd = xEnd;
		}
		public int getyEnd() {
			return yEnd;
		}
		public void setyEnd(int yEnd) {
			this.yEnd = yEnd;
		}
		
		

}
