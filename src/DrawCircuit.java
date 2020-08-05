import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

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

public class DrawCircuit {
    static JFrame frame = new JFrame();
    static ArrayList<Element> element = new ArrayList<>();
    static Container container = frame.getContentPane();
    static File addressOfTextFile = new File ("Circuit.txt");
    static JButton run = new JButton("RUN");
    static JButton load = new JButton("LOAD");
    static JButton draw = new JButton("DRAW");
    static MyJPanel myJPanel = new MyJPanel();
    static int isLoadPressed = 0;
    public static void main (String[] args) throws FileNotFoundException {
        frame.setTitle("Solve & Drawing Circuits");
        frame.setSize(1700,1033);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setIconImage(new ImageIcon("icon2.JPG").getImage());
        Container container = frame.getContentPane();
        frame.setLayout(null);
        frame.setVisible(true);
        load.setBackground(Color.LIGHT_GRAY);
        load.setBounds(1115,10,170,30);
        run.setBackground(Color.LIGHT_GRAY);
        run.setBounds(1115+180,10,170,30);
        draw.setBackground(Color.LIGHT_GRAY);
        draw.setBounds(1115+2*180,10,170,30);
        JTextArea textArea = new JTextArea();
        //textArea.setBackground(new Color(253, 246, 202));
        textArea.setBounds(1115,50,1385+260-1115,985-50);
        Border border = BorderFactory.createLineBorder(Color.BLACK,2,true);
        textArea.setBorder(border);
        textArea.setText("\n");
        myJPanel.setBounds(0,0,1100,1000);
        container.setBackground(new Color(247, 247, 247));
        container.add(myJPanel);
        container.add(load);
        container.add(run);
        container.add(draw);
        container.add(textArea);
        load.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FileSystemView fsv;
                fsv = FileSystemView.getFileSystemView();
                File fileO = new File("C:");
                FileNameExtensionFilter filter = new FileNameExtensionFilter("text files", "txt");
                JFileChooser fileChooser = new JFileChooser(fileO,fsv);
                fileChooser.setFileFilter(filter);
                int response = fileChooser.showOpenDialog(frame);
                if (response == JFileChooser.APPROVE_OPTION){
                    isLoadPressed=1;
                    addressOfTextFile = fileChooser.getSelectedFile();
                    File file = fileChooser.getSelectedFile();
                    String text = "";
                    try {
                        Scanner sc = new Scanner(file);
                        String line = sc.nextLine();
                        String[] split = line.split("\\s+");
                        while (!split[0].equals(".tran")){
                            text+=line+"\n";
                            line = sc.nextLine();
                            split = line.split("\\s+");
                        }
                        text+=line;
                    }
                    catch (FileNotFoundException ex) { }
                    textArea.setText(text);
                }
            }
        });
        run.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(isLoadPressed == 1) {
                    FileSystemView fsv;
                    fsv = FileSystemView.getFileSystemView();
                    File fileO = new File("CircuitAnswers.txt");
                    FileNameExtensionFilter filter = new FileNameExtensionFilter("text files", "txt");
                    JFileChooser fileChooser = new JFileChooser(fileO,fsv);
                    fileChooser.setFileFilter(filter);
                    int response = fileChooser.showOpenDialog(frame);
                    if (response == JFileChooser.APPROVE_OPTION) {
                        Main.cirAnswers = fileChooser.getSelectedFile();
                        container.removeAll();
                        container.add(load);
                        container.add(run);
                        container.add(draw);
                        container.add(textArea);
                        container.add(myJPanel);
                        try {
                            FileWriter fileWriter = new FileWriter(addressOfTextFile);
                            fileWriter.write(textArea.getText());
                            fileWriter.close();
                            Main.main(args);
                        } catch (IOException ex) {
                        }
                        frame.setVisible(false);
                        frame.setVisible(true);
                    }
                }
                else{
                    JOptionPane.showMessageDialog(
                            frame,
                            "first you should LOAD your circuit!",
                            "CAUTION",
                            JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
        draw.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(element.size()==0){
                    JOptionPane.showMessageDialog(
                            frame,
                            "first you should RUN your circuit!",
                            "CAUTION",
                            JOptionPane.INFORMATION_MESSAGE);
                }
                else {
                    String[] strings = new String[element.size()];
                    for (int i = 0; i < element.size(); i++) {
                        strings[i] = element.get(i).name;
                    }
                    ImageIcon icon = new ImageIcon();
                    String nameOfElement;
                    nameOfElement = (String) JOptionPane.showInputDialog(frame, "choose your desired element.", "Drawing Information",
                            JOptionPane.QUESTION_MESSAGE, icon, strings, element.get(0).name);
                    if(nameOfElement!=null){
                        int numberOfElement=-1;
                        for(int i=0;i<element.size()&&numberOfElement==-1;i++)
                            if(element.get(i).name.equals(nameOfElement))
                                numberOfElement=i;
                        diagramDrawing(element.get(numberOfElement).storedVoltages,"V("+nameOfElement+")","V");
                        diagramDrawing(element.get(numberOfElement).storedCurrents,"I("+nameOfElement+")","A");
                        ArrayList<Double> power = new ArrayList<>();
                        for(int i=0;i<element.get(numberOfElement).storedVoltages.size();i++)
                            power.add(element.get(numberOfElement).storedVoltages.get(i)
                                    *element.get(numberOfElement).storedCurrents.get(i));
                        diagramDrawing(power,"P("+nameOfElement+")","W");
                    }
                }
            }
        });
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static void diagramDrawing(ArrayList<Double> amount, String name, String unit){
        JFrame diagramFrame = new JFrame(name+" ["+unit+"]");
        diagramFrame.setSize(1020,650);
        diagramFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        diagramFrame.setIconImage(new ImageIcon("icon.png").getImage());
        Container container = diagramFrame.getContentPane();
        container.setBackground(new Color(254,254, 254));
        diagramFrame.setVisible(true);
        diagramFrame.setLayout(null);
        Double max=amount.get(1);
        Double min=amount.get(1);
        for(int i=2;i<amount.size();i++){
            if(amount.get(i)>max)
                max=amount.get(i);
            if(amount.get(i)<min)
                min=amount.get(i);
        }
        DiagramPanel diagramPanel = new DiagramPanel(amount,max,min,1,amount.size()-1);
        diagramPanel.setBounds(100,20,850,650);
        container.add(diagramPanel);
        DiagramBachGroundPanel diagramBachGroundPanel = new DiagramBachGroundPanel();
        diagramBachGroundPanel.setBounds(98,20,860,650);
        container.add(diagramBachGroundPanel);
        JLabel maxLabel = new JLabel(toStringForDouble(max));
        maxLabel.setBounds(10,0,80,30);
        maxLabel.setBackground(new Color(254,254, 254));
        container.add(maxLabel);
        if(max==min&&max>0)
            min = 0.0;
        JLabel minLabel = new JLabel(toStringForDouble(min));
        minLabel.setBounds(10,500,80,30);
        minLabel.setBackground(new Color(254,254, 254));
        if(max!=min) {
            container.add(minLabel);
            for(int i=1;i<=4;i++){
                Double d = min+(((max-min)*i)/5);
                JLabel otherLabel = new JLabel(toStringForDouble(d));
                otherLabel.setBounds(10,500-100*i,80,30);
                otherLabel.setBackground(new Color(254,254, 254));
                container.add(otherLabel);
            }
        }
        Double d = Main.cir.time;
        JLabel timeLabel = new JLabel(toStringForDouble(d)+" [s]");
        timeLabel.setBounds(920,520,90,30);
        timeLabel.setBackground(new Color(254,254, 254));
        container.add(timeLabel);
        JLabel timeLabel2 = new JLabel("0.0");
        timeLabel2.setBounds(100,520,80,30);
        timeLabel2.setBackground(new Color(254,254, 254));
        container.add(timeLabel2);
        for(int i=1;i<=4;i++){
            Double dou = i*Main.cir.time/5;
            JLabel otherLabel = new JLabel(toStringForDouble(dou));
            otherLabel.setBounds(100+850*i/5,520,80,30);
            otherLabel.setBackground(new Color(254,254, 254));
            container.add(otherLabel);
        }
        JLabel info1 = new JLabel("Draw from:");
        info1.setBounds(10,555,65,30);
        info1.setBackground(new Color(254,254, 254));
        container.add(info1);
        JTextField from = new JTextField("");
        from.setBounds(75,558,60,24);
        from.setBackground(new Color(254,254, 254));
        container.add(from);
        JLabel info2 = new JLabel("(s) to:");
        info2.setBounds(135,555,40,30);
        info2.setBackground(new Color(254,254, 254));
        container.add(info2);
        JTextField to = new JTextField("");
        to.setBounds(170,558,60,24);
        to.setBackground(new Color(254,254, 254));
        container.add(to);
        JLabel info3 = new JLabel("(s).");
        info3.setBounds(230,555,40,30);
        info3.setBackground(new Color(254,254, 254));
        container.add(info3);
        JButton set = new JButton("SET");
        set.setBounds(270,558,80,24);
        set.setBackground(Color.LIGHT_GRAY);
        container.add(set);
        set.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (Double.parseDouble(from.getText()) >= 0 && Double.parseDouble(from.getText()) <= Main.cir.time
                            &&
                            Double.parseDouble(to.getText()) >= 0 && Double.parseDouble(to.getText()) <= Main.cir.time) {
                        if (Double.parseDouble(from.getText()) >= Double.parseDouble(to.getText())) {
                            JOptionPane.showMessageDialog(
                                    diagramFrame,
                                    "your numbers are incorrect.\n[\"from\" should be less than \"to\".]",
                                    "CAUTION",
                                    JOptionPane.INFORMATION_MESSAGE);
                            from.setText("");
                            to.setText("");
                        } else {
                            Double dt = Main.cir.dt;
                            container.removeAll();
                            int firstIndex = 0;
                            int lastIndex = 0;
                            while (firstIndex * dt < Double.parseDouble(from.getText()))
                                firstIndex++;
                            lastIndex = firstIndex;
                            while (lastIndex * dt <= Double.parseDouble(to.getText()))
                                lastIndex++;
                            lastIndex--;
                            Double max = amount.get(firstIndex+1);
                            Double min = amount.get(firstIndex+1);
                            for (int i = firstIndex + 2; i <= lastIndex; i++) {
                                if (amount.get(i) > max)
                                    max = amount.get(i);
                                if (amount.get(i) < min)
                                    min = amount.get(i);
                            }
                            DiagramPanel diagramPanel = new DiagramPanel(amount, max, min, firstIndex+1, lastIndex);
                            diagramPanel.setBounds(100, 20, 850, 650);
                            container.add(diagramPanel);

                            JLabel maxLabel = new JLabel(toStringForDouble(max));
                            maxLabel.setBounds(10, 5, 80, 30);
                            maxLabel.setBackground(new Color(254, 254, 254));
                            container.add(maxLabel);
                            if(max==min&&max>0)
                                min=0.0;
                            JLabel minLabel = new JLabel(toStringForDouble(min));
                            minLabel.setBounds(10, 500, 80, 30);
                            minLabel.setBackground(new Color(254, 254, 254));
                            if(max!=min) {
                                container.add(minLabel);
                                for(int i=1;i<=4;i++){
                                    Double d = min+((max-min)*i/5);
                                    JLabel otherLabel = new JLabel(toStringForDouble(d));
                                    otherLabel.setBounds(10,500-100*i,80,30);
                                    otherLabel.setBackground(new Color(254,254, 254));
                                    container.add(otherLabel);
                                }
                            }
                            JLabel timeLabel;
                            if(to.getText().length()!=1)
                                timeLabel = new JLabel(to.getText() + " [s]");
                            else
                                timeLabel = new JLabel(to.getText() + ".0 [s]");
                            timeLabel.setBounds(920, 520, 90, 30);
                            timeLabel.setBackground(new Color(254, 254, 254));
                            container.add(timeLabel);
                            JLabel timeLabel2;
                            if(from.getText().length()!=1)
                                timeLabel2 = new JLabel(from.getText());
                            else
                                timeLabel2 = new JLabel(from.getText()+".0");
                            timeLabel2.setBounds(100, 520, 80, 30);
                            timeLabel2.setBackground(new Color(254, 254, 254));
                            container.add(timeLabel2);
                            for(int i=1;i<=4;i++){
                                Double dou = Double.parseDouble(from.getText()) + (Double.parseDouble(to.getText())-Double.parseDouble(from.getText()))*i/5;
                                JLabel otherLabel = new JLabel(toStringForDouble(dou));
                                otherLabel.setBounds(100+850*i/5,520,80,30);
                                otherLabel.setBackground(new Color(254,254, 254));
                                container.add(otherLabel);
                            }
                            container.add(info1);
                            container.add(info2);
                            container.add(info3);
                            container.add(from);
                            container.add(to);
                            container.add(set);
                            to.setText("");
                            from.setText("");
                            container.add(diagramBachGroundPanel);
                            diagramFrame.setVisible(false);
                            diagramFrame.setVisible(true);
                        }
                    } else {
                        JOptionPane.showMessageDialog(
                                diagramFrame,
                                "your numbers are not in time analysis interval!\n[from 0 (s) to " + Main.cir.time + " (s).]",
                                "CAUTION",
                                JOptionPane.INFORMATION_MESSAGE);
                        from.setText("");
                        to.setText("");
                    }
                }
                catch (NumberFormatException ep){
                    JOptionPane.showMessageDialog(
                            diagramFrame,
                            "your inputs don't follow the pattern.\n[Caution: use of extensions (m,u,n,...) is not allowed.]",
                            "CAUTION",
                            JOptionPane.INFORMATION_MESSAGE);
                    from.setText("");
                    to.setText("");
                }
            }
        });
    }
    public static String toStringForDouble (Double d){
        String sum ="";
        if(d<0&&d>-1)
            sum+="-";
        int a = (int) (d/1);
        sum+=a;
        sum+=".";
        int b = (int)Math.abs((10000000*d-10000000*a)/1);
        if (b>1000000)
            sum+="";
        else if (b>100000)
            sum+="0";
        else if (b>10000)
            sum+="00";
        else if (b>1000)
            sum+="000";
        else if (b>100)
            sum+="0000";
        else if (b>10)
            sum+="00000";
        else if (b>0)
            sum+="000000";
        if(b%1000000==0)
            b/=1000000;
        if(b%100000==0)
            b/=100000;
        if(b%10000==0)
            b/=10000;
        if(b%1000==0)
            b/=1000;
        if(b%100==0)
            b/=100;
        if(b%10==0)
            b/=10;
        sum+=b;
        return sum;
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static void draw() {
        frame.setTitle("Solve & Drawing Circuits");
        frame.setSize(1700,1033);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setIconImage(new ImageIcon("icon2.JPG").getImage());
        Container container = frame.getContentPane();
        myJPanel.setBounds(0,0,1100,1000);
        container.setBackground(new Color(247, 247, 247));
        container.add(myJPanel);
        GroundPanel groundPanel = new GroundPanel();
        groundPanel.setBounds(0,985-50,1060,50);
        container.add(groundPanel);
        ArrayList<ArrayList<Element>> parallelElements = new ArrayList<ArrayList<Element>>();
        ArrayList<Element> firstArrayList = new ArrayList<Element>();
        firstArrayList.add(element.get(0));
        parallelElements.add(firstArrayList);
        for(int i=1;i<element.size();i++){
            int neshangar=-1;
            for(int j=0;j<parallelElements.size()&&neshangar==-1;j++){
                if((element.get(i).positiveNode.name==parallelElements.get(j).get(0).positiveNode.name&&
                        element.get(i).negativeNode.name==parallelElements.get(j).get(0).negativeNode.name)
                        ||
                        (element.get(i).negativeNode.name==parallelElements.get(j).get(0).positiveNode.name&&
                        element.get(i).positiveNode.name==parallelElements.get(j).get(0).negativeNode.name))
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
            if(parallelElements.get(i).get(0).positiveNode.name==0){
                Point point = new Point(parallelElements.get(i).get(0).negativeNode.name);
                xForDrawing = point.x;
                yForDrawing = point.y;
                distanceBetweenTwoPoints = 105+5*170-point.y;
                typeOfDrawing = "up to down";
            }
            else if(parallelElements.get(i).get(0).negativeNode.name==0){
                Point point = new Point(parallelElements.get(i).get(0).positiveNode.name);
                xForDrawing = point.x;
                yForDrawing = point.y;
                distanceBetweenTwoPoints = 105+5*170-point.y;
                typeOfDrawing = "up to down";
            }
            else{
                Point point1 = new Point(parallelElements.get(i).get(0).negativeNode.name);
                Point point2 = new Point(parallelElements.get(i).get(0).positiveNode.name);
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
                String name = parallelElements.get(i).get(0).name;
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
                    String name = parallelElements.get(i).get(j).name;
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
                    String name = parallelElements.get(i).get(j).name;
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
        frame.setLayout(null);
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
        forValue.setBounds(xForText,ertefa/2+10+y,20,30);
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
        forValue.setBounds(tool/2+10+x,yForText,20,30);
        forValue.add(valueInGraphic);
        forValue.setBackground(new Color(247, 247, 247));
        frame.getContentPane().add(forValue);
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    static Information giveInformationForDrawing(int xForDrawing,int yForDrawing,Element element,String typeOfDrawing) {
    String typeOfElementInGraphics="";
    String value="";
    if(typeOfDrawing.equals("right to left")) {
        String rightToLeftOrLeftToRight;
        Point positiveNode = new Point(element.positiveNode.name);
        if (positiveNode.x==xForDrawing)
            rightToLeftOrLeftToRight="+";
        else
            rightToLeftOrLeftToRight="-";
        if (element.name.charAt(0)=='R'){
            value=element.gValue;
            typeOfElementInGraphics = "right and left resistance";
        }
        if (element.name.charAt(0)=='L'){
            value=element.gValue;
            typeOfElementInGraphics = "right and left inductance";
        }
        if (element.name.charAt(0)=='C'){
            value=element.gValue;
            typeOfElementInGraphics = "right and left capacitance";
        }
        if (element.name.charAt(0)=='D'){
            if(rightToLeftOrLeftToRight.equals("+"))
                typeOfElementInGraphics = "right diode";
            if(rightToLeftOrLeftToRight.equals("-"))
                typeOfElementInGraphics = "left diode";
        }
        if (element.name.charAt(0)=='V'){
            if(!element.isAC){
                double d = element.v+element.u*Math.sin(element.phase);
                int a = (int) (d / 0.01);
                value = Double.toString(a*0.01);
                if(rightToLeftOrLeftToRight.equals("+"))
                    typeOfElementInGraphics = "right voltage dc source";
                if(rightToLeftOrLeftToRight.equals("-"))
                    typeOfElementInGraphics = "left voltage dc source";
            }
            else{
                double d = 2*Math.PI*element.frequency;
                int a = (int) (d / 0.01);
                String omega = Double.toString(a*0.01);
                //value = element.v+"+"+element.u+"*sin("+omega+"t+"+element.phase+")";
                if(element.v!=0)
                    value = (int)element.v+"+"+(int)element.u+"*sin";
                else
                    value = (int)element.u+"*sin";
                typeOfElementInGraphics = "right and left ac source";
            }
        }
        if (element.name.charAt(0)=='E'){
            //value = element.k+"*(V("+element.nodeA.name+")-V("+element.nodeB.name+"))";
            value = (int)element.k+"*v";
            if(rightToLeftOrLeftToRight.equals("+"))
                typeOfElementInGraphics = "right controlled voltage source";
            if(rightToLeftOrLeftToRight.equals("-"))
                typeOfElementInGraphics = "left controlled voltage source";
        }
        if (element.name.charAt(0)=='H'){
            //value = element.k+"*I("+element.ele.name+")";
            value = (int)element.k+"*i";
            if(rightToLeftOrLeftToRight.equals("+"))
                typeOfElementInGraphics = "right controlled voltage source";
            if(rightToLeftOrLeftToRight.equals("-"))
                typeOfElementInGraphics = "left controlled voltage source";
        }
        if (element.name.charAt(0)=='I'){
            if(!element.isAC){
                double d = element.v+element.u*Math.sin(element.phase);
                int a = (int) (d / 0.01);
                value = Double.toString(a*0.01);
                if(rightToLeftOrLeftToRight.equals("+"))
                    typeOfElementInGraphics = "right current dc source";
                if(rightToLeftOrLeftToRight.equals("-"))
                    typeOfElementInGraphics = "left current dc source";
            }
            else{
                double d = 2*Math.PI*element.frequency;
                int a = (int) (d / 0.01);
                String omega = Double.toString(a*0.01);
                //value = element.v+"+"+element.u+"*sin("+omega+"t+"+element.phase+")";
                if(element.v!=0)
                    value = (int)element.v+"+"+(int)element.u+"*sin";
                else
                    value = (int)element.u+"*sin";
                typeOfElementInGraphics = "right and left ac source";
            }
        }
        if (element.name.charAt(0)=='G'){
            //value = element.k+"*(V("+element.nodeA.name+")-V("+element.nodeB.name+"))";
            value = (int)element.k+"*v";
            if(rightToLeftOrLeftToRight.equals("+"))
                typeOfElementInGraphics = "right controlled current source";
            if(rightToLeftOrLeftToRight.equals("-"))
                typeOfElementInGraphics = "left controlled current source";
        }
        if (element.name.charAt(0)=='F'){
            //value = element.k+"*I("+element.ele.name+")";
            value = (int)element.k+"*i";
            if(rightToLeftOrLeftToRight.equals("+"))
                typeOfElementInGraphics = "right controlled current source";
            if(rightToLeftOrLeftToRight.equals("-"))
                typeOfElementInGraphics = "left controlled current source";
        }
    }
    if(typeOfDrawing.equals("up to down")) {
        String upToDownOrDownToUp;
        Point positivePoint = new Point(element.positiveNode.name);
        if(positivePoint.y == yForDrawing)
            upToDownOrDownToUp="+";
        else
            upToDownOrDownToUp="-";
        if (element.name.charAt(0)=='R'){
            value=element.gValue;
            typeOfElementInGraphics = "up and down resistance";
        }
        if (element.name.charAt(0)=='L'){
            value=element.gValue;
            typeOfElementInGraphics = "up and down inductance";
        }
        if (element.name.charAt(0)=='C'){
            value=element.gValue;
            typeOfElementInGraphics = "up and down capacitance";
        }
        if (element.name.charAt(0)=='D'){
            if(upToDownOrDownToUp.equals("+"))
                typeOfElementInGraphics = "up diode";
            if(upToDownOrDownToUp.equals("-"))
                typeOfElementInGraphics = "down diode";
        }
        if (element.name.charAt(0)=='V'){
            if(!element.isAC){
                double d = element.v+element.u*Math.sin(element.phase);
                int a = (int) (d / 0.01);
                value = Double.toString(a*0.01);
                if(upToDownOrDownToUp.equals("+"))
                    typeOfElementInGraphics = "up voltage dc source";
                if(upToDownOrDownToUp.equals("-"))
                    typeOfElementInGraphics = "down voltage dc source";
            }
            else{
                double d = 2*Math.PI*element.frequency;
                int a = (int) (d / 0.01);
                String omega = Double.toString(a*0.01);
                //value = element.v+"+"+element.u+"*sin("+omega+"t+"+element.phase+")";
                if(element.v!=0)
                    value = (int)element.v+"+"+(int)element.u+"*sin";
                else
                    value = (int)element.u+"*sin";
                typeOfElementInGraphics = "up and down ac source";
            }
        }
        if (element.name.charAt(0)=='E'){
            //value = element.k+"*(V("+element.nodeA.name+")-V("+element.nodeB.name+"))";
            value = (int)element.k+"*v";
            if(upToDownOrDownToUp.equals("+"))
                typeOfElementInGraphics = "up controlled voltage source";
            if(upToDownOrDownToUp.equals("-"))
                typeOfElementInGraphics = "down controlled voltage source";
        }
        if (element.name.charAt(0)=='H'){
            //value = element.k+"*I("+element.ele.name+")";
            value = (int)element.k+"*i";
            if(upToDownOrDownToUp.equals("+"))
                typeOfElementInGraphics = "up controlled voltage source";
            if(upToDownOrDownToUp.equals("-"))
                typeOfElementInGraphics = "down controlled voltage source";
        }
        if (element.name.charAt(0)=='I'){
            if(!element.isAC){
                double d = element.v+element.u*Math.sin(element.phase);
                int a = (int) (d / 0.01);
                value = Double.toString(a*0.01);
                if(upToDownOrDownToUp.equals("+"))
                    typeOfElementInGraphics = "up current dc source";
                if(upToDownOrDownToUp.equals("-"))
                    typeOfElementInGraphics = "down current dc source";
            }
            else{
                double d = 2*Math.PI*element.frequency;
                int a = (int) (d / 0.01);
                String omega = Double.toString(a*0.01);
                //value = element.v+"+"+element.u+"*sin("+omega+"t+"+element.phase+")";
                if(element.v!=0)
                    value = (int)element.v+"+"+(int)element.u+"*sin";
                else
                    value = (int)element.u+"*sin";
                typeOfElementInGraphics = "up and down ac source";
            }
        }
        if (element.name.charAt(0)=='G'){
            //value = element.k+"*(V("+element.nodeA.name+")-V("+element.nodeB.name+"))";
            value = (int)element.k+"*v";
            if(upToDownOrDownToUp.equals("+"))
                typeOfElementInGraphics = "up controlled current source";
            if(upToDownOrDownToUp.equals("-"))
                typeOfElementInGraphics = "down controlled current source";
        }
        if (element.name.charAt(0)=='F'){
            //value = element.k+"*I("+element.ele.name+")";
            value = (int)element.k+"*i";
            if(upToDownOrDownToUp.equals("+"))
                typeOfElementInGraphics = "up controlled current source";
            if(upToDownOrDownToUp.equals("-"))
                typeOfElementInGraphics = "down controlled current source";
        }
    }
    return new Information(value,typeOfElementInGraphics);
    }
}
