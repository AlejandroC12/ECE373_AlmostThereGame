package GameDriver;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.awt.image.ImageProducer;
import java.awt.image.RGBImageFilter;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.Timer;

import LivingBeings.Enemy;
import LivingBeings.Student;
import Vehicles.Car;
import Maps.*;


public class AlmostThere extends JPanel implements ActionListener , GameLoader{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final int DELAY = 100;
    private final int x[] = new int[2];
    private final int y[] = new int[2];
    
    private ImageProducer filteredImgProd,filteredWalker,filteredUser;
    private Image         transparentImg,transparentUser;
    
	private JButton Start,LoadGame,Settings;
	private JButton Easy, Medium, Hard;
	
	private int Level;
	private double Difficulty;
	private boolean Collision = false,inGame   = true,inMenu   = true;

	private GameMap MapLevel = new GameMap();
	private Student Player   = new Student();
	public Image PlayerImage, BackGround;

	public int EnemyFreq;
	private Random RandomInteger    = new Random();
	
	private ArrayList<Car> EnemyCarsList             = new ArrayList<Car>();
	private ArrayList<Enemy> EnemyWalkerList         = new ArrayList<Enemy>();
	private ArrayList<Image> EnemysWalkersImageList  = new ArrayList<Image>();
	private ArrayList<Image> EnemysCarsImageList     = new ArrayList<Image>();

	
	private int Iterations ;
	private int PlayerState ;
	
	private Timer timer;
	private static Clip clip; // Game Music
	
		 public AlmostThere() {		
			         Difficulty = 1 ; 
			 		 inGame = false;
			 		 inMenu = true;
			 		 StartMenu();
			 		 buildGUI() ;
			 		 setVisible(true);	
			 		 Level = 1 ;
			 		 Iterations = 0 ;
			 		 
		 }
		 	
	    private void initGame() {  	
	    	x[1]  = MapLevel.GetXStart(); // x axis starting location level dependent 
	        y[1]  = MapLevel.GetYStart(); // y axis starting location level dependent 
	        x[0]  = x[1];
	        y[0]  = y[1];
	        
	        if(MapLevel.GetLevel() != 2 && MapLevel.GetLevel() != 4) {
	        	clip.close();
	        	GameMusic(new File(MapLevel.getLevelSong()));
	        }       
	        if(MapLevel.GetLevel() == 2) { // level 2 requires more speed or is impossible to manuver sideways
	        	Player.SetVelocity(10);
	        }else {
	        	Player.SetVelocity(4); // normal speed 
	        }   
	        Player.SetVelocity(30);
	           loadImages();
		       StartEnemy(); // creates the array of enemys so the game can start
		       Filter();    // filter background for the superior images
	       timer = new Timer(DELAY, this); // delay for the action performed so we can repaint and do all the game logic
	       timer.start();
	    }
	    
		private void loadImages() {
			BackGround =  MapLevel.StartLevelBackGround();//ImageIO.read(new File("C:\\Users\\Alejandro\\Desktop\\ECE373\\AlmostThere\\Level1.png"));	
	        int NumberofEvil = MapLevel.getEnemyFrequency();
	        NumberofEvil = (int) Math.ceil(Difficulty*NumberofEvil);
	        MapLevel.setEnemyFrequency(NumberofEvil);
	        
	    	PlayerImage = Player.LoadPlayerImage(Integer.toString(Level));
	    	EnemysWalkersImageList.clear();
	    	EnemyCarsList.clear();
	    	EnemyWalkerList.clear();
	    	if(MapLevel.GetLevel() != 2 && MapLevel.GetLevel() != 3) {
		        for(int i = 0 ; i < MapLevel.getEnemyFrequency() ; i++) {
		        	Car oneCar = new Car("Normal");      	
		        	EnemysCarsImageList.add(oneCar.LoadCarImage());
		        	EnemyCarsList.add(oneCar);
		        }	
	    	}
	    	if(MapLevel.GetLevel() != 1) {    		
	    		for(int i = 0 ; i < MapLevel.getEnemyFrequency() ; i++) {
			        Enemy oneEnemy = new Enemy("FootBall", 20);   
			        EnemysWalkersImageList.add(oneEnemy.LoadEnemyWalkerImage());
			        EnemyWalkerList.add(oneEnemy);
			    }	
	    	}
	    }
	    
