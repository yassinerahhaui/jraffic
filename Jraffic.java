import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;
import java.util.ArrayList;

public class Jraffic extends JFrame {
	ArrayList<VehicleCar> cars = new ArrayList<>();

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
		private static int roadWidth = 100;
		private static int roadLength = 400;
		private int safeGape = 20;
		static int halfRoad = roadWidth / 2;
		static int a = roadLength - halfRoad;
		private static Rectangle stopArea = new Rectangle(a, a, roadWidth, roadWidth);

		public enum Intersection {
			bottomRight(roadLength, roadLength),
			topRight(roadLength, a),
			topLeft(a, a),
			bottomLeft(a, roadLength);

			static int count = Intersection.values().length;

			Point value;

			Intersection(int x, int y) {
				this.value = new Point(x, y);
			}

			static Intersection get(int index) {
				return Intersection.values()[index];
			}
		}

		private List<Vehicle> Vehicles;

		private Intersection getIntersection(Direction dir, Destination dest) {
			// System.out.println("direction: " + dir);
			// System.out.println("destination: " + dest);

			if (dest == Destination.Left) {
				return Intersection.get(Math.floorMod(dir.ordinal() + 1, Intersection.count));
			}

			if (dest == Destination.Right) {
				return Intersection.get(dir.ordinal());
			}

			return null;
		}

		private boolean shouldTurn(Vehicle v) {
			Destination dest = v.getDestination();
			if (dest == Destination.Straight)
				return false;

			Direction dir = v.getDirection();
			Intersection inter = getIntersection(dir, dest);
			Dimension d = new Dimension(halfRoad, halfRoad);
			Rectangle boundy = new Rectangle(v.getX(), v.getY(), v.getSize(), v.getSize());
			return new Rectangle(inter.value, d).contains(boundy);
		}

		private void changeDirection(Vehicle v) {
			Destination dest = v.getDestination();
			int current = v.getDirection().ordinal();
			if (!v.getDirectionChanged()) {
				switch (dest) {
					case Left:
						current++;
						break;
					case Right:
						current--;
						break;
					default:
						break;
				}
				v.setDirectionChanged();
			}
			current = Math.floorMod(current, Direction.count);
			v.setDirection(Direction.values()[current]);
		}

		public RoadPanel() {
			setBackground(new Color(0, 64, 0));

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
			g2d.setColor(Color.RED);
			int i = 0;
			for (VehicleCar car : cars) {
				i++;
				// draw intersection rect
				g.setColor(Color.RED);
				// Intersection inter = getIntersection(car.getDirection(),
				// car.getDestination());
				// if (inter != null) {
				// Point p = inter.value;
				// g.drawRect(p.x, p.y, halfRoad, halfRoad);
				// }

				g2d.draw(stopArea);
				g.setColor(car.getColor());
				g.fillRect(car.getX(), car.getY(), car.getSize(), car.getSize());

				if (shouldTurn(car))
					changeDirection(car);

				car.updatePosition();
			}
			g.setColor(Color.WHITE);
		}

		private void drawIntersection(Graphics2D g2d) {
			g2d.setColor(Color.LIGHT_GRAY);
			g2d.fillRect(350, 0, roadWidth, roadLength);
			g2d.fillRect(350, 800 - roadLength, roadWidth, roadLength);
			g2d.fillRect(0, 350, roadLength, roadWidth);
			g2d.fillRect(800 - roadLength, 350, roadLength, roadWidth);

			// g2d.setColor(Color.RED);
			// Dimension d = new Dimension(halfRoad, halfRoad);
			// Rectangle rect = new Rectangle(Intersection.topRight.value, d);

			// g2d.draw(rect);
		}

		private void drawLandMarkings(Graphics2D g2d) {
			int roadWidth = 100;
			int dashLength = 20;
			int gapLength = 10;
			g2d.setColor(Color.WHITE);
			float[] dashPattern = { dashLength, gapLength };
			g2d.setStroke(new BasicStroke(3, 0, BasicStroke.JOIN_BEVEL, 0, dashPattern, 0));
			int Center = 350 + roadWidth / 2;
			g2d.drawLine(Center, 0, Center, 350);
			g2d.drawLine(Center, 450, Center, 800);
			g2d.drawLine(0, Center, 350, Center);
			g2d.drawLine(450, Center, 800, Center);
		}

		private void drawTrafficLights(Graphics2D g2d) {
			int lightSize = 20;
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
		}

		public void keyPressed(KeyEvent e) {
			int keyCode = e.getKeyCode();
			if (keyCode == KeyEvent.VK_UP || keyCode == KeyEvent.VK_DOWN
					|| keyCode == KeyEvent.VK_LEFT || keyCode == KeyEvent.VK_RIGHT) {
				Long last = (Long) this.getClientProperty("lastArrowTime");
				long now = System.currentTimeMillis();
				if (last != null && now - last < 1000) {
					Toolkit.getDefaultToolkit().beep(); // optional feedback
					return;
				}
				this.putClientProperty("lastArrowTime", now);
			}
			switch (keyCode) {
				case KeyEvent.VK_ESCAPE:
					System.exit(0);
					break;
				case KeyEvent.VK_UP:
					cars.add(new VehicleCar(Direction.NORTH));
					break;
				case KeyEvent.VK_DOWN:
					cars.add(new VehicleCar(Direction.SOUTH));
					break;
				case KeyEvent.VK_LEFT:
					cars.add(new VehicleCar(Direction.EAST));
					break;
				case KeyEvent.VK_RIGHT:
					cars.add(new VehicleCar(Direction.WEST));
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