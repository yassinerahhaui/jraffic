import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Jraffic extends JFrame {

    public Jraffic() {
        setTitle("Jraffic");
        setSize(800, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        add(new RoadPanel());
    }

    class RoadPanel extends JPanel {

        public RoadPanel() {
            Timer timer = new Timer(16, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    repaint();
                }
            });
            timer.start();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;

            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setStroke(new BasicStroke(2));
            drawIntersection(g2d);
            drawLandMarkings(g2d);
        }

        private void drawIntersection(Graphics2D g2d) {
            int roadWidth = 100;
            int roadLength = 400;
            g2d.setColor(Color.LIGHT_GRAY);
            g2d.fillRect(350, 0, roadWidth, roadLength);  // North
            g2d.fillRect(350, 800 - roadLength, roadWidth, roadLength); // South
            g2d.fillRect(0, 350, roadLength, roadWidth);  // West
            g2d.fillRect(800 - roadLength, 350, roadLength, roadWidth); // East
        }

        private void drawLandMarkings(Graphics2D g2d) {
            int laneWidth = 10;
            g2d.setColor(Color.GRAY);
            for (int i = 0; i < 5; i++) {
                g2d.fillRect(390 + i * laneWidth, 0, laneWidth, 400);  // Vertical
            }
			for (int i = 0; i < 5; i++) {
                g2d.fillRect(0, 390 + i * laneWidth, 400, laneWidth);  // Horizontal
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Jraffic().setVisible(true);
            }
        });
    }
}
