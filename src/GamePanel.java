
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;


import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

public class GamePanel extends JPanel implements ActionListener, KeyListener {

	private int speed_factor;
	private Timer timer;
	private JPanel[][] tiles = new JPanel[25][25];
	private int snake_length = 3;
	private int head_pos;
	private ArrayList<Point> snake = new ArrayList<Point>();
	private int x_cord;
	private int y_cord;
	Point top_of_snake;
	Point score_point;
	boolean lose;
	boolean boundary;
	private int direction; 


	public GamePanel() {

		this.setLayout(new GridLayout(25,25));
		this.setBackground(Color.black);

		this.addKeyListener(this);
		this.setFocusable(true);
		this.requestFocusInWindow();

		speed_factor = 150;

		timer = new Timer(speed_factor,this);
		timer.start();

		addTiles();
		startingPoint();
		initializeSnake();

		// test fruit

		tiles[14][22].setBackground(Color.red);
		
		lose = false; //start by not losing the game
		boundary = false; //snake has not hit a boundary



	}

	protected void paintComponent(Graphics g){
		super.paintComponent(g);
		g.setColor(Color.black);
		g.fillRect(0, 0, 800, 800);

		if(loseCheck()) {
			System.out.println("You have lost. Game over");
			
			JOptionPane.showMessageDialog(this, "Game Over!");
			return;
		}


		//snake movement (in a method later?)
		//left  
		if(direction == 1 ) {			
			moveLeft();
		}

		if(direction == 2 ) {
			moveUp();
		}

		if(direction == 3 ) {
			moveDown();
		}

		if(direction == 4 ) {			
			moveRight();
		}


	}


	private boolean loseCheck() {
		
		
		if(boundary) {
			return true;
		}
		
		Point hold = snake.get(snake.size()-1);
		
		for(int i=0; i<snake.size()-1; i++){
			if(snake.get(snake.size()-1).equals(snake.get(i))){
		    	System.out.println("LOOOOOOOOOOOOOSE");
		    	return true;
		    }
		}
	
		
		
		return false;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		repaint();

		// Speeding up the snake
		if(System.currentTimeMillis()%1000000==0 && !(speed_factor < 30)) {
			timer.setDelay(speed_factor-=5);
		}
		
		if(loseCheck()){
			timer.stop();
			return;
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// Keys for moving snake

		if(e.getKeyCode() == 37) {// left
			if (direction!=4){
				direction  = 1;
			}
		}

		if(e.getKeyCode() == 38) { //up
			if(direction!=2){
				direction  = 3;		
			}
		}

		if(e.getKeyCode() == 39) { // right
			if(direction!=1){
				direction  = 4;
			}
		}

		if(e.getKeyCode() == 40) { //down
			if(direction!=3){
				direction  = 2; 
			}
		}
	}



	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}


	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
	}


	public void initializeSnake(){
		//initial snake
		for(int i=0;i<snake_length;i++){
			tiles[x_cord][y_cord].setBackground(Color.green);


			Point snake_point  = new Point();	
			snake_point.x = x_cord;
			snake_point.y = y_cord;

			snake.add(snake_point);

			x_cord+=1;			
		}
		x_cord--;
	}

	public void startingPoint(){
		x_cord = 5;
		y_cord = 16;	
		direction = 4; 

	}


	private void addTiles() {
		for (y_cord=0; y_cord<25; y_cord++){
			for(x_cord=0; x_cord<25; x_cord++){
				tiles[x_cord][y_cord] = new JPanel();
				tiles[x_cord][y_cord].setBorder(BorderFactory.createLineBorder(Color.pink));
				add(tiles[x_cord][y_cord]);
				tiles[x_cord][y_cord].setBackground(Color.white);	
			}
		}
	}

	private void moveLeft(){
		head_pos = x_cord - 1; // figure out why this works exactly to avoid errors
		if(head_pos >= 0  ){

			x_cord = head_pos;				
			tiles[x_cord][y_cord].setBackground(Color.green);

			Point snake_point  = new Point();
			snake_point.x = x_cord;
			snake_point.y = y_cord;

			snake.add(snake_point);


			tiles[snake.get(0).x][snake.get(0).y].setBackground(Color.white);
			snake.remove(0);


			head_pos --;
		}

		else {
			boundary = true;
			System.out.println("You lost");
			return;
		}
	}
	private void moveRight(){
		head_pos = x_cord + 1; // figure out why this works exactly to avoid errors
		if(head_pos <=24   ){

			x_cord = head_pos;				
			tiles[x_cord][y_cord].setBackground(Color.green);


			Point snake_point  = new Point();
			snake_point.x = x_cord;
			snake_point.y = y_cord;

			snake.add(snake_point);

			tiles[snake.get(0).x][snake.get(0).y].setBackground(Color.white);
			snake.remove(0);



			head_pos ++;
		}

		else {
			System.out.println("You lost");
			boundary = true;
			return;
		}
	}
	private void moveUp(){
		head_pos = y_cord+1;
		//conversion of current head position (in terms of x) now in terms of y)
		System.out.println(head_pos);

		if(head_pos < 25  ){

			y_cord = head_pos;				
			tiles[x_cord][y_cord].setBackground(Color.green);



			Point snake_point  = new Point();
			snake_point.x = x_cord;
			snake_point.y = y_cord;

			snake.add(snake_point);

			tiles[snake.get(0).x][snake.get(0).y].setBackground(Color.white);
			snake.remove(0);



			//tiles[x_cord][y_cord-snake_length].setBackground(Color.white);
			head_pos ++;
		}

		else {
			System.out.println("You lost");
			boundary = true;
			return;
		}


	}
	private void moveDown(){ // the down is actually up I think...
		head_pos = y_cord-1;

		if(head_pos >= 0  ){
			top_of_snake = snake.get(snake.size()-1);

			y_cord = head_pos;				
			tiles[x_cord][y_cord].setBackground(Color.green);

			Point snake_point  = new Point();
			snake_point.x = x_cord;
			snake_point.y = y_cord;

			snake.add(snake_point);

			if(!(tiles[14][22].equals(tiles[x_cord][y_cord]))) {
				tiles[snake.get(0).x][snake.get(0).y].setBackground(Color.white);
				snake.remove(0);
			}

			else {

				System.out.println("We went here!");
				snake_length++; //does it matter?
				//do nothing else I guess
			}

			head_pos --;



		}


		else {
			boundary = true;
			System.out.println("You lost");
			return;
		}
	}

}
