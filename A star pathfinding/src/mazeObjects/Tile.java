package mazeObjects;

import java.awt.*;
import java.util.ArrayList;

public class Tile implements Comparable<Tile>{ // Comparable so that they can be ordered by cost.
    TileType type;
    Maze maze;

    Double totalCost = 0.0;
    Double travelledCost = 0.0;
    Point location;

    ArrayList<Point> movesToGetToMe = new ArrayList<>();

    public Tile(TileType type,  Point location, Maze containedIn){
        this.type = type;
        this.location = location;
        this.maze = containedIn;
    }

    @Override
    public int compareTo(Tile o) {
        return (int)(Math.round(this.totalCost) - Math.round(o.totalCost));
    }
}
