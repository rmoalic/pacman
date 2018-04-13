package View;

import java.awt.event.*;

import javax.swing.ImageIcon;

import Data.DataLoader;
import Data.InvalidDataException;
import Logic.Logic;
import Logic.Jeux;
import java.awt.*;  

public class Fenetre  {
	
	private Frame f;
	private Logic l;

	public Fenetre(Logic gamelogic) {
	    l = gamelogic;
	    f= new Frame("Canvas Example");

	    int zoom = 2;

	    //f.add(new Drawer());  
	    f.setLayout(null);
	    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	    Dimension fensize = new Dimension(l.getPasDeResolution()*30*zoom, l.getPasDeResolution()*30*zoom);
	    f.setLocation(screenSize.width/2-300, screenSize.height/2-300);
	    f.setResizable(false);
	    //f.setUndecorated(true);
	    f.setTitle("PacMan Game");
	    f.setIconImage(new ImageIcon("images/pac-man.png").getImage());
	    f.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent){
				System.exit(0);
			}
		});


		Drawer drawer = new Drawer(l, zoom, fensize);
	    drawer.setBackground(Color.black);
	    f.setIgnoreRepaint(true);
	    //drawer.createBufferStrategy(2);
	    drawer.setSize(fensize);
	    
        f.addKeyListener(new CustomKeyListener());

        InfoPanel info = new InfoPanel(l);
		f.setLayout(new BorderLayout());
		f.add(drawer, BorderLayout.CENTER);
		f.add(info, BorderLayout.PAGE_END);
		f.pack();
	    drawer.validate();
	    f.setVisible(true);

        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        while(true) {
			drawer.update(drawer.getGraphics());
			info.updateInfo();
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	class CustomKeyListener implements KeyListener {
		public void keyTyped(KeyEvent e) {
		}

		public void keyPressed(KeyEvent e) {
		    switch (e.getKeyCode()) {
                case KeyEvent.VK_UP:
                    l.movePlayerUp();
                    break;
                case KeyEvent.VK_DOWN:
                    l.movePlayerDown();
                    break;
                case KeyEvent.VK_LEFT:
                    l.movePlayerLeft();
                    break;
                case KeyEvent.VK_RIGHT:
                    l.movePlayerRight();
                    break;

            }
		}

		public void keyReleased(KeyEvent e) {
		}
	}

	public static void main(String[] args) throws InvalidDataException {
        DataLoader dl = new DataLoader("map1.xml");
        Jeux j = new Jeux(dl);
		new Fenetre(j);
	}
}

