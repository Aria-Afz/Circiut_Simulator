import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class DiagramPanel extends JPanel {
    ArrayList<Double> amount;
    Double time = Main.cir.time;
    Double dt = Main.cir.dt;
    Double max;
    Double min;
    DiagramPanel (ArrayList<Double> a ,Double maximum,Double minimum){
        amount = a;
        max = maximum;
        min = minimum;
    }
    @Override
    public void paintComponent(Graphics g) {
        g.setColor(Color.BLUE);
        Graphics2D graphics2D = (Graphics2D) g;
        Stroke stroke = new BasicStroke(2);
        graphics2D.setStroke(stroke);
        if((max-min)!=0)
            for(int i=0;i<amount.size()-1;i++){
                graphics2D.drawLine((int)(i*dt*850/time),(int)(500-498*(amount.get(i)-min)/(max-min)),
                        (int)((i+1)*dt*850/time),(int)(500-498*(amount.get(i+1)-min)/(max-min)));
            }
        else
            for(int i=0;i<amount.size()-1;i++){
                graphics2D.drawLine((int)(i*dt*850/time),2,
                        (int)((i+1)*dt*850/time),2);
            }
    }
}


class DiagramBachGroundPanel extends JPanel{
    private BufferedImage image;
    DiagramBachGroundPanel (){
        try {
            image = ImageIO.read(new File("chart.JPG"));
        }
        catch (IOException ex) { }
    }
    @Override
    public void paint(Graphics g) {
        g.drawImage(image,0,0,850,505,this);
    }
}


class MyJPanel extends JPanel{
    @Override
    public void paint(Graphics g) {
        g.setColor(Color.BLACK);
        //g.setColor(new Color(247, 247, 247));
        Graphics2D graphics2D = (Graphics2D) g;
        Stroke stroke = new BasicStroke(2);
        graphics2D.setStroke(stroke);
        g.drawRect(2,2,1090,982);
        g.setColor(Color.BLACK);
        g.setColor(Color.gray);
        for(int i=0;i<=5;i++)
            for(int j=0;j<=5;j++)
                graphics2D.fillOval(105+170*i,105+170*j,5,5);
    }
}


class GroundPanel extends JPanel {
    private BufferedImage image;
    private BufferedImage image2;

    public GroundPanel() {
        try {
            image = ImageIO.read(new File("left ground.jpeg"));
        }
        catch (IOException ex) { }
        try {
            image2 = ImageIO.read(new File("right ground.jpeg"));
        }
        catch (IOException ex) { }
    }

    public void paint(Graphics g) {
        g.setColor(Color.BLACK);
        Graphics2D graphics2D = (Graphics2D) g;
        Stroke stroke = new BasicStroke(2);
        graphics2D.setStroke(stroke);
        graphics2D.drawLine(30, 42/2, 1060-30, 42/2);
        g.drawImage(image, 20, 0, 30, 42, this);
        g.drawImage(image2,1060-20-30, 0,30, 42, this);
    }
}