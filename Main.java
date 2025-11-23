import javax.swing.*;
import java.awt.*;
import traffic_package.*;
// import java.awt.event.ActionEvent;
// import java.awt.event.ActionListener;

public class Main {

    public Main() {
        JFrame frame = new JFrame("Traffic Road");
        // close the program whene window closing.
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setSize(800, 800); // set window size to width-> 800px && height-> 800px.
        frame.setBackground(Color.BLACK);
        
        TrafficCanvas canvas = new TrafficCanvas();

        // frame.add(canvas);

        frame.setVisible(true);
    }

    public static void main(String[] args) {
        System.err.println("Entry point");
    }
}
