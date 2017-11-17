package GameDriver;

import java.util.ArrayList;
import java.util.Map;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
//import javafx.scene.media.MediaPlayer;
import javax.swing.*;

import Maps.GameMap;




public class HRGUI extends JFrame implements GameLoader { 
//	private Map MapLevel;
	
	private static int Level;

	public HRGUI(int Lev){
		//super("Almost There!"); // calls the constructor of jframe and inciializes the frame with titlte with windowtitle	
		//System.out.println(Level);
		try {
			Image img1 = ImageIO.read(new File("Images\\UofA.jpg"));
			this.setIconImage(img1);		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

			add(new AlmostThere());
			setTitle("Almost There!");
			setLocationRelativeTo(null);
		    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		    this.setExtendedState(this.getExtendedState() | JFrame.MAXIMIZED_BOTH); // Maximize the screen


	}
	

	public static void saveData(GameMap SAVEORLOAD) {
		 
	      try {
	         FileOutputStream fileOut = new FileOutputStream("SavedGame.ser");
	         ObjectOutputStream out   = new ObjectOutputStream(fileOut);
	         out.writeObject(SAVEORLOAD);
	         out.close();
	         fileOut.close();
	          System.out.printf("Save");
	      }catch(IOException i) {
	         i.printStackTrace();
	      }  
		
		
	}

	public static GameMap loadData() {
		GameMap SAVEORLOAD;
	     try {
	         FileInputStream fileIn = new FileInputStream("SavedGame.ser");
	         ObjectInputStream in   = new ObjectInputStream(fileIn);
	         SAVEORLOAD = (GameMap) in.readObject();
	         in.close();
	         fileIn.close();
	         Level = SAVEORLOAD.GetLevel();
	         System.out.printf("Load");
	      }catch(IOException i) {
	         i.printStackTrace();
	         return null;
	      }catch(ClassNotFoundException c) {
	         System.out.println("Employee class not found");
	         c.printStackTrace();
	         return null;
	      }
		return SAVEORLOAD;
	}
    
    
 
	

}
