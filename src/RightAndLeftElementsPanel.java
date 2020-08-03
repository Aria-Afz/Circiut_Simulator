import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class RightAndLeftElementsPanel extends JPanel {
    private BufferedImage image;
    int length;
    String type;
    String typeOfParallel;
    public RightAndLeftElementsPanel(int l,String t,String to) {
        type = t;
        length = l;
        typeOfParallel = to;
        try {
            image = ImageIO.read(new File(type+".jpg"));
        }
        catch (IOException ex) {
            // handle exception...
        }
    }
    public void paint(Graphics g) {
        g.setColor(Color.BLACK);
        Graphics2D graphics2D = (Graphics2D) g;
        Stroke stroke = new BasicStroke(2);
        graphics2D.setStroke(stroke);
        int yForLine=18;
        int widthOfImage = 55;
        int heightOgImage = 40;
        int yOfPhoto=0;
        if (type.equals("right and left resistance")) {
            yForLine = 14;
            heightOgImage = 30;
        }
        if(type.equals("right and left capacitance"))
            yForLine=19;
        if (type.equals("right and left inductance")) {
            yForLine = 4;
            heightOgImage =25;
        }
        if (type.equals("right voltage dc source")||type.equals("left voltage dc source")
                ||type.equals("right current dc source")||type.equals("left current dc source")
                ||type.equals("right controlled voltage source")||type.equals("left controlled voltage source")
                ||type.equals("right controlled current source")||type.equals("left controlled current source")
                ||type.equals("right and left ac source")) {
            yForLine = 20;
            widthOfImage = 55;
        }
        if (type.equals("right diode")||type.equals("left diode")) {
            yForLine = 15;
            heightOgImage = 30;
        }
        if(typeOfParallel.equals("1 in 1"))
            graphics2D.drawLine(0,yForLine,length,yForLine);
        if(typeOfParallel.equals("down in 2")){
            graphics2D.drawLine(0,yForLine,length,yForLine);
            graphics2D.drawLine(0,0,0,yForLine);
            graphics2D.drawLine(length,0,length,yForLine);
        }
        if(typeOfParallel.equals("up in 2")){
            graphics2D.drawLine(0,yForLine,length,yForLine);
            graphics2D.drawLine(0,40,0,yForLine);
            graphics2D.drawLine(length,40,length,yForLine);
        }
        if(typeOfParallel.equals("up in 3")){
            graphics2D.drawLine(0,90,length/2-widthOfImage/2,yForLine);
            graphics2D.drawLine(length,90,length/2+widthOfImage/2,yForLine);
        }
        if(typeOfParallel.equals("down in 3")){
            yOfPhoto+=51;
            yForLine+=51;
            graphics2D.drawLine(0,0,length/2-widthOfImage/2,yForLine);
            graphics2D.drawLine(length,0,length/2+widthOfImage/2,yForLine);
        }
        g.drawImage(image,length/2-widthOfImage/2,yOfPhoto,widthOfImage,heightOgImage,this);
    }
}
