package GameDriver;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JComponent;

class ImagePanel extends JComponent {
    private Image image;
    private int xlocation;
    private int ylocation;
    public ImagePanel(Image image,int x,int y) {
        this.image = image;
        xlocation  = x ;
        ylocation  = y ;
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);    
        g.drawImage(image, xlocation, ylocation, this);
    }
    
}