	    private void CollisonDetection() {
	    	if(MapLevel.GetLevel() != 2 && MapLevel.GetLevel() != 3) {
	    		for(int i = 0 ; i < MapLevel.getEnemyFrequency() ; i++) { 
			    	if(EnemyCarsList.get(i).getxLocation()[0] >= x[1]-20 && EnemyCarsList.get(i).getxLocation()[0] <= x[1]+20 && EnemyCarsList.get(i).getyLocation()[0] >= y[1] && EnemyCarsList.get(i).getyLocation()[0] <= y[1]+70) {
			    		PlayerImage = Player.LoadPlayerImage("death");
			    		Filter();
			    		Iterations = 0 ;
				        Collision = true;
			    	}
	    		}
	    	}
	    
	    	if(MapLevel.GetLevel() != 1) { 
		    	for(int i = 0 ; i < MapLevel.getEnemyFrequency() ; i++) { 		
			    	if(EnemyWalkerList.get(i).getxLocation()[0] >= x[1]-10 && EnemyWalkerList.get(i).getxLocation()[0] <= x[1]+10 && EnemyWalkerList.get(i).getyLocation()[0] >= y[1] && EnemyWalkerList.get(i).getyLocation()[0] <= y[1]+50) {
			    		PlayerImage = Player.LoadPlayerImage("death");
			    		Filter();
			    		Iterations = 0 ;
				        Collision = true;
			    	}
	    		}
    		}
	    }
	    
	    private void Filter() {
	    	ImageFilter filter = new RGBImageFilter(){
		          int transparentColor = Color.white.getRGB() | 0xFF000000;
		          public final int filterRGB(int x, int y, int rgb) {
		             if ((rgb | 0xFF000000) == transparentColor) {
		                return 0x00FFFFFF & rgb;
		             } else {
		                return rgb;
		             }
		          }
		       };       
		        filteredUser    = new FilteredImageSource(PlayerImage.getSource(), filter);
			    transparentUser = Toolkit.getDefaultToolkit().createImage(filteredUser);
			if(EnemyCarsList.size() > 0) {    
			    filteredImgProd = new FilteredImageSource(EnemyCarsList.get(0).LoadCarImage().getSource(), filter);
			    for(int  i = 0 ; i < EnemyCarsList.size(); i++) {
			        transparentImg = Toolkit.getDefaultToolkit().createImage(filteredImgProd);   
			        EnemysCarsImageList.set(i,transparentImg);
			    }  
			}  
			if(EnemyWalkerList.size() > 0) {
				filteredWalker = new FilteredImageSource(EnemyWalkerList.get(0).LoadEnemyWalkerImage().getSource(), filter);
				for(int  i = 0 ; i < EnemyWalkerList.size(); i++) {
				      transparentImg = Toolkit.getDefaultToolkit().createImage(filteredWalker);   
				      EnemysWalkersImageList.set(i,transparentImg);
				}   
		    }
	    }
		// ******************************************* Player inGame Handles
		private void PlayerChange() {
			
	        if(Collision == true) {
		        PlayerImage = Player.LoadPlayerImage(Integer.toString(MapLevel.GetLevel()));
	    		x[1]  = MapLevel.GetXStart();
		        y[1]  = MapLevel.GetYStart(); 
		        x[0]  = x[1];
		        y[0]  = y[1];	
	        	Collision = false;
	        	PlayerState = 0 ;
	        }else if(PlayerState == 2){
	        	 PlayerImage = Player.LoadPlayerImage(Integer.toString(MapLevel.GetLevel())+2);
	        	 PlayerState = 0;
	        }else if(PlayerState == 1){
	        	 PlayerImage = Player.LoadPlayerImage(Integer.toString(MapLevel.GetLevel())+1);
	        	 PlayerState = 0;
	        }else {
	        	 PlayerImage = Player.LoadPlayerImage(Integer.toString(MapLevel.GetLevel()));
	        }
	        Filter();
	        Iterations = 0 ;
		}
		
