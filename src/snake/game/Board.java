package snake.game;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class Board extends JPanel implements ActionListener{ //  You cannot add a window(jFrame) to a container. so here we use jPannel. jPanel acts like an container .

	private Image apple; // For using the images globally.
	private Image dot;
	private Image head;
	private int snakeSize;  // For initial size of the snake that is 3.
	
	// frame size = 300 * 300 = 90000 , dot size for x and y both location will be 10 * 10 = 100, hence total dots in frame == 90000 / 100 = 900;
	private final int DOT_SIZE = 10;  
	private final int TOTAL_SIZE = 900;
	private final int AppleRandomPos = 29; // random no for plotting the apple in frame
	
	private int Appple_X;
	private int Appple_Y;
	
	private final int[] x = new int[TOTAL_SIZE];  // these x and y is for coordinate of snake in the frame.
	private final int[] y = new int[TOTAL_SIZE];
	
	private boolean leftDirection = false; // Initial boolean values of snake direction.
	private boolean rightDirection = true;
	private boolean upDirection = false;
	private boolean downDirection = false;
	
	private boolean inGame = true;
	
	private Timer timer;
	
	Board(){
		
		addKeyListener(new SnakeMove()); //Adds the specified key listener to receive key events from this component, and also we called the function keyPressed using this.
		setBackground(Color.BLACK);
		setPreferredSize(new Dimension(300, 300));  //setPreferredSize is in java swing package and new dimension is java awt package.
		// setPreferredSize is used for set the size of frame using dimension as width and height.	
		
		setFocusable(true);  // because keyListner or key event function will not work if we don't make it true.
		
		loadImages();
		initGame();
		
		}
	
	public void loadImages() {
		ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("snake/game/icons/apple.png"));
		apple = i1.getImage();
		// using class loader we getting the images from the system with its inbuilt function.
		
		ImageIcon i2 = new ImageIcon(ClassLoader.getSystemResource("snake/game/icons/dot.png"));
		dot = i2.getImage();
		
		ImageIcon i3 = new ImageIcon(ClassLoader.getSystemResource("snake/game/icons/head.png"));
		head = i3.getImage();
		
		// Science the imageIcon is an local variable in this function only, so for use these images globally we need declare global variable to store these images.
		
	}
	
	public void initGame() {
		snakeSize = 3; //  Initially the size of snake will be 3 or 4.
		for(int i = 0; i < snakeSize; i++) {
			x[i] = 50 - i * DOT_SIZE; // this is because the location of x vary from head of snake to tail by dot size. 
			y[i] = 50; // for y coordinate location will be the same from head to tail.
		}
		
		LocateApple(); // Calling function for locating the apple.
		
		timer = new Timer(150, this); // this will work with actionListner interface in which their is method called actionPerformed
		timer.start(); 
	}
	
	public void LocateApple() {
		
		// Random function will return values between 0 and 1, which is of no use so to locate apple onto the frame we have to multiply it with some integer no.
		int random = (int)(Math.random() * AppleRandomPos);
		// Suppose math.random will give max value i.e 1. if appleRandomPos = 50 , then random value will be 50 * 1 = 50, so Apple_x coordinate will be 
		// random * dotSize so 50 * 10 = 500, and we know that frame max size is 300 x 300 because of this reason we have to take of AppleRandomPos such that 
		// the apple coordinate x and y does not be greater that 300 hence we take AppleRandomPos its value as 29, 29 * 1 = 29, 29 * 10 = 290, and per dot size is 10 that this will work fine.
		Appple_X = random * DOT_SIZE;
		
		// Do this same for apple_y coordinate.
		random = (int)(Math.random() * AppleRandomPos);
		Appple_Y = random * DOT_SIZE;


	}
	
	public void AppleCollision() {
		if((x[0] == Appple_X) && (y[0] == Appple_Y)) {
			snakeSize++;
			LocateApple();
		}
	}
	
	public void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		drawImages(g);
		
	}
	
	public void drawImages(Graphics g) {
		
		if(inGame) {
			 g.drawImage(apple, Appple_X, Appple_Y, this);	 
			 for(int i = 0; i < snakeSize; i++) {
				 if(i == 0) {
					 g.drawImage(head, x[i], y[i], this);
				 }
				 else {
					 g.drawImage(dot, x[i], y[i], this);
				 }
			 }
			 Toolkit.getDefaultToolkit().sync();
			 //sync means Synchronizes this toolkit's graphics state. Some window systems may do buffering of graphics events.This method ensures that the display is up-to-date. It is useful for animation

		}
		else {
			gameOver(g);
		}
	}
	
	public void gameOver(Graphics g) {
		String display = "Game Over";
		Font font = new Font("SAN_SERIF", Font.BOLD, 15);
		FontMetrics metric = getFontMetrics(font);
		
		g.setColor(Color.LIGHT_GRAY);
		g.setFont(font);
		g.drawString(display, (300 - metric.stringWidth(display)) / 2, 300/2);
	}
	
	public void checkCollision() {
		
		for(int i = snakeSize; i > 0; i--) {
			if((i > 3) && (x[0] == x[i]) && (y[0] == y [i])) {
				inGame = false;
			}
		}
		
		if(x[0] >= 300) {
			inGame = false;
		}
		
		if(y[0] >= 300) {
			inGame = false;
		}
		
		if(x[0] < 0) {
			inGame = false;
		}
		
		if(y[0] < 0) {
			inGame = false;
		}
		
		if(!inGame) {
		timer.stop();
		}
		
	}
	
	public void snakeMove() {
		
		for(int i = snakeSize; i > 0; i--) {
			x[i] = x[i - 1];
			y[i] = y[i - 1];
		}
		
		if(leftDirection) {
			x[0] -= DOT_SIZE;
		}
		
		if(rightDirection) {
			x[0] += DOT_SIZE;
		}
		
		if(upDirection) {
			y[0] -= DOT_SIZE;
		}
		
		if(downDirection) {
			y[0] += DOT_SIZE;
		}
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// actionListener is an interface so for implementing it we also need to override all its methods like this one.
		// from this we will tell on which thing timer will applied through this actionPerformed function.
		
		if(inGame) {   // Initially inGame is true 
			AppleCollision();
			checkCollision();
			snakeMove();
		}
		
		repaint();  // if change the look of component like change in apple position and snake then we have to call or use this method in order to repaint it.
		
	}
	
	private class SnakeMove extends KeyAdapter{
		// Here we are using class in a class function of java we can't declare the class as public because we already have main class as public.
		// Invoked when a key has been typed.
	    // This event occurs when a key press is followed by a key release.
		
		public void keyPressed(KeyEvent e) {
		
			int key = e.getKeyCode(); // this will get the key code which is pressed
			
			if(key == KeyEvent.VK_LEFT && (!rightDirection)) {
				
				// it will check if the key pressed id left and also check the snake snake is not moving in the right side direction, because it cannot move back and fourth.
				leftDirection = true;
				upDirection = false;
				downDirection = false;
			}
			
			if(key == KeyEvent.VK_RIGHT && (!leftDirection)) {
				rightDirection = true;
				upDirection = false;
				downDirection = false;
			}
			
			if(key == KeyEvent.VK_UP && (!downDirection)) {
				upDirection = true;
				leftDirection = false;
				rightDirection = false;
			}
			
			if(key == KeyEvent.VK_DOWN && (!upDirection)) {
				downDirection = true;
				leftDirection = false;
				rightDirection = false;
			}
			
		}
	
	
	}
	
	



}
