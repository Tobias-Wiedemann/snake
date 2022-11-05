package player;

import java.awt.*;

public class DirectionPoint {
    private Point point;
    private Direction direction;
    public DirectionPoint(Point p, Direction d) {
        point = p;
        direction = d;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public Point getPoint() {
        return point;
    }

    public Direction getDirection() {
        return direction;
    }
}
