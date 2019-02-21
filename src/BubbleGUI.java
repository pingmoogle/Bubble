import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

public class BubbleGUI extends JPanel{
    public JPanel panel1;
    public JButton btnClear;
    public JButton btnPause;
    public JSlider slider1;
    public JPanel panel2;
    Random rand=new Random();
    ArrayList<Bubble> bubbleList;
    int size=25;
    Timer timer;
    int delay=33;

    public BubbleGUI(){
        timer = new Timer(delay, new BubbleListener() );
        bubbleList=new ArrayList<Bubble>();
        setBackground(Color.black);
        JPanel panel1=new JPanel();
        add(panel1);
//        JPanel panel2=new JPanel();
//        add(panel2);

        JButton btnPause=new JButton("暂停");
        btnPause.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JButton btn = (JButton)e.getSource();
                if (btn.getText().equals("暂停")){
                    timer.stop();
                    btn.setText("开始");
                }
                else {
                    timer.start();
                    btn.setText("暂停");
                }
            }
        });

        JSlider slider1=new JSlider();
        slider1.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                int speed = slider1.getValue() + 1;
                int delay = 1000 / speed;
                timer.setDelay(delay);
            }
        });
        JButton btnClear=new JButton("清除");
        btnClear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                bubbleList = new ArrayList<Bubble>();
                repaint();
            }
        });

        slider1.setValue(30);
        slider1.setPaintTicks(true);
        slider1.setPaintLabels(true);
        slider1.setMinorTickSpacing(5);
        slider1.setMaximum(120);
        slider1.setMajorTickSpacing(30);
        panel1.add(slider1);
        panel1.add(btnPause);
        panel1.add(btnClear);
        addMouseListener( new BubbleListener() );
        addMouseMotionListener( new BubbleListener() );
        addMouseWheelListener( new BubbleListener() );
        timer.start();

    }
    private class BubbleListener extends MouseAdapter implements ActionListener {
        public void mousePressed(MouseEvent e) {
            bubbleList.add(new Bubble(e.getX(), e.getY(), size));
            repaint();
        }
        public void mouseDragged(MouseEvent e) {
            bubbleList.add(new Bubble(e.getX(), e.getY(), size));
            repaint();
        }
        public void mouseWheelMoved(MouseWheelEvent e) {
            if(System.getProperty("os.name").startsWith("Mac"))
                size += e.getUnitsToScroll();
            else
                size -= e.getUnitsToScroll();
            if (size < 3)
                size = 3;
        }
        public void actionPerformed(ActionEvent e) {
            for (Bubble b : bubbleList)
                b.update();
            repaint();
        }
    }
    public void paintComponent(Graphics canvas){
        super.paintComponent(canvas);
        for(Bubble b : bubbleList){
            b.draw(canvas);
        }
    }
    private class Bubble{
        private int x;
        private int y;
        private int size;
        private Color color;
        private int xspeed, yspeed;
        private final int MAX_SPEED = 5;
        public Bubble(int newX,int newY,int newSize){
            x=newX;
            y=newY;
            size=newSize;
            color=new Color(
                    rand.nextInt(256),rand.nextInt(256),rand.nextInt(256),rand.nextInt(256)
            );
            xspeed = rand.nextInt(MAX_SPEED * 2 + 1) - MAX_SPEED;
            yspeed = rand.nextInt(MAX_SPEED * 2 + 1) - MAX_SPEED;
            if(xspeed==0) xspeed=1;
            if(yspeed==0) yspeed=1;
        }
        public void draw(Graphics canvas){
            canvas.setColor(color);
            canvas.fillOval(x-size/2,y-size/2,size,size);
        }
        public void update(){
            x += xspeed;
            y += yspeed;
            if (x - size/2 <= 0 || x + size/2 >= getWidth())
                xspeed = -xspeed;
            if (y - size/2 <= 0 || y + size/2 >= getHeight())
                yspeed = -yspeed;
        }
    }

}
