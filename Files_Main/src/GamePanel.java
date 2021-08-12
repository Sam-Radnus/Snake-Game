import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.Timer;
import java.util.*;
public class GamePanel extends JPanel implements ActionListener {
    final static int SCREEN_HEIGHT=600;
    Random rand=new Random();
    final static int SCREEN_WIDTH=600;
    static int appleX;          //x co-ordinate of the apple
    static int appleY;          //y co-ordinate of the apple
    final static int UNIT_SIZE=25;
    int GAME_UNITS=(SCREEN_HEIGHT+SCREEN_WIDTH)/UNIT_SIZE;
    int x[]=new int[GAME_UNITS];
    int y[]=new int[GAME_UNITS];
    int bodyparts=6;            //initial size of the snake
    int applesEaten=0;
    boolean running=false;
    Timer timer;
    char direction='R';
    GamePanel()
    {
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
        this.addKeyListener(new MyKeyAdapter());
        startGame();
    }
    public void startGame()
    {
        running=true;
        newApple();
        timer=new Timer(75,this);
        timer.start();

    }
    public void paintComponent(Graphics G)
    {
        super.paintComponent(G);
        draw(G);

    }
    public void move()               //to move the sneke
    {
        for(int i=bodyparts;i>0;i--)
        {
            x[i]=x[i-1];
            y[i]=y[i-1];
        }
        switch(direction)
        {
            case 'R': x[0]=x[0]+UNIT_SIZE;
                break;
            case 'L': x[0]=x[0]-UNIT_SIZE;
                break;
            case 'U': y[0]=y[0]-UNIT_SIZE;
                break;
            case 'D': y[0]=y[0]+UNIT_SIZE;
                break;
        }
    }
    public void draw(Graphics G) {
        if(running) {
            for (int i = 0; i < UNIT_SIZE; i++) {
               // G.drawLine(i * UNIT_SIZE, 0, i * UNIT_SIZE, SCREEN_HEIGHT);  /* Forms a Grid */
               // G.drawLine(0, i * UNIT_SIZE, SCREEN_WIDTH, i * UNIT_SIZE);
            }
            G.setColor(Color.RED);
            G.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);
            G.setColor(Color.CYAN);
            G.setFont(new Font("Monospace",Font.PLAIN,25));
            G.drawString("Score:"+applesEaten,200,100);


            for (int i = bodyparts; i >= 0; i--) {
                if (i == 0) {
                    G.setColor(Color.green);
                    G.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                } else {
                    G.setColor(Color.yellow);
                    G.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                }
            }
        }
        else{
            gameOver(G);
        }
    }
    public void newApple()                  //place the apple at a random position
    {
        appleX=rand.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE;
        appleY=rand.nextInt((int)(SCREEN_HEIGHT/UNIT_SIZE))*UNIT_SIZE;
    }
    public void checkApples()               //to check's whether the snake came in contact with a apple
    {
        if((x[0]==appleX)&&(y[0]==appleY))
        {
            newApple();
            bodyparts++;
            applesEaten++;
        }
    }
    public void checkCollisions()           //to check if the head of the snake touches itself or any of the border
    {
        for(int i=bodyparts;i>0;i--)
        {
            if((x[0]==x[i])&&(y[0]==y[i]))
            {
                running=false;
            }
        }
        if(x[0]<0)
        {
            running=false;
        }
        if(y[0]<0)
        {
            running=false;
        }
        if(x[0]>SCREEN_WIDTH)
        {
            running=false;
        }
        if(y[0]>SCREEN_HEIGHT)
        {
            running=false;
        }
    }
    @Override
    public void actionPerformed(ActionEvent ae)
    {
        if(running) {
            move();                            //to move the snake
            checkApples();                     //to check's whether the snake came in contact with a apple
            checkCollisions();                 //to check if the head of the snake touches itself or any of the border
        }
       repaint();
    }
    public void gameOver(Graphics G)
    {
        G.setColor(Color.RED);
        G.setFont(new Font("Monospace", Font.PLAIN,100));
        G.drawString("Game Over",50,200);
        G.setColor(Color.CYAN);
        G.setFont(new Font("Monospace",Font.PLAIN,50));
        G.drawString("Your Score:"+applesEaten,150,500);
    }
    public class MyKeyAdapter extends KeyAdapter{
       @Override
        public void keyPressed(KeyEvent e)
       {
           switch(e.getKeyCode())
           {
               case KeyEvent.VK_LEFT:   if(direction!='R')  //if the direction of the snake is not already moving int the  right direction
                                          direction='L';    //since the snake cannot move in the opposite direction
                                        break;
               case KeyEvent.VK_RIGHT:  if(direction!='L')
                                           direction='R';
                                        break;
               case KeyEvent.VK_UP:     if(direction!='D')
                                           direction='U';
                                        break;
               case KeyEvent.VK_DOWN:   if(direction!='U')
                                           direction='D';
                                        break;

           }
       }
    }
}
