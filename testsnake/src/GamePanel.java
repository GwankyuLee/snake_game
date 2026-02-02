import java.awt.event.*;
import java.awt.*;
import java.util.Random;
import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import javax.swing.JPanel;
/**
 *
 * @author glee11
 */
public class GamePanel extends JPanel implements ActionListener{
    
    private static final int GAME_WIDTH = 600;
    private static final int GAME_HEIGHT = 600;
    private static final int BLOCK_SIZE = 30;
    private static final int TOTAL_BLOCK_IN_GAME = (GAME_WIDTH/BLOCK_SIZE) * (GAME_HEIGHT/BLOCK_SIZE);
    private static final int DELAY = 100;
    private int[] x = new int[TOTAL_BLOCK_IN_GAME];
    private int[] y = new int[TOTAL_BLOCK_IN_GAME];
    private boolean start = false;
    private boolean eat = false;
    private int bodyLength = 4;
    private boolean gameOver = false;
    private int highestScore=0;
    private int foodX;
    private int foodY;
    private int obstacleX;
    private int obstacleY;
    private int obstacle2Y;
    private int obstacle2X;
    private int score=0;
    private Timer timer;
    private String direction = "UP";
    Random random = new Random();
    
    
    
    GamePanel() {
        //set starting point of the snake
        for (int i = 0; i<bodyLength; i++) {
            if (i == 0) {
                x[0] = ((GAME_WIDTH/BLOCK_SIZE)/2) *BLOCK_SIZE;
                y[0] = ((GAME_HEIGHT/BLOCK_SIZE)/2) *BLOCK_SIZE;
            }
            else {
                x[i] = ((GAME_WIDTH/BLOCK_SIZE)/2) *BLOCK_SIZE;
                y[i] = (((GAME_HEIGHT/BLOCK_SIZE)/2) *BLOCK_SIZE) + i*BLOCK_SIZE;
            }
        }
        //makes the food disapear from screen until starGame() method
        newFood();
        this.setPreferredSize(new Dimension(GAME_WIDTH, GAME_HEIGHT));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        startGame();
        obstacle();
        obstacle2();
        ReadFile();
    }
        
    public void startGame() {
        start = true;
        newFood();
        timer = new Timer(DELAY, this);
        timer.start();
    }
        
    @Override
    public void paintComponent(Graphics g) {
	super.paintComponent(g);
	draw(g);
    }
    
    public void draw(Graphics g) {
        if (start) {
            for (int i = 0; i<GAME_WIDTH/BLOCK_SIZE; i++) {
            g.drawLine(0, i*BLOCK_SIZE, GAME_WIDTH, i*BLOCK_SIZE);
            g.drawLine(i*BLOCK_SIZE, 0, i*BLOCK_SIZE, GAME_HEIGHT);
            } 
        
            g.setColor(Color.red);
            g.fillOval(foodX, foodY, BLOCK_SIZE, BLOCK_SIZE);
            
            g.setColor(new Color(random.nextInt(255),random.nextInt(255),random.nextInt(255)));
            g.fillRect(obstacleX, obstacleY, BLOCK_SIZE, BLOCK_SIZE);
            
            g.setColor(new Color(random.nextInt(255),random.nextInt(255),random.nextInt(255)));
            g.fillRect(obstacle2X, obstacle2Y, BLOCK_SIZE, BLOCK_SIZE);
        
            for (int i = 0; i<bodyLength; i++) {
                if (i == 0) {
                g.setColor(Color.white);
                g.fillRect(x[i], y[i], BLOCK_SIZE, BLOCK_SIZE);
            }
                else {
                g.setColor(Color.green);
                g.fillRect(x[i], y[i], BLOCK_SIZE, BLOCK_SIZE);
                }
            }
        }
        else if(gameOver){
            gameOver(g);
        }
        
    }
    public void obstacle() {
        if (start) {
            obstacleX = random.nextInt(GAME_WIDTH/BLOCK_SIZE) * BLOCK_SIZE;
            obstacleY = random.nextInt(GAME_HEIGHT/BLOCK_SIZE) * BLOCK_SIZE;
        }
    }
    public void obstacle2() {
        if (start) {
            obstacle2X = random.nextInt(GAME_WIDTH/BLOCK_SIZE) * BLOCK_SIZE;
            obstacle2Y = random.nextInt(GAME_HEIGHT/BLOCK_SIZE) * BLOCK_SIZE;
        }
    }
    public void newFood() {
        if (start == true) {
            foodX = random.nextInt(GAME_WIDTH/BLOCK_SIZE) * BLOCK_SIZE;
            foodY = random.nextInt(GAME_HEIGHT/BLOCK_SIZE) * BLOCK_SIZE;
            
            while (foodX == obstacleX && foodY == obstacleY) {
                foodX = random.nextInt(GAME_WIDTH/BLOCK_SIZE) * BLOCK_SIZE;
                foodY = random.nextInt(GAME_HEIGHT/BLOCK_SIZE) * BLOCK_SIZE;
            }
            while (foodX == obstacle2X && foodY == obstacle2Y) {
                foodX = random.nextInt(GAME_WIDTH/BLOCK_SIZE) * BLOCK_SIZE;
                foodY = random.nextInt(GAME_HEIGHT/BLOCK_SIZE) * BLOCK_SIZE;
            }
        }
        else {
            foodX = 10000;
            foodY = 10000;
        }
        eat = false;
    }
    
