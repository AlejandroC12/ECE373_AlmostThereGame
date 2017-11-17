package LivingBeings;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Student extends Person{
	private double Life; // number of hearts the student has 
	public Student(){
		super("God",5);
		Life = 5;
	}
	Student(String name,int Vel,double life){
		super(name,Vel);
		Life = life;
	}
	
	public void SetLife(double life) {
		Life = life;
	}
	public double GetLife() {
		return Life;
	}
	
	public Image LoadPlayerImage(String Code){
		//if(this.Name == "Normal") {
		 PlayerImage = null;
		if(Code.equals("1")) { // this is the not moving state
			try {
				PlayerImage = ImageIO.read(new File("Images\\MortyBack.JPG"));
			} catch (IOException e) {
				e.printStackTrace();
			}			
	    }else if(Code.equals("11")) {
			try {
				PlayerImage = ImageIO.read(new File("Images\\MortyBack1.JPG"));
			} catch (IOException e) {
				e.printStackTrace();
			}			
	    }else if(Code.equals("12")) {
			try {
				PlayerImage = ImageIO.read(new File("Images\\MortyBack2.JPG"));
			} catch (IOException e) {
				e.printStackTrace();
			}			
	    }else if(Code.equals("2")){
			try {
				PlayerImage = ImageIO.read(new File("Images\\SideWaysMorty.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}	
	    }else if(Code.equals("death")){
			try {
				PlayerImage = ImageIO.read(new File("Images\\RoadOverMorty.JPG"));
			} catch (IOException e) {
				e.printStackTrace();
			}	
	    }else {
			try {
				PlayerImage = ImageIO.read(new File("Images\\MortyBack.JPG"));
			} catch (IOException e) {
				e.printStackTrace();
			}	
	    }
		return PlayerImage;
//		return null;
	}

}
