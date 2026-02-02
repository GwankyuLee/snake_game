import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


//implements ActionListener
public class GameFrame extends JFrame{
    
    //make top panel and bottom panel with all the scores, names, and start button, ghiehst score as well
    JPanel top;
//    JButton clickToStart;
    JLabel score, highestScore;
//    JTextField userName;
    String playerName;
    GamePanel snake = new GamePanel();
    
    
    
    
    public GameFrame() {
        
        
//        instructions = new JLabel("Enter your UserName and Click Start to start");
//        clickToStart = new JButton("Start");
//        clickToStart.setForeground(Color.white);
//        clickToStart.setBackground(Color.black);
//        clickToStart.addActionListener(this);
        score = new JLabel("Score : 0");
        highestScore = new JLabel();
        highestScore.setText("HighScore : "+snake.getHighestScore());
//        userName = new JTextField();
        top = new JPanel(new CardLayout());
        top.setPreferredSize(new Dimension(600, 100));
        top.setBackground(Color.white);
        top.setLayout(null);
//        top.add(instructions);
//        instructions.setFont(new Font("Sans Serif", Font.BOLD, 16));
//        instructions.setBounds(10, 15, 500,20);
//        top.add(clickToStart);
//        clickToStart.setBounds(450, 6, 90,40);
        top.add(score);
        score.setBounds(100, 30, 200, 40);
        score.setFont(new Font("Sans Serif", Font.BOLD, 30));
        top.add(highestScore);
        highestScore.setBounds(300, 30, 280, 40);
        highestScore.setFont(new Font("Sans Serif", Font.BOLD, 30));
        
//        top.add(userName);
//        userName.setBounds(10,60, 150,30);
//        userName.setText("UserName : ");
        
        
        
        
        
        this.add(top, BorderLayout.NORTH);
        this.add(snake);
        this.setTitle("Snake Game");
        this.pack();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        
        while (snake.getStart()) {
            updateScore();
        }
        
    }
    
    public void updateScore() {
        score.setText("Score : "+snake.getScore());
            
    }
    
    
    
    
    
    

//    @Override
//    public void actionPerformed(ActionEvent e) {
//        String name = userName.getText();
//        int index = name.lastIndexOf(" ");
//        if (index>=name.length()-1) {
//            playerName = "unknown";
//        }
//        else {
//            playerName = name.substring(index+1);
//        }
//        
//        if (e.getSource() == clickToStart) {
//            
//            snake.startGame();
//            //get last index of space, and get anything after that
//            //if there is nothing after the space, then userName = null
//        }
//        
//        
        
            
        
        
//    }
    
    
}
