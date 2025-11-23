import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;;

public class Jraffic extends JFrame {

	public Jraffic() {
		setTitle("Jraffic");
		setSize(800, 800);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		add(new RoadPanel());
	}

	class RoadPanel extends JPanel implements KeyListener {
		private boolean northSouthGreen = true;
		private int timerCount = 0;
		private final int LIGHT_DURATION = 180;
		private List<Vehicle> Vehicles;

		public RoadPanel() {
			setFocusable(true);
			requestFocusInWindow();
			addKeyListener(this);
			Timer timer = new Timer(16, new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					timerCount++;
					if (timerCount >= LIGHT_DURATION) {
						northSouthGreen = !northSouthGreen;
						timerCount = 0;
					}
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
			drawTrafficLights(g2d);
		}

		private void drawIntersection(Graphics2D g2d) {
			int roadWidth = 100;
			int roadLength = 400;
			g2d.setColor(Color.LIGHT_GRAY);
			g2d.fillRect(350, 0, roadWidth, roadLength);
			g2d.fillRect(350, 800 - roadLength, roadWidth, roadLength);
			g2d.fillRect(0, 350, roadLength, roadWidth);
			g2d.fillRect(800 - roadLength, 350, roadLength, roadWidth);
		}

		private void drawLandMarkings(Graphics2D g2d) {
			int roadWidth = 100;
			int dashLength = 20;
			int gapLength = 10;
			g2d.setColor(Color.WHITE);
			float[] dashPattern = { dashLength, gapLength };
			g2d.setStroke(new BasicStroke(3, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, dashPattern, 0));
			int verticalCenter = 350 + roadWidth / 2;
			g2d.drawLine(verticalCenter, 0, verticalCenter, 350);
			g2d.drawLine(verticalCenter, 450, verticalCenter, 800);
			int horizontalCenter = 350 + roadWidth / 2;
			g2d.drawLine(0, horizontalCenter, 350, horizontalCenter);
			g2d.drawLine(450, horizontalCenter, 800, horizontalCenter);
		}

		private void drawTrafficLights(Graphics2D g2d) {
			int lightSize = 20;
			g2d.setColor(Color.BLACK);
			drawLight(g2d, 330, 330, lightSize, northSouthGreen ? Color.GREEN : Color.RED);
			drawLight(g2d, 450, 450, lightSize, northSouthGreen ? Color.GREEN : Color.RED);
			drawLight(g2d, 450, 330, lightSize, northSouthGreen ? Color.RED : Color.GREEN);
			drawLight(g2d, 330, 450, lightSize, northSouthGreen ? Color.RED : Color.GREEN);
		}

		private void drawLight(Graphics2D g2d, int x, int y, int size, Color color) {
			g2d.setColor(Color.BLACK);
			g2d.fillRect(x, y, size, size);
			g2d.setColor(color);
			g2d.fillOval(x + 2, y + 2, size - 4, size - 4);
			g2d.setColor(new Color(color.getRed(), color.getGreen(), color.getBlue(), 100));
			g2d.fillOval(x - 5, y - 5, size + 10, size + 10);
		}

		public void keyPressed(KeyEvent e) {
			int keyCode = e.getKeyCode();

			switch (keyCode) {
				case KeyEvent.VK_ESCAPE:
					System.exit(0);
					break;
				case KeyEvent.VK_UP:
					break;
				case KeyEvent.VK_DOWN:
					break;
				case KeyEvent.VK_LEFT:
					break;
				case KeyEvent.VK_RIGHT:
					break;
				case KeyEvent.VK_R:
					break;
			}
		}

		@Override
		public void keyTyped(KeyEvent e) {
		}

		@Override
		public void keyReleased(KeyEvent e) {
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