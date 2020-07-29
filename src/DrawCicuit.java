import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

class UpAndDownElementsPanel extends JPanel {
    private BufferedImage image;
    int length;
    String type;
    public UpAndDownElementsPanel(int l,String t) {
        type = t;
        length = l;
        try {
            image = ImageIO.read(new File(type+".jpeg"));
        }
        catch (IOException ex) {
            // handle exception...
        }
    }
    public void paint(Graphics g) {
        g.setColor(Color.BLACK);
        Graphics2D graphics2D = (Graphics2D) g;
        Stroke stroke = new BasicStroke(3);
        graphics2D.setStroke(stroke);
        int xForLine=18;
        int heigthOfImage = 100;
        if (type.equals("up and down resistance")||type.equals("up and down capacitance"))
            xForLine=18;
        if (type.equals("up and down inductance"))
            xForLine=6;
        if (type.equals("up voltage dc source")||type.equals("down voltage dc source")
                ||type.equals("up current dc source")||type.equals("down current dc source")
                ||type.equals("up controlled voltage source")||type.equals("down controlled voltage source")
                ||type.equals("up controlled current source")||type.equals("down controlled current source")
                ||type.equals("up and down ac source")) {
            xForLine = 19;
            heigthOfImage = 65;
        }
        if (type.equals("up diode")||type.equals("down diode"))
            xForLine=19;

        graphics2D.drawLine(xForLine,0,xForLine,length);
        g.drawImage(image,0,length/2-heigthOfImage/2,40,heigthOfImage,this);
    }
    /*@Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        AffineTransform tx = AffineTransform.getRotateInstance(0);
        AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);

        // Drawing the rotated image at the required drawing locations
        g.drawImage(op.filter(image, null), 0, 0, 100, 50, null);
        //////////////////////////////////////////////////////////
        //g.drawImage(image, 0, 0,150,100, this); // see javadoc for more info on the parameters
    }*/
}

class RightAndLeftElementsPanel extends JPanel {
    private BufferedImage image;
    int length;
    String type;
    public RightAndLeftElementsPanel(int l,String t) {
        type = t;
        length = l;
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
        Stroke stroke = new BasicStroke(3);
        graphics2D.setStroke(stroke);
        int yForLine=18;
        int widthOfImage = 100;
        if (type.equals("right and left resistance")||type.equals("right and left capacitance"))
            yForLine=18;
        if (type.equals("right and left inductance"))
            yForLine=6;
        if (type.equals("right voltage dc source")||type.equals("left voltage dc source")
                ||type.equals("right current dc source")||type.equals("left current dc source")
                ||type.equals("right controlled voltage source")||type.equals("left controlled voltage source")
                ||type.equals("right controlled current source")||type.equals("left controlled current source")
                ||type.equals("right and down ac source")) {
            yForLine = 19;
            widthOfImage = 65;
        }
        if (type.equals("right diode")||type.equals("left diode"))
            yForLine=19;

        graphics2D.drawLine(0,yForLine,length,yForLine);
        g.drawImage(image,length/2-widthOfImage/2,0,widthOfImage,40,this);
    }
}


class MyJPanel extends JPanel{
    @Override
    public void paint(Graphics g) {
        g.setColor(Color.BLACK);
        Graphics2D graphics2D = (Graphics2D) g;
        Stroke stroke = new BasicStroke(2);
        graphics2D.setStroke(stroke);
        g.drawRect(0,0,950,950);
        g.setColor(Color.gray);
        for(int i=0;i<=5;i++)
            for(int j=0;j<=5;j++)
                graphics2D.fillOval(120+140*i,150+140*j,5,5);
    }
}

public class DrawCicuit {
    ArrayList<Element> elements = new ArrayList<Element>(Circuit.allElements.values());
    static JFrame frame = new JFrame();
    public static void main(){
        frame.setTitle("Graphic drawing of the circuit");
        frame.setSize(1420,1000);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setIconImage(new ImageIcon("icon.png").getImage());
        Container container = frame.getContentPane();
        Component glassPane = frame.getGlassPane();
        JRootPane rootPane = frame.getRootPane();
        MyJPanel myJPanel = new MyJPanel();
        myJPanel.setBounds(0,0,1000,1000);
        container.setBackground(new Color(247, 247, 247));
        container.add(myJPanel);

        JPanel labelJPanel =new JPanel();
        JLabel label = new JLabel("the schematic of circuit");
        Font font = new Font(Font.SANS_SERIF,Font.BOLD,30);
        label.setFont(font);
        labelJPanel.add(label);
        labelJPanel.setBounds(0,0,950,50);
        labelJPanel.setBackground(Color.gray);
        container.add(labelJPanel);

        paintUpAndDownElements("up diode",120,150,420);
        paintRightAndLeftElements("right diode",120,150,420);



        frame.setLayout(null);
        frame.setVisible(true);
    }
    static void paintUpAndDownElements(String type,int x,int y,int ertefa){
        UpAndDownElementsPanel r1 = new UpAndDownElementsPanel(ertefa,type);
        if (type.equals("up and down resistance")||type.equals("up and down capacitance"))
            x-=18;
        if (type.equals("up and down inductance"))
            x-=6;
        if (type.equals("up voltage dc source")||type.equals("down voltage dc source")
                ||type.equals("up current dc source")||type.equals("down current dc source")
                ||type.equals("up controlled voltage source")||type.equals("down controlled voltage source")
                ||type.equals("up controlled current source")||type.equals("down controlled current source")
                ||type.equals("up and down ac source"))
            x-= 19;
        if (type.equals("up diode")||type.equals("down diode"))
            x-=19;
        r1.setBounds(x,y,40,ertefa);
        frame.getContentPane().add(r1);
    }
    static void paintRightAndLeftElements(String type,int x,int y,int tool){
        RightAndLeftElementsPanel r1 = new RightAndLeftElementsPanel(tool,type);
        if (type.equals("right and left resistance")||type.equals("right and left capacitance"))
            y-=18;
        if (type.equals("right and left inductance"))
            y-=6;
        if (type.equals("right voltage dc source")||type.equals("left voltage dc source")
                ||type.equals("right current dc source")||type.equals("left current dc source")
                ||type.equals("right controlled voltage source")||type.equals("left controlled voltage source")
                ||type.equals("right controlled current source")||type.equals("left controlled current source")
                ||type.equals("right and down ac source"))
            y-= 19;
        if (type.equals("right diode")||type.equals("left diode"))
            y-=19;
        r1.setBounds(x,y,tool,40);
        frame.getContentPane().add(r1);
    }
}