    public void move() {
        for (int i = bodyLength; i>0; i--) {
            x[i]  = x[i-1];
            y[i] = y[i-1];
        }
        switch (direction) {
            case "UP" : 
                y[0] = y[0]-BLOCK_SIZE;
                break;
            case "DOWN" : 
                y[0] = y[0] + BLOCK_SIZE;
                break;
            case "RIGHT" : 
                x[0] = x[0]+BLOCK_SIZE;
                break;
            case "LEFT" : 
                x[0] = x[0]-BLOCK_SIZE;
                break;
        }
    }
    //this includes setEat
    public void checkEat() {
        if (x[0] == foodX && y[0] == foodY) {
            score++;
            bodyLength ++;
            newFood();
            eat = true;
        }
    }
    public void checkCollisions() {
        //check if head collides with body
        for (int i = bodyLength; i>0; i--) {
            if ((x[0] == x[i]) && (y[0] == y[i])) {
                start = false;
                gameOver = true;
            }
        }
        if (x[0] == obstacleX && y[0] == obstacleY) {
            start = false;
            gameOver = true;
        }
        if (x[0] == obstacle2X && y[0] == obstacle2Y) {
            start = false;
            gameOver = true;
        }
        
        if (x[0]<0) {
            start = false;
            gameOver = true;
        }
        else if (x[0]> GAME_WIDTH) {
            start = false;
            gameOver = true;
        }
        else if (y[0] < 0) {
            start = false;
            gameOver = true;
        }
        else if (y[0] > GAME_HEIGHT) {
            start = false;
            gameOver = true;
        }
        
        
        
        if (!start) {
            timer.stop();
        }
        
    }
    public void gameOver(Graphics g) {
        g.setColor(Color.white);
        g.setFont(new Font("Arial", Font.BOLD, 75));
        FontMetrics font = getFontMetrics(g.getFont());
        g.drawString("Game Over", (GAME_WIDTH - font.stringWidth("Game Over"))/2, GAME_HEIGHT/2 );
        
        
        
    }
    public boolean getStart() {
        return start;
    }
    public int getScore() {
        return score;
    }
    public int getHighestScore(){
        return highestScore;
    }
    public void WriteFile(int score) {
        if (gameOver == true) {
            try{
                FileWriter fw = new FileWriter("GameList.txt", true);
                PrintWriter pw = new PrintWriter(fw);
                pw.println("Score : "+ score);
                pw.close();
                fw.close();
            }
            catch(IOException e){
                System.err.println(e);
            }
        }
    }
    //this includes setting a highScore
    public void ReadFile() {
        if (start == true){
        try {
            FileReader fr = new FileReader("GameList.txt");
            BufferedReader br = new BufferedReader(fr);
            br.mark(1000);
            String t= br.readLine();
            int[] scores;
            int count=0;
            while (t!= null) {
                count++;
                t=br.readLine();
            }
            scores = new int[count];
            br.reset();
            for (int i = 0; i<scores.length; i++) {
                t = br.readLine();
                scores[i] = Integer.parseInt(t.substring(t.lastIndexOf(" ")+1));
            }
            for (int i = 0; i<scores.length; i++) {
                if (highestScore<scores[i]) {
                    highestScore = scores[i];
                }
            }
        }
        catch(IOException e) {
            System.err.println(e);
        }
        }
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (start) {
            
            move();
            checkCollisions();
            checkEat();
        }
        if (gameOver) {
            WriteFile(score);
        }
        repaint();
    }
	
    //basically like actionPerformed but for the buttons in the Keyboard
    public class MyKeyAdapter extends KeyAdapter{
        
	@Override
	public void keyPressed(KeyEvent e) {
            
            if (start) {
                
                switch(e.getKeyCode()) {
                case KeyEvent.VK_LEFT : 
                    if (direction.equals("RIGHT")) {
                        
                    }else {
                        direction = "LEFT";
                    }
                    break;
                case KeyEvent.VK_RIGHT : 
                    if (direction.equals("LEFT")) {
                        
                    }else {
                        direction = "RIGHT";
                    }
                    break;
                case KeyEvent.VK_UP : 
                    if (direction.equals("DOWN")) {
                        
                    }else {
                        direction = "UP";
                    }
                    break;
                case KeyEvent.VK_DOWN : 
                    if (direction.equals("UP")) {
                        
                    }else {
                        direction = "DOWN";
                    }
                    break;
                }
            }
	}
        //not needed
        @Override
        public void keyTyped(KeyEvent e) {
        }
        //Not needed
        @Override
        public void keyReleased(KeyEvent e) {
        }
        
        
    }
    
}
