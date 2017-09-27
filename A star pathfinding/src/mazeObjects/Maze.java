package mazeObjects;

import main.MazeUtils;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Maze {
    public Tile[][] tiles;
    public Point start;
    public Point end;
    private ArrayList<Point> solutions;

    private final Point size = new Point(30,30);
    private final Point halfSize = new Point(size.x / 2, size.y / 2);

    public void solve(){
        Boolean foundCorrectRoute = false;

        ArrayList<Tile> openTiles = new ArrayList<>();
        openTiles.add(this.tiles[this.start.x][this.start.y]);

        while (!foundCorrectRoute){

            Tile[] tilesToSort = openTiles.toArray(new Tile[0]); // Will become an ordered list of the open tiles.

            Arrays.sort(tilesToSort);

            Tile cheapestTile = tilesToSort[0];
            // Find cheapest, expand it then remove it from open tiles.

            for(Tile movableTile: MazeUtils.getNearbyTiles(this,cheapestTile.location)){ // Get's tiles next to cheapest tiles. This doesn't count diagonals.

                if(movableTile.type == TileType.END){
                    foundCorrectRoute = true;
                    System.out.println("End found.");
                    cheapestTile.movesToGetToMe.add(cheapestTile.location);
                    this.solutions = cheapestTile.movesToGetToMe;
                }

                if((!openTiles.contains(movableTile))  && // If open tiles doesn't already have the tile found in it.
                        movableTile.type != TileType.WALL && movableTile.type != TileType.SEARCHED){ // And it is able to be moved in.
                    openTiles.add(movableTile);
                    movableTile.travelledCost = cheapestTile.travelledCost + 1;

                    ArrayList<Point> pointsToMeCloned = (ArrayList<Point>) cheapestTile.movesToGetToMe.clone();
                    pointsToMeCloned.add(cheapestTile.location);
                    movableTile.movesToGetToMe = pointsToMeCloned;

                    Double guessMultiplier = 2.0; // This can be changed to suit the program for different mazes.
                    // If guessMultiplier is a low value, the system is more suited for traditional mazes, closed single file corridors.
                    // If guessMultiplier is high, the system is suited for open areas
                    Double guessCost = Math.abs(movableTile.location.x - movableTile.maze.end.x) + Math.abs(movableTile.location.y - movableTile.maze.end.y) * guessMultiplier;


                    movableTile.totalCost = movableTile.travelledCost + guessCost;
                }
            }

            if(cheapestTile.type != TileType.START){ // Ensures the program doesn't turn the start square blue.
                cheapestTile.type = TileType.SEARCHED;
            }

            openTiles.remove(cheapestTile); // Tile has been checked and isn't in the open tiles anymore.
        }
    }

    public void render(Graphics2D g){
        for(int y = 0; y < this.tiles[0].length; y ++){ // For all the tiles.
            for(int x = 0; x < this.tiles.length; x ++){
                Tile tile = this.tiles[x][y];

                g.setColor(tile.type.color);
                g.fillRect(x * size.x, y * size.y, size.x, size.y);
            }
        }
        g.setStroke(new BasicStroke(10)); // Width of the line which shows the shortest route path.

        Point previousPoint = null;
        for(int index = 0; index < this.solutions.size(); index ++){ // For each point in list of points showing path called solutions
            Point p = this.solutions.get(index);
            if(previousPoint != null){

                // This is used to make the line a gradient of colour.
                Integer greenFactor =  (int) (((double)index / (double)this.solutions.size()) * 255);
                g.setColor(new Color(0,greenFactor,0));

                // Draw a line from each point to the point previous.
                g.drawLine(previousPoint.x * size.x + halfSize.x, previousPoint.y * size.y + halfSize.y, p.x * size.x + halfSize.x,p.y * size.y + halfSize.y);
            }

            previousPoint = p;
        }
    }
}
