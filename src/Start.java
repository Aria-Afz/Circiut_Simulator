import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;

public class Start {
    static JFrame frame = new JFrame();
    static String addressOfTextFile = "Circuit.txt";
    public static void main (String[] args) throws FileNotFoundException {
        frame.setTitle("Graphic drawing of the circuit");
        frame.setSize(1520,1033);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setIconImage(new ImageIcon("icon.png").getImage());
        Container container = frame.getContentPane();
        Main.main(args);
    }
}
