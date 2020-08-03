import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Start {
    static JFrame frame = DrawCircuit.frame;
    static Container container = frame.getContentPane();
    static File addressOfTextFile = new File ("Circuit.txt");
    static JButton run = new JButton("RUN");
    static JButton load = new JButton("LOAD");
    static JTextArea textArea = new JTextArea();
    public static void main (String[] args) throws FileNotFoundException {
        frame.setTitle("Graphic drawing of the circuit");
        frame.setSize(1700,1033);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setIconImage(new ImageIcon("icon.png").getImage());
        Container container = frame.getContentPane();
        frame.setLayout(null);
        frame.setVisible(true);
        load.setBackground(Color.LIGHT_GRAY);
        load.setBounds(1115,10,260,30);
        run.setBackground(Color.LIGHT_GRAY);
        run.setBounds(1385,10,260,30);
        JTextArea textArea = new JTextArea();
        //textArea.setBackground(new Color(253, 246, 202));
        textArea.setBounds(1115,50,1385+260-1115,985-50);
        Border border = BorderFactory.createLineBorder(Color.BLACK,2,true);
        textArea.setBorder(border);
        textArea.setText("\n");
        container.add(textArea);
        primaryThings();
        //Main.main(args);
        load.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FileSystemView fsv;
                fsv = FileSystemView.getFileSystemView();
                File fileO = new File("C:\\Users\\Lenovo\\Desktop");
                JFileChooser fileChooser = new JFileChooser(fileO,fsv);
                int response = fileChooser.showOpenDialog(frame);
                if (response == JFileChooser.APPROVE_OPTION){
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
                    catch (FileNotFoundException ex) {
                        //
                    }
                    textArea.setText(text);
                }
            }
        });
        run.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                container.removeAll();
                primaryThings();
                container.add(textArea);
                try {
                    FileWriter fileWriter = new FileWriter(addressOfTextFile);
                    fileWriter.write(textArea.getText());
                    fileWriter.close();
                    Main.main(args);
                }
                catch (IOException ex) {
                    //ex.printStackTrace();
                }
            }
        });
    }
    static void primaryThings(){
        MyJPanel myJPanel = new MyJPanel();
        myJPanel.setBounds(0,0,1100,1000);
        container.setBackground(new Color(247, 247, 247));
        container.add(myJPanel);
        container.add(load);
        container.add(run);
    }
}


