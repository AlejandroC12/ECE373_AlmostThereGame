package LivingBeings;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Enemy extends Person{
	private String Type; // type of human enemy
	
	
	private Image EnemyWalkerImage;
	
	private  int xLocation[] =new int [1];
	private  int yLocation[] =new int [1];
	
	private  int xEnd;
	private  int yEnd;
	
	
	public Enemy(){
		super("Walker",3); // standard walking human 
		Type = "Walker";
	}
	public Enemy(String type,int velocity){
		super("Normal",velocity);
		Type = type;
	}
	public void SetType(String type) {
		Type  = type;
	}
	public String GetType() {
		return Type;
	}
	
	
	
	public Image LoadEnemyWalkerImage() {
		if(this.GetType() == "FootBall") {
			try {
				EnemyWalkerImage = ImageIO.read(new File("Images\\FootBallPlayer.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}			
			return EnemyWalkerImage;
		}
		return null;
	
	}
	public void setEnemyWalkerImage(Image enemyWalkerImage) {
		EnemyWalkerImage = enemyWalkerImage;
	}
	public int[] getxLocation() {
		return xLocation;
	}
	public void setxLocation(int xLocation[]) {
		this.xLocation = xLocation;
	}
	public int[] getyLocation() {
		return yLocation;
	}
	public void setyLocation(int yLocation[]) {
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
