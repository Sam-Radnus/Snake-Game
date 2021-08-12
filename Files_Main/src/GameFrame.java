import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
public class GameFrame extends JFrame{
    GameFrame()
    {
        this.add(new GamePanel());
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setVisible(true);
    }
}
