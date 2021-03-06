import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class DiagramPanel extends JPanel {
    ArrayList<Double> amount;
    Double max;
    Double min;
    int firstIndex;
    int lastIndex;
    DiagramPanel (ArrayList<Double> a ,Double maximum,Double minimum,int firstNum, int lastNum){
        amount = a;
        max = maximum;
        min = minimum;
        firstIndex = firstNum;
        lastIndex = lastNum;
    }
    @Override
    public void paintComponent(Graphics g) {
        g.setColor(Color.BLUE);
        Graphics2D graphics2D = (Graphics2D) g;
        Stroke stroke = new BasicStroke(2);
        graphics2D.setStroke(stroke);
        if((max-min)!=0)
            for(int i=firstIndex;i<=lastIndex-1;i++){
                graphics2D.drawLine((int)(850*(i-firstIndex)/(lastIndex-firstIndex)),(int)(498-496*(amount.get(i)-min)/(max-min)),
                        (int)(850*(i+1-firstIndex)/(lastIndex-firstIndex)),(int)(498-496*(amount.get(i+1)-min)/(max-min)));
            }
        else
            graphics2D.drawLine(0,2, 850,2);
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
        g.drawImage(image,0,0,857,503,this);
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