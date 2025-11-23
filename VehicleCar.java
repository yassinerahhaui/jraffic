
import java.awt.Color;
import java.util.Random;

public class VehicleCar implements Vehicle {
    private int x;
    private int y;
    private int size = 40;

    public int getSize() {
        return size;
    }

    private Destination destination;
    private Direction direction;
    private Color color;

    public VehicleCar(Direction dir) {
        this.direction = dir;
        Random random = new Random();
        int routeChoice = random.nextInt(3);
        switch (routeChoice) {
            case 1:
                this.destination = Destination.Straight;
                this.color = Color.CYAN;
                break;
            case 2:
                this.destination = Destination.Right;
                this.color = Color.YELLOW;
                break;
            default:
                this.destination = Destination.Left;
                this.color = Color.RED;
        }

        switch (this.direction) {
            case NORTH:
                setX(405);
                setY(800);
                break;
            case EAST:
                setX(0);
                setY(405);
                break;
            case SOUTH:
                setX(355);
                setY(0);
                break;
            case WEST:
                setX(800);
                setY(355);
                break;
            default:
                break;
        }
    }

    public void updatePosition() {
        switch (this.direction) {
            case NORTH:
                setX(405);
                setY(getY() - 1);
                break;
            case EAST:
                setX(getX() + 1);
                setY(405);
                break;
            case SOUTH:
                setX(355);
                setY(getY() + 1);
                break;
            case WEST:
                setX(getX() - 1);
                setY(355);
                break;
            default:
                break;
        }
    }

    public Color getColor() {
        return this.color;
    }

    @Override
    public int getX() {
        return this.x;
    }

    @Override
    public int getY() {
        return this.y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public Destination getDestination() {
        return this.destination;
    }

    @Override
    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    @Override
    public Direction getDirection() {
        return this.direction;
    }

}