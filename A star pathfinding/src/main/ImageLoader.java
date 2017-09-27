package main;

import mazeObjects.Maze;
import mazeObjects.Tile;
import mazeObjects.TileType;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageLoader {
    Maze loadMaze(String path){
        try {
            BufferedImage image = ImageIO.read(new File(path));
            Tile[][] output = new Tile[image.getWidth()][image.getHeight()];
            Maze m = new Maze();

            for(int y = 0; y < image.getHeight(); y ++){
                for(int x = 0; x < image.getWidth(); x ++){
                    Integer rgb = image.getRGB(x,y);

                    TileType type = getTileBasedOnColour(rgb);

                    if(type == TileType.START) m.start = new Point(x,y);
                    if(type == TileType.END) m.end = new Point(x,y);

                    output[x][y] = new Tile(type, new Point(x,y),m); // Places the current pixel in the array storing every tile.
                }
            }

            m.tiles = output;


            return m;
        } catch (IOException e) {
            System.out.println("Problem reading image.");
            e.printStackTrace();
        }
        return null;
    }
    private TileType getTileBasedOnColour(Integer rgb){
        switch(rgb){
            case 0xffffffff: // Checks for white pixels (255,255,255,255)
                return TileType.PATH;
            case 0xff000000: // Checks for black pixels (255,0,0,0)
                return TileType.WALL;
            case 0xff00ff00: // Checks for green pixels (255,0,255,0)
                return TileType.START;
            case 0xffff0000: // Checks for red pixels (255,255,0,0)
                return TileType.END;

            default:
                System.out.println("Pixel found doesn't match any tile type.");
                System.exit(1);
                return null;
        }
    }
}