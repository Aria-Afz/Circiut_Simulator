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
class GroundPanel extends JPanel {
    private BufferedImage image;
    private BufferedImage image2;

    public GroundPanel() {
        try {
            image = ImageIO.read(new File("left ground.jpeg"));
        } catch (IOException ex) {
            // handle exception...
        }
        try {
            image2 = ImageIO.read(new File("right ground.jpeg"));
        } catch (IOException ex) {
            // handle exception...
        }
    }

    public void paint(Graphics g) {
        g.setColor(Color.BLACK);
        Graphics2D graphics2D = (Graphics2D) g;
        Stroke stroke = new BasicStroke(2);
        graphics2D.setStroke(stroke);

        graphics2D.drawLine(30, 45/2, 1060-30, 45/2);
        g.drawImage(image, 20, 0, 30, 45, this);
        g.drawImage(image2,1060-20-30, 0,30, 45, this);
    }
}
class Point{
    int x;
    int y;
    Point(int i){
        int kharejeQesmat = i/6+1;
        int baqimande = i%6;
        if(baqimande==0) {
            kharejeQesmat--;
            baqimande = 6;
        }
        y=105+5*170-170*kharejeQesmat;
        x=105+(baqimande-1)*170;
    }
}
class Information{
    String value;
    String typeOfElementInGraphics;
    Information (String v,String t){
        value = v;
        typeOfElementInGraphics = t;
    }
}
//////////////////////////////////////up and down///////////////////////////////////////////////////////////
//////////////////////////////////////up and down///////////////////////////////////////////////////////////
class UpAndDownElementsPanel extends JPanel {
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
        catch (IOException ex) {
            // handle exception...
        }
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

//////////////////////////////right and left///////////////////////////////////////////////////////
//////////////////////////////right and left///////////////////////////////////////////////////////
class RightAndLeftElementsPanel extends JPanel {
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

//////////////////////////////////////main////////////////////////////////////////////////////////////////////
//////////////////////////////////////main////////////////////////////////////////////////////////////////////
//////////////////////////////////////main////////////////////////////////////////////////////////////////////
//////////////////////////////////////main////////////////////////////////////////////////////////////////////
//////////////////////////////////////main////////////////////////////////////////////////////////////////////
class MyJPanel extends JPanel{
    @Override
    public void paint(Graphics g) {
        g.setColor(Color.BLACK);
        Graphics2D graphics2D = (Graphics2D) g;
        Stroke stroke = new BasicStroke(2);
        graphics2D.setStroke(stroke);
        g.drawRect(0,0,1060,985);
        g.setColor(Color.gray);
        for(int i=0;i<=5;i++)
            for(int j=0;j<=5;j++)
                graphics2D.fillOval(105+170*i,105+170*j,5,5);
    }
}

public class DrawCircuit {
    static JFrame frame = new JFrame();
    ArrayList<Element> element;
    public DrawCircuit (ArrayList<Element> elementsForGraphics){
        element = elementsForGraphics;
    }
    public void main(){
        frame.setTitle("Graphic drawing of the circuit");
        frame.setSize(1520,1033);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setIconImage(new ImageIcon("icon.png").getImage());
        Container container = frame.getContentPane();
        Component glassPane = frame.getGlassPane();
        JRootPane rootPane = frame.getRootPane();
        MyJPanel myJPanel = new MyJPanel();
        myJPanel.setBounds(0,0,1070,1000);
        container.setBackground(new Color(247, 247, 247));
        container.add(myJPanel);
        GroundPanel groundPanel = new GroundPanel();
        groundPanel.setBounds(0,985-50,1060,50);
        container.add(groundPanel);
        ////////////////////////////////////////example of drawing///////////////////////////////////////////////
        ////////////////////////////////////////example of drawing///////////////////////////////////////////////
        paintUpAndDownElements("up voltage dc source",105+170*3,105,170*2,"D1","30m","right in 2");
        paintUpAndDownElements("up diode",105+170*3,105,170*2,"D1","30m","left in 2");

        paintRightAndLeftElements("right and left resistance",105,105+170*0,170*2,"D2","50m","up in 2");
        paintRightAndLeftElements("right current dc source",105,105+170*0,170*2,"D2","50m","down in 2");

        paintRightAndLeftElements("right and left capacitance",105+170*0,105+170*2,170*2,"D2","50m","up in 3");
        paintRightAndLeftElements("right and left ac source",105+170*0,105+170*2,170*2,"D2","50m","1 in 1");
        paintRightAndLeftElements("right controlled voltage source",105+170*0,105+170*2,170*2,"D2","50m","down in 3");

        paintUpAndDownElements("up and down capacitance",105,105+170*4,170,"D1","30m","left in 3");
        paintUpAndDownElements("up and down inductance",105,105+170*4,170,"D2","50m","1 in 1");
        paintUpAndDownElements("up controlled current source",105,105+170*4,170,"D1","30m","right in 3");

        paintUpAndDownElements("up and down inductance",105,105,170*1,"D2","50m","1 in 1");
        paintRightAndLeftElements("right and left resistance",105+340,105+340,170,"D2","50m","1 in 1");
        paintRightAndLeftElements("right diode",105+340,105,170,"D2","50m","1 in 1");
        ////////////////////////////////////////example of drawing///////////////////////////////////////////////
        ////////////////////////////////////////example of drawing///////////////////////////////////////////////
        ArrayList<ArrayList<Element>> parallelElements = new ArrayList<ArrayList<Element>>();
        ArrayList<Element> firstArrayList = new ArrayList<Element>();
        firstArrayList.add(element.get(0));
        parallelElements.add(firstArrayList);
        for(int i=1;i<element.size();i++){
            int neshangar=-1;
            for(int j=0;j<parallelElements.size()&&neshangar==-1;j++){
                if((element.get(i).getPositiveNode().getName()==parallelElements.get(j).get(0).getPositiveNode().getName()&&
                        element.get(i).getNegativeNode().getName()==parallelElements.get(j).get(0).getNegativeNode().getName())
                        ||
                        (element.get(i).getNegativeNode().getName()==parallelElements.get(j).get(0).getPositiveNode().getName()&&
                        element.get(i).getPositiveNode().getName()==parallelElements.get(j).get(0).getNegativeNode().getName()))
                    neshangar=j;
            }
            if(neshangar!=-1)
                parallelElements.get(neshangar).add(element.get(i));
            if(neshangar==-1){
                ArrayList<Element> temporaryArrayList = new ArrayList<Element>();
                temporaryArrayList.add(element.get(i));
                parallelElements.add(temporaryArrayList);
            }
        }
        for(int i=0;i<parallelElements.size();i++){
            int xForDrawing=1;
            int yForDrawing=1;
            int numberOfElements = parallelElements.get(i).size();
            int distanceBetweenTwoPoints=1;
            String typeOfDrawing="up to down";
            if(parallelElements.get(i).get(0).getPositiveNode().getName()==0){
                Point point = new Point(parallelElements.get(i).get(0).getNegativeNode().getName());
                xForDrawing = point.x;
                yForDrawing = point.y;
                distanceBetweenTwoPoints = 105+5*170-point.y;
                typeOfDrawing = "up to down";
            }
            else if(parallelElements.get(i).get(0).getNegativeNode().getName()==0){
                Point point = new Point(parallelElements.get(i).get(0).getPositiveNode().getName());
                xForDrawing = point.x;
                yForDrawing = point.y;
                distanceBetweenTwoPoints = 105+5*170-point.y;
                typeOfDrawing = "up to down";
            }
            else{
                Point point1 = new Point(parallelElements.get(i).get(0).getNegativeNode().getName());
                Point point2 = new Point(parallelElements.get(i).get(0).getPositiveNode().getName());
                if(point1.x==point2.x){
                    typeOfDrawing = "up to down";
                    xForDrawing = point1.x;
                    if(point1.y>point2.y){
                        yForDrawing=point2.y;
                        distanceBetweenTwoPoints=point1.y-point2.y;
                    }
                    if(point2.y>point1.y){
                        yForDrawing=point1.y;
                        distanceBetweenTwoPoints=point2.y-point1.y;
                    }
                }
                else if(point1.y==point2.y){
                    typeOfDrawing = "right to left";
                    yForDrawing = point1.y;
                    if(point1.x>point2.x){
                        xForDrawing=point2.x;
                        distanceBetweenTwoPoints=point1.x-point2.x;
                    }
                    if(point2.x>point1.x){
                        xForDrawing=point1.x;
                        distanceBetweenTwoPoints=point2.x-point1.x;
                    }
                }
                else
                    System.out.println("Error Error المان مورب");
            }
            if(numberOfElements==1){
                String name = parallelElements.get(i).get(0).getName();
                Information info = giveInformationForDrawing (xForDrawing,yForDrawing,
                        parallelElements.get(i).get(0),typeOfDrawing);
                if(typeOfDrawing.equals("up to down"))
                    paintUpAndDownElements(info.typeOfElementInGraphics, xForDrawing,yForDrawing
                            ,distanceBetweenTwoPoints,name,info.value,"1 in 1");
                if(typeOfDrawing.equals("right to left"))
                    paintRightAndLeftElements(info.typeOfElementInGraphics, xForDrawing,yForDrawing
                            ,distanceBetweenTwoPoints,name,info.value,"1 in 1");
            }
            if(numberOfElements==2){
                for(int j=0;j<=1;j++){
                    String name = parallelElements.get(i).get(j).getName();
                    Information info = giveInformationForDrawing (xForDrawing,yForDrawing,
                            parallelElements.get(i).get(j),typeOfDrawing);
                    String typeOfParallel="";
                    if(typeOfDrawing.equals("up to down")) {
                        if(j==0)
                            typeOfParallel="left in 2";
                        if(j==1)
                            typeOfParallel="right in 2";
                        paintUpAndDownElements(info.typeOfElementInGraphics, xForDrawing, yForDrawing
                                , distanceBetweenTwoPoints, name, info.value, typeOfParallel);
                    }
                    if(typeOfDrawing.equals("right to left")) {
                        if(j==0)
                            typeOfParallel="up in 2";
                        if(j==1)
                            typeOfParallel="down in 2";
                        paintRightAndLeftElements(info.typeOfElementInGraphics, xForDrawing, yForDrawing
                                , distanceBetweenTwoPoints, name, info.value, typeOfParallel);
                    }
                }
            }
            if(numberOfElements==3){
                for(int j=0;j<=2;j++){
                    String name = parallelElements.get(i).get(j).getName();
                    Information info = giveInformationForDrawing (xForDrawing,yForDrawing,
                            parallelElements.get(i).get(j),typeOfDrawing);
                    String typeOfParallel="";
                    if(typeOfDrawing.equals("up to down")) {
                        if(j==0)
                            typeOfParallel="left in 3";
                        if(j==1)
                            typeOfParallel="1 in 1";
                        if(j==2)
                            typeOfParallel="right in 3";
                        paintUpAndDownElements(info.typeOfElementInGraphics, xForDrawing, yForDrawing
                                , distanceBetweenTwoPoints, name, info.value, typeOfParallel);
                    }
                    if(typeOfDrawing.equals("right to left")) {
                        if(j==0)
                            typeOfParallel="up in 3";
                        if(j==1)
                            typeOfParallel="1 in 1";
                        if(j==2)
                            typeOfParallel="down in 3";
                        paintRightAndLeftElements(info.typeOfElementInGraphics, xForDrawing, yForDrawing
                                , distanceBetweenTwoPoints, name, info.value, typeOfParallel);
                    }
                }
            }
        }
        //frame.setLayout(null);
        frame.setVisible(true);
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    static void paintUpAndDownElements(String typeOfElementInGraphics,int x,int y,int ertefa,
                                       String name,String value,String typeOfParallel){
        UpAndDownElementsPanel r1 = new UpAndDownElementsPanel(ertefa,typeOfElementInGraphics,typeOfParallel);
        int copyOfX = x;
        int zekhamatPanel=40;
        int xForText = x+40;
        if(typeOfParallel.equals("1 in 1")) {
            xForText = x+40-19;
            if (typeOfElementInGraphics.equals("up and down resistance")) {
                x -= 14;
                xForText -= 5;
            }
            if (typeOfElementInGraphics.equals("up and down capacitance"))
                x -= 19;
            if (typeOfElementInGraphics.equals("up and down inductance")) {
                x -= 4;
            }
            if (typeOfElementInGraphics.equals("up voltage dc source") || typeOfElementInGraphics.equals("down voltage dc source")
                    || typeOfElementInGraphics.equals("up current dc source") || typeOfElementInGraphics.equals("down current dc source")
                    || typeOfElementInGraphics.equals("up controlled voltage source") || typeOfElementInGraphics.equals("down controlled voltage source")
                    || typeOfElementInGraphics.equals("up controlled current source") || typeOfElementInGraphics.equals("down controlled current source")
                    || typeOfElementInGraphics.equals("up and down ac source"))
                x -= 20;
            if (typeOfElementInGraphics.equals("up diode") || typeOfElementInGraphics.equals("down diode")) {
                x -= 15;
                xForText -= 6;
            }
        }
        if(typeOfParallel.equals("right in 2")){ }
        if(typeOfParallel.equals("left in 2")){
            x-=40;
            xForText=x-30;
        }
        if(typeOfParallel.equals("left in 3")){
            zekhamatPanel=90;
            x-=90;
            xForText = x+40;
        }
        if(typeOfParallel.equals("right in 3")){
            zekhamatPanel=91;
            xForText = x+91;
        }
        r1.setBounds(x,y,zekhamatPanel,ertefa);
        frame.getContentPane().add(r1);

        JLabel nameInGraphic = new JLabel(name);
        nameInGraphic.setBackground(new Color(247, 247, 247));
        JPanel forName  = new JPanel();
        forName.setBounds(xForText,ertefa/2-20+y,30,30);
        forName.add(nameInGraphic);
        forName.setBackground(new Color(247, 247, 247));
        frame.getContentPane().add(forName);

        JLabel valueInGraphic = new JLabel(value);
        valueInGraphic.setBackground(new Color(247, 247, 247));
        JPanel forValue  = new JPanel();
        forValue.setBounds(xForText,ertefa/2+10+y,30,30);
        forValue.add(valueInGraphic);
        forValue.setBackground(new Color(247, 247, 247));
        frame.getContentPane().add(forValue);
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    static void paintRightAndLeftElements(String typeOfElementInGraphics,int x,int y,int tool,
                                          String name,String value,String typeOfParallel){
        RightAndLeftElementsPanel r1 = new RightAndLeftElementsPanel(tool,typeOfElementInGraphics,typeOfParallel);
        int zekhamatPanel=40;
        int yForText=y+40;
        if(typeOfParallel.equals("1 in 1")) {
            yForText = y + 40 - 19;
            if (typeOfElementInGraphics.equals("right and left resistance")) {
                y -= 14;
                yForText -= 5;
            }
            if (typeOfElementInGraphics.equals("right and left capacitance"))
                y -= 19;
            if (typeOfElementInGraphics.equals("right and left inductance")) {
                y -= 4;
            }
            if (typeOfElementInGraphics.equals("right voltage dc source") || typeOfElementInGraphics.equals("left voltage dc source")
                    || typeOfElementInGraphics.equals("right current dc source") || typeOfElementInGraphics.equals("left current dc source")
                    || typeOfElementInGraphics.equals("right controlled voltage source") || typeOfElementInGraphics.equals("left controlled voltage source")
                    || typeOfElementInGraphics.equals("right controlled current source") || typeOfElementInGraphics.equals("left controlled current source")
                    || typeOfElementInGraphics.equals("right and left ac source"))
                y -= 20;
            if (typeOfElementInGraphics.equals("right diode") || typeOfElementInGraphics.equals("left diode")) {
                y -= 15;
                yForText -= 6;
            }
        }
        if(typeOfParallel.equals("down in 2")){ }
        if(typeOfParallel.equals("up in 2")){
            y-=40;
            yForText = y-30;
        }
        if(typeOfParallel.equals("up in 3")){
            zekhamatPanel=90;
            y-=90;
            yForText = y+40;
        }
        if(typeOfParallel.equals("down in 3")){
            zekhamatPanel=91;
            yForText = y+91;
        }
        r1.setBounds(x,y,tool,zekhamatPanel);
        frame.getContentPane().add(r1);

        JLabel nameInGraphic = new JLabel(name);
        nameInGraphic.setBackground(new Color(247, 247, 247));
        JPanel forName  = new JPanel();
        forName.setBounds(tool/2-20+x,yForText,30,30);
        forName.add(nameInGraphic);
        forName.setBackground(new Color(247, 247, 247));
        frame.getContentPane().add(forName);

        JLabel valueInGraphic = new JLabel(value);
        valueInGraphic.setBackground(new Color(247, 247, 247));
        JPanel forValue  = new JPanel();
        forValue.setBounds(tool/2+10+x,yForText,30,30);
        forValue.add(valueInGraphic);
        forValue.setBackground(new Color(247, 247, 247));
        frame.getContentPane().add(forValue);
    }
    static Information giveInformationForDrawing(int xForDrawing,int yForDrawing,Element element,String typeOfDrawing) {
    String typeOfElementInGraphics="";
    String value="";
    if(typeOfDrawing.equals("right to left")) {
        String rightToLeftOrLeftToRight;

        /////////////////////////////////////////////////////////////////////////////////////////////////////
        if (element.getName().charAt(0)=='R'){

        }
        if (element.getName().charAt(0)=='L'){

        }
        if (element.getName().charAt(0)=='C'){

        }
        if (element.getName().charAt(0)=='D'){

        }
        if (element.getName().charAt(0)=='V'){

        }
        if (element.getName().charAt(0)=='E'){

        }
        if (element.getName().charAt(0)=='H'){

        }
        if (element.getName().charAt(0)=='I'){

        }
        if (element.getName().charAt(0)=='G'){

        }
        if (element.getName().charAt(0)=='F'){

        }
        /////////////////////////////////////////////////////////////////////////////////////////////////////
    }
    if(typeOfDrawing.equals("up to down")) {
        String upToDownOrDownToUp;

        /////////////////////////////////////////////////////////////////////////////////////////////////////
        if (element.getName().charAt(0)=='R'){

        }
        if (element.getName().charAt(0)=='L'){

        }
        if (element.getName().charAt(0)=='C'){

        }
        if (element.getName().charAt(0)=='D'){

        }
        if (element.getName().charAt(0)=='V'){

        }
        if (element.getName().charAt(0)=='E'){

        }
        if (element.getName().charAt(0)=='H'){

        }
        if (element.getName().charAt(0)=='I'){

        }
        if (element.getName().charAt(0)=='G'){

        }
        if (element.getName().charAt(0)=='F'){

        }
        /////////////////////////////////////////////////////////////////////////////////////////////////////
    }
    Information info = new Information(value,typeOfElementInGraphics);
    return info;
    }
}
