package GameDriver;

import java.awt.EventQueue;

import javax.swing.JFrame;

import Maps.*;

public interface GameLoader {
	int Level = 0;
	//public void StartMenu();
	
    public static void main(String[] args) {   	
    	//HRGUI StartGame = new HRGUI(Level);
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {                
                JFrame ex = new HRGUI(Level);
                ex.setVisible(true); 
            //    System.out.println("Game Loader MAIN");
            }
        });
        
    }
}
