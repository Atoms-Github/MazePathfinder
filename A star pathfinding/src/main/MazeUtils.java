package main;

import mazeObjects.Maze;
import mazeObjects.Tile;

import java.awt.*;
import java.util.ArrayList;

public class MazeUtils {
    public static ArrayList<Tile> getNearbyTiles(Maze maze, Point location){
        ArrayList<Tile> tiles = new ArrayList<>();
        for(int yOffset = -1; yOffset <= 1; yOffset ++) {
            for (int xOffset = -1; xOffset <= 1; xOffset++) {
                if(Math.abs(yOffset) + Math.abs(xOffset) == 2){ // Discards this loop if the tile found is at a diagonal to the current tile.
                    continue;
                }
                tiles.add(maze.tiles[location.x + xOffset][location.y + yOffset]);
            }
        }
        return tiles;
    }
}
