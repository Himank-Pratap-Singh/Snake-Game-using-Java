package snake.game;

import javax.swing.*;

public class Snake extends JFrame{ // JFrame for making the frame which come under swing package. 

	Snake(){
		// As soon i call the main function the and make the object of snake class the snake() cons will automatically get called 
		// so all the basic functionality of the snake game will be in board class . Hence we make the object of board class in snake constructor.
		// For showing anything onto the frame then we  should add that using add() function. 
		Board b = new Board();
		add(b);
		pack(); // this function will internally calling getPreferredSize because in board class we use setPrefferdSize. use to set the size of frame.
		setLocationRelativeTo(null); // For getting the frame in the center. can also use setLoaction for passing your own location.
		setTitle("Snake Game"); // Can also use super("Snake Game") but it has one condition super always will be the first statement in constructor.
		setResizable(false);
		
	}
	
	public static void main(String[] args) { 
		new Snake().setVisible(true);  // for making a frame we have to do setVisible = true , because it is false by default.
	}
	
}
