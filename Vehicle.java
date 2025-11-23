import java.awt.Color;

public interface Vehicle {
    int getX();

    int getY();

    Color getColor();

    void setDirection(Direction direction);

    void getDirection(Direction direction);
}