	    private class TAdapter extends KeyAdapter {	
	        @Override
	        public void keyPressed(KeyEvent e) {
	            int key = e.getKeyCode();
	            if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A) {
	                move(0);//System.out.println("LEFT");
	            }else if (key == KeyEvent.VK_RIGHT|| key == KeyEvent.VK_D) {
	                move(1);//System.out.println("Rigth");
	            }else if ((key == KeyEvent.VK_UP  || key == KeyEvent.VK_W) ) {
	                move(2);// System.out.println("Up");
	            }else if ((key == KeyEvent.VK_DOWN|| key == KeyEvent.VK_S)) {
	                move(3);//System.out.println("Down");
	            }else if(key == KeyEvent.VK_ESCAPE) {
	            	MapLevel.SetLevel(2); 
	            	HRGUI.saveData(MapLevel);//System.out.println("Esc event");
	            }
	        }
	 }
	    private void move(int direction) {
	        x[1] =   x[0];   y[1] =  y[0];
	        if(Collision != true) {
	        	//DetectMapConflict();
		        if (direction == 0 && x[1] > 0) {
		            x[0]  -= Player.GetVelocity();  
		            if((PlayerState == 0 || PlayerState == 1 )&& PlayerState != 2 ) {
		            	PlayerState = 2;
		            }else if( (PlayerState == 0 || PlayerState == 2) && PlayerState != 1) {
		            	PlayerState = 1;
		            }else{
		            	PlayerState = 0 ;
		            }
		        }else if (direction == 1 && x[1] < 1885) {
		            x[0]  += Player.GetVelocity();
		            if( (PlayerState == 0 || PlayerState == 1 ) && PlayerState != 2 ) {
		            	PlayerState = 2;
		            }else if( (PlayerState == 0 || PlayerState == 2) && PlayerState != 1) {
		            	PlayerState = 1;
		            }else{
		            	PlayerState = 0 ;
		            }
		        }else if (direction == 2 && y[1] > 0) {
		            y[0]  -= Player.GetVelocity();
		            if((PlayerState == 0 || PlayerState == 1 )&& PlayerState != 2 ) {
		            	PlayerState = 2;
		            }else if( (PlayerState == 0 || PlayerState == 2) && PlayerState != 1) {
		            	PlayerState = 1;
		            }else{
		            	PlayerState = 0 ;
		            }
		        }else if (direction == 3 && y[1] < 935) {
		            y[0]  += Player.GetVelocity();
		            if((PlayerState == 0 || PlayerState == 1 )&& PlayerState != 2 ) {
		            	PlayerState = 2;
		            }else if( (PlayerState == 0 || PlayerState == 2) && PlayerState != 1) {
		            	PlayerState = 1;
		            }else{
		            	PlayerState = 0 ;
		            }
		        }
	        }
	       // System.out.println("Y = " + y[0]);
	       // System.out.println("X = " + x[0]);
	    }
	
	    private void CheckEnd(){
	    	if(MapLevel.GetLevel() == 1 || MapLevel.GetLevel() == 3 || MapLevel.GetLevel() == 4) {
		    	if(x[1] <  MapLevel.GetXEnd() && y[1] < MapLevel.GetYEnd()) {
		    		MapLevel.SetLevel(MapLevel.GetLevel()+1);
		    		loadImages();
		    		initGame();
		    	}
	    	}else {
		    	if(x[1] >  MapLevel.GetXEnd() && y[1] < MapLevel.GetYEnd()) {
		    		MapLevel.SetLevel(MapLevel.GetLevel()+1);
		    		loadImages();
		    		initGame();
		    	}
	    	}
	    }
	    

	    //******************************************************* Enemy in game handles

	    
	    private void StartEnemy() {
	    	int yTemp[] ;int xTemp[] ;
	    	int index   = RandomInteger.nextInt(MapLevel.getXEnemyStartLocation().size());
	    	int rand    = RandomInteger.nextInt(8);
		    if(MapLevel.GetLevel() != 2 && MapLevel.GetLevel() != 3) { 
		        for(int i = 0 ; i < EnemyCarsList.size() ; i++) {
			    	 index   = RandomInteger.nextInt(MapLevel.getXEnemyStartLocation().size());
			    	 rand    = RandomInteger.nextInt(8);
			    		    				    	
				    	xTemp     = EnemyCarsList.get(i).getxLocation();
			    		xTemp[0]  = MapLevel.getXEnemyStartLocation().get(index);	
			 	    	yTemp     = EnemyCarsList.get(i).getyLocation();
			 	    	yTemp[0]  = MapLevel.getYEnemyStartLocation().get(index);

				        if(xTemp[0] >= 2000) {
				        	xTemp[0] += 200*(rand);
				        }else {
				        	xTemp[0] -= 200*(rand);
				        }
				    	
				    	if(xTemp[0] >= 2000) {
				    		EnemyCarsList.get(i).SetVelocity(-50);
				    	}else if(xTemp[0] < -50) {
				    		EnemyCarsList.get(i).SetVelocity(50);
				    	}
				    	EnemyCarsList.get(i).setxLocation(xTemp);
				    	EnemyCarsList.get(i).setxEnd(MapLevel.getXEnemyEndLocation().get(index));
				    	//System.out.println(MapLevel.getXEnemyEndLocation().get(index));
				    	EnemyCarsList.get(i).setyLocation(yTemp);
				    	EnemyCarsList.get(i).setyEnd(MapLevel.getYEnemyEndLocation().get(index));  	
		        } 
		    }
		    if(MapLevel.GetLevel() != 1) { 
		        for(int i = 0 ; i < EnemyWalkerList.size() ; i++) {
			    	    index   = RandomInteger.nextInt(MapLevel.getXEnemyStartLocation().size());
			    	    rand    = RandomInteger.nextInt(8);
			    				    	
				    	xTemp     = EnemyWalkerList.get(i).getxLocation();
			    		xTemp[0]  = MapLevel.getXEnemyStartLocation().get(index);
			    		//xTemp[0]  =  xTemp[1];	
			 	    	yTemp     = EnemyWalkerList.get(i).getyLocation();
			 	    	yTemp[0]  = MapLevel.getYEnemyStartLocation().get(index);
			 	    	//yTemp[0]  = yTemp[1];
			 	    	
				        if(xTemp[0] >= 2000) {
				        	xTemp[0] += 200*(rand);
				        }else {
				        	xTemp[0] -= 200*(rand);
				        }
				        //xTemp[0]   = xTemp[1];
				    	//yTemp[0]   = yTemp[1];
				    	
				    	if(xTemp[0] >= 2000) {
				    		EnemyWalkerList.get(i).SetVelocity(-15);
				    	}else if(xTemp[0] < -50) {
				    		EnemyWalkerList.get(i).SetVelocity(15);
				    	}
				    	EnemyWalkerList.get(i).setxLocation(xTemp);
				    	EnemyWalkerList.get(i).setxEnd(MapLevel.getXEnemyEndLocation().get(index));
				    	//System.out.println(MapLevel.getXEnemyEndLocation().get(index));
				    	EnemyWalkerList.get(i).setyLocation(yTemp);
				    	EnemyWalkerList.get(i).setyEnd(MapLevel.getYEnemyEndLocation().get(index));  	
		        } 
  	
		    }
	    }
	    
	    private void EnemyMove() {	
	    	int yTemp[] ;  int xTemp[] ;
	    	 if(MapLevel.GetLevel() != 2 && MapLevel.GetLevel() != 3) { 
		    	 for(int i = 1 ; i <EnemyCarsList.size(); i++) {  
		    		xTemp  = EnemyCarsList.get(i).getxLocation(); // get the reference of x 
		    		//xTemp[1]  = xTemp[0];    		
		 	    	yTemp = EnemyCarsList.get(i).getyLocation(); // get the reference of y
		 	    	//yTemp[1]  = yTemp[0];    	
		 	    	xTemp[0] += EnemyCarsList.get(i).GetVelocity();
		 	    	
			    	EnemyCarsList.get(i).setxLocation(xTemp);  
			    	EnemyCarsList.get(i).setyLocation(yTemp);
		    	 }
	    	 }
	    	 if(MapLevel.GetLevel() != 1) {
		    	 for(int i = 1 ; i < EnemyWalkerList.size(); i++) {  
		    		xTemp  = EnemyWalkerList.get(i).getxLocation(); // get the reference of x 
		    		//xTemp[1]  = xTemp[0];    		
		 	    	yTemp = EnemyWalkerList.get(i).getyLocation(); // get the reference of y
		 	    	//yTemp[1]  = yTemp[0];    	
		 	    	xTemp[0] += EnemyWalkerList.get(i).GetVelocity();
		 	    	if(MapLevel.GetLevel() == 3) {
		 	    		yTemp[0] -= EnemyWalkerList.get(i).GetVelocity()/15;
		 	    		
		 	    	}
		 	    	EnemyWalkerList.get(i).setxLocation(xTemp);  
		 	    	EnemyWalkerList.get(i).setyLocation(yTemp);
		    	 }
	    	 }
	    }
	    
	    private void EnemyDissapear() {
	    	RandomInteger = new Random();
	    	int xTemp[] ;   int yTemp[] ;
    		int index = 0 ;
	    	if(MapLevel.GetLevel() != 2 && MapLevel.GetLevel() != 3) {
		    	for(int i = 0 ; i < EnemyCarsList.size() ; i++) { 
			    	if(EnemyCarsList.get(i).getxLocation()[0] >= EnemyCarsList.get(i).getxEnd()-Math.abs(EnemyCarsList.get(i).GetVelocity()) && EnemyCarsList.get(i).getxLocation()[0] <= EnemyCarsList.get(i).getxEnd()+Math.abs(EnemyCarsList.get(i).GetVelocity()) ) {
			    		index = RandomInteger.nextInt(MapLevel.getXEnemyStartLocation().size());
	
			    		xTemp     = EnemyCarsList.get(i).getxLocation();
			    		xTemp[0]  = MapLevel.getXEnemyStartLocation().get(index);
			    		//xTemp[0]  =  xTemp[1];	
			 	    	yTemp     = EnemyCarsList.get(i).getyLocation();
			 	    	yTemp[0] =  MapLevel.getYEnemyStartLocation().get(index);
			 	    	//yTemp[0]  = yTemp[1];
			 	    	
				    	 if(xTemp[0] >= 2100) {
					    		EnemyCarsList.get(i).SetVelocity(-50);
				    	 }
				    	 else if(xTemp[0] < -50) {
					    		EnemyCarsList.get(i).SetVelocity(50);
					     }
			    	 
				    	EnemyCarsList.get(i).setxLocation(xTemp);
				    	EnemyCarsList.get(i).setxEnd(MapLevel.getXEnemyEndLocation().get(index));
				    	EnemyCarsList.get(i).setyLocation(yTemp);
				    	EnemyCarsList.get(i).setyEnd(MapLevel.getYEnemyEndLocation().get(index));	    	
			    	}
		    	}
	    	}
	    	if(MapLevel.GetLevel() != 1) {
		    	for(int i = 0 ; i < EnemyWalkerList.size() ; i++) { 
			    	if(EnemyWalkerList.get(i).getxLocation()[0] >= EnemyWalkerList.get(i).getxEnd()-Math.abs(EnemyWalkerList.get(i).GetVelocity()) && EnemyWalkerList.get(i).getxLocation()[0] <= EnemyWalkerList.get(i).getxEnd()+Math.abs(EnemyWalkerList.get(i).GetVelocity()) ) {
			    		index = RandomInteger.nextInt(MapLevel.getXEnemyStartLocation().size());
	
			    		xTemp     = EnemyWalkerList.get(i).getxLocation();
			    		xTemp[0]  = MapLevel.getXEnemyStartLocation().get(index);
			    		//xTemp[0]  =  xTemp[1];	
			 	    	yTemp     = EnemyWalkerList.get(i).getyLocation();
			 	    	yTemp[0] =  MapLevel.getYEnemyStartLocation().get(index);
			 	    	//yTemp[0]  = yTemp[1];
			 	    	
				    	 if(xTemp[0] >= 2100) {
				    		 EnemyWalkerList.get(i).SetVelocity(-15);
				    	 }
				    	 else if(xTemp[0] < -50) {
				    		 EnemyWalkerList.get(i).SetVelocity(15);
					     }
			    	 
				    	 EnemyWalkerList.get(i).setxLocation(xTemp);
				    	 EnemyWalkerList.get(i).setxEnd(MapLevel.getXEnemyEndLocation().get(index));
				    	 EnemyWalkerList.get(i).setyLocation(yTemp);
				    	 EnemyWalkerList.get(i).setyEnd(MapLevel.getYEnemyEndLocation().get(index));	    	
			    	}
		    	}
	    		
	    		
	    	}
	    	
	    }
	    
	    // this is the stetical part of the code painting and music
	    
	    @Override
	    public void paintComponent(Graphics g) {    	 	
	        super.paintComponent(g);
	        doDrawing(g);
	    }
	    	 
	    private void doDrawing(Graphics g) {     
	    	g.drawImage(BackGround, 0, 0, this); // BackGround Image
	        if (inGame) { 	
	        	if(EnemyCarsList.size() != 0) {
		        	if(MapLevel.GetLevel() != 2 && MapLevel.GetLevel() != 3 ) {
			        	for(int i = 0 ; i < MapLevel.getEnemyFrequency() ; i++) { // Enemy Car painting 
			        		g.drawImage(EnemysCarsImageList.get(i), EnemyCarsList.get(i).getxLocation()[0], EnemyCarsList.get(i).getyLocation()[0], this);    	
			        	}
		        	}
	        	}
	        	if(EnemyWalkerList.size() != 0) {
		        	for(int i = 0 ; i <  MapLevel.getEnemyFrequency() ; i++) { // Enemy Car painting 
		        		g.drawImage(EnemysWalkersImageList.get(i), EnemyWalkerList.get(i).getxLocation()[0], EnemyWalkerList.get(i).getyLocation()[0], this);    	
		        	}
	        	}
	        	g.drawImage(transparentUser, x[1], y[1], this); // this is the user picture getting draw
	        	//g.drawImage(transparentImg, xEnemy[1], yEnemy[1], this);  
	            Toolkit.getDefaultToolkit().sync();
	        } else if(inMenu){
	        	Start.setSize(1000,100);
		        Start.setBounds(775, 795,380, 185);
	        	        
				LoadGame.setSize(1000,100);
				LoadGame.setBounds(1550, 795,380, 185);   
	            
				Settings.setSize(1000,100);
				Settings.setBounds(0, 795,380, 185);
			    Toolkit.getDefaultToolkit().sync();	
				//Easy.setSize(1000,100);
				//Easy.setBounds(0, 795,380, 185);
	        } else {
	        	Toolkit.getDefaultToolkit().sync();	
	        	
				
	        	Easy.setSize(1000,100);
				Easy.setBounds(0, 795,380, 185);
				
				Medium.setSize(1000,100);
				Medium.setBounds(775, 795,380, 185);
		        

				Hard.setSize(1000,100);
				Hard.setBounds(1550, 795,380, 185);
				
	        }
	    }
	    
	    @Override
	    public void actionPerformed(ActionEvent e) {      	       
	        CheckEnd();
	        EnemyMove();
	        EnemyDissapear();
	        if(Collision  == false) {
	        	CollisonDetection();	
	        }
	        if(Iterations > 3) { // Number of repaints dependent of the Delay to Change the player visual image 
	        	PlayerChange();
	        }
	        repaint();
	        Iterations++;
	    }
	 
		 
			public void buildGUI() { 
			    Start.addActionListener(new ButtonListener());
			    LoadGame.addActionListener(new ButtonListener());
			    Settings.addActionListener(new ButtonListener());
			    
			    Easy.addActionListener(new ButtonListener());
			    Medium.addActionListener(new ButtonListener());
			    Hard.addActionListener(new ButtonListener());
			}
			public void StartMenu() {	 
					try {
						BackGround = ImageIO.read(new File("Images\\Back.jpg"));

						
						Image img3       = ImageIO.read(new File("Images\\PlayButton.jpg"));
						ImageIcon Icon1  =  new ImageIcon(img3);//createImageIcon("images/right.gif");
				        Start = new JButton(Icon1);
   
						Image img4 = ImageIO.read(new File("Images\\LoadButton.PNG"));
						ImageIcon Icon2  =  new ImageIcon(img4);//createImageIcon("images/right.gif");
						LoadGame = new JButton(Icon2);   

						Image img5 = ImageIO.read(new File("Images\\SettingsButton.PNG"));
						ImageIcon Icon3  =  new ImageIcon(img5);//createImageIcon("images/right.gif");
						Settings = new JButton(Icon3);
						
						Image img6       = ImageIO.read(new File("Images\\EasyButton.png"));
						ImageIcon Icon4  =  new ImageIcon(img6);//createImageIcon("images/right.gif");
						Easy = new JButton(Icon4);
						Easy.setVisible(false);
						Easy.setEnabled(false);
						Image img7 = ImageIO.read(new File("Images\\MediumButton.PNG"));
						ImageIcon Icon5  =  new ImageIcon(img7);//createImageIcon("images/right.gif");
						Medium = new JButton(Icon5);
						Medium.setVisible(false);
						Medium.setEnabled(false);
						Image img8 = ImageIO.read(new File("Images\\HardButton.PNG"));
						ImageIcon Icon6  =  new ImageIcon(img8);//createImageIcon("images/right.gif");
						Hard = new JButton(Icon6);
						Hard.setVisible(false);
						Hard.setEnabled(false);
			           
						GameMusic(new File("Songs\\WotLK.wav"));
				        add(Start);
				        add(LoadGame);
				        add(Settings);
				        add(Easy);
				        add(Medium);
				        add(Hard);
					} catch (IOException e) {
						e.printStackTrace();
					}	
			   }
		
			 private class ButtonListener implements ActionListener { // handle the button clicks at intial screen
					public void actionPerformed(ActionEvent e) { //this is the method MenuListener must implement, as it comes from the ActionListener interface.
						JButton source = (JButton)(e.getSource());
						if(source.equals(Start) ){
							handleStart();		
						}else if(source.equals(LoadGame) ) {
							handleLoadGame();
						}else if(source.equals(Settings) ) {
							handleSettings();
						}else if(source.equals(Easy) ) {
							Difficulty = .66;
							inMenu = true;
							
							Easy.setEnabled(false);Easy.setVisible(false);
							Medium.setEnabled(false);Medium.setVisible(false);
							Hard.setEnabled(false);Hard.setVisible(false);
							
							Start.setEnabled(true)   ;Start.setVisible(true);
						    LoadGame.setEnabled(true);LoadGame.setVisible(true);       
						    Settings.setEnabled(true);Settings.setVisible(true);
						}else if(source.equals(Medium) ) {
							Difficulty = 1;
							inMenu = true;
							
							Easy.setEnabled(false);Easy.setVisible(false);
							Medium.setEnabled(false);Medium.setVisible(false);
							Hard.setEnabled(false);Hard.setVisible(false);
							
							Start.setEnabled(true)   ;Start.setVisible(true);
						    LoadGame.setEnabled(true);LoadGame.setVisible(true);       
						    Settings.setEnabled(true);Settings.setVisible(true);
							
							//handleSettings();
						}else if(source.equals(Hard) ) {
							Difficulty = 1.66;
							inMenu = true;
							
							Easy.setEnabled(false);Easy.setVisible(false);
							Medium.setEnabled(false);Medium.setVisible(false);
							Hard.setEnabled(false);Hard.setVisible(false);
							
							Start.setEnabled(true)   ;Start.setVisible(true);
						    LoadGame.setEnabled(true);LoadGame.setVisible(true);       
						    Settings.setEnabled(true);Settings.setVisible(true);
						}
					}	
						private void handleStart(){ 
					        addKeyListener(new TAdapter());
					        setFocusable(true);    
					        MapLevel  = new GameMap(Level);
					        inGame    = true;
					          
					        Start.setEnabled(false)   ;Start.setVisible(false);
					        LoadGame.setEnabled(false);LoadGame.setVisible(false);       
					        Settings.setEnabled(false);Settings.setVisible(false);
			
					        loadImages();
					        initGame();
					        
							//System.out.println("Start Button Click");
						}		
						private void handleLoadGame(){
							MapLevel = HRGUI.loadData();
							Level    = MapLevel.GetLevel();
							
							//handleStart();
							//System.out.println("LoadGame Button Click");
						}
						
						private void handleSettings(){
							Start.setEnabled(false)   ;Start.setVisible(false);
					        LoadGame.setEnabled(false);LoadGame.setVisible(false);       
					        Settings.setEnabled(false);Settings.setVisible(false);
					        
					
								//BackGround = ImageIO.read(new File("C:\\Users\\Alejandro\\Desktop\\ECE373\\AlmostThere\\Difficulty.jpg"));
									
								 
								
								Easy.setEnabled(true);Easy.setVisible(true);
								Medium.setEnabled(true);Medium.setVisible(true);
								Hard.setEnabled(true);Hard.setVisible(true);
								//GameMusic(new File("C:\\Users\\Alejandro\\Desktop\\ECE373\\AlmostThere\\WotLK.wav"));
						      //  add(Easy);
						      //  add(Medium);
						      //  add(Hard);
								inMenu = false;
							//System.out.println("Settings Button Click");
						}				
				}
			
			
			public static void GameMusic(File filename) {
				try {
					AudioInputStream audioInputStream  = AudioSystem.getAudioInputStream(filename.getAbsoluteFile());
					clip = AudioSystem.getClip();
					clip = AudioSystem.getClip();
					clip.open(audioInputStream);
					clip.loop(10);				
				} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e1) {
					e1.printStackTrace();
				}
				
			 }


}
