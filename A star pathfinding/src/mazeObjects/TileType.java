package mazeObjects;

import java.awt.*;

public enum TileType {

    PATH(Color.WHITE), WALL(Color.BLACK), START(Color.GREEN), END(Color.RED), SEARCHED(Color.BLUE);

    TileType(Color color){
        this.color = color;
    }

    public Color color;
}
