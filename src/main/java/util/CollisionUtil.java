package util;

import data.*;

public class CollisionUtil {

    public static boolean check2DCollision(Bee bee1, Bee bee2) {

        double xDif = bee1.toCartesianPoint3D().getX() - bee2.toCartesianPoint3D().getX();
        double yDif = bee1.toCartesianPoint3D().getY() - bee2.toCartesianPoint3D().getY();

        double distanceSquared = xDif * xDif + yDif * yDif;
        boolean collision = distanceSquared < (100 + 100) * (100 + 100);

        return collision;

    }

}
