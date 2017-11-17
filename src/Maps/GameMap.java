package Maps;


import java.awt.Image;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import javax.imageio.ImageIO;


public class GameMap implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int level;
	private transient Image BackGround;
	private int XStart;
	private int YStart;
	private int XEnd;
	private int YEnd;
	private int EnemyFrequency;
	private ArrayList<Integer> XEnemyStartLocation = new ArrayList<Integer>();
	private ArrayList<Integer> YEnemyStartLocation = new ArrayList<Integer>();
	private ArrayList<Integer> XEnemyEndLocation   = new ArrayList<Integer>();
	private ArrayList<Integer> YEnemyEndLocation   = new ArrayList<Integer>();
	private String LevelSong;
	
	
	public GameMap() {
		level  = 0 ;
		XStart = 0 ;
		YStart = 0 ;
		EnemyFrequency = 5 ; 

	}
	public GameMap(int Lev) {
		level = Lev ;
		XStart = 0 ;
		YStart = 0 ;
		EnemyFrequency = 5 ; 
		XEnemyStartLocation.add(-70);YEnemyStartLocation.add(850); // Cars Starting From left
		XEnemyStartLocation.add(-70);YEnemyStartLocation.add(770); 
		XEnemyStartLocation.add(-70);YEnemyStartLocation.add(670);
		//XEnemyStartLocation.add(-70);YEnemyStartLocation.add(570);// Middle Turn Lane Going to right
		//this is from rigth to left
		XEnemyStartLocation.add(2100);YEnemyStartLocation.add(470);
		XEnemyStartLocation.add(2100);YEnemyStartLocation.add(390);
		XEnemyStartLocation.add(2100);YEnemyStartLocation.add(290);
			
		
		
		XEnemyEndLocation.add(1600);YEnemyEndLocation.add(850);  // Where Cars Disappear
		XEnemyEndLocation.add(1600);YEnemyEndLocation.add(770); 
		XEnemyEndLocation.add(1600);YEnemyEndLocation.add(670); 
		//XEnemyEndLocation.add(1950);YEnemyEndLocation.add(570); // Middle Lane 
		//from rigth to left
		XEnemyEndLocation.add(0);YEnemyEndLocation.add(470);  // Where Cars Disappear
		XEnemyEndLocation.add(0);YEnemyEndLocation.add(370); 
		XEnemyEndLocation.add(0);YEnemyEndLocation.add(270); 
	}
	public GameMap(int Lev,int x,int y) {
		level = Lev ;
		XStart = x ;
		YStart = y ;
		EnemyFrequency = 5 ; 
		XEnemyStartLocation.add(-70);YEnemyStartLocation.add(850); // Cars Starting From left
		XEnemyStartLocation.add(-70);YEnemyStartLocation.add(770); 
		XEnemyStartLocation.add(-70);YEnemyStartLocation.add(670);
		XEnemyStartLocation.add(-70);YEnemyStartLocation.add(570);// Middle Turn Lane Going to right
			
		XEnemyEndLocation.add(1950);YEnemyEndLocation.add(850);  // Where Cars Disappear
		XEnemyEndLocation.add(1950);YEnemyEndLocation.add(770); 
		XEnemyEndLocation.add(1950);YEnemyEndLocation.add(670); 
		XEnemyEndLocation.add(1950);YEnemyEndLocation.add(570); // Middle Lane 
	}
	public int GetLevel() {
		return this.level;
	}
	public void SetLevel(int Level) {
		this.level = Level;
	}
	
	
	public Image StartLevelBackGround() {
		if(level == 1) {
			this.level = 1 ; 
			try {
				EnemyFrequency = 20; 
				
				BackGround = ImageIO.read(new File("Images\\Level1.png"));
				XStart  = 900;
				YStart  = 900;
				XEnd    = 2000;
				YEnd    = 250;
				setLevelSong("Songs\\MainTheme.wav");
			} catch (IOException e) {
				e.printStackTrace();
			}
 
		}else if(level == 2) {
			this.level = 2 ; 
			XEnemyStartLocation.clear();YEnemyStartLocation.clear();XEnemyEndLocation.clear();YEnemyEndLocation.clear();
			
			//XEnemyStartLocation.add(2100);YEnemyStartLocation.add(970);
			XEnemyStartLocation.add(2000);YEnemyStartLocation.add(870);
			XEnemyStartLocation.add(2000);YEnemyStartLocation.add(770);
			XEnemyStartLocation.add(2000);YEnemyStartLocation.add(670);
			XEnemyStartLocation.add(2000);YEnemyStartLocation.add(570);
			XEnemyStartLocation.add(2000);YEnemyStartLocation.add(470);
		
			//XEnemyStartLocation.add(-70);YEnemyStartLocation.add(570);// Middle Turn Lane Going to right
			//from rigth to left
			//XEnemyEndLocation.add(100);YEnemyEndLocation.add(970);
			XEnemyEndLocation.add(200);YEnemyEndLocation.add(870); // Middle Lane
			XEnemyEndLocation.add(150);YEnemyEndLocation.add(770); // Middle Lane
			XEnemyEndLocation.add(100);YEnemyEndLocation.add(670);  // Where Cars Disappear
			XEnemyEndLocation.add(100);YEnemyEndLocation.add(570); 
			XEnemyEndLocation.add(100);YEnemyEndLocation.add(470); 
			
			try {
				EnemyFrequency = 20 ;
				XStart  = 200;
				YStart  = 550;
				XEnd    = 1600;
				YEnd    = 1800;
				BackGround = ImageIO.read(new File("Images\\Level2.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}

		}else if(level == 3) {	
			this.level = 3 ;
			//XEnemyStartLocation.add(2100);YEnemyStartLocation.add(970);
			XEnemyStartLocation.add(2100);YEnemyStartLocation.add(650);
			XEnemyStartLocation.add(2100);YEnemyStartLocation.add(600);
			XEnemyStartLocation.add(2100);YEnemyStartLocation.add(550);
			XEnemyStartLocation.add(2100);YEnemyStartLocation.add(510);
			XEnemyStartLocation.add(2100);YEnemyStartLocation.add(470);

			//XEnemyStartLocation.add(-70);YEnemyStartLocation.add(570);// Middle Turn Lane Going to right
		 
			//from rigth to left
			//XEnemyEndLocation.add(100);YEnemyEndLocation.add(970);
			XEnemyEndLocation.add(100);YEnemyEndLocation.add(650); // Middle Lane
			XEnemyEndLocation.add(100);YEnemyEndLocation.add(600); // Middle Lane
			XEnemyEndLocation.add(100);YEnemyEndLocation.add(550);  // Where Cars Disappear
			XEnemyEndLocation.add(100);YEnemyEndLocation.add(510); 
			XEnemyEndLocation.add(100);YEnemyEndLocation.add(470); 
			try {
				XStart  = 1550;
				YStart  = 900;
				XEnd    = 150;
				YEnd    = 150;
				EnemyFrequency = 12;
				setLevelSong("Songs\\PVZ.wav");
				BackGround = ImageIO.read(new File("Images\\Level3.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}	
		}else if(level == 4) {
			this.level = 4 ;
			try {
				XStart  = 900;
				YStart  = 900;
				XEnd    = 1700;
				YEnd    = 250;
				EnemyFrequency = 15 ;
				BackGround = ImageIO.read(new File("Images\\Level5.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}	
		}
		return BackGround;
	}
	
	
	public void SetBackGround(Image BackGround) {
		this.BackGround = BackGround;
	}
		
	public int GetXStart() {
		return XStart;
	}
	public void SetXStart(int XStart) {
		this.XStart = XStart;
	}
	
	
	public int GetYStart() {
		return YStart;
	}
	public void SetYStart(int YStart) {
		this.YStart = YStart;
	}
	
	
	
	public int GetXEnd() {
		return XEnd;
	}
	public void SetXEnd(int XEnd) {
		this.XEnd = XEnd;
	}
	
	
	public int GetYEnd() {
		return YEnd;
	}
	public void SetYEnd(int YEnd) {
		this.YEnd = YEnd;
	}
	public int getEnemyFrequency() {
		return EnemyFrequency;
	}
	public void setEnemyFrequency(int enemyFrequency) {
		EnemyFrequency = enemyFrequency;
	}
	public ArrayList<Integer> getXEnemyStartLocation() {
		return XEnemyStartLocation;
	}
	public void setXEnemyStartLocation(ArrayList<Integer> xEnemyStartLocation) {
		XEnemyStartLocation = xEnemyStartLocation;
	}
	public ArrayList<Integer> getYEnemyStartLocation() {
		return YEnemyStartLocation;
	}
	public void setYEnemyStartLocation(ArrayList<Integer> yEnemyStartLocation) {
		YEnemyStartLocation = yEnemyStartLocation;
	}
	public ArrayList<Integer> getXEnemyEndLocation() {
		return XEnemyEndLocation;
	}
	public void setXEnemyEndLocation(ArrayList<Integer> xEnemyEndLocation) {
		XEnemyEndLocation = xEnemyEndLocation;
	}
	public ArrayList<Integer> getYEnemyEndLocation() {
		return YEnemyEndLocation;
	}
	public void setYEnemyEndLocation(ArrayList<Integer> yEnemyEndLocation) {
		YEnemyEndLocation = yEnemyEndLocation;
	}
	public String getLevelSong() {
		return LevelSong;
	}
	public void setLevelSong(String levelSong) {
		LevelSong = levelSong;
	}
	
	
	

	
}
