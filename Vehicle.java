import java.awt.Point;

public interface Vehicle {

    int getX();

    int getSize();

    int getY();

    Destination getDestination();

    void setDirection(Direction direction);

    boolean getDirectionChanged();

    void setDirectionChanged();

    Direction getDirection();

    Point nextPosition();
}
