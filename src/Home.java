import javax.swing.*;

public class Home extends JFrame {
    public static void main(String args[]){
        JFrame frame =new JFrame("BubbleGame");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(new BubbleGUI());
        frame.setSize(new java.awt.Dimension(1024,768));
        frame.setVisible(true);

    }
}
