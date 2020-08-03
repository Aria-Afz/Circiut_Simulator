import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class UpAndDownElementsPanel extends JPanel {
    private BufferedImage image;
    int length;
    String type;
    String typeOfParallel;
    public UpAndDownElementsPanel(int l,String t,String to) {
        type = t;
        length = l;
        typeOfParallel=to;
        try {
            image = ImageIO.read(new File(type+".jpeg"));
        }
        catch (IOException ex) { }
    }
    public void paint(Graphics g) {
        g.setColor(Color.BLACK);
        Graphics2D graphics2D = (Graphics2D) g;
        Stroke stroke = new BasicStroke(2);
        graphics2D.setStroke(stroke);
        int xForLine=18;
        int heigthOfImage = 55;
        int widthOfImage=40;
        int xOfPhoto=0;
        if (type.equals("up and down resistance")) {
            xForLine = 14;
            widthOfImage = 30;
        }
        if(type.equals("up and down capacitance"))
            xForLine=19;
        if (type.equals("up and down inductance")) {
            xForLine = 4;
            widthOfImage = 25;
        }
        if (type.equals("up voltage dc source")||type.equals("down voltage dc source")
                ||type.equals("up current dc source")||type.equals("down current dc source")
                ||type.equals("up controlled voltage source")||type.equals("down controlled voltage source")
                ||type.equals("up controlled current source")||type.equals("down controlled current source")
                ||type.equals("up and down ac source")) {
            xForLine = 20;
            heigthOfImage = 55;
        }
        if (type.equals("up diode")||type.equals("down diode")) {
            xForLine = 15;
            widthOfImage = 30;
        }
        if(typeOfParallel.equals("1 in 1"))
            graphics2D.drawLine(xForLine,0,xForLine,length);
        if(typeOfParallel.equals("right in 2")){
            graphics2D.drawLine(xForLine,0,xForLine,length);
            graphics2D.drawLine(0,0,xForLine,0);
            graphics2D.drawLine(xForLine,length,0,length);
        }
        if(typeOfParallel.equals("left in 2")){
            graphics2D.drawLine(xForLine,0,xForLine,length);
            graphics2D.drawLine(40,0,xForLine,0);
            graphics2D.drawLine(xForLine,length,40,length);
        }
        if(typeOfParallel.equals("left in 3")){
            graphics2D.drawLine(90,0,xForLine,length/2-heigthOfImage/2);
            graphics2D.drawLine(90,length,xForLine,length/2+heigthOfImage/2);
        }
        if(typeOfParallel.equals("right in 3")){
            xOfPhoto+=51;
            xForLine+=51;
            graphics2D.drawLine(0,0,xForLine,length/2-heigthOfImage/2);
            graphics2D.drawLine(0,length,xForLine,length/2+heigthOfImage/2);
        }
        g.drawImage(image,xOfPhoto,length/2-heigthOfImage/2,widthOfImage,heigthOfImage,this);
    }
}
